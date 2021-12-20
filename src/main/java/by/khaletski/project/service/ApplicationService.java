package by.khaletski.project.service;

import by.khaletski.project.entity.Application;
import by.khaletski.project.service.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ApplicationService {
    List<Application> findAll() throws ServiceException;

    Optional<Application> find(int id) throws ServiceException;

    boolean add(Map<String, String> applicationData) throws ServiceException;

    boolean changeStatus(int id, Application.Status status) throws ServiceException;

    boolean edit(Map<String, String> applicationData) throws ServiceException;

    boolean remove(int id) throws ServiceException;
}
