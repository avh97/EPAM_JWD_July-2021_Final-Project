package by.khaletski.project.dao.impl;

import by.khaletski.project.dao.ApplicationDao;
import by.khaletski.project.dao.pool.ConnectionPool;
import by.khaletski.project.entity.Application;
import by.khaletski.project.entity.Conference;
import by.khaletski.project.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Dao class "ApplicationDao"
 *
 * @author Anton Khaletski
 */

public class ApplicationDaoImpl implements ApplicationDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SQL_ADD_APPLICATION
            = "INSERT INTO applications (user_id,conference_id,descripton,status) values(?,?,?,?)";
    private static final String SQL_EDIT_APPLICATION
            = "UPDATE applications SET user_id=?, conference_id=?, application_description=?, status=? WHERE id=?";
    private static final String SQL_CHANGE_APPLICATION_STATUS
            = "UPDATE applications set status=? WHERE id=?";
    private static final String SQL_FIND_ALL_APPLICATIONS
            = "SELECT conferences.conference_name, conferences.conference_status, conferences.date, "
            + "applications.application_description, applications.application_status, "
            + "users.name, users.patronymic, users.surname, users.role FROM conferences "
            + "INNER JOIN applications ON applications.conference_id = conferences.id "
            + "INNER JOIN users ON applications.user_id = users.id";
    private static final String SQL_FIND_ALL_APPLICATIONS_BY_USER_ID
            = "SELECT conferences.conference_name, conferences.conference_status, conferences.date, "
            + "applications.application_description, applications.application_status, "
            + "users.name, users.patronymic, users.surname, users.role FROM conferences "
            + "INNER JOIN applications ON applications.conference_id = conferences.id "
            + "INNER JOIN users ON applications.user_id = users.id WHERE users.id=?";
    private static final String SQL_FIND_ALL_APPLICATIONS_BY_STATUS
            = "SELECT conferences.conference_name, conferences.conference_status, conferences.date, "
            + "applications.application_description, applications.application_status, "
            + "users.name, users.patronymic, users.surname, users.role FROM conferences "
            + "INNER JOIN applications ON applications.conference_id = conferences.id "
            + "INNER JOIN users ON applications.user_id = users.id WHERE applications.status=?";
    private static final String SQL_FIND_ALL_APPLICATIONS_BY_DATE
            = "SELECT conferences.conference_name, conferences.conference_status, conferences.date, "
            + "applications.application_description, applications.application_status, "
            + "users.name, users.patronymic, users.surname, users.role FROM conferences "
            + "INNER JOIN applications ON applications.conference_id = conferences.id "
            + "INNER JOIN users ON applications.user_id = users.id WHERE conferences.date=?";

    @Override
    public boolean addApplication(Application application) {
        LOGGER.info("Try to add application to the database");
        boolean isAdded = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_APPLICATION)) {
            preparedStatement.setInt(1, application.getUser().getId());
            preparedStatement.setInt(2, application.getConference().getId());
            preparedStatement.setString(3, application.getApplicationDescription());
            preparedStatement.setString(4, application.getApplicationStatus().name());
            int rowCount = preparedStatement.executeUpdate();
            if (rowCount != 0) {
                isAdded = true;
                LOGGER.info("Application has been added:");
            } else {
                LOGGER.error("Application has not been added");
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
        }
        return isAdded;
    }

    @Override
    public boolean editApplication(Application application) {
        LOGGER.info("Attempt to edit application in the database");
        boolean isEdited = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_EDIT_APPLICATION)) {
            preparedStatement.setInt(1, application.getUser().getId());
            preparedStatement.setInt(2, application.getConference().getId());
            preparedStatement.setString(3, application.getApplicationDescription());
            preparedStatement.setString(4, application.getApplicationStatus().name());
            preparedStatement.setInt(5, application.getId());
            int rowCount = preparedStatement.executeUpdate();
            if (rowCount != 0) {
                isEdited = true;
                LOGGER.info("Application has been edited");
            } else {
                LOGGER.error("Application has not been edited");
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
        }
        return isEdited;
    }

    @Override
    public boolean changeApplicationStatus(int id, Application.Status status) {
        LOGGER.info("Attempt to change application status");
        boolean isChanged = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHANGE_APPLICATION_STATUS)) {
            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, id);
            int rowCount = preparedStatement.executeUpdate();
            if (rowCount != 0) {
                isChanged = true;
                LOGGER.info("Application status has been changed");
            } else {
                LOGGER.error("Application status has not been changed");
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
        }
        return isChanged;
    }

    @Override
    public List<Application> findAllApplications() {
        LOGGER.info("Attempt to find all applications");
        List<Application> applications = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_APPLICATIONS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                applications.add(getApplication(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException");
        }
        LOGGER.info("Applications have been found");
        return applications;
    }

    @Override
    public List<Application> findApplicationsByUserId(int userId) {
        LOGGER.info("Attempt to find applications by user id");
        List<Application> applications = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_APPLICATIONS_BY_USER_ID)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                applications.add(getApplication(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
        }
        LOGGER.info("Applications have been found");
        return applications;
    }

    @Override
    public List<Application> findApplicationsByStatus(Application.Status status) {
        LOGGER.info("Attempt to find all applications by status");
        List<Application> applications = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(SQL_FIND_ALL_APPLICATIONS_BY_STATUS)) {
            preparedStatement.setString(1, status.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                applications.add(getApplication(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
        }
        LOGGER.info("Applications have been found");
        return applications;
    }

    @Override
    public List<Application> findApplicationsByDate(Date date) {
        LOGGER.info("Attempt to find all applications by date");
        List<Application> applications = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(SQL_FIND_ALL_APPLICATIONS_BY_DATE)) {
            preparedStatement.setDate(1, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                applications.add(getApplication(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
        }
        LOGGER.info("Applications have been found");
        return applications;
    }

    private Application getApplication(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int userId = resultSet.getInt("user_id");
        String email = resultSet.getString("email");
        String name = resultSet.getString("name");
        String patronymic = resultSet.getString("patronymic");
        String surname = resultSet.getString("surname");
        User.Role role = User.Role.valueOf(resultSet.getString("role").toUpperCase());
        User user = new User.Builder()
                .setUserId(userId)
                .setEmail(email)
                .setName(name)
                .setPatronymic(patronymic)
                .setSurname(surname)
                .setRole(role)
                .build();
        int conferenceId = resultSet.getInt("conference_id");
        String conferenceName = resultSet.getString("conference_name");
        String description = resultSet.getString("conference_description");
        Date date = resultSet.getDate("date");
        Conference.Status conferenceStatus = Conference
                .Status.valueOf(resultSet.getString("conference_status").toUpperCase());
        Conference conference = new Conference.Builder()
                .setId(id)
                .setName(name)
                .setDescription(description)
                .setDate(date)
                .setStatus(conferenceStatus)
                .build();
        Application.Status applicationStatus = Application
                .Status.valueOf(resultSet.getString("application_status"));
        Application application = new Application.Builder()
                .setId(id)
                .setUser(user)
                .setConferenceId(conference)
                .setStatus(applicationStatus)
                .build();
        return application;
    }
}
