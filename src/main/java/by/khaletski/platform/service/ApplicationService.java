package by.khaletski.platform.service;

import by.khaletski.platform.entity.Application;
import by.khaletski.platform.entity.Conference;
import by.khaletski.platform.entity.User;
import by.khaletski.platform.service.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ApplicationService {
    List<Application> findAll() throws ServiceException;

    Optional<Application> find(int id) throws ServiceException;

    List<Application> findByUserId(int id) throws ServiceException;

    List<Application> findByConferenceId(int id) throws ServiceException;

    boolean add(User user, Conference conference, Map<String, String> applicationData) throws ServiceException;

    boolean changeStatus(int id, Application.Status status) throws ServiceException;

    boolean edit(Map<String, String> applicationData) throws ServiceException;

    boolean remove(int id) throws ServiceException;
}
