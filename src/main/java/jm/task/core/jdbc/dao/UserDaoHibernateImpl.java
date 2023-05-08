package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(Util.QUERY_CREATE_TABLE).executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(Util.QUERY_DROP_TABLE).executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
           return (List<User>) session.createQuery("from User").list();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
