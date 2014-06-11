package org.nelo.admin.controllers;

import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.nelo.admin.validators.ReservationValidator;
import org.nelo.dao.FoodPriceDao;
import org.nelo.dao.ReservationDao;
import org.nelo.dao.RoomDao;
import org.nelo.dao.UserAccountDao;
import org.nelo.dao.abstractDao.DaoFilter;
import org.nelo.entities.*;
import org.nelo.entities.enums.Roles;
import org.nelo.utils.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * User: olariu.georgiana@gmail.coom
 * 6/1/14 4:24 PM
 */
@Controller
@RequestMapping(value = "/admin")
public class ReservationsController {

    @Autowired
    private ReservationValidator reservationValidator;

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private UserAccountDao userAccountDao;

    @Autowired
    private FoodPriceDao foodPriceDao;

    @Autowired
    private EmailSender emailSender;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true));
    }

    @RequestMapping(value = "/reservation", method = RequestMethod.GET)
    public String getReservation(@RequestParam(required = false) Integer reservationId, ModelMap modelMap) {

        ReservationSearch reservation = new ReservationSearch();

        if (reservationId != null) {
            Reservation byId = reservationDao.getById(reservationId);
            reservation.setReservationId(byId.getReservationId());
            reservation.setUserAccount(byId.getUserAccount());
            reservation.setStartDate(byId.getStartDate());
            reservation.setEndDate(byId.getEndDate());
            reservation.setRoomId(byId.getRoom().getRoomId());
            reservation.setRoomPrice(byId.getRoom().getPrice());
            reservation.setBreakfast(byId.getBreakfast());
            reservation.setLunch(byId.getLunch());
            reservation.setDinner(byId.getDinner());
            reservation.setTotalPaid(byId.getTotalPaid());

            modelMap.put("selectedRoom", byId.getRoom());
        }

        FoodPrice foodPrice = foodPriceDao.getFoodPrice();
        reservation.setBreakfastPrice(foodPrice.getBreakfast());
        reservation.setLunchPrice(foodPrice.getLunch());
        reservation.setDinnerPrice(foodPrice.getDinner());

        modelMap.put("reservationSearch", reservation);

        return "registerReservationAdmin";
    }

    @RequestMapping(value = "/reservation", method = RequestMethod.POST)
    public String submitRoomForm(@RequestParam(required = false) String searchAction, @ModelAttribute ReservationSearch reservationSearch, BindingResult bindingResult, ModelMap modelMap) throws IOException {

        if (reservationSearch.getRoomId() != null) {
            Room byId = roomDao.getById(reservationSearch.getRoomId());
            if (byId != null)
                modelMap.put("selectedRoom", byId);
        }

        //avem actiune de search
        if (searchAction != null) {

            final Date startDate = reservationSearch.getStartDate();
            final Date endDate = reservationSearch.getEndDate();
            final Boolean internet = reservationSearch.getInternet();
            final Boolean airConditioner = reservationSearch.getAirConditioner();
            final Boolean phone = reservationSearch.getPhone();
            final Boolean roomView = reservationSearch.getRoomView();
            final Boolean smoking = reservationSearch.getSmoking();
            final Boolean pets = reservationSearch.getPets();
            final Boolean safe = reservationSearch.getSafe();
            final Boolean teaCoffee = reservationSearch.getTeaCoffee();
            final Boolean hydromassageTub = reservationSearch.getHydromassageTub();

            modelMap.put("reservationSearch", reservationSearch);

            //daca nu se respecta validarile pentru search, atunci ne intoarcem la formular
            if (startDate == null || endDate == null) {
                bindingResult.rejectValue("endDate", null, "Check in and check out date must be completed!");

                return "registerReservationAdmin";
            } else {

                List<Room> freeRoomsForPeriod1 = reservationDao.getFreeRoomsForPeriod(startDate, endDate, internet, airConditioner, phone,
                        roomView, smoking, pets, safe, teaCoffee, hydromassageTub);
                List<Room> freeRoomsForPeriod = freeRoomsForPeriod1;
                modelMap.put("availableRooms", freeRoomsForPeriod);

                return "registerReservationAdmin";
            }

        } else {

            reservationValidator.validate(reservationSearch, bindingResult);

            //facem salvare de rezervare
            if (bindingResult.hasErrors()) {

                List<Room> freeRoomsForPeriod = reservationDao.getFreeRoomsForPeriod(reservationSearch.getStartDate(), reservationSearch.getEndDate(), reservationSearch.getInternet(),
                        reservationSearch.getAirConditioner(), reservationSearch.getPhone(), reservationSearch.getRoomView(), reservationSearch.getSmoking(), reservationSearch.getPets(),
                        reservationSearch.getSafe(), reservationSearch.getTeaCoffee(), reservationSearch.getHydromassageTub());

                modelMap.put("availableRooms", freeRoomsForPeriod);

                return "registerReservationAdmin";
            }

            //inregistrare rezervare
            Reservation reservation = new Reservation();

            if (reservationSearch.getReservationId() != -1)
                reservation = reservationDao.getById(reservationSearch.getReservationId());

            UserAccount byEmail = userAccountDao.getByEmail(reservationSearch.getUserAccount().getEmail());
            if (byEmail != null) {
                reservation.setUserAccount(byEmail);
            } else {

                UserAccount userAccount = reservationSearch.getUserAccount();
                userAccount.setUserPassword(RandomStringUtils.randomAlphanumeric(8));
                userAccount.setUserRole(Roles.USER.getType());

                userAccount = userAccountDao.save(userAccount);

                emailSender.setEmailForInternRegistration(userAccount);

                reservation.setUserAccount(userAccount);
            }

            reservation.setRoom(roomDao.getById(reservationSearch.getRoomId()));
            reservation.setStartDate(reservationSearch.getStartDate());
            reservation.setEndDate(reservationSearch.getEndDate());
            reservation.setReservationDate(new Date());
            reservation.setBreakfast(reservationSearch.getBreakfast());
            reservation.setLunch(reservationSearch.getLunch());
            reservation.setDinner(reservationSearch.getDinner());

            FoodPrice foodPrice = foodPriceDao.getFoodPrice();

            Integer totalPrice = new Integer(0);

            if (reservation.getBreakfast())
                totalPrice += foodPrice.getBreakfast();
            if (reservation.getLunch())
                totalPrice += foodPrice.getLunch();
            if (reservation.getDinner())
                totalPrice += foodPrice.getDinner();

            totalPrice += reservation.getRoom().getPrice();
            long l = dateDiff(reservation.getEndDate(), reservation.getStartDate());
            totalPrice = (int) l * totalPrice;

            reservation.setTotalPaid(totalPrice);

            reservationDao.save(reservation);
        }

        return "redirect:/admin/reservations";
    }

    @RequestMapping(value = "/reservations", method = RequestMethod.GET)
    public String getReservations(ModelMap modelMap) {
        DaoFilter filter = new DaoFilter() {
            @Override
            public void bindCriteria(Criteria criteria) {
                criteria.add(Restrictions.isNull("registration"));
            }
        };
        modelMap.put("reservationList", reservationDao.getList(filter));
        modelMap.put("reservationSearch", new ReservationSearch());
        return "reservationListAdmin";
    }

    @RequestMapping(value = "/reservations", method = RequestMethod.POST)
    public String getReservationsWithFilters(@ModelAttribute final ReservationSearch reservationSearch, ModelMap modelMap) {

        DaoFilter filter = new DaoFilter() {
            @Override
            public void bindCriteria(Criteria criteria) {
                criteria.createAlias("room", "room");
                criteria.createAlias("userAccount", "userAccount");
                criteria.add(Restrictions.isNull("registration"));
                if (reservationSearch.getRoomNumber() != null)
                    criteria.add(Restrictions.eq("room.roomNumber", String.valueOf(reservationSearch.getRoomNumber())));
                if (reservationSearch.getUserAccount().getEmail() != null && !reservationSearch.getUserAccount().getEmail().isEmpty())
                    criteria.add(Restrictions.eq("userAccount.email", reservationSearch.getUserAccount().getEmail()));
                if (reservationSearch.getStartDate() != null)
                    criteria.add(Restrictions.ge("startDate", reservationSearch.getStartDate()));
                if (reservationSearch.getEndDate() != null)
                    criteria.add(Restrictions.ge("endDate", reservationSearch.getEndDate()));
            }
        };

        modelMap.put("reservationList", reservationDao.getList(filter));
        return "reservationListAdmin";
    }

    @RequestMapping(value = "/deleteReservation", method = RequestMethod.GET)
    public String deleteReservations(@RequestParam Integer reservationId) {

        reservationDao.delete(reservationDao.getById(reservationId));

        return "redirect:/admin/reservations";
    }

    @RequestMapping(value = "/searchUser", method = RequestMethod.GET)
    public
    @ResponseBody
    List<UserAccount> getUsers(@RequestParam String term) {

        List<UserAccount> all = userAccountDao.getByEmailPart(term);

        return all;
    }

    public long dateDiff(Date dateIni, Date dateFin) {
        long MILLISECS_PER_DAY = 24 * 60 * 60 * 1000;
        long days = 0l;

        try {
            days = (dateFin.getTime() - dateIni.getTime()) / MILLISECS_PER_DAY;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return days;
    }

}
