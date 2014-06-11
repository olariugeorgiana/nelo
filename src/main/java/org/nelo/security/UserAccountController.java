package org.nelo.security;

import org.apache.commons.lang.RandomStringUtils;
import org.nelo.dao.UserAccountDao;
import org.nelo.entities.UserAccount;
import org.nelo.entities.enums.Roles;
import org.nelo.utils.EmailSender;
import org.nelo.web.validators.RegisterAccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserAccountController {

    @Autowired
    private RegisterAccountValidator registerAccountValidator;

    @Autowired
    private UserAccountDao userAccountDao;

    @Autowired
    private EmailSender emailSender;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(registerAccountValidator);
    }

    @RequestMapping(value = "/loginError", method = RequestMethod.GET)
    public String getLoginForm() {

        return "forward:/authentification";
    }

    @RequestMapping(value = "/authentification", method = RequestMethod.GET)
    public String getLoginAccountForm(ModelMap modelMap) {

        modelMap.put("userAccountRegister", new UserAccount());
        modelMap.put("userAccountLogin", new UserAccount());

        return "authentificationWeb";
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String submitRegisterAccountForm(@Validated @ModelAttribute("userAccountRegister") UserAccount userAccount, BindingResult bindingResult,ModelMap modelMap) {

        if (bindingResult.hasErrors()) {
            modelMap.put("userAccountLogin", new UserAccount());
            return "authentificationWeb";
        }

        userAccount.setUserPassword(RandomStringUtils.randomAlphanumeric(8));
        userAccount.setUserRole(Roles.USER.getType());
        userAccount = userAccountDao.save(userAccount);

        emailSender.setEmailForInternRegistration(userAccount);

        return "redirect:/";
    }

    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String getAccessDeniedPage() {

        return "accessDenied";
    }

    @RequestMapping(value = "/pageNotExist", method = RequestMethod.GET)
    public String getNotExistsPage() {

        return "pageNotExist";
    }

}
