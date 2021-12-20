package by.khaletski.project.service;

import by.khaletski.project.entity.Topic;
import by.khaletski.project.service.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TopicService {
    List<Topic> findAll() throws ServiceException;

    Optional<Topic> find(int id) throws ServiceException;

    boolean add(Map<String, String> topicData) throws ServiceException;

    boolean edit(Map<String, String> topicData) throws ServiceException;

    boolean remove(int id) throws ServiceException;
}
