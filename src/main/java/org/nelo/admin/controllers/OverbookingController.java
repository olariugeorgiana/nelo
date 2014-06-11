package org.nelo.admin.controllers;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.nelo.dao.RegistrationDao;
import org.nelo.dao.ReservationDao;
import org.nelo.dao.RoomDao;
import org.nelo.dao.RoomImgDao;
import org.nelo.dao.abstractDao.DaoFilter;
import org.nelo.entities.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@Controller
@RequestMapping(value = "/admin")
public class OverbookingController {

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private RoomImgDao roomImgDao;

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private RegistrationDao registrationDao;

    @RequestMapping(value = "/overbooking", method = RequestMethod.GET)
    public String getOverbookingForm(ModelMap modelMap) {

        List<Room> virtualRooms = roomDao.getVirtualRooms();

        //daca nu am nici o camera virtuala in sistem, atunci se genereaza
        if (virtualRooms == null || virtualRooms != null && virtualRooms.size() == 0) {

            Calendar instance = Calendar.getInstance();
            instance.setTime(new Date());
            instance.set(Calendar.DATE, -30);
            final Date last30DaysStart = instance.getTime(); // acum 30 de zile

            DaoFilter filterRegistrations = new DaoFilter() {
                @Override
                public void bindCriteria(Criteria criteria) {
                    criteria.add(Restrictions.ge("startDate", last30DaysStart));
                }
            };

            int registrationCount = registrationDao.count(filterRegistrations);

            DaoFilter filterReservations = new DaoFilter() {
                @Override
                public void bindCriteria(Criteria criteria) {
                    criteria.add(Restrictions.ge("startDate", last30DaysStart));
                }
            };

            int reservationsCount = reservationDao.count(filterReservations);

            int roomNumber = roomDao.count(null);

            //calculul numarului de camere care se vor virtualiza
//            int roomToOverbook = (int) ((1 - ((double) (registrationCount / reservationsCount))) * roomNumber);
            int roomToOverbook = (int) ((1 - ((double) (800 / 1000.0))) * roomNumber);
            Random rand = new Random();

            //generarea random a camerelor de virtualizat
            Set<Integer> roomNumbers = new HashSet<Integer>();
            if (roomToOverbook > 0)
                while (true) {
                    int roomNumberToClone = rand.nextInt((roomNumber - 1) + 1) + 1;
                    roomNumbers.add(roomNumberToClone);

                    if (roomNumbers.size() == roomToOverbook)
                        break;
                }

            //se cloneaza camerele generate anterior
            for (int i = 0; i < roomNumbers.size(); i++) {

                Room byNumber = roomDao.getByNumber(new Room(String.valueOf(roomNumbers.toArray()[i])));

                Room clonedRoom = new Room();
                clonedRoom.setRoomNumber(byNumber.getRoomNumber());
                clonedRoom.setRoomType(byNumber.getRoomType());
                clonedRoom.setDescription(byNumber.getDescription());
                clonedRoom.setPrice(byNumber.getPrice());
                clonedRoom.setInternet(byNumber.getInternet());
                clonedRoom.setAirConditioner(byNumber.getAirConditioner());
                clonedRoom.setPhone(byNumber.getPhone());
                clonedRoom.setRoomView(byNumber.getRoomView());
                clonedRoom.setSmoking(byNumber.getSmoking());
                clonedRoom.setPets(byNumber.getPets());
                clonedRoom.setSafe(byNumber.getSafe());
                clonedRoom.setTeaCoffee(byNumber.getTeaCoffee());
                clonedRoom.setHydromassageTub(byNumber.getHydromassageTub());
                clonedRoom.setVirtualRoom(true);
                clonedRoom.setRoom(byNumber);
                clonedRoom.setDeleted(false);

                roomDao.save(clonedRoom);
            }

            virtualRooms = roomDao.getVirtualRooms();
        }

        modelMap.put("virtualRooms", virtualRooms);


        return "overbookingAdmin";
    }

    //3 zile - 1000 ms * 60 de sec/min * 60 de min/ora * 72 de ore
    @Scheduled(fixedDelay = 259200000)
    public void overbookingRunner() {

        List<Room> virtualRooms = roomDao.getVirtualRooms();

        if (virtualRooms != null && virtualRooms.size() > 0) {
            for (Room virtualRoom : virtualRooms) {
                virtualRoom.setDeleted(true);
                roomDao.save(virtualRoom);
            }
        }

        getOverbookingForm(new ModelMap());
    }

}
