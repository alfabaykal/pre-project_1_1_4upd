package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory factory = Util.getInstance().getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = factory.openSession();
        try {
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
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                try {
                    session.close();
                } catch (Exception e) {

                }
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users;")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                try {
                    session.close();
                } catch (Exception e) {

                }
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                try {
                    session.close();
                } catch (Exception e) {

                }
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            Object user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                try {
                    session.close();
                } catch (Exception e) {

                }
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = factory.openSession();
        List<User> users = null;
        try {
            session.beginTransaction();
            String sql = "SELECT * FROM users";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            users = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                try {
                    session.close();
                } catch (Exception e) {

                }
            }
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users;")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                try {
                    session.close();
                } catch (Exception e) {

                }
            }
        }
    }
}
