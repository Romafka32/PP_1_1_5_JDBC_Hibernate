package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String CREATE = "CREATE TABLE IF NOT EXISTS mydbtest.users (\n" +
            "  id INT NOT NULL AUTO_INCREMENT,\n" +
            "  name VARCHAR(45) NOT NULL,\n" +
            "  lastName VARCHAR(45) NOT NULL,\n" +
            "  age INT(3) NOT NULL,\n" +
            "  PRIMARY KEY (id))\n" +
            "ENGINE = InnoDB\n" +
            "DEFAULT CHARACTER SET = utf8;";
    private static final String DROP = "DROP TABLE IF EXISTS mydbtest.users";
    private static final String SAVE = "INSERT INTO users(name, lastName, age) VALUES(?, ?, ?)";
    private static final String GET_ALL = "SELECT * FROM users";
    private static final String DELETE = "DELETE FROM users WHERE id = ?";

    private static final String CLEAR = "TRUNCATE table mydbtest.users";

    private static final SessionFactory sessionFactory =  Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(CREATE,User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(DROP,User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            NativeQuery<User> userNativeQuery = session.createNativeQuery(SAVE, User.class);
            userNativeQuery.setParameter(1,name);
            userNativeQuery.setParameter(2,lastName);
            userNativeQuery.setParameter(3,age);
            userNativeQuery.executeUpdate();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            NativeQuery<User> userNativeQuery = session.createNativeQuery(DELETE, User.class);
            userNativeQuery.setParameter(1,id).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            list = session.createNativeQuery(GET_ALL, User.class).list();
            transaction.commit();
            list.stream().forEach(System.out::println);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(CLEAR,User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
