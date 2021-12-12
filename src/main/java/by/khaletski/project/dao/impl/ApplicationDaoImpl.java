package by.khaletski.project.dao.impl;

import by.khaletski.project.dao.ApplicationDao;
import by.khaletski.project.dao.pool.ConnectionPool;
import by.khaletski.project.entity.Application;
import by.khaletski.project.entity.Conference;
import by.khaletski.project.entity.User;
import by.khaletski.project.dao.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
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
    private static final String SQL_FIND_APPLICATION_BY_ID
            = "SELECT conferences.conference_name, conferences.conference_status, conferences.date, "
            + "applications.application_description, applications.application_status, "
            + "users.name, users.patronymic, users.surname, users.role FROM conferences "
            + "INNER JOIN applications ON applications.conference_id = conferences.id "
            + "INNER JOIN users ON applications.user_id = users.id WHERE applications.id=?";
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
    public boolean addApplication(Application application) throws DaoException {
        LOGGER.info("Attempt to add new application to the database");
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
    public boolean editApplication(Application application) throws DaoException {
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
            LOGGER.error("Failed attempt to edit application in the database");
            throw new DaoException(e);
        }
        return isEdited;
    }

    @Override
    public boolean changeApplicationStatus(int applicationId, Application.Status applicationStatus)
            throws DaoException {
        LOGGER.info("Attempt to change application status in the database");
        boolean isChanged = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHANGE_APPLICATION_STATUS)) {
            preparedStatement.setString(1, applicationStatus.name());
            preparedStatement.setInt(2, applicationId);
            int rowCount = preparedStatement.executeUpdate();
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
    public List<Application> findAllApplications() throws DaoException {
        LOGGER.info("Attempt to find all applicationList in the database");
        List<Application> applicationList = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_APPLICATIONS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                applicationList.add(retrieveApplication(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find all applications in the database");
            throw new DaoException(e);
        }
        return applicationList;
    }

    @Override
    public Optional<Application> findApplicationById(int applicationId) throws DaoException {
        LOGGER.info("Attempt to find applicationList by user id in the database");
        Optional<Application> optionalApplication = Optional.empty();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_APPLICATION_BY_ID)) {
            preparedStatement.setInt(1, applicationId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Application application = retrieveApplication(resultSet);
                optionalApplication = Optional.of(application);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find all applications by user id in the database");
            throw new DaoException(e);
        }
        return optionalApplication;
    }

    @Override
    public List<Application> findApplicationsByStatus(Application.Status applicationStatus) throws DaoException {
        LOGGER.info("Attempt to find applicationList by application status id in the database");
        List<Application> applicationList = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(SQL_FIND_ALL_APPLICATIONS_BY_STATUS)) {
            preparedStatement.setString(1, applicationStatus.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                applicationList.add(retrieveApplication(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find all applications by application status in the database");
            throw new DaoException(e);
        }
        return applicationList;
    }

    @Override
    public List<Application> findApplicationsByDate(Date conferenceDate) throws DaoException {
        LOGGER.info("Attempt to find all applicationList by conferenceDate");
        List<Application> applicationList = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(SQL_FIND_ALL_APPLICATIONS_BY_DATE)) {
            preparedStatement.setDate(1, conferenceDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                applicationList.add(retrieveApplication(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find all applications by conference date in the database");
            throw new DaoException(e);
        }
        return applicationList;
    }

    private Application retrieveApplication(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int userId = resultSet.getInt("user_id");
        String userEmail = resultSet.getString("email");
        String userName = resultSet.getString("name");
        String userPatronymic = resultSet.getString("patronymic");
        String userSurname = resultSet.getString("surname");
        User.Role userRole = User.Role.valueOf(resultSet.getString("role").toUpperCase());
        User user = new User.Builder()
                .setUserId(userId)
                .setEmail(userEmail)
                .setName(userName)
                .setPatronymic(userPatronymic)
                .setSurname(userSurname)
                .setRole(userRole)
                .build();
        int conferenceId = resultSet.getInt("conference_id");
        String conferenceName = resultSet.getString("conference_name");
        String conferenceDescription = resultSet.getString("conference_description");
        Date conferenceDate = resultSet.getDate("date");
        Conference.Status conferenceStatus = Conference
                .Status.valueOf(resultSet.getString("conference_status").toUpperCase());
        Conference conference = new Conference.Builder()
                .setId(conferenceId)
                .setName(conferenceName)
                .setDescription(conferenceDescription)
                .setDate(conferenceDate)
                .setStatus(conferenceStatus)
                .build();
        Application.Status applicationStatus = Application
                .Status.valueOf(resultSet.getString("application_status"));
        return new Application.Builder()
                .setId(id)
                .setUser(user)
                .setConference(conference)
                .setStatus(applicationStatus)
                .build();
    }
}
