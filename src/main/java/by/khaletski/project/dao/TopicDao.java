package by.khaletski.project.dao;

import by.khaletski.project.entity.Topic;
import by.khaletski.project.dao.exception.DaoException;

import java.util.List;

public interface TopicDao {
    List<Topic> findAllTopics() throws DaoException;

    List<Topic> findTopicByName(String topicName) throws DaoException;

    Topic findTopicById(int topicId) throws DaoException;

    boolean addTopic(Topic topic) throws DaoException;

    boolean removeTopic(int topicId) throws DaoException;

    boolean editTopic(Topic topic) throws DaoException;
}
