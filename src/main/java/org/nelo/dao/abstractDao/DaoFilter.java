package org.nelo.dao.abstractDao;


import org.hibernate.Criteria;

public interface DaoFilter {

    public void bindCriteria(Criteria criteria);
}
