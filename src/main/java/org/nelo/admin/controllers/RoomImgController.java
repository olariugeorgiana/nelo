package org.nelo.admin.controllers;

import org.nelo.dao.RoomImgDao;
import org.nelo.entities.RoomImg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * User: Georgiana
 */
@Controller
@RequestMapping(value = "/admin")
public class RoomImgController {

    @Autowired
    private RoomImgDao roomImgDao;

    @RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
    public String saveImgs(@ModelAttribute RoomImg roomImg, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "registerRoomAdmin";

        roomImgDao.save(roomImg);

        return "redirect:/admin/rooms";
    }
}
