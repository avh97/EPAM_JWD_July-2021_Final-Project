package by.khaletski.project.dao;

import by.khaletski.project.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Dao class "UserDao"
 *
 * @author Anton Khaletski
 */

public class UserDao {
    static final Logger LOGGER = LogManager.getLogger(UserDao.class);
    private static final String SQL_FIND_ALL_USERS
            = "SELECT id, email, name, surname, patronymic, role FROM users";
    private static final String SQL_FIND_USERS_BY_NAME
            = "SELECT id, email, name, patronymic, surname, role FROM users WHERE name=?";
    private static final String SQL_FIND_USERS_BY_PATRONYMIC
            = "SELECT id, email, name, patronymic, surname, role FROM users WHERE patronymic=?";
    private static final String SQL_FIND_USERS_BY_SURNAME
            = "SELECT id, email, name, patronymic, surname, role FROM users WHERE surname=?";
    private static final String SQL_FIND_USERS_BY_ROLE
            = "SELECT id, email, name, patronymic, surname, role FROM users WHERE role=?";
    private static final String SQL_ADD_USER
            = "INSERT INTO users (id, email, name, patronymic, surname, role) values(?,?,?,?,?,?)";
    private static final String SQL_REMOVE_USER
            = "DELETE FROM users WHERE email=?";
    private static final String SQL_CHANGE_USER_ROLE
            = "UPDATE users SET role=? WHERE user_id=?";
    private static final String SQL_CHANGE_USER_INFO
            = "UPDATE users SET email=?, name=?, patronymic=?, surname=?, role=? WHERE id=?";

    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_USERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userList.add(getUser(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return userList;
    }

    public List<User> findUsersByName(String name) {
        List<User> userList = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USERS_BY_NAME)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userList.add(getUser(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return userList;
    }

    public List<User> findUsersByPatronymic(String patronymic) {
        List<User> userList = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USERS_BY_PATRONYMIC)) {
            statement.setString(1, patronymic);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userList.add(getUser(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return userList;
    }

    public List<User> findUsersBySurname(String surname) {
        List<User> userList = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USERS_BY_SURNAME)) {
            statement.setString(1, surname);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userList.add(getUser(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return userList;
    }

    public List<User> findUsersByRole(User.Role role) {
        List<User> userList = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USERS_BY_ROLE)) {
            statement.setString(1, role.name());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userList.add(getUser(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return userList;
    }

    public boolean addUser(User user, String password) {
        LOGGER.info("Attempt to add user to the database");
        boolean ifAdded = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_USER)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, password);
            statement.setString(3, user.getName());
            statement.setString(4, user.getPatronymic());
            statement.setString(5, user.getSurname());
            int rowCount = statement.executeUpdate();
            if (rowCount != 0) {
                ifAdded = true;
                LOGGER.info("User has been added");
            } else {
                LOGGER.info("User has not been added");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return ifAdded;
    }

    public boolean removeUser(User user) {
        LOGGER.info("Attempt to remove user from the database");
        boolean ifRemoved = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_REMOVE_USER)) {
            statement.setString(1, user.getEmail());
            int rowCount = statement.executeUpdate();
            if (rowCount != 0) {
                ifRemoved = true;
                LOGGER.info("User has been removed");
            } else {
                LOGGER.info("User has not been removed");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return ifRemoved;
    }


    public boolean changePersonalInfo(User user) {
        LOGGER.info("Attempt to change user personal info");
        boolean isChanged = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CHANGE_USER_INFO)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPatronymic());
            statement.setString(3, user.getSurname());
            statement.setString(4, user.getRole().name());
            statement.setInt(5, user.getId());
            int rowCount = statement.executeUpdate();
            if (rowCount != 0) {
                isChanged = true;
                LOGGER.info("User personal information has been changed");
            } else {
                LOGGER.info("User personal information has not been changed");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return isChanged;
    }

    public boolean changeUserRole(int id, User.Role role) {
        LOGGER.info("Attempt to change user role");
        boolean isChanged = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CHANGE_USER_ROLE)) {
            statement.setString(1, role.name());
            statement.setLong(2, id);
            int rowCount = statement.executeUpdate();
            if (rowCount != 0) {
                isChanged = true;
                LOGGER.info("User role has been changed");
            } else {
                LOGGER.info("User role has been changed");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return isChanged;
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String email = resultSet.getString("email");
        String name = resultSet.getString("name");
        String patronymic = resultSet.getString("patronymic");
        String surname = resultSet.getString("surname");
        User.Role role = User.Role.valueOf(resultSet.getString("role").toUpperCase());
        return new User.Builder()
                .setUserId(id)
                .setEmail(email)
                .setName(name)
                .setPatronymic(patronymic)
                .setSurname(surname)
                .setRole(role)
                .build();
    }
}
