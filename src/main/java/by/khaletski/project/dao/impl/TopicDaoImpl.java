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

/**
 * Dao class "TopicDao"
 *
 * @author Anton Khaletski
 */

public class TopicDaoImpl implements TopicDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SQL_FIND_ALL_TOPICS
            = "SELECT id, topic_name, image_name, topic_description FROM topics";
    private static final String SQL_FIND_TOPIC_BY_NAME
            = "SELECT id, topic_name, image_name, topic_description FROM topics WHERE name=?";
    private static final String SQL_FIND_TOPIC_BY_ID
            = "SELECT id, topic_name, image_name, topic_description FROM topics WHERE id=?";
    private static final String SQL_ADD_TOPIC
            = "INSERT INTO procedures (topic_name, image_name, topic_description) values(?,?,?)";
    private static final String SQL_REMOVE_TOPIC_BY_ID
            = "DELETE FROM topics WHERE id=?";
    private static final String SQL_EDIT_TOPIC_BY_ID
            = "UPDATE procedures SET topic_name=?, image_name=?, topic_description=? WHERE id=?";

    @Override
    public List<Topic> findAllTopics() throws DaoException {
        LOGGER.info("Attempt to find all topics in the database");
        List<Topic> topicList = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_TOPICS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                topicList.add(retrieveTopic(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find all topics in the database");
            throw new DaoException(e);
        }
        return topicList;
    }

    @Override
    public List<Topic> findTopicByName(String topicName) throws DaoException {
        LOGGER.info("Attempt to find all topics by topic name in the database");
        List<Topic> topicList = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_TOPIC_BY_NAME)) {
            preparedStatement.setString(1, topicName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                topicList.add(retrieveTopic(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find all topics by topic name in the database");
            throw new DaoException(e);
        }
        return topicList;
    }

    @Override
    public Topic findTopicById(int topicId) throws DaoException {
        LOGGER.info("Attempt to find topic by topic id in the database");
        Topic topic = null;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_TOPIC_BY_ID)) {
            preparedStatement.setInt(1, topicId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                topic = retrieveTopic(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find topic by topic id in the database");
            throw new DaoException(e);
        }
        return topic;
    }

    @Override
    public boolean addTopic(Topic topic) throws DaoException {
        LOGGER.info("Attempt to add new topic to the database");
        boolean isAdded = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_TOPIC)) {
            statement.setString(1, topic.getTopicName());
            statement.setString(2, topic.getImage());
            statement.setString(3, topic.getTopicDescription());
            int rowCount = statement.executeUpdate();
            if (rowCount != 0) {
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
    public boolean removeTopic(int topicId) throws DaoException {
        LOGGER.info("Attempt to remove topic from the database");
        boolean ifRemoved = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_REMOVE_TOPIC_BY_ID)) {
            statement.setInt(1, topicId);
            int rowCount = statement.executeUpdate();
            if (rowCount != 0) {
                ifRemoved = true;
                LOGGER.info("Topic has been removed");
            } else {
                LOGGER.info("Topic has not been removed");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to remove topic from the database");
            throw new DaoException(e);
        }
        return ifRemoved;
    }

    @Override
    public boolean editTopic(Topic topic) throws DaoException {
        LOGGER.info("Attempt to change topic in the database");
        boolean isChanged = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_EDIT_TOPIC_BY_ID)) {
            statement.setString(1, topic.getTopicName());
            statement.setString(2, topic.getImage());
            statement.setString(3, topic.getTopicDescription());
            statement.setInt(4, topic.getId());
            int rowCount = statement.executeUpdate();
            if (rowCount != 0) {
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

    private Topic retrieveTopic(ResultSet resultSet) throws SQLException {
        int procedureId = resultSet.getInt("id");
        String name = resultSet.getString("topic_name");
        String imageName = resultSet.getString("image_name");
        String description = resultSet.getString("topic_description");
        return new Topic.Builder()
                .setId(procedureId)
                .setName(name)
                .setImageName(imageName)
                .setDescription(description)
                .build();
    }
}
