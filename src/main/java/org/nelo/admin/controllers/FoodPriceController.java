package org.nelo.admin.controllers;

import org.nelo.dao.FoodPriceDao;
import org.nelo.entities.FoodPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/admin")
public class FoodPriceController {

    @Autowired
    private FoodPriceDao foodPriceDao;

    @RequestMapping(value = "/foodPrice", method = RequestMethod.GET)
    public String getFoodPriceForm(ModelMap modelMap) {
        FoodPrice foodPrice = foodPriceDao.getFoodPrice();

        if (foodPrice == null)
            foodPrice = new FoodPrice();

        modelMap.put("foodPrice", foodPrice);
        return "foodPriceAdmin";
    }

    @RequestMapping(value = "/foodPrice", method = RequestMethod.POST)
    public String saveFoodPriceForm(@ModelAttribute FoodPrice foodPrice) {
        foodPriceDao.save(foodPrice);
        return "redirect:/admin/foodPrice";
    }

}
