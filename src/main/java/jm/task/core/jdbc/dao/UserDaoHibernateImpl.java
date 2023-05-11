package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
        User user = new User(name, lastName, age);
        try(Session session = Util.getSessionFactory().openSession()) {
            Transaction tr = session.beginTransaction();
            session.save(user);
            tr.commit();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = Util.getSessionFactory().openSession()) {
            Transaction tr = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            tr.commit();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
           return (List<User>) session.createQuery("From User").list();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tr = session.beginTransaction();
            session.createQuery("Delete From User").executeUpdate();
            tr.commit();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
