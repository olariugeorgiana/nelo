package org.nelo.security;

import org.nelo.dao.UserAccountDao;
import org.nelo.entities.enums.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserAccountAuthentificationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserAccountDao userAccountDao;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            if (grantedAuthority.getAuthority().equals(Roles.USER.getType())) {
                redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/");
            } else if (grantedAuthority.getAuthority().equals(Roles.RECEPTIONIST.getType())) {
                redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/admin");
            } else if (grantedAuthority.getAuthority().equals(Roles.ADMINISTRATOR.getType())) {
                redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/admin");
            }

            break;
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userName = userDetails.getUsername();
        httpServletRequest.getSession().setAttribute("loggedUser", userAccountDao.getByEmail(userName));

    }

}
