package by.khaletski.platform.service;

import by.khaletski.platform.entity.Topic;
import by.khaletski.platform.service.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TopicService {
    List<Topic> findAll() throws ServiceException;

    Optional<Topic> find(int id) throws ServiceException;

    boolean add(Map<String, String> topicData) throws ServiceException;

    boolean edit(Topic topic, Map<String, String> topicData) throws ServiceException;

    boolean remove(int id) throws ServiceException;
}
