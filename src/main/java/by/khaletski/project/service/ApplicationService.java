package by.khaletski.project.service;

import by.khaletski.project.entity.Application;
import by.khaletski.project.service.exception.ServiceException;

import java.sql.Date;
import java.util.List;

public interface ApplicationService {
    boolean addApplication(Application application) throws ServiceException;

    boolean editApplication(Application application) throws ServiceException;

    boolean changeApplicationStatus(int applicationId, Application.Status applicationStatus) throws ServiceException;

    List<Application> findAllApplications() throws ServiceException;

    List<Application> findApplicationsByUserId(int userId) throws ServiceException;

    List<Application> findApplicationsByStatus(Application.Status applicationStatus) throws ServiceException;

    List<Application> findApplicationsByDate(Date conferenceDate) throws ServiceException;
}
