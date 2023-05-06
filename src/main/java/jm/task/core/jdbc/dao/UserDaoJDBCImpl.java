package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Util dbUtil;
    public UserDaoJDBCImpl() {
        dbUtil = new Util();
    }

    public void createUsersTable() {
        statementExecute("CREATE TABLE IF NOT EXISTS users (" +
                               "`id` INT NOT NULL AUTO_INCREMENT," +
                               "`name` VARCHAR(45) NOT NULL," +
                               "`lastName` VARCHAR(45) NOT NULL," +
                               "`age` INT(3) NULL," +
                               "PRIMARY KEY (`id`))" +
                               " ENGINE = InnoDB" +
                               " DEFAULT CHARACTER SET = utf8");
    }

    public void dropUsersTable() {
        statementExecute("DROP TABLE IF EXISTS users");
    }

    public void saveUser(String name, String lastName, byte age) {
        statementExecute(String.format("INSERT INTO users (name, lastName, age) values ('%s', '%s', %d)",
                name, lastName, age));
    }

    public void removeUserById(long id) {
        statementExecute(String.format("DELETE FROM users WHERE id = %d", id));
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = dbUtil.getConnection(); Statement statement = connection.createStatement()) {
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
        statementExecute("DELETE FROM users");
    }

    private void statementExecute(String query) {
        try (Connection connection = dbUtil.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
