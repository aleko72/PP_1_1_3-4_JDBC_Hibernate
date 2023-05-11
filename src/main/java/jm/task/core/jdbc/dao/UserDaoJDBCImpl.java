package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        Connection connection = null;
        Statement statement;

        try {
            connection = Util.getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            statement.execute(Util.QUERY_CREATE_TABLE);
            connection.commit();
            connection.close();

        } catch(SQLException e){
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        Connection connection = null;
        Statement statement;

        try {
            connection = Util.getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            statement.execute(Util.QUERY_DROP_TABLE);
            connection.commit();
            connection.close();

        } catch(SQLException e){
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = Util.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(query);

            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
            connection.commit();
            connection.close();

        } catch(SQLException e){
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM users WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = Util.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(query);

            statement.setLong(1, id);
            statement.executeUpdate();
            connection.commit();
            connection.close();

        } catch(SQLException e){
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery("SELECT * FROM users");
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String lastName = result.getString("lastName");
                int age = result.getInt("age");
                User user = new User(name, lastName, (byte) age);
                user.setId((long) id);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String query = "DELETE FROM users";
        Connection connection = null;
        Statement statement;

        try {
            connection = Util.getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            statement.executeUpdate(query);
            connection.commit();
            connection.close();

        } catch(SQLException e){
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        }
    }
}
