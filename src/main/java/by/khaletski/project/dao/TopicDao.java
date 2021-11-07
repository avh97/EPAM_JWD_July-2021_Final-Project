package by.khaletski.project.dao;

import by.khaletski.project.dao.pool.ConnectionPool;
import by.khaletski.project.entity.Topic;
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

public class TopicDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SQL_FIND_ALL_TOPICS
            = "SELECT id, topic_name, image_name, topic_description FROM topics";
    private static final String SQL_FIND_TOPIC_BY_NAME
            = "SELECT id, topic_name, image_name, topic_description FROM topics WHERE name=?";
    private static final String SQL_ADD_TOPIC
            = "INSERT INTO procedures (topic_name, image_name, topic_description) values(?,?,?)";
    private static final String SQL_REMOVE_TOPIC_BY_ID
            = "DELETE FROM topics WHERE id=?";
    private static final String SQL_EDIT_TOPIC_BY_ID
            = "UPDATE procedures SET topic_name=?, image_name=?, topic_description=? WHERE id=?";

    public List<Topic> findAllTopics() {
        LOGGER.info("Attempt to find all topics in the database");
        List<Topic> topicList = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_TOPICS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                topicList.add(getTopic(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException");
        }
        return topicList;
    }

    public List<Topic> findTopicsByName(String name) {
        LOGGER.info("Attempt to find all topics by name in the database");
        List<Topic> topicList = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_TOPIC_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                topicList.add(getTopic(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException");
        }
        return topicList;
    }

    public boolean addTopic(Topic topic) {
        LOGGER.info("Attempt to add topic to the database");
        boolean isAdded = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_TOPIC)) {
            statement.setString(1, topic.getTopicName());
            statement.setString(2, topic.getImage());
            statement.setString(3, topic.getTopicDescription());
            int rowCount = statement.executeUpdate();
            if (rowCount != 0) {
                isAdded = true;
                LOGGER.info("Topic has been added");
            } else {
                LOGGER.error("Topic has not been added");
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException");
        }
        return isAdded;
    }

    public boolean removeTopic(int id) {
        LOGGER.info("Attempt to remove topic from the database");
        boolean ifRemoved = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_REMOVE_TOPIC_BY_ID)) {
            statement.setInt(1, id);
            int rowCount = statement.executeUpdate();
            if (rowCount != 0) {
                ifRemoved = true;
                LOGGER.info("Topic has been removed");
            } else {
                LOGGER.info("Topic has not been removed");
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException");
        }
        return ifRemoved;
    }

    public boolean editTopic(Topic topic) {
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
            LOGGER.error("SQLException");
        }
        return isChanged;
    }

    private Topic getTopic(ResultSet resultSet) throws SQLException {
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
