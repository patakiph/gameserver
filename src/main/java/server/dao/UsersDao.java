package server.dao;

import jersey.repackaged.com.google.common.base.Joiner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import server.servlets.users_data.User;

import java.util.Arrays;
import java.util.List;

/**
 * Created by s.rybalkin on 17.10.2016.
 */
public class UsersDao implements Dao<User> {
    private static final Logger log = LogManager.getLogger(UsersDao.class);

    @Override
    public List<User> getAll() {
        return Database.selectTransactional(session -> session.createQuery("from user").list());
    }


    @Override
    public List<User> getAllWhere(String ... hqlConditions) {
        String totalCondition = Joiner.on(" and ").join(Arrays.asList(hqlConditions));
        return Database.selectTransactional(session ->session.createQuery("from Users where " + totalCondition).list());
    }

    @Override
    public void insert(User user) {
        try {
            Database.doTransactional(session -> session.save(user));
        } catch (HibernateException e){
            e.printStackTrace();
        }
    }

}
