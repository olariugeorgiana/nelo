package org.nelo.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.nelo.dao.abstractDao.BaseDao;
import org.nelo.dao.abstractDao.DaoFilter;
import org.nelo.entities.Registration;
import org.nelo.entities.UserAccount;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository

public class RegistrationDao extends BaseDao<Registration> {

    public List<Registration> getForUser(final String email) {
        DaoFilter filter = new DaoFilter() {
            @Override
            public void bindCriteria(Criteria criteria) {
                criteria.createAlias("userAccount", "userAccount");
                criteria.add(Restrictions.eq("userAccount.email", email));
            }
        };

        return getList(filter);
    }

    public Registration getForReservation(final Integer reservationId) {
        DaoFilter filter = new DaoFilter() {
            @Override
            public void bindCriteria(Criteria criteria) {
                criteria.createAlias("reservation", "reservation");
                criteria.add(Restrictions.eq("reservation.reservationId", reservationId));
            }
        };

        List<Registration> list = getList(filter);

        if (list != null && list.size() == 1)
            return list.get(0);

        return null;
    }

    public Long getTotalPaidToHotel() {
        Query namedQuery = getSessionFactory().getCurrentSession().getNamedQuery(Registration.TOTAL_PAID_REGISTRATION_TOTAL);
        Long aLong = (Long) namedQuery.uniqueResult();
        return aLong != null ? aLong : 0L;
    }

    public Long getTotalPaidRoomsToHotel() {
        Query namedQuery = getSessionFactory().getCurrentSession().getNamedQuery(Registration.TOTAL_PAID_REGISTRATION_ROOMS);
        Long aLong = (Long) namedQuery.uniqueResult();
        return aLong != null ? aLong : 0L;
    }

    public Long getTotalPaidBreakfastToHotel() {
        Query namedQuery = getSessionFactory().getCurrentSession().getNamedQuery(Registration.TOTAL_PAID_REGISTRATION_BREAKFAST);
        Long aLong = (Long) namedQuery.uniqueResult();
        return aLong != null ? aLong : 0L;
    }

    public Long getTotalPaidLunchToHotel() {
        Query namedQuery = getSessionFactory().getCurrentSession().getNamedQuery(Registration.TOTAL_PAID_REGISTRATION_LUNCH);
        Long aLong = (Long) namedQuery.uniqueResult();
        return aLong != null ? aLong : 0L;
    }

    public Long getTotalPaidDinnerToHotel() {
        Query namedQuery = getSessionFactory().getCurrentSession().getNamedQuery(Registration.TOTAL_PAID_REGISTRATION_DINNER);
        Long aLong = (Long) namedQuery.uniqueResult();
        return aLong != null ? aLong : 0L;
    }

    public Map<UserAccount, Long> getTop10Users() {
        Query namedQuery = getSessionFactory().getCurrentSession().getNamedQuery(Registration.TOTAL_PAID_REGISTRATION_USERS);
        ArrayList<Object[]> obj = (ArrayList<Object[]>) namedQuery.list();

        Map<UserAccount, Long> map = new LinkedHashMap<UserAccount, Long>();

        for (Object[] object : obj) {
            Long valuePaid = (Long) object[0];
            UserAccount userAccount = (UserAccount) object[1];
            map.put(userAccount, valuePaid);
        }

        return map;
    }

}
