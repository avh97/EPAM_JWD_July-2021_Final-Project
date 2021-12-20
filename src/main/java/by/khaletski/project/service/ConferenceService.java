package by.khaletski.project.service;

import by.khaletski.project.entity.Conference;
import by.khaletski.project.service.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ConferenceService {
    List<Conference> findAll() throws ServiceException;

    Optional<Conference> find(int id) throws ServiceException;

    boolean add(Map<String, String> conferenceData) throws ServiceException;

    boolean changeStatus(int id, Conference.Status status) throws ServiceException;

    boolean edit(Conference conference, Map<String, String> conferenceData) throws ServiceException;

    boolean remove(int id) throws ServiceException;
}
