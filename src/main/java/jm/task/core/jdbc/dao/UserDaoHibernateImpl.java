package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        SessionFactory factory = Util.getSessionFactory();
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery(
                    "CREATE TABLE IF NOT EXISTS users\n" +
                    "(id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,\n" +
                    "name VARCHAR(45) NOT NULL,\n" +
                    "lastName VARCHAR(45) NOT NULL,\n" +
                    "age TINYINT NOT NULL)\n" +
                    "CHARSET = utf8;")
            .executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            factory.close();
        }
    }

    @Override
    public void dropUsersTable() {
        SessionFactory factory = Util.getSessionFactory();
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users;")
            .executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            factory.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        SessionFactory factory = Util.getSessionFactory();
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (factory.getCurrentSession() != null) {
                factory.getCurrentSession().getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            factory.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        SessionFactory factory = Util.getSessionFactory();
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            Object user = session.get(User.class,id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (factory.getCurrentSession() != null) {
                factory.getCurrentSession().getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            factory.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        SessionFactory factory = Util.getSessionFactory();
        List<User> users = null;
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            String sql = "SELECT * FROM users";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            users = query.list();
            session.close();
        } catch (HibernateException e) {
            if (factory.getCurrentSession() != null) {
                factory.getCurrentSession().getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            factory.close();
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        SessionFactory factory = Util.getSessionFactory();
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users;")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            factory.close();
        }
    }
}
