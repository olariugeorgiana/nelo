package org.nelo.admin.controllers;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.nelo.admin.validators.RegisterRoomValidator;
import org.nelo.dao.RoomDao;
import org.nelo.dao.RoomImgDao;
import org.nelo.dao.abstractDao.DaoFilter;
import org.nelo.entities.Room;
import org.nelo.entities.RoomImg;
import org.nelo.entities.enums.RoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * User: georgiana.olariu@greensoft.com.ro
 * Date: 15.05.2014 20:54
 */
@Controller
@RequestMapping(value = "/admin")
public class RoomController {

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private RoomImgDao roomImgDao;

    @Autowired
    private RegisterRoomValidator roomValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(roomValidator);
    }

    @ModelAttribute(value = "roomTypeList")
    public List<RoomType> getRoomTypeList() {
        return RoomType._values;
    }

    @RequestMapping(value = "/room", method = RequestMethod.GET)
    public String getRoomForm(@RequestParam(value = "roomId", required = false) Integer roomId, ModelMap modelMap) throws Exception {

        Room room = new Room();

        if (roomId != null) {
            room = roomDao.getById(roomId);
            if (room == null)
                throw new Exception("Room does not exists!");
            room.setImages(roomImgDao.getForRoom(roomId));
        }

        modelMap.put("room", room);

        return "registerRoomAdmin";
    }

    @RequestMapping(value = "/room", method = RequestMethod.POST)
    public String submitRoomForm(@Validated @ModelAttribute Room room, BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors())
            return "registerRoomAdmin";

        room = roomDao.save(room);

        //salvarea pozelor pentru camera
        for (MultipartFile multipartFile : room.getFiles()) {
            if (multipartFile != null && !multipartFile.isEmpty()) {
                RoomImg roomImg = new RoomImg();
                roomImg.setImgName(multipartFile.getOriginalFilename());
                roomImg.setImgBytes(multipartFile.getBytes());
                roomImg.setRoom(room);
                roomImgDao.save(roomImg);
            }
        }

        //stergerea pozelor daca exista
        if (room.getRoomdIdsToDelete().length() > 0) {
            String[] split = room.getRoomdIdsToDelete().split(";");
            for (int i = 1; i < split.length; i++) {
                roomImgDao.delete(roomImgDao.getById(Integer.valueOf(split[i])));
            }

        }

        return "redirect:/admin/rooms";
    }

    @RequestMapping(value = "/rooms", method = RequestMethod.GET)
    public String getRooms(ModelMap modelMap) {
        DaoFilter filter = new DaoFilter() {
            @Override
            public void bindCriteria(Criteria criteria) {
                criteria.add(Restrictions.eq("virtualRoom", false));
                criteria.add(Restrictions.eq("deleted", false));
                criteria.addOrder(Order.asc("roomNumber"));
            }
        };
        modelMap.put("roomsList", roomDao.getList(filter));
        return "roomsListAdmin";
    }

    @RequestMapping(value = "/deleteRoom", method = RequestMethod.GET)
    public String deleteReservations(@RequestParam Integer roomId) {

        Room byId = roomDao.getById(roomId);
        byId.setDeleted(true);
        roomDao.save(byId);

        return "redirect:/admin/rooms";
    }

}
