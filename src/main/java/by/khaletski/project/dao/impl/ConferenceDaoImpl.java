package by.khaletski.project.dao.impl;

import by.khaletski.project.dao.ConferenceDao;
import by.khaletski.project.dao.pool.ConnectionPool;
import by.khaletski.project.entity.Conference;
import by.khaletski.project.entity.Topic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Dao class "ConferenceDao"
 *
 * @author Anton Khaletski
 */

public class ConferenceDaoImpl implements ConferenceDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SQL_FIND_ALL_CONFERENCES
            = "SELECT id, topic_id, conference_name, conference_description, date, conference_status FROM conferences";
    private static final String SQL_FIND_CONFERENCE_BY_NAME
            = "SELECT id, topic_id, conference_name, conference_description, date, conference_status "
            + "FROM conferences WHERE conference_name=?";
    private static final String SQL_ADD_CONFERENCE
            = "INSERT INTO conferences"
            + " (topic_id, conference_name, conference_description, date, conference_status) values(?,?,?,?,?)";
    private static final String SQL_REMOVE_CONFERENCE_BY_ID
            = "DELETE FROM conferences WHERE id=?";
    private static final String SQL_EDIT_CONFERENCE_BY_ID
            = "UPDATE conferences "
            + "SET conference_name=?, conference_description=?, date=?, conference_status=? WHERE id=?";
    private static final String SQL_CHANGE_CONFERENCE_STATUS_BY_ID
            = "UPDATE conferences SET conference_status=? WHERE id=?";

    @Override
    public List<Conference> findAllConferences() {
        LOGGER.info("Attempt to find all conferences in the database");
        List<Conference> conferenceList = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_CONFERENCES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                conferenceList.add(getConference(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
        }
        return conferenceList;
    }

    @Override
    public List<Conference> findConferencesByName(String name) {
        LOGGER.info("Attempt to find conferences in the database by name");
        List<Conference> conferenceList = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_CONFERENCE_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                conferenceList.add(getConference(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
        }
        return conferenceList;
    }

    @Override
    public boolean addConference(Conference conference) {
        LOGGER.info("Attempt to add conference to the database");
        boolean isAdded = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_CONFERENCE)) {
            preparedStatement.setInt(1, conference.getTopic().getId());
            preparedStatement.setString(2, conference.getConferenceName());
            preparedStatement.setString(3, conference.getConferenceDescription());
            preparedStatement.setDate(4, conference.getDate());
            preparedStatement.setString(5, conference.getConferenceStatus().name());
            int rowCount = preparedStatement.executeUpdate();
            if (rowCount != 0) {
                isAdded = true;
                LOGGER.info("Conference has been added");
            } else {
                LOGGER.error("Conference has not been added");
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
        }
        return isAdded;
    }

    @Override
    public boolean removeConference(Conference conference) {
        LOGGER.info("Attempt to remove conference from the database");
        boolean ifRemoved = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_REMOVE_CONFERENCE_BY_ID)) {
            preparedStatement.setString(1, conference.getConferenceName());
            int rowCount = preparedStatement.executeUpdate();
            if (rowCount != 0) {
                ifRemoved = true;
                LOGGER.info("Conference has been removed");
            } else {
                LOGGER.info("Conference has not been removed");
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
        }
        return ifRemoved;
    }

    @Override
    public boolean editConference(Conference conference) {
        LOGGER.info("Attempt to change conference in the database");
        boolean isChanged = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_EDIT_CONFERENCE_BY_ID)) {
            preparedStatement.setString(1, conference.getConferenceName());
            preparedStatement.setString(2, conference.getConferenceDescription());
            preparedStatement.setDate(3, conference.getDate());
            preparedStatement.setString(4, conference.getConferenceStatus().name());
            preparedStatement.setInt(5, conference.getId());
            int rowCount = preparedStatement.executeUpdate();
            if (rowCount != 0) {
                isChanged = true;
                LOGGER.info("Conference has been changed");
            } else {
                LOGGER.error("Conference has not been changed");
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
        }
        return isChanged;
    }

    @Override
    public boolean changeConferenceStatus(int id, Conference.Status status) {
        LOGGER.info("Attempt to change conference status in the database");
        boolean isChanged = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHANGE_CONFERENCE_STATUS_BY_ID)) {
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
            LOGGER.error("SQL exception");
        }
        return isChanged;
    }

    private Conference getConference(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("conference_name");
        String description = resultSet.getString("conference_description");
        Date date = resultSet.getDate("date");
        Conference.Status status = Conference
                .Status.valueOf(resultSet.getString("conference_status").toUpperCase());
        return new Conference.Builder()
                .setId(id)
                .setName(name)
                .setDescription(description)
                .setDate(date)
                .setStatus(status)
                .build();
    }
}