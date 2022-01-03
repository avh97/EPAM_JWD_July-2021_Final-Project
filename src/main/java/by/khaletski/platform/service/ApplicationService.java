package by.khaletski.platform.service;

import by.khaletski.platform.entity.Application;
import by.khaletski.platform.service.exception.ServiceException;

import java.util.List;

public interface ApplicationService {
    List<Application> findAll() throws ServiceException;

    List<Application> findByUserId(String id) throws ServiceException;

    List<Application> findByConferenceId(String id) throws ServiceException;

    boolean add(String userId, String conferenceId, String description) throws ServiceException;

    boolean changeStatus(String id, String status) throws ServiceException;

    boolean remove(String id) throws ServiceException;
}
