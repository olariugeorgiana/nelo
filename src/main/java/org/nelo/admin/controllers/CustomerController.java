package org.nelo.admin.controllers;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.nelo.dao.UserAccountDao;
import org.nelo.dao.abstractDao.DaoFilter;
import org.nelo.entities.enums.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User: georgiana.olariu@greensoft.com.ro
 * Date: 15.05.2014 21:16
 */
@Controller
@RequestMapping(value = "/admin")
public class CustomerController {

    @Autowired
    private UserAccountDao userAccountDao;

    @RequestMapping(value = "customers", method = RequestMethod.GET)
    public String getCustomersList(ModelMap modelMap) {
        DaoFilter filter = new DaoFilter() {
            @Override
            public void bindCriteria(Criteria criteria) {
                criteria.add(Restrictions.eq("userRole", Roles.USER.getType()));
            }
        };
        modelMap.put("customersList", userAccountDao.getList(filter));
        return "customerListAdmin";
    }
}
