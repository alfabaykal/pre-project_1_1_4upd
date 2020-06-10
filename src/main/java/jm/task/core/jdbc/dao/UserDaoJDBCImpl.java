package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public static final String createUsersTablePlease =
            "CREATE TABLE IF NOT EXISTS users\n" +
                    "(id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,\n" +
                    "name VARCHAR(45) NOT NULL,\n" +
                    "lastName VARCHAR(45) NOT NULL,\n" +
                    "age TINYINT NOT NULL)\n" +
                    "CHARSET = utf8;";

    public static final String dropUsersTablePlease =
            "DROP TABLE IF EXISTS users;";

    public static final String saveUserPlease =
            "INSERT INTO users(name, lastName, age) " +
                    "VALUES(?, ?, ?);";

    public static final String removeUserByIdPlease =
            "DELETE FROM users " +
                    "WHERE id = ?;";

    public static final String getAllUsersPlease = "SELECT * FROM users;";
    public static final String cleanUsersTablePlease = "DELETE FROM users;";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createUsersTablePlease);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(dropUsersTablePlease);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(saveUserPlease)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(removeUserByIdPlease)) {
            statement.setLong(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getAllUsersPlease);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                usersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(cleanUsersTablePlease);
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
