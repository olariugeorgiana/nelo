package org.nelo.admin.controllers;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.nelo.dao.RegistrationDao;
import org.nelo.dao.ReservationDao;
import org.nelo.dao.RoomDao;
import org.nelo.dao.UserAccountDao;
import org.nelo.dao.abstractDao.DaoFilter;
import org.nelo.entities.UserAccount;
import org.nelo.entities.enums.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
@RequestMapping(value = "/admin")
public class StatisticsController {

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private RegistrationDao registrationDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private UserAccountDao userAccountDao;

    @RequestMapping(method = RequestMethod.GET)
    public String getStatisticsPage(ModelMap modelMap) {
        //cate rezervari sunt in total
        int totalReservationsCount = reservationDao.count(null);
        modelMap.put("totalReservationsCount", totalReservationsCount);

        //cate rezervari sunt fara inregistrare
        DaoFilter totalReservationWithoutRegistrationFilter = new DaoFilter() {
            @Override
            public void bindCriteria(Criteria criteria) {
                criteria.add(Restrictions.isNull("registration"));
            }
        };
        int totalReservationWithoutRegistrationCount = reservationDao.count(totalReservationWithoutRegistrationFilter);
        modelMap.put("totalReservationWithoutRegistrationCount", totalReservationWithoutRegistrationCount);

        //cate inregistrari sunt
        int totalRegistrationCount = registrationDao.count(null);
        modelMap.put("totalRegistrationCount", totalRegistrationCount);

        //cate inregistrari sunt fara rezervare
        DaoFilter totalRegistrationWithoutReservationFilter = new DaoFilter() {
            @Override
            public void bindCriteria(Criteria criteria) {
                criteria.add(Restrictions.isNull("reservation"));
            }
        };
        int totalRegistrationWithoutReservationCount = registrationDao.count(totalRegistrationWithoutReservationFilter);
        modelMap.put("totalRegistrationWithoutReservationCount", totalRegistrationWithoutReservationCount);

        //raport rezervare la inregistrare
        //todo

        //cate camere sunt
        DaoFilter filter = new DaoFilter() {
            @Override
            public void bindCriteria(Criteria criteria) {
                criteria.add(Restrictions.eq("deleted", false));
            }
        };
        int totalRoomsCount = roomDao.count(filter);
        modelMap.put("totalRoomsCount", totalRoomsCount);

        //media de ocupare a unei camere pe perioada
        //todo

        //cati utilizatori sunt
        DaoFilter totalCustomersFilter = new DaoFilter() {
            @Override
            public void bindCriteria(Criteria criteria) {
                criteria.add(Restrictions.eq("userRole", Roles.USER.getType()));
            }
        };
        int totalCustomersCount = userAccountDao.count(totalCustomersFilter);
        modelMap.put("totalCustomersCount", totalCustomersCount);

        //top 10 utilizatori la plata in sistem
        Map<UserAccount, Long> top10Users = registrationDao.getTop10Users();
        modelMap.put("top10Users", top10Users);

        //total platit pana acum la hotel
        Long totalPaidToHotel = registrationDao.getTotalPaidToHotel();
        modelMap.put("totalPaidToHotel", totalPaidToHotel);
        Long totalPaidRoomsToHotel = registrationDao.getTotalPaidRoomsToHotel();
        modelMap.put("totalPaidRoomsToHotel", totalPaidRoomsToHotel);
        Long totalPaidBreakfastToHotel = registrationDao.getTotalPaidBreakfastToHotel();
        modelMap.put("totalPaidBreakfastToHotel", totalPaidBreakfastToHotel);
        Long totalPaidLunchToHotel = registrationDao.getTotalPaidLunchToHotel();
        modelMap.put("totalPaidLunchToHotel", totalPaidLunchToHotel);
        Long totalPaidDinnerToHotel = registrationDao.getTotalPaidDinnerToHotel();
        modelMap.put("totalPaidDinnerToHotel", totalPaidDinnerToHotel);

//        //total receptionisti
//        DaoFilter totalReceptionistFilter = new DaoFilter() {
//            @Override
//            public void bindCriteria(Criteria criteria) {
//                criteria.add(Restrictions.eq("userRole", Roles.USER.getType()));
//            }
//        };
//        int totalReceptionistCount = userAccountDao.count(totalReceptionistFilter);
//
//        //top receptionisti dupa rezervari si inregistrari

        return "homeAdmin";
    }

}
