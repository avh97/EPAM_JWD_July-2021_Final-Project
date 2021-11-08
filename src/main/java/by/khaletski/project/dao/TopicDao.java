package by.khaletski.project.dao;

import by.khaletski.project.entity.Topic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface TopicDao {
    List<Topic> findAllTopics();

    List<Topic> findTopicsByName(String topicName);

    boolean addTopic(Topic topic);

    boolean removeTopic(int topicId);

    boolean editTopic(Topic topic);
}
