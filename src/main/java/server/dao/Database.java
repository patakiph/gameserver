package server.dao;

import org.hibernate.HibernateException;
import org.hibernate.cfg.*;
import org.hibernate.SessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import server.servlets.users_data.Token;
import server.servlets.users_data.User;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Created by Ольга on 04.11.2016.
 */
public class Database {
    private static final Logger log = LogManager.getLogger(Database.class);
    private static final SessionFactory sessionFactory;

    static {
        try{
            sessionFactory = new Configuration().configure()
                    .buildSessionFactory();
        }catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        log.info("Session factory configured.");
    }

    static <T> List<T> selectTransactional(Function<Session, List<T>> selectAction) {
        Transaction txn = null;
        List<T> ts = Collections.emptyList();
        try (Session session = Database.openSession()) {
            txn = session.beginTransaction();
            ts = selectAction.apply(session);
            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }

        return ts;
    }

    static void doTransactional(Function<Session, ?> f) {
        Transaction txn = null;
        try (Session session = Database.openSession()) {
            txn = session.beginTransaction();
            f.apply(session);
            txn.commit();
        } catch (HibernateException e) {
            if (txn!=null) txn.rollback();
            e.printStackTrace();
        }
    }

    static Session openSession() {
        return sessionFactory.openSession();
    }

    private Database() { }
}
