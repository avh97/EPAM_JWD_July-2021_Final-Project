package by.khaletski.platform.service;

import by.khaletski.platform.entity.Topic;
import by.khaletski.platform.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface TopicService {
    List<Topic> findAll() throws ServiceException;

    Optional<Topic> find(String id) throws ServiceException;

    boolean add(String name, String description) throws ServiceException;

    boolean edit(Topic topic, String name, String description) throws ServiceException;

    boolean remove(String id) throws ServiceException;
}
