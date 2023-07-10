package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    private Transaction transaction = null;
    private final SessionFactory sessionFactory = getSessionFactory();

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        String create = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(50) NOT NULL," +
                "lastName VARCHAR(50) NOT NULL," +
                "age TINYINT NOT NULL" +
                ")";

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(create).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();

            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        String drop = "DROP TABLE IF EXISTS users";

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(drop).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();

            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();

            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);

            if (user != null) {
                session.delete(user);
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();

            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        String get = "FROM User";

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(get, User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        String clean = "DELETE FROM User";

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery(clean).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();

            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
