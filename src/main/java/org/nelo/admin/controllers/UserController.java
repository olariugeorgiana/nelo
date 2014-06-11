package org.nelo.admin.controllers;

import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.nelo.admin.validators.RegisterUserValidator;
import org.nelo.dao.UserAccountDao;
import org.nelo.dao.abstractDao.DaoFilter;
import org.nelo.entities.UserAccount;
import org.nelo.entities.enums.Roles;
import org.nelo.utils.EmailSender;
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

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * User: olariu.georgiana@gmail.coom
 * 6/3/14 9:20 PM
 */
@Controller
@RequestMapping(value = "/admin")
public class UserController {

    @Autowired
    private UserAccountDao userAccountDao;
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private RegisterUserValidator registerUserValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(registerUserValidator);
    }

    @ModelAttribute(value = "userRoleList")
    public List<Roles> getRoomTypeList() {
        return Arrays.asList(Roles.RECEPTIONIST, Roles.ADMINISTRATOR);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getUser(ModelMap modelMap) {

        UserAccount userAccount = new UserAccount();

        modelMap.put("userAccount", userAccount);

        return "userRegistrationAdmin";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String submitUser(@Validated @ModelAttribute UserAccount userAccount, BindingResult bindingResult, ModelMap modelMap) throws IOException {

        if (bindingResult.hasErrors())
            return "userRegistrationAdmin";

        userAccount.setUserPassword(RandomStringUtils.randomAlphanumeric(8));
        userAccount = userAccountDao.save(userAccount);

        emailSender.setEmailForInternRegistration(userAccount);

        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getUsers(ModelMap modelMap) {
        DaoFilter filter = new DaoFilter() {
            @Override
            public void bindCriteria(Criteria criteria) {
                criteria.add(Restrictions.disjunction().add(Restrictions.eq("userRole", Roles.RECEPTIONIST.getType())).add(Restrictions.eq("userRole", Roles.ADMINISTRATOR.getType())));
            }
        };
        modelMap.put("usersList", userAccountDao.getList(filter));
        return "userListAdmin";
    }

}
