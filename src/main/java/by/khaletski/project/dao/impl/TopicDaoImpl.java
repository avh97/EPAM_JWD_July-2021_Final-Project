package by.khaletski.project.dao.impl;

import by.khaletski.project.dao.TopicDao;
import by.khaletski.project.dao.pool.ConnectionPool;
import by.khaletski.project.entity.Topic;
import by.khaletski.project.dao.exception.DaoException;
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
 * Dao class "TopicDao"
 *
 * @author Anton Khaletski
 */

public class TopicDaoImpl implements TopicDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SQL_FIND_ALL_TOPICS
            = "SELECT id, topic_name, topic_description FROM topics";
    private static final String SQL_FIND_TOPIC_BY_ID
            = "SELECT id, topic_name, topic_description FROM topics WHERE id=?";
    private static final String SQL_ADD_TOPIC
            = "INSERT INTO topics (topic_name, topic_description) values(?,?)";
    private static final String SQL_EDIT_TOPIC_BY_ID
            = "UPDATE topics SET topic_name=?, topic_description=? WHERE id=?";
    private static final String SQL_REMOVE_TOPIC_BY_ID
            = "DELETE FROM topics WHERE id=?";

    @Override
    public List<Topic> findAll() throws DaoException {
        LOGGER.info("Attempt to find all topics in the database");
        List<Topic> topics = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_TOPICS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                topics.add(retrieve(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find all topics in the database");
            throw new DaoException(e);
        }
        return topics;
    }

    @Override
    public Optional<Topic> find(int id) throws DaoException {
        LOGGER.info("Attempt to find topic by topic id in the database");
        Optional<Topic> optional = Optional.empty();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_TOPIC_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Topic topic = retrieve(resultSet);
                optional = Optional.of(topic);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find topic by topic id in the database");
            throw new DaoException(e);
        }
        return optional;
    }

    @Override
    public boolean add(Topic topic) throws DaoException {
        LOGGER.info("Attempt to add new topic to the database");
        boolean isAdded = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_TOPIC)) {
            statement.setString(1, topic.getName());
            statement.setString(2, topic.getDescription());
            int counter = statement.executeUpdate();
            if (counter != 0) {
                isAdded = true;
                LOGGER.info("New topic has been added");
            } else {
                LOGGER.error("New topic has not been added");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to add topic to the database");
            throw new DaoException(e);
        }
        return isAdded;
    }

    @Override
    public boolean edit(Topic topic) throws DaoException {
        LOGGER.info("Attempt to change topic in the database");
        boolean isChanged = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_EDIT_TOPIC_BY_ID)) {
            statement.setString(1, topic.getName());
            statement.setString(2, topic.getDescription());
            statement.setInt(3, topic.getId());
            int counter = statement.executeUpdate();
            if (counter != 0) {
                isChanged = true;
                LOGGER.info("Topic has been changed");
            } else {
                LOGGER.error("Topic has not been changed");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to change topic in the database");
            throw new DaoException(e);
        }
        return isChanged;
    }

    @Override
    public boolean remove(int id) throws DaoException {
        LOGGER.info("Attempt to remove topic from the database");
        boolean isRemoved = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_REMOVE_TOPIC_BY_ID)) {
            statement.setInt(1, id);
            int counter = statement.executeUpdate();
            if (counter != 0) {
                isRemoved = true;
                LOGGER.info("Topic has been removed");
            } else {
                LOGGER.info("Topic has not been removed");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to remove topic from the database");
            throw new DaoException(e);
        }
        return isRemoved;
    }

    private Topic retrieve(ResultSet resultSet) throws SQLException {
        return new Topic.Builder()
                .setId(resultSet.getInt("id"))
                .setName(resultSet.getString("topic_name"))
                .setDescription(resultSet.getString("topic_description"))
                .build();
    }
}
