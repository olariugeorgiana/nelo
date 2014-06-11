package org.nelo.dao;


import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.nelo.dao.abstractDao.BaseDao;
import org.nelo.dao.abstractDao.DaoFilter;
import org.nelo.entities.Reservation;
import org.nelo.entities.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ReservationDao extends BaseDao<Reservation> {

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private RegistrationDao registrationDao;

    public List<Reservation> getForUser(final String email) {
        DaoFilter filter = new DaoFilter() {
            @Override
            public void bindCriteria(Criteria criteria) {
                criteria.createAlias("userAccount", "userAccount");
                criteria.add(Restrictions.eq("userAccount.email", email));
            }
        };

        List<Reservation> list = getList(filter);
        List<Reservation> finalList = new ArrayList<Reservation>();

        for (final Reservation reservation : list) {

            DaoFilter filterRegistration = new DaoFilter() {
                @Override
                public void bindCriteria(Criteria criteria) {
                    criteria.createAlias("reservation", "reservation");
                    criteria.add(Restrictions.eq("reservation.reservationId", reservation.getReservationId()));
                }
            };

            int count = registrationDao.count(filterRegistration);

            if (count == 0)
                finalList.add(reservation);
        }

        return finalList;
    }

    public List<Room> getFreeRoomsForPeriod(final Date startDate, final Date endDate, final Boolean internet,
                                            final Boolean airConditioner, final Boolean phone, final Boolean roomView,
                                            final Boolean smoking, final Boolean pets, final Boolean safe,
                                            final Boolean teaCoffee, final Boolean hydromassageTub) {
        if (startDate == null || endDate == null) return new ArrayList<Room>();

        DaoFilter roomFilter = new DaoFilter() {
            @Override
            public void bindCriteria(Criteria criteria) {
                criteria.add(Restrictions.eq("virtualRoom", false));
                criteria.add(Restrictions.eq("deleted", false));

                if (internet) criteria.add(Restrictions.eq("internet", internet));
                if (airConditioner) criteria.add(Restrictions.eq("airConditioner", airConditioner));
                if (phone) criteria.add(Restrictions.eq("phone", phone));
                if (roomView) criteria.add(Restrictions.eq("roomView", roomView));
                if (smoking) criteria.add(Restrictions.eq("smoking", smoking));
                if (pets) criteria.add(Restrictions.eq("pets", pets));
                if (safe) criteria.add(Restrictions.eq("safe", safe));
                if (teaCoffee) criteria.add(Restrictions.eq("teaCoffee", teaCoffee));
                if (hydromassageTub) criteria.add(Restrictions.eq("hydromassageTub", hydromassageTub));
            }
        };

        List<Room> freeRooms = roomDao.getList(roomFilter);

        freeRooms = getEmptyRooms(freeRooms, startDate, endDate);

        final List<Integer> roomsIds = new ArrayList<Integer>();

        for (Room room : freeRooms) {
            roomsIds.add(room.getRoomId());
        }

        DaoFilter virtualRoomsFilter = new

                DaoFilter() {
                    @Override
                    public void bindCriteria(Criteria criteria) {
                        criteria.createAlias("room", "room");
                        criteria.add(Restrictions.eq("virtualRoom", true));
                        criteria.add(Restrictions.eq("deleted", false));
                        criteria.add(Restrictions.not(Restrictions.in("room.roomId", roomsIds)));
                    }
                };
        List<Room> virtualRooms = roomDao.getList(virtualRoomsFilter);
        virtualRooms = getEmptyRooms(virtualRooms, startDate, endDate);
        freeRooms.addAll(virtualRooms);

        Room[] roomsSorted = freeRooms.toArray(new Room[]{});
        Arrays.sort(roomsSorted);
        freeRooms.clear();
        freeRooms = Arrays.asList(roomsSorted);

        return freeRooms;
    }

    private List<Room> getEmptyRooms(List<Room> rooms, final Date startDate, final Date endDate) {
        List<Room> freeRooms = new ArrayList<Room>();
        Map<Integer, Room> registrationRooms = new HashMap<Integer, Room>();
        Map<Integer, Room> reservationRooms = new HashMap<Integer, Room>();

        for (final Room room : rooms) {
            DaoFilter roomsFilter = new DaoFilter() {
                @Override
                public void bindCriteria(Criteria criteria) {
                    criteria.createAlias("room", "room");
                    criteria.add(Restrictions.eq("room.roomId", room.getRoomId()));
                    criteria.add(Restrictions.lt("startDate", endDate));
                    criteria.add(Restrictions.gt("endDate", startDate));
                }
            };

            //camerele libere din zona de inregistrari
            int countRegistrations = registrationDao.count(roomsFilter);
            if (countRegistrations == 0)
                registrationRooms.put(room.getRoomId(), room);

            int countReservation = count(roomsFilter);
            if (countReservation == 0)
                reservationRooms.put(room.getRoomId(), room);
        }

        for (Map.Entry<Integer, Room> integerRoomEntry : registrationRooms.entrySet()) {
            if (reservationRooms.get(integerRoomEntry.getKey()) != null)
                freeRooms.add(integerRoomEntry.getValue());
        }

        return freeRooms;
    }

}
