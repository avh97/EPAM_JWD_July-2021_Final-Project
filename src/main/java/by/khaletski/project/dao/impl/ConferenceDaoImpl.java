package by.khaletski.project.dao.impl;

import by.khaletski.project.dao.ConferenceDao;
import by.khaletski.project.dao.TopicDao;
import by.khaletski.project.dao.pool.ConnectionPool;
import by.khaletski.project.entity.Conference;
import by.khaletski.project.dao.exception.DaoException;
import by.khaletski.project.entity.Topic;
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
 * Dao class "ConferenceDao"
 *
 * @author Anton Khaletski
 */

public class ConferenceDaoImpl implements ConferenceDao {
    TopicDao topicDao = new TopicDaoImpl();
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SQL_FIND_ALL_CONFERENCES
            = "SELECT id, topic_id, conference_name, conference_description, date, conference_status FROM conferences";
    private static final String SQL_FIND_CONFERENCE_BY_NAME
            = "SELECT id, topic_id, conference_name, conference_description, date, conference_status"
            + "FROM conferences WHERE conference_name=?";
    private static final String SQL_FIND_CONFERENCE_BY_ID
            = "SELECT id, topic_name, image_name, topic_description FROM topics WHERE id=?";
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
    public List<Conference> findAllConferences() throws DaoException {
        LOGGER.info("Attempt to find all conferences in the database");
        List<Conference> conferenceList = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_CONFERENCES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                conferenceList.add(retrieveConference(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt find all conferences in the database");
            throw new DaoException(e);
        }
        return conferenceList;
    }

    @Override
    public List<Conference> findConferencesByName(String conferenceName) throws DaoException {
        LOGGER.info("Attempt to find conferences by conference name in the database");
        List<Conference> conferenceList = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_CONFERENCE_BY_NAME)) {
            preparedStatement.setString(1, conferenceName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                conferenceList.add(retrieveConference(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find all conferences by conference name in the database");
            throw new DaoException(e);
        }
        return conferenceList;
    }

    @Override
    public Optional<Conference> findConferenceById(int conferenceId) throws DaoException {
        LOGGER.info("Attempt to find conference by conference id in the database");
        Optional<Conference> optionalConference = Optional.empty();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_CONFERENCE_BY_ID)) {
            preparedStatement.setInt(1, conferenceId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Conference conference = retrieveConference(resultSet);
                optionalConference = Optional.of(conference);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find conference by conference id in the database");
            throw new DaoException(e);
        }
        return optionalConference;
    }

    @Override
    public boolean addConference(Conference conference) throws DaoException {
        LOGGER.info("Attempt to add new conference to the database");
        boolean isAdded = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_CONFERENCE)) {
            preparedStatement.setInt(1, conference.getTopic().getId());
            preparedStatement.setString(2, conference.getConferenceName());
            preparedStatement.setString(3, conference.getConferenceDescription());
            preparedStatement.setDate(4, conference.getConferenceDate());
            preparedStatement.setString(5, conference.getConferenceStatus().name());
            int rowCount = preparedStatement.executeUpdate();
            if (rowCount != 0) {
                isAdded = true;
                LOGGER.info("New conference has been added");
            } else {
                LOGGER.error("New conference has not been added");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to add new conference to the database");
            throw new DaoException(e);
        }
        return isAdded;
    }

    @Override
    public boolean removeConference(int conferenceId) throws DaoException {
        LOGGER.info("Attempt to remove conference from the database");
        boolean ifRemoved = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_REMOVE_CONFERENCE_BY_ID)) {
            preparedStatement.setInt(1, conferenceId);
            int rowCount = preparedStatement.executeUpdate();
            if (rowCount != 0) {
                ifRemoved = true;
                LOGGER.info("Conference has been removed");
            } else {
                LOGGER.info("Conference has not been removed");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to remove conference from the database");
            throw new DaoException(e);
        }
        return ifRemoved;
    }

    @Override
    public boolean editConference(Conference conference) throws DaoException {
        LOGGER.info("Attempt to edit conference in the database");
        boolean isChanged = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_EDIT_CONFERENCE_BY_ID)) {
            preparedStatement.setString(1, conference.getConferenceName());
            preparedStatement.setString(2, conference.getConferenceDescription());
            preparedStatement.setDate(3, conference.getConferenceDate());
            preparedStatement.setString(4, conference.getConferenceStatus().name());
            preparedStatement.setInt(5, conference.getId());
            int rowCount = preparedStatement.executeUpdate();
            if (rowCount != 0) {
                isChanged = true;
                LOGGER.info("Conference has been edited");
            } else {
                LOGGER.error("Conference has not been edited");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to edit conference from the database");
            throw new DaoException(e);
        }
        return isChanged;
    }

    @Override
    public boolean changeConferenceStatus(int conferenceId, Conference.Status status) throws DaoException {
        LOGGER.info("Attempt to change conference status in the database");
        boolean isChanged = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHANGE_CONFERENCE_STATUS_BY_ID)) {
            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, conferenceId);
            int rowCount = preparedStatement.executeUpdate();
            if (rowCount != 0) {
                isChanged = true;
                LOGGER.info("Conference status has been changed");
            } else {
                LOGGER.info("Conference status has not been changed");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to change conference status in the database");
            throw new DaoException(e);
        }
        return isChanged;
    }

    private Conference retrieveConference(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int topicId = resultSet.getInt("topic_id");
        // FIXME: 12.12.2021 replace "findTopicById" with "JOIN"
        Topic topic = new Topic();
        try {
            Optional<Topic> optionalTopic = topicDao.findTopicById(topicId);
            if (optionalTopic.isPresent()) {
                topic = optionalTopic.get();
            }
        } catch (DaoException e) {
            LOGGER.error(e);
        }
        String conferenceName = resultSet.getString("conference_name");
        String conferenceDescription = resultSet.getString("conference_description");
        Date date = resultSet.getDate("date");
        Conference.Status status = Conference
                .Status.valueOf(resultSet.getString("conference_status").toUpperCase());
        return new Conference.Builder()
                .setId(id)
                .setTopic(topic)
                .setName(conferenceName)
                .setDescription(conferenceDescription)
                .setDate(date)
                .setStatus(status)
                .build();
    }
}