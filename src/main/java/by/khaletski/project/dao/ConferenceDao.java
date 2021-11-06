package by.khaletski.project.dao;

import by.khaletski.project.entity.Conference;
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
 * Dao class "ConferenceDao"
 *
 * @author Anton Khaletski
 */

public class ConferenceDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SQL_FIND_ALL_CONFERENCES
            = "SELECT id, topic_id, name, description, date, status FROM conferences";
    private static final String SQL_FIND_CONFERENCE_BY_NAME
            = "SELECT id, topic_id, name, description, date, status FROM conferences WHERE name=?";
    private static final String SQL_ADD_CONFERENCE
            = "INSERT INTO conferences (topic_id, name, description, date, status) values(?,?,?,?,?)";
    private static final String SQL_REMOVE_CONFERENCE
            = "DELETE FROM conferences WHERE id=?";
    private static final String SQL_EDIT_CONFERENCE
            = "UPDATE conferences SET name=?, description=?, date=?, status=? WHERE id=?";
    private static final String SQL_CHANGE_CONFERENCE_STATUS
            = "UPDATE conferences SET status=? WHERE id=?";

    public List<Conference> findAll() {
        List<Conference> conferenceList = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_CONFERENCES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                conferenceList.add(getConference(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return conferenceList;
    }

    public List<Conference> findConferencesByName(String name) {
        List<Conference> conferenceList = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_CONFERENCE_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                conferenceList.add(getConference(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return conferenceList;
    }

    public boolean addConference(Conference conference) {
        LOGGER.info("Attempt to add conference to the database");
        boolean isAdded = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_CONFERENCE)) {
            preparedStatement.setInt(1, conference.getTopic().getId());
            preparedStatement.setString(2, conference.getName());
            preparedStatement.setString(3, conference.getDescription());
            preparedStatement.setDate(4, conference.getDate());
            preparedStatement.setString(5, conference.getStatus().name());
            int rowCount = preparedStatement.executeUpdate();
            if (rowCount != 0) {
                isAdded = true;
                LOGGER.info("Conference has been added");
            } else {
                LOGGER.error("Conference has not been added");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return isAdded;
    }

    public boolean removeConference(Conference conference) {
        LOGGER.info("Attempt to remove conference from the database");
        boolean ifRemoved = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_REMOVE_CONFERENCE)) {
            preparedStatement.setString(1, conference.getName());
            int rowCount = preparedStatement.executeUpdate();
            if (rowCount != 0) {
                ifRemoved = true;
                LOGGER.info("Conference has been removed");
            } else {
                LOGGER.info("Conference has not been removed");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return ifRemoved;
    }

    public boolean editConference(Conference conference) {
        LOGGER.info("Attempt to change conference in the database");
        boolean isChanged = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_EDIT_CONFERENCE)) {
            preparedStatement.setString(1, conference.getName());
            preparedStatement.setString(2, conference.getDescription());
            preparedStatement.setDate(3, conference.getDate());
            preparedStatement.setString(4, conference.getStatus().name());
            preparedStatement.setInt(5, conference.getId());
            int rowCount = preparedStatement.executeUpdate();
            if (rowCount != 0) {
                isChanged = true;
                LOGGER.info("Conference has been changed");
            } else {
                LOGGER.error("Conference has not been changed");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return isChanged;
    }

    public boolean changeConferenceStatus(int id, Conference.Status status) {
        LOGGER.info("Attempt to change conference status");
        boolean isChanged = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHANGE_CONFERENCE_STATUS)) {
            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, id);
            int rowCount = preparedStatement.executeUpdate();
            if (rowCount != 0) {
                isChanged = true;
                LOGGER.info("Conference status has been changed");
            } else {
                LOGGER.info("Conference status has been changed");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return isChanged;
    }
    private Conference getConference(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        Date date = resultSet.getDate("date");
        Conference.Status status = Conference.Status.valueOf(resultSet.getString("status").toUpperCase());
        return new Conference.Builder()
                .setId(id)
                .setName(name)
                .setDescription(description)
                .setDate(date)
                .setStatus(status)
                .build();
    }
}