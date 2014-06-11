package org.nelo.web.controllers;

import org.apache.commons.lang.RandomStringUtils;
import org.nelo.admin.validators.ReservationValidator;
import org.nelo.dao.*;
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

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class BookingController {

    @Autowired
    private ReservationValidator reservationValidator;

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private RoomImgDao roomImgDao;

    @Autowired
    private FoodPriceDao foodPriceDao;

    @Autowired
    private UserAccountDao userAccountDao;

    @Autowired
    private EmailSender emailSender;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true));
    }

    @RequestMapping(value = "/booking", method = RequestMethod.GET)
    public String getBookingPage(ModelMap modelMap, HttpSession session) {

        ReservationSearch reservationSearch = new ReservationSearch();

        if (session.getAttribute("loggedUser") != null) {
            UserAccount userAccount = (UserAccount) session.getAttribute("loggedUser");
            reservationSearch.setUserAccount(userAccount);
        }

        FoodPrice foodPrice = foodPriceDao.getFoodPrice();
        reservationSearch.setBreakfastPrice(foodPrice.getBreakfast());
        reservationSearch.setLunchPrice(foodPrice.getLunch());
        reservationSearch.setDinnerPrice(foodPrice.getDinner());

        modelMap.put("reservationSearch", reservationSearch);

        return "booking";
    }

    @RequestMapping(value = "/booking", method = RequestMethod.POST)
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

            Integer totalPrice = new Integer(0);

            if (reservationSearch.getBreakfast())
                totalPrice += reservationSearch.getBreakfastPrice();
            if (reservationSearch.getLunch())
                totalPrice += reservationSearch.getLunchPrice();
            if (reservationSearch.getDinner())
                totalPrice += reservationSearch.getDinnerPrice();

            totalPrice += reservationSearch.getRoomPrice();
            long l = dateDiff(reservationSearch.getEndDate(), reservationSearch.getStartDate());
            totalPrice = (int) l * totalPrice;

            reservationSearch.setTotalPaid(totalPrice);

            modelMap.put("reservationSearch", reservationSearch);

            //daca nu se respecta validarile pentru search, atunci ne intoarcem la formular
            if (startDate == null || endDate == null) {
                bindingResult.rejectValue("endDate", null, "Check in and check out date must be completed!");

                return "booking";
            } else {

                List<Room> freeRoomsForPeriod = reservationDao.getFreeRoomsForPeriod(startDate, endDate, internet, airConditioner, phone,
                        roomView, smoking, pets, safe, teaCoffee, hydromassageTub);
                modelMap.put("availableRooms", freeRoomsForPeriod);
                modelMap.put("searchAction", true);

                return "booking";
            }

        } else {

            reservationValidator.validate(reservationSearch, bindingResult);

            Integer totalPrice = new Integer(0);

            if (reservationSearch.getBreakfast())
                totalPrice += reservationSearch.getBreakfastPrice();
            if (reservationSearch.getLunch())
                totalPrice += reservationSearch.getLunchPrice();
            if (reservationSearch.getDinner())
                totalPrice += reservationSearch.getDinnerPrice();

            totalPrice += reservationSearch.getRoomPrice();

            reservationSearch.setTotalPaid(totalPrice);

            //facem salvare de rezervare
            if (bindingResult.hasErrors()) {

                List<Room> freeRoomsForPeriod = reservationDao.getFreeRoomsForPeriod(reservationSearch.getStartDate(), reservationSearch.getEndDate(), reservationSearch.getInternet(),
                        reservationSearch.getAirConditioner(), reservationSearch.getPhone(), reservationSearch.getRoomView(), reservationSearch.getSmoking(), reservationSearch.getPets(),
                        reservationSearch.getSafe(), reservationSearch.getTeaCoffee(), reservationSearch.getHydromassageTub());
                modelMap.put("availableRooms", freeRoomsForPeriod);
                return "booking";
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
            reservation.setTotalPaid(reservationSearch.getTotalPaid());

            reservationDao.save(reservation);
        }

        return "redirect:/";
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
