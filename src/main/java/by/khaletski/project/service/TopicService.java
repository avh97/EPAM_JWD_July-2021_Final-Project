package by.khaletski.project.service;

import by.khaletski.project.entity.Topic;
import by.khaletski.project.service.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface TopicService {
    List<Topic> findAllTopics() throws ServiceException;

    List<Topic> findTopicsByName(String topicName) throws ServiceException;

    boolean addTopic(Map<String, String> topicData) throws ServiceException;

    boolean removeTopic(int topicId) throws ServiceException;

    boolean editTopic(Map<String, String> topicData) throws ServiceException;
}
