package org.nelo.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.nelo.dao.abstractDao.BaseDao;
import org.nelo.entities.UserAccount;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserAccountDao extends BaseDao<UserAccount> {

    public UserAccount getByEmail(final String email) {

        Criteria searchFilter = createCriteria();
        searchFilter.add(Restrictions.eq("email", email));

        List<UserAccount> userAccountList = getList(searchFilter);

        if (userAccountList != null && userAccountList.size() == 1)
            return userAccountList.get(0);

        return null;
    }

    public List<UserAccount> getByEmailPart(final String emailPart) {

        Criteria searchFilter = createCriteria();
        searchFilter.add(Restrictions.ilike("email", emailPart, MatchMode.ANYWHERE));

        return getList(searchFilter);
    }

}
