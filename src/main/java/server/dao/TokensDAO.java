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
 * Created by Ольга on 05.11.2016.
 */
public class TokensDAO implements Dao<Token> {
    private static final Logger log = LogManager.getLogger(UsersDAO.class);

    @Override
    public List<Token> getAll() {
        return Database.selectTransactional(session -> session.createQuery("from Token").list());

    }


    @Override
    public List<Token> getAllWhere(String ... hqlConditions) {
        String totalCondition = Joiner.on(" and ").join(Arrays.asList(hqlConditions));
        return Database.selectTransactional(session ->session.createQuery("from Token where " + totalCondition).list());
    }

    @Override
    public void insert(Token token) {

        try {
            Database.doTransactional(session -> session.save(token));
        } catch (HibernateException e){
            e.printStackTrace();
        }
    }

    public void delete(Token token) {

        try {
            Database.updateTransactional(session -> session.delete(token));
        } catch (HibernateException e){
            e.printStackTrace();
        }
    }
}
