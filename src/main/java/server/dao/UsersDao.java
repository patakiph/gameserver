package server.dao;

import jersey.repackaged.com.google.common.base.Joiner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import server.servlets.users_data.Token;
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
        return Database.selectTransactional(session -> session.createQuery("from User").list());

    }


    @Override
    public List<User> getAllWhere(String ... hqlConditions) {
        String totalCondition = Joiner.on(" and ").join(Arrays.asList(hqlConditions));
        return Database.selectTransactional(session ->session.createQuery("from User where " + totalCondition).list());
    }

    @Override
    public void insert(User user) {

        try {
                       Database.doTransactional(session -> session.save(user));
                    } catch (HibernateException e){
                        e.printStackTrace();
                    }
    }
    public List<User> findByLogin(String login) {
               return getAllWhere(String.format("login='%s'", login));
           }

    public void update(User user) {

        try {
            Database.updateTransactional(session -> session.update(user));
        } catch (HibernateException e){
            e.printStackTrace();
        }
    }
}
