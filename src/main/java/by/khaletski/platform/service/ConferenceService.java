package by.khaletski.platform.service;

import by.khaletski.platform.entity.Conference;
import by.khaletski.platform.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface ConferenceService {
    List<Conference> findAll() throws ServiceException;

    Optional<Conference> find(String id) throws ServiceException;

    boolean add(String topicId, String name, String description, String date) throws ServiceException;

    boolean changeStatus(String id, String status) throws ServiceException;

    boolean edit(Conference conference, String topicId, String name, String description, String date)
            throws ServiceException;

    boolean remove(String id) throws ServiceException;
}
