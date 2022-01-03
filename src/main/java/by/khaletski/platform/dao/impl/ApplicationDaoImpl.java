package by.khaletski.platform.dao.impl;

import by.khaletski.platform.dao.ApplicationDao;
import by.khaletski.platform.dao.pool.ConnectionPool;
import by.khaletski.platform.entity.Application;
import by.khaletski.platform.entity.Conference;
import by.khaletski.platform.entity.Topic;
import by.khaletski.platform.entity.User;
import by.khaletski.platform.dao.exception.DaoException;
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
 * Dao class "ApplicationDao"
 *
 * @author Anton Khaletski
 */

public class ApplicationDaoImpl implements ApplicationDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SQL_FIND_ALL_APPLICATIONS
            = "SELECT applications.id, applications.user_id, applications.conference_id, \n" +
            "applications.application_description, applications.application_status, \n" +
            "conferences.topic_id, conferences.conference_name, conferences.conference_description, \n" +
            "conferences.conference_status, conferences.date, \n" +
            "topics.topic_name, topics.topic_description,\n" +
            "users.email, users.name, users.patronymic, users.surname, users.role FROM applications\n" +
            "INNER JOIN conferences ON applications.conference_id = conferences.id\n" +
            "INNER JOIN topics ON conferences.topic_id = topics.id\n" +
            "INNER JOIN users ON applications.user_id = users.id";
    private static final String SQL_FIND_APPLICATION_BY_ID
            = "SELECT applications.id, applications.user_id, applications.conference_id, \n" +
            "applications.application_description, applications.application_status, \n" +
            "conferences.topic_id, conferences.conference_name, conferences.conference_description, \n" +
            "conferences.conference_status, conferences.date, \n" +
            "topics.topic_name, topics.topic_description,\n" +
            "users.email, users.name, users.patronymic, users.surname, users.role FROM applications\n" +
            "INNER JOIN conferences ON applications.conference_id = conferences.id\n" +
            "INNER JOIN topics ON conferences.topic_id = topics.id\n" +
            "INNER JOIN users ON applications.user_id = users.id WHERE applications.id=?";
    private static final String SQL_FIND_APPLICATIONS_BY_USER_ID
            = "SELECT applications.id, applications.user_id, applications.conference_id, \n" +
            "applications.application_description, applications.application_status, \n" +
            "conferences.topic_id, conferences.conference_name, conferences.conference_description, \n" +
            "conferences.conference_status, conferences.date, \n" +
            "topics.topic_name, topics.topic_description,\n" +
            "users.email, users.name, users.patronymic, users.surname, users.role FROM applications\n" +
            "INNER JOIN conferences ON applications.conference_id = conferences.id\n" +
            "INNER JOIN topics ON conferences.topic_id = topics.id\n" +
            "INNER JOIN users ON applications.user_id = users.id WHERE applications.user_id=?";
    private static final String SQL_FIND_USERS_BY_CONFERENCE_ID
                = "SELECT applications.id, applications.user_id, applications.conference_id, \n" +
                        "applications.application_description, applications.application_status, \n" +
                        "conferences.topic_id, conferences.conference_name, conferences.conference_description, \n" +
                        "conferences.conference_status, conferences.date, \n" +
                        "topics.topic_name, topics.topic_description,\n" +
                        "users.email, users.name, users.patronymic, users.surname, users.role FROM applications\n" +
                        "INNER JOIN conferences ON applications.conference_id = conferences.id\n" +
                        "INNER JOIN topics ON conferences.topic_id = topics.id\n" +
                        "INNER JOIN users ON applications.user_id = users.id WHERE applications.conference_id=?";
    private static final String SQL_ADD_APPLICATION
            = "INSERT INTO applications (user_id, conference_id, application_description, application_status) values(?,?,?,?)";
    private static final String SQL_CHANGE_APPLICATION_STATUS
            = "UPDATE applications set application_status=? WHERE id=?";
    private static final String SQL_EDIT_APPLICATION
            = "UPDATE applications SET user_id=?, conference_id=?, application_description=?, status=? WHERE id=?";
    private static final String SQL_REMOVE_APPLICATION_BY_ID
            = "DELETE FROM applications WHERE id=?";

    @Override
    public List<Application> findAll() throws DaoException {
        LOGGER.info("Attempt to find all applications in the database");
        List<Application> applicationList = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_APPLICATIONS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                applicationList.add(retrieve(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find all applications in the database");
            throw new DaoException(e);
        }
        return applicationList;
    }

    @Override
    public Optional<Application> find(int id) throws DaoException {
        LOGGER.info("Attempt to find application by user id in the database");
        Optional<Application> optionalApplication = Optional.empty();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_APPLICATION_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Application application = retrieve(resultSet);
                optionalApplication = Optional.of(application);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find all applications by user id in the database");
            throw new DaoException(e);
        }
        return optionalApplication;
    }

    @Override
    public List<Application> findByUserId(int id) throws DaoException {
        LOGGER.info("Attempt to find applications by user id in the database");
        List<Application> applications = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_APPLICATIONS_BY_USER_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                applications.add(retrieve(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find all applications by user id in the database");
            throw new DaoException(e);
        }
        return applications;
    }

    @Override
    public List<Application> findByConferenceId(int id) throws DaoException {
        LOGGER.info("Attempt to find users by conference id in the database");
        List<Application> applications = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USERS_BY_CONFERENCE_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                applications.add(retrieve(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find users by conference id in the database");
            throw new DaoException(e);
        }
        return applications;
    }

    @Override
    public boolean add(Application application) throws DaoException {
        LOGGER.info("Attempt to add new application to the database");
        boolean isAdded = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_APPLICATION)) {
            statement.setInt(1, application.getUser().getId());
            statement.setInt(2, application.getConference().getId());
            statement.setString(3, application.getDescription());
            statement.setString(4, application.getStatus().name());
            int rowCount = statement.executeUpdate();
            if (rowCount != 0) {
                isAdded = true;
                LOGGER.info("New application has been added:");
            } else {
                LOGGER.error("New application has not been added");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to add new application to the database");
            throw new DaoException(e);
        }
        return isAdded;
    }

    @Override
    public boolean changeStatus(int id, Application.Status status)
            throws DaoException {
        LOGGER.info("Attempt to change application status in the database");
        boolean isChanged = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CHANGE_APPLICATION_STATUS)) {
            statement.setString(1, status.name());
            statement.setInt(2, id);
            int rowCount = statement.executeUpdate();
            if (rowCount != 0) {
                isChanged = true;
                LOGGER.info("Application status has been changed");
            } else {
                LOGGER.error("Application status has not been changed");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to change application status in the database");
            throw new DaoException(e);
        }
        return isChanged;
    }

    @Override
    public boolean edit(Application application) throws DaoException {
        LOGGER.info("Attempt to edit application in the database");
        boolean isEdited = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_EDIT_APPLICATION)) {
            statement.setInt(1, application.getUser().getId());
            statement.setInt(2, application.getConference().getId());
            statement.setString(3, application.getDescription());
            statement.setString(4, application.getStatus().name());
            statement.setInt(5, application.getId());
            int rowCount = statement.executeUpdate();
            if (rowCount != 0) {
                isEdited = true;
                LOGGER.info("Application has been edited");
            } else {
                LOGGER.error("Application has not been edited");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to edit application in the database");
            throw new DaoException(e);
        }
        return isEdited;
    }

    @Override
    public boolean remove(int id) throws DaoException {
        LOGGER.info("Attempt to remove application from the database");
        boolean ifRemoved = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_REMOVE_APPLICATION_BY_ID)) {
            statement.setInt(1, id);
            int rowCount = statement.executeUpdate();
            if (rowCount != 0) {
                ifRemoved = true;
                LOGGER.info("Application has been removed");
            } else {
                LOGGER.info("Application has not been removed");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to remove application from the database");
            throw new DaoException(e);
        }
        return ifRemoved;
    }

    private Application retrieve(ResultSet resultSet) throws SQLException {
        return new Application.Builder()
                .setId(resultSet.getInt("id"))
                .setUser(new User.Builder()
                        .setId(resultSet.getInt("user_id"))
                        .setEmail(resultSet.getString("email"))
                        .setName(resultSet.getString("name"))
                        .setPatronymic(resultSet.getString("patronymic"))
                        .setSurname(resultSet.getString("surname"))
                        .setRole(User.Role.valueOf(resultSet.getString("role")))
                        .build())
                .setConference(new Conference.Builder()
                        .setId(resultSet.getInt("conference_id"))
                        .setTopic(new Topic.Builder()
                                .setId(resultSet.getInt("topic_id"))
                                .setName(resultSet.getString("topic_name"))
                                .setDescription(resultSet.getString("topic_description"))
                                .build())
                        .setName(resultSet.getString("conference_name"))
                        .setDescription(resultSet.getString("conference_description"))
                        .setDate(resultSet.getDate("date"))
                        .setStatus(Conference.Status.valueOf(resultSet.getString("conference_status")))
                        .build())
                .setDescription(resultSet.getString("application_description"))
                .setStatus(Application.Status.valueOf(resultSet.getString("application_status")))
                .build();
    }
}
