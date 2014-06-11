package org.nelo.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.nelo.dao.abstractDao.BaseDao;
import org.nelo.dao.abstractDao.DaoFilter;
import org.nelo.entities.RoomImg;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: georgiana.olariu@greensoft.com.ro
 * Date: 15.05.2014 10:43
 */
@Repository
public class RoomImgDao extends BaseDao<RoomImg> {

    public List<RoomImg> getForRoom(final int roomId) {
        DaoFilter filter = new DaoFilter() {
            @Override
            public void bindCriteria(Criteria criteria) {
                criteria.createAlias("room", "room");
                criteria.add(Restrictions.eq("room.roomId", roomId));
            }
        };
        return getList(filter);
    }

}
