package by.khaletski.platform.dao.impl;

import by.khaletski.platform.dao.ConferenceDao;
import by.khaletski.platform.dao.pool.ConnectionPool;
import by.khaletski.platform.entity.Conference;
import by.khaletski.platform.dao.exception.DaoException;
import by.khaletski.platform.entity.Topic;
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
 * The methods in this class are used for creating a PreparedStatement, executing the query
 * and processing the ResultSet object.
 *
 * @author Anton Khaletski
 */

public class ConferenceDaoImpl implements ConferenceDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SQL_FIND_ALL_CONFERENCES
            = "SELECT conferences.id, conferences.topic_id, conferences.name, " +
            "conferences.description, conferences.date, conferences.status, " +
            "topics.name AS topic_name, topics.description AS topic_description " +
            "FROM conferences INNER JOIN topics ON topics.id = conferences.topic_id";
    private static final String SQL_FIND_CONFERENCE_BY_ID
            = "SELECT conferences.id, conferences.topic_id, conferences.name, " +
            "conferences.description, conferences.date, conferences.status, " +
            "topics.name AS topic_name, topics.description AS topic_description " +
            "FROM conferences INNER JOIN topics ON topics.id = conferences.topic_id WHERE conferences.id=?";
    private static final String SQL_ADD_CONFERENCE
            = "INSERT INTO conferences (topic_id, name, description, date, status) values(?,?,?,?,?)";
    private static final String SQL_EDIT_CONFERENCE_BY_ID
            = "UPDATE conferences SET topic_id=?, name=?, description=?, date=? WHERE id=?";
    private static final String SQL_CHANGE_CONFERENCE_STATUS_BY_ID
            = "UPDATE conferences SET status=? WHERE id=?";
    private static final String SQL_REMOVE_CONFERENCE_BY_ID
            = "DELETE FROM conferences WHERE id=?";

    @Override
    public List<Conference> findAll() throws DaoException {
        LOGGER.info("Attempt to find all conferences in the database");
        List<Conference> conferences = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_CONFERENCES)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                conferences.add(retrieve(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find all conferences in the database");
            throw new DaoException(e);
        }
        return conferences;
    }

    @Override
    public Optional<Conference> find(int id) throws DaoException {
        LOGGER.info("Attempt to find conference by conference ID in the database");
        Optional<Conference> optional = Optional.empty();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_CONFERENCE_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Conference conference = retrieve(resultSet);
                optional = Optional.of(conference);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find conference by conference ID in the database");
            throw new DaoException(e);
        }
        return optional;
    }

    @Override
    public boolean add(Conference conference) throws DaoException {
        LOGGER.info("Attempt to add new conference to the database");
        boolean isAdded = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_CONFERENCE)) {
            statement.setInt(1, conference.getTopic().getId());
            statement.setString(2, conference.getName());
            statement.setString(3, conference.getDescription());
            statement.setDate(4, conference.getDate());
            statement.setString(5, conference.getStatus().name());
            int counter = statement.executeUpdate();
            if (counter != 0) {
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
    public boolean edit(Conference conference) throws DaoException {
        LOGGER.info("Attempt to edit conference in the database");
        boolean isChanged = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_EDIT_CONFERENCE_BY_ID)) {
            statement.setInt(1, conference.getTopic().getId());
            statement.setString(2, conference.getName());
            statement.setString(3, conference.getDescription());
            statement.setDate(4, conference.getDate());
            statement.setInt(5, conference.getId());
            int counter = statement.executeUpdate();
            if (counter != 0) {
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
    public boolean changeStatus(int id, Conference.Status status) throws DaoException {
        LOGGER.info("Attempt to change conference status in the database");
        boolean isChanged = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CHANGE_CONFERENCE_STATUS_BY_ID)) {
            statement.setString(1, status.name());
            statement.setInt(2, id);
            int counter = statement.executeUpdate();
            if (counter != 0) {
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

    @Override
    public boolean remove(int id) throws DaoException {
        LOGGER.info("Attempt to remove conference from the database");
        boolean isRemoved = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_REMOVE_CONFERENCE_BY_ID)) {
            statement.setInt(1, id);
            int counter = statement.executeUpdate();
            if (counter != 0) {
                isRemoved = true;
                LOGGER.info("Conference has been removed");
            } else {
                LOGGER.info("Conference has not been removed");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to remove conference from the database");
            throw new DaoException(e);
        }
        return isRemoved;
    }

    /**
     * This method creates an object from ResultSet.
     */

    private Conference retrieve(ResultSet resultSet) throws SQLException {
        return new Conference.Builder()
                .setId(resultSet.getInt("id"))
                .setTopic(new Topic.Builder()
                        .setId(resultSet.getInt("topic_id"))
                        .setName(resultSet.getString("topic_name"))
                        .setDescription(resultSet.getString("topic_description"))
                        .build())
                .setName(resultSet.getString("name"))
                .setDescription(resultSet.getString("description"))
                .setDate(resultSet.getDate("date"))
                .setStatus(Conference.Status.valueOf(resultSet.getString("status")))
                .build();
    }
}
