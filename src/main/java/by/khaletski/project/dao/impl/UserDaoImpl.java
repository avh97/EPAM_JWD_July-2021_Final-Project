package by.khaletski.project.dao.impl;

import by.khaletski.project.dao.UserDao;
import by.khaletski.project.dao.exception.DaoException;
import by.khaletski.project.dao.pool.ConnectionPool;
import by.khaletski.project.entity.User;
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
 * Dao class "UserDao"
 *
 * @author Anton Khaletski
 */

public class UserDaoImpl implements UserDao {
    static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);
    private static final String SQL_FIND_ALL_USERS
            = "SELECT id, email, name, surname, patronymic, role FROM users";
    private static final String SQL_FIND_USERS_BY_SURNAME
            = "SELECT id, email, name, patronymic, surname, role FROM users WHERE surname=?";
    private static final String SQL_FIND_USERS_BY_ROLE
            = "SELECT id, email, name, patronymic, surname, role FROM users WHERE role=?";
    private static final String SQL_FIND_USER_BY_ID
            = "SELECT id, email, name, patronymic, surname, role FROM users WHERE id=?";
    private static final String SQL_ADD_USER
            = "INSERT INTO users (email, password, name, patronymic, surname) values(?,?,?,?,?)";
    private static final String SQL_REMOVE_USER_BY_ID
            = "DELETE FROM users WHERE id=?";
    private static final String SQL_CHANGE_USER_ROLE_BY_ID
            = "UPDATE users SET role=? WHERE id=?";
    private static final String SQL_EDIT_USER_INFO_BY_ID
            = "UPDATE users SET name=?, patronymic=?, surname=? WHERE id=?";
    private static final String SQL_FIND_PASSWORD_BY_EMAIL
            = "SELECT password FROM users WHERE email=?";
    private static final String SQL_FIND_USER_BY_EMAIL =
            "SELECT id, email, name, patronymic, surname, role FROM users WHERE email=?";

    @Override
    public List<User> findAllUsers() throws DaoException {
        LOGGER.info("Attempt to find all users in the database");
        List<User> userList = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_USERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userList.add(retrieveUser(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find all users in the database");
            throw new DaoException(e);
        }
        return userList;
    }

    @Override
    public List<User> findUsersBySurname(String userSurname) throws DaoException {
        LOGGER.info("Attempt to find all users by surname in the database");
        List<User> userList = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USERS_BY_SURNAME)) {
            statement.setString(1, userSurname);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userList.add(retrieveUser(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find all users by surname in the database");
            throw new DaoException(e);
        }
        return userList;
    }

    @Override
    public List<User> findUsersByRole(User.Role userRole) throws DaoException {
        LOGGER.info("Attempt to find all users by role in the database");
        List<User> userList = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USERS_BY_ROLE)) {
            statement.setString(1, userRole.name());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userList.add(retrieveUser(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find all users by role in the database");
            throw new DaoException(e);
        }
        return userList;
    }

    @Override
    public Optional<User> findUserById(int userId) throws DaoException {
        LOGGER.info("Attempt to find user by user ID in the database");
        Optional<User> optionalUser = Optional.empty();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_ID)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = retrieveUser(resultSet);
                optionalUser = Optional.of(user);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find user by user ID in the database");
            throw new DaoException(e);
        }
        return optionalUser;
    }

    @Override
    public boolean addUser(User user, String userPassword) throws DaoException {
        LOGGER.info("Attempt to add new user to the database");
        boolean ifAdded = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_USER)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, userPassword);
            statement.setString(3, user.getName());
            statement.setString(4, user.getPatronymic());
            statement.setString(5, user.getSurname());
            int rowCount = statement.executeUpdate();
            if (rowCount != 0) {
                ifAdded = true;
                LOGGER.info("New user has been added");
            } else {
                LOGGER.info("New has not been added");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to add new user to the database");
            throw new DaoException(e);
        }
        return ifAdded;
    }

    @Override
    public boolean removeUser(int userId) throws DaoException {
        LOGGER.info("Attempt to remove user from the database");
        boolean ifRemoved = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_REMOVE_USER_BY_ID)) {
            statement.setInt(1, userId);
            int rowCount = statement.executeUpdate();
            if (rowCount != 0) {
                ifRemoved = true;
                LOGGER.info("User has been removed");
            } else {
                LOGGER.info("User has not been removed");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to remove user from the database");
            throw new DaoException(e);
        }
        return ifRemoved;
    }

    @Override
    public boolean changeUserRole(int userId, User.Role userRole) throws DaoException {
        LOGGER.info("Attempt to change user role in the database");
        boolean isChanged = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CHANGE_USER_ROLE_BY_ID)) {
            statement.setString(1, userRole.name());
            statement.setLong(2, userId);
            int rowCount = statement.executeUpdate();
            if (rowCount != 0) {
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
    public boolean editUserInfo(User user) throws DaoException {
        LOGGER.info("Attempt to change user info in the database");
        boolean isChanged = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_EDIT_USER_INFO_BY_ID)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPatronymic());
            statement.setString(3, user.getSurname());
            statement.setInt(4, user.getId());
            int rowCount = statement.executeUpdate();
            if (rowCount != 0) {
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
    public Optional<String> findPasswordByEmail(String userEmail) throws DaoException {
        LOGGER.info("Attempt to find user by password in the database");
        Optional<String> optionalPassword;
        String password;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_PASSWORD_BY_EMAIL)) {
            statement.setString(1, userEmail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                password = resultSet.getString("password");
                optionalPassword = Optional.of(password);
            } else {
                optionalPassword = Optional.empty();
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find user by password in the database");
            throw new DaoException(e);
        }
        return optionalPassword;
    }

    @Override
    public Optional<User> findUserByEmail(String userEmail) throws DaoException {
        LOGGER.info("Attempt to find user by email in the database");
        Optional<User> optionalUser;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_EMAIL)) {
            statement.setString(1, userEmail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = retrieveUser(resultSet);
                optionalUser = Optional.of(user);
            } else {
                optionalUser = Optional.empty();
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find user by email in the database");
            throw new DaoException(e);
        }
        return optionalUser;
    }

    private User retrieveUser(ResultSet resultSet) throws SQLException {
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
