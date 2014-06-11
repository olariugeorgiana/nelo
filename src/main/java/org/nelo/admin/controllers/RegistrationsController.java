package org.nelo.admin.controllers;

import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.nelo.admin.validators.RegistrationValidator;
import org.nelo.dao.*;
import org.nelo.dao.abstractDao.DaoFilter;
import org.nelo.entities.*;
import org.nelo.entities.enums.Roles;
import org.nelo.utils.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * User: georgiana.olariu@greensoft.com.ro
 * Date: 02.06.2014 14:58
 */
@Controller
@RequestMapping(value = "/admin")
public class RegistrationsController {

    @Autowired
    private RegistrationValidator registrationValidator;
    @Autowired
    private RegistrationDao registrationDao;
    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private RoomDao roomDao;
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

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String getRegistration(@RequestParam(required = false) Integer registrationId, @RequestParam(required = false) Integer reservationId, ModelMap modelMap) {

        RegistrationSearch registration = new RegistrationSearch();

        //daca intram pe o inregistrare deja creata
        if (registrationId != null && reservationId == null) {
            Registration byId = registrationDao.getById(registrationId);
            registration.setRegistrationId(byId.getRegistrationId());
            registration.setUserAccount(byId.getUserAccount());
            registration.setStartDate(byId.getStartDate());
            registration.setEndDate(byId.getEndDate());
            registration.setRoomId(byId.getRoom().getRoomId());
            registration.setRoomPrice(byId.getRoom().getPrice());
            registration.setBreakfast(byId.getBreakfast());
            registration.setLunch(byId.getLunch());
            registration.setDinner(byId.getDinner());
            registration.setTotalPaid(byId.getTotalPaid());
            if (byId.getReservation() != null)
                registration.setReservationId(byId.getReservation().getReservationId());

            modelMap.put("selectedRoom", byId.getRoom());
        }

        //daca intram de pe o rezervare
        if (reservationId != null && registrationId == null) {
            Registration forReservation = registrationDao.getForReservation(reservationId);

            if (forReservation == null) {
                Reservation byId = reservationDao.getById(reservationId);
                registration.setUserAccount(byId.getUserAccount());
                registration.setStartDate(byId.getStartDate());
                registration.setEndDate(byId.getEndDate());
                registration.setRoomId(byId.getRoom().getRoomId());
                registration.setRoomPrice(byId.getRoom().getPrice());
                registration.setReservationId(byId.getReservationId());
                registration.setBreakfast(byId.getBreakfast());
                registration.setLunch(byId.getLunch());
                registration.setDinner(byId.getDinner());
                registration.setTotalPaid(byId.getTotalPaid());

                modelMap.put("selectedRoom", byId.getRoom());
            } else {
                registration.setUserAccount(forReservation.getUserAccount());
                registration.setStartDate(forReservation.getStartDate());
                registration.setEndDate(forReservation.getEndDate());
                registration.setRoomId(forReservation.getRoom().getRoomId());
                registration.setRoomPrice(forReservation.getRoom().getPrice());
                registration.setBreakfast(forReservation.getBreakfast());
                registration.setLunch(forReservation.getLunch());
                registration.setDinner(forReservation.getDinner());
                registration.setTotalPaid(forReservation.getTotalPaid());
                if (forReservation.getReservation() != null)
                    registration.setReservationId(forReservation.getReservation().getReservationId());

                modelMap.put("selectedRoom", forReservation.getRoom());
            }

        }
        FoodPrice foodPrice = foodPriceDao.getFoodPrice();
        registration.setBreakfastPrice(foodPrice.getBreakfast());
        registration.setLunchPrice(foodPrice.getLunch());
        registration.setDinnerPrice(foodPrice.getDinner());

        modelMap.put("registrationSearch", registration);

        return "registrationAdmin";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String submitRoomForm(@RequestParam(required = false) String searchAction, @Validated @ModelAttribute RegistrationSearch registrationSearch, BindingResult bindingResult, ModelMap modelMap) throws IOException {

        if (registrationSearch.getRoomId() != null) {
            Room byId = roomDao.getById(registrationSearch.getRoomId());
            if (byId != null)
                modelMap.put("selectedRoom", byId);
        }

        //avem actiune de search
        if (searchAction != null) {

            final Date startDate = registrationSearch.getStartDate();
            final Date endDate = registrationSearch.getEndDate();
            final Boolean internet = registrationSearch.getInternet();
            final Boolean airConditioner = registrationSearch.getAirConditioner();
            final Boolean phone = registrationSearch.getPhone();
            final Boolean roomView = registrationSearch.getRoomView();
            final Boolean smoking = registrationSearch.getSmoking();
            final Boolean pets = registrationSearch.getPets();
            final Boolean safe = registrationSearch.getSafe();
            final Boolean teaCoffee = registrationSearch.getTeaCoffee();
            final Boolean hydromassageTub = registrationSearch.getHydromassageTub();


            modelMap.put("registrationSearch", registrationSearch);

            //daca nu se respecta validarile pentru search, atunci ne intoarcem la formular
            if (startDate == null || endDate == null) {
                bindingResult.rejectValue("endDate", null, "Check in and check out date must be completed!");

                return "registrationAdmin";
            } else {

                List<Room> freeRoomsForPeriod = reservationDao.getFreeRoomsForPeriod(startDate, endDate, internet, airConditioner, phone,
                        roomView, smoking, pets, safe, teaCoffee, hydromassageTub);
                modelMap.put("availableRooms", freeRoomsForPeriod);

                return "registrationAdmin";
            }

        } else {

            registrationValidator.validate(registrationSearch, bindingResult);

            //facem salvare de rezervare
            if (bindingResult.hasErrors()) {
                List<Room> freeRoomsForPeriod = reservationDao.getFreeRoomsForPeriod(registrationSearch.getStartDate(), registrationSearch.getEndDate(), registrationSearch.getInternet(),
                        registrationSearch.getAirConditioner(), registrationSearch.getPhone(), registrationSearch.getRoomView(), registrationSearch.getSmoking(), registrationSearch.getPets(),
                        registrationSearch.getSafe(), registrationSearch.getTeaCoffee(), registrationSearch.getHydromassageTub());
                modelMap.put("availableRooms", freeRoomsForPeriod);
                return "registrationAdmin";
            }

            //inregistrare rezervare
            Registration registration = new Registration();

            if (registrationSearch.getRegistrationId() != -1)
                registration = registrationDao.getById(registrationSearch.getRegistrationId());

            //daca exista un utilizator in sistem cu emailul introdus, se foloseste, altfel se creaza si se da email
            UserAccount byEmail = userAccountDao.getByEmail(registrationSearch.getUserAccount().getEmail());
            if (byEmail != null) {
                registration.setUserAccount(byEmail);
            } else {

                UserAccount userAccount = registrationSearch.getUserAccount();
                userAccount.setUserPassword(RandomStringUtils.randomAlphanumeric(8));
                userAccount.setUserRole(Roles.USER.getType());

                userAccount = userAccountDao.save(userAccount);

                emailSender.setEmailForInternRegistration(userAccount);

                registration.setUserAccount(userAccount);
            }

            registration.setRoom(roomDao.getById(registrationSearch.getRoomId()));
            registration.setStartDate(registrationSearch.getStartDate());
            registration.setEndDate(registrationSearch.getEndDate());
            registration.setBreakfast(registrationSearch.getBreakfast());
            registration.setLunch(registrationSearch.getLunch());
            registration.setDinner(registrationSearch.getDinner());

            //se calculeaza pretul camerei
            FoodPrice foodPrice = foodPriceDao.getFoodPrice();
            Integer totalPrice = new Integer(0);

            if (registration.getBreakfast())
                totalPrice += foodPrice.getBreakfast();
            if (registration.getLunch())
                totalPrice += foodPrice.getLunch();
            if (registration.getDinner())
                totalPrice += foodPrice.getDinner();

            totalPrice += registration.getRoom().getPrice();
            long l = dateDiff(registration.getEndDate(), registration.getStartDate());
            totalPrice = (int) l * totalPrice;

            registration.setTotalPaid(totalPrice);
            registration.setBreakfastPrice(foodPrice.getBreakfast());
            registration.setLunchPrice(foodPrice.getLunch());
            registration.setDinnerPrice(foodPrice.getDinner());

            if (registrationSearch.getReservationId() != null)
                registration.setReservation(reservationDao.getById(registrationSearch.getReservationId()));

            registration = registrationDao.save(registration);

            //daca rezervarea se face pe o camera cu overbooking, se marcheaza rezervarea pe camera complementara
            if (registrationSearch.getReservationId() != null) {
                final Reservation byId = reservationDao.getById(registrationSearch.getReservationId());
                byId.setRegistration(registration);
                reservationDao.save(byId);

                DaoFilter complementaryFilter = new DaoFilter() {
                    @Override
                    public void bindCriteria(Criteria criteria) {
                        criteria.createAlias("room", "room");
                        criteria.add(Restrictions.eq("room.virtualRoom", !byId.getRoom().getVirtualRoom()));
                        criteria.add(Restrictions.ne("reservationId", byId.getReservationId()));
                        criteria.add(Restrictions.eq("startDate", byId.getStartDate()));
                        criteria.add(Restrictions.eq("endDate", byId.getEndDate()));
                    }
                };

                List<Reservation> list = reservationDao.getList(complementaryFilter);

                if (list != null && list.size() == 1) {
                    Reservation complementaryReservation = list.get(0);
                    complementaryReservation.setRegistration(registration);
                    reservationDao.save(complementaryReservation);
                }

            }

        }

        return "redirect:/admin/registrations";
    }

    @RequestMapping(value = "/registrations", method = RequestMethod.GET)
    public String getReservations(ModelMap modelMap) {
        modelMap.put("registrationList", registrationDao.getAll());
        modelMap.put("registrationSearch", new RegistrationSearch());
        return "registrationListAdmin";
    }

    @RequestMapping(value = "/registrations", method = RequestMethod.POST)
    public String getReservationsFiltered(@ModelAttribute final RegistrationSearch reservationSearch, ModelMap modelMap) {
        DaoFilter filter = new DaoFilter() {
            @Override
            public void bindCriteria(Criteria criteria) {
                criteria.createAlias("room", "room");
                criteria.createAlias("userAccount", "userAccount");
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

        modelMap.put("registrationList", registrationDao.getList(filter));
        return "registrationListAdmin";
    }

    @RequestMapping(value = "/deleteRegistration", method = RequestMethod.GET)
    public String deleteReservations(@RequestParam Integer registrationId) {

        registrationDao.delete(registrationDao.getById(registrationId));

        return "redirect:/admin/registrations";
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
