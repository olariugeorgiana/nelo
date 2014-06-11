package org.nelo.dao.abstractDao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public abstract class BaseDao<T> {

    public static final Order DEFAULT_ORDER = Order.asc("id");
    protected SessionFactory sessionFactory;
    private Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public BaseDao() {

        ParameterizedType pt = null;
        if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
            pt = (ParameterizedType) getClass().getGenericSuperclass();
        } else if (getClass().getSuperclass().getGenericSuperclass() instanceof ParameterizedType) {
            pt = (ParameterizedType) getClass().getSuperclass().getGenericSuperclass();
        } else if (getClass().getSuperclass().getSuperclass().getGenericSuperclass() instanceof ParameterizedType) {
            pt = (ParameterizedType) getClass().getSuperclass().getSuperclass().getGenericSuperclass();
        }

        if (pt == null) {
            throw new RuntimeException("Nu s-a putut determina ParameterizedType (BaseDao) ");
        }

        this.persistentClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    public SessionFactory getSessionFactory() {

        return sessionFactory;
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Session getSession() {

        return getSessionFactory().getCurrentSession();
    }

    public Class<T> getPersistentClass() {

        return persistentClass;
    }

    public T getById(Integer id) {

        return (T) getSession().get(getPersistentClass(), id);
    }

    public T save(T entity) {
        getSession().saveOrUpdate(entity);
        return entity;
    }

    public T insert(T entity) {
        getSession().save(entity);
        return entity;
    }

    public T update(T entity) {
        getSession().update(entity);
        return entity;
    }

    public void delete(T entity) {
        getSession().delete(entity);
    }

    public List<T> getAll() {

        return (List<T>) getSession().createCriteria(getPersistentClass()).setMaxResults(1000).addOrder(DEFAULT_ORDER).list();
    }

    public List<T> getList(Criteria criteria) {
        return criteria.list();
    }

    public List<T> getList(DaoFilter daoFilter) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());

        if (daoFilter != null) {
            daoFilter.bindCriteria(criteria);
        }

        return criteria.list();
    }

    protected Order getDefaultOrder() {

        return DEFAULT_ORDER;
    }

    protected Criteria createCriteria() {

        return getSession().createCriteria(getPersistentClass());
    }

    public int count(DaoFilter filtru) {

        Criteria criteria = getSession().createCriteria(getPersistentClass());

        if (filtru != null) {
            filtru.bindCriteria(criteria);
        }

        criteria.setProjection(null).setProjection(Projections.rowCount());

        Object r = criteria.uniqueResult();
        if (r == null) r = "0";

        return Integer.parseInt(r.toString());
    }

}
