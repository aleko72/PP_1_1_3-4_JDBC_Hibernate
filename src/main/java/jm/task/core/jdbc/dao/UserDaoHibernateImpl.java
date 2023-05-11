package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        Transaction tr = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            session.createSQLQuery(Util.QUERY_CREATE_TABLE).executeUpdate();
            tr.commit();
        }catch (Exception e){
            if(tr != null){
                tr.rollback();
            }
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction tr = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            session.createSQLQuery(Util.QUERY_DROP_TABLE).executeUpdate();
            tr.commit();
        }catch (Exception e){
            if(tr != null){
                tr.rollback();
            }
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tr = null;
        try(Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            session.save(new User(name, lastName, age));
            tr.commit();
        }catch (Exception e){
            if(tr != null){
                tr.rollback();
            }
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tr = null;
        try(Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            tr.commit();
        }catch (Exception e){
            if(tr != null){
                tr.rollback();
            }
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
          return session.createQuery("from User", User.class).list();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction tr = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            session.createQuery("Delete From User").executeUpdate();
            tr.commit();
        }catch (Exception e){
            if(tr != null){
                tr.rollback();
            }
            e.printStackTrace();
            throw e;
        }
    }
}
