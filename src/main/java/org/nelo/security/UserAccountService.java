package org.nelo.security;

import org.nelo.dao.UserAccountDao;
import org.nelo.entities.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserAccountService implements UserDetailsService {

    @Autowired
    private UserAccountDao userAccountDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserAccount byEmail = userAccountDao.getByEmail(email);

        if (byEmail == null)
            throw new UsernameNotFoundException("User not found!");

        ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(byEmail.getUserRole()));

        UserDetails userDetails = new User(byEmail.getEmail(), byEmail.getUserPassword(), authorities);

        return userDetails;
    }
}
