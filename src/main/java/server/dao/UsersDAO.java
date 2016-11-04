package server.dao;

import jersey.repackaged.com.google.common.base.Joiner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.servlets.users_data.User;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Ольга on 04.11.2016.
 */
public class UsersDAO implements Dao<User> {
    private static final Logger log = LogManager.getLogger(UsersDAO.class);

    @Override
    public List<User> getAll() {
        return Database.selectTransactional(session -> session.createQuery("SELECT from USER").list());

    }


    @Override
    public List<User> getAllWhere(String ... hqlConditions) {
        String totalCondition = Joiner.on(" and ").join(Arrays.asList(hqlConditions));
        return null;
   //     return Database.selectTransactional(session ->session.createQuery("from Person where " + totalCondition).list());
    }

    @Override
    public void insert(User user) {

     Database.doTransactional(session -> session.save(user));
    }

}
