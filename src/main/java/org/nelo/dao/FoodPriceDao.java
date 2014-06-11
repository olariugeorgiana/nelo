package org.nelo.dao;

import org.nelo.dao.abstractDao.BaseDao;
import org.nelo.entities.FoodPrice;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FoodPriceDao extends BaseDao<FoodPrice> {

    public FoodPrice getFoodPrice() {
        List<FoodPrice> all = getAll();
        return all != null && all.size() == 1 ? all.get(0) : new FoodPrice();
    }

}
