package by.khaletski.platform.dao.impl;

import by.khaletski.platform.dao.UserDao;
import by.khaletski.platform.dao.exception.DaoException;
import by.khaletski.platform.dao.pool.ConnectionPool;
import by.khaletski.platform.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Dao class "UserDao".
 * The methods in this class are used for creating a PreparedStatement, executing the query
 * and processing the ResultSet object.
 *
 * @author Anton Khaletski
 */

public class UserDaoImpl implements UserDao {
    static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);
    private static final String SQL_FIND_ALL_USERS
            = "SELECT id, email, name, surname, patronymic, role FROM users";
    private static final String SQL_FIND_USER_BY_ID
            = "SELECT id, email, name, patronymic, surname, role FROM users WHERE id=?";
    private static final String SQL_ADD_USER
            = "INSERT INTO users (email, password, name, patronymic, surname) values(?,?,?,?,?)";
    private static final String SQL_CHANGE_USER_ROLE_BY_ID
            = "UPDATE users SET role=? WHERE id=?";
    private static final String SQL_EDIT_USER_INFO_BY_ID
            = "UPDATE users SET email=?, name=?, patronymic=?, surname=? WHERE id=?";
    private static final String SQL_REMOVE_USER_BY_ID
            = "DELETE FROM users WHERE id=?";
    private static final String SQL_FIND_USER_BY_EMAIL =
            "SELECT id, email, name, patronymic, surname, role FROM users WHERE email=?";
    private static final String SQL_FIND_PASSWORD_BY_EMAIL
            = "SELECT password FROM users WHERE email=?";

    @Override
    public List<User> findAll() throws DaoException {
        LOGGER.info("Attempt to find all users in the database");
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_USERS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(retrieve(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find all users in the database");
            throw new DaoException(e);
        }
        return users;
    }

    @Override
    public Optional<User> find(int id) throws DaoException {
        LOGGER.info("Attempt to find user by user ID in the database");
        Optional<User> optional = Optional.empty();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = retrieve(resultSet);
                optional = Optional.of(user);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find user by user ID in the database");
            throw new DaoException(e);
        }
        return optional;
    }

    @Override
    public boolean add(User user, String password) throws DaoException {
        LOGGER.info("Attempt to add new user to the database");
        boolean isAdded = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_USER)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, password);
            statement.setString(3, user.getName());
            statement.setString(4, user.getPatronymic());
            statement.setString(5, user.getSurname());
            int counter = statement.executeUpdate();
            if (counter != 0) {
                isAdded = true;
                LOGGER.info("New user has been added");
            } else {
                LOGGER.info("New has not been added");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to add new user to the database");
            throw new DaoException(e);
        }
        return isAdded;
    }

    @Override
    public boolean changeRole(int id, User.Role role) throws DaoException {
        LOGGER.info("Attempt to change user role in the database");
        boolean isChanged = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CHANGE_USER_ROLE_BY_ID)) {
            statement.setString(1, role.name());
            statement.setLong(2, id);
            int counter = statement.executeUpdate();
            if (counter != 0) {
                isChanged = true;
                LOGGER.info("User role has been changed");
            } else {
                LOGGER.info("User role has been changed");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to change user role in the database");
            throw new DaoException(e);
        }
        return isChanged;
    }

    @Override
    public boolean edit(User user) throws DaoException {
        LOGGER.info("Attempt to change user info in the database");
        boolean isChanged = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_EDIT_USER_INFO_BY_ID)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPatronymic());
            statement.setString(4, user.getSurname());
            statement.setInt(5, user.getId());
            int counter = statement.executeUpdate();
            if (counter != 0) {
                isChanged = true;
                LOGGER.info("User info has been changed");
            } else {
                LOGGER.info("User info has not been changed");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to change user info in the database");
            throw new DaoException(e);
        }
        return isChanged;
    }

    @Override
    public boolean remove(int id) throws DaoException {
        LOGGER.info("Attempt to remove user from the database");
        boolean isRemoved = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_REMOVE_USER_BY_ID)) {
            statement.setInt(1, id);
            int counter = statement.executeUpdate();
            if (counter != 0) {
                isRemoved = true;
                LOGGER.info("User has been removed");
            } else {
                LOGGER.info("User has not been removed");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to remove user from the database");
            throw new DaoException(e);
        }
        return isRemoved;
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        LOGGER.info("Attempt to find user by email in the database");
        Optional<User> optional;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = retrieve(resultSet);
                optional = Optional.of(user);
            } else {
                optional = Optional.empty();
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find user by email in the database");
            throw new DaoException(e);
        }
        return optional;
    }

    @Override
    public Optional<String> findPasswordByEmail(String email) throws DaoException {
        LOGGER.info("Attempt to find user by password in the database");
        Optional<String> optional;
        String password;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_PASSWORD_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                password = resultSet.getString("password");
                optional = Optional.of(password);
            } else {
                optional = Optional.empty();
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find user by password in the database");
            throw new DaoException(e);
        }
        return optional;
    }

    /**
     * This method creates an object from ResultSet.
     * @param resultSet
     * @return
     * @throws SQLException
     */

    private User retrieve(ResultSet resultSet) throws SQLException {
        return new User.Builder()
                .setId(resultSet.getInt("id"))
                .setEmail(resultSet.getString("email"))
                .setName(resultSet.getString("name"))
                .setPatronymic(resultSet.getString("patronymic"))
                .setSurname(resultSet.getString("surname"))
                .setRole(User.Role.valueOf(resultSet.getString("role")))
                .build();
    }
}
