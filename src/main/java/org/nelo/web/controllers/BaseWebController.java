package org.nelo.web.controllers;


import org.nelo.dao.RegistrationDao;
import org.nelo.dao.ReservationDao;
import org.nelo.dao.RoomDao;
import org.nelo.dao.RoomImgDao;
import org.nelo.entities.Registration;
import org.nelo.entities.Reservation;
import org.nelo.entities.Room;
import org.nelo.entities.UserAccount;
import org.nelo.entities.enums.RoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BaseWebController {

    @Autowired
    private RoomDao roomDao;
    @Autowired
    private RoomImgDao roomImgDao;
    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private RegistrationDao registrationDao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home1() {
        return "homeWeb";
    }

    @RequestMapping(value = "/facilities", method = RequestMethod.GET)
    public String getFacilities() {
        return "facilitiesWeb";
    }

    @RequestMapping(value = "/infoPage", method = RequestMethod.GET)
    public String getReservationWeb() {

        return "infoPage";
    }

    @RequestMapping(value = "/roomweb", method = RequestMethod.GET)
    public String getRoomWeb(ModelMap modelMap) {

        List<Room> list = new ArrayList<Room>();
        list.addAll(roomDao.getListByType(RoomType.SINGLE));
        list.addAll(roomDao.getListByType(RoomType.DOUBLE_TWIN));
        list.addAll(roomDao.getListByType(RoomType.MATRIMONIAL));


        for (Room room : list) {
            room.setImages(roomImgDao.getForRoom(room.getRoomId()));
        }

        modelMap.put("roomsList", list);

        return "roomWeb";
    }

    @RequestMapping(value = "/personalInfo", method = RequestMethod.GET)
    public String getPersonalInfo(HttpSession session, ModelMap modelMap) {

        List<Reservation> reservationList = reservationDao.getForUser(((UserAccount) session.getAttribute("loggedUser")).getEmail());
        List<Registration> registrationList = registrationDao.getForUser(((UserAccount) session.getAttribute("loggedUser")).getEmail());

        modelMap.put("reservationList", reservationList);
        modelMap.put("registrationList", registrationList);

        return "personalInfo";
    }

    @RequestMapping(value = "/deleteReservation", method = RequestMethod.GET)
    public String deleteReservations(@RequestParam Integer reservationId) {

        reservationDao.delete(reservationDao.getById(reservationId));

        return "redirect:/personalInfo";
    }

}
