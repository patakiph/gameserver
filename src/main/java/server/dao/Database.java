package server.dao;

import com.fasterxml.classmate.AnnotationConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import server.servlets.users_data.User;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;


class Database {
    private static final Logger log = LogManager.getLogger(Database.class);
    private static final SessionFactory sessionFactory;

    static {
        try {

            sessionFactory = new Configuration().
                    configure().
                    //addPackage("com.xyz") //add package if used.
                            addAnnotatedClass(User.class).buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
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
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

    static Session openSession() {
        return sessionFactory.openSession();
    }

    private Database() { }
}
