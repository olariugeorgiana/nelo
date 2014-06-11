package org.nelo.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.nelo.dao.abstractDao.BaseDao;
import org.nelo.dao.abstractDao.DaoFilter;
import org.nelo.entities.Room;
import org.nelo.entities.enums.RoomType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoomDao extends BaseDao<Room> {

    public Room getByNumber(final Room room) {

        Criteria searchFilter = createCriteria();
        searchFilter.add(Restrictions.eq("roomNumber", room.getRoomNumber()));
        if (room.getRoomId() != -1)
            searchFilter.add(Restrictions.ne("roomId", room.getRoomId()));

        List<Room> roomList = getList(searchFilter);

        if (roomList != null && roomList.size() == 1)
            return roomList.get(0);
        return null;
    }

    public List<Room> getListByType(final RoomType roomType) {
        DaoFilter filter = new DaoFilter() {
            @Override
            public void bindCriteria(Criteria criteria) {
                criteria.add(Restrictions.eq("deleted", false));
                criteria.add(Restrictions.eq("roomType", roomType.getType()));
                criteria.addOrder(Order.desc("roomType"));
            }
        };

        return getList(filter);
    }

    public List<Room> getVirtualRooms() {
        DaoFilter filter = new DaoFilter() {
            @Override
            public void bindCriteria(Criteria criteria) {
                criteria.add(Restrictions.eq("virtualRoom", true));
            }
        };

        return getList(filter);
    }

}
