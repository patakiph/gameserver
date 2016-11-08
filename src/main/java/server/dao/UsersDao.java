package server.dao;

import jersey.repackaged.com.google.common.base.Joiner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import server.servlets.users_data.Token;
import server.servlets.users_data.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ольга on 04.11.2016.
 */
public class UsersDAO implements Dao<User> {
    private static final Logger log = LogManager.getLogger(UsersDAO.class);
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS users;";

    @Override
    public List<User> getAll() {
        return Database.selectTransactional(session -> session.createQuery("from User").list());

    }


    @Override
    public List<User> getAllWhere(String ... hqlConditions) {
        String totalCondition = Joiner.on(" and ").join(Arrays.asList(hqlConditions));
        return Database.selectTransactional(session ->session.createQuery("from User where " + totalCondition).list());
    }
    public static int dropTable() {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            System.out.println(DROP_TABLE);
            stm.executeUpdate(DROP_TABLE);
            return 1;
        } catch (SQLException e) {
            log.error("Failed to insert.", e);
        }
        return -1;
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
