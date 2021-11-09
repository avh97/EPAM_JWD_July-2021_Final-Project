package by.khaletski.project.service.impl;

import by.khaletski.project.dao.ApplicationDao;
import by.khaletski.project.entity.Application;
import by.khaletski.project.dao.exception.DaoException;
import by.khaletski.project.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.util.List;

public class ApplicationServiceImpl implements by.khaletski.project.service.ApplicationService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ApplicationDao applicationDao;

    public ApplicationServiceImpl(ApplicationDao applicationDao) {
        this.applicationDao = applicationDao;
    }

    @Override
    public boolean addApplication(Application application) throws ServiceException {
        boolean isAdded;
        try {
            isAdded = applicationDao.addApplication(application);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return isAdded;
    }

    @Override
    public boolean editApplication(Application application) throws ServiceException {
        boolean isEdited;
        try {
            isEdited = applicationDao.editApplication(application);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return isEdited;
    }

    @Override
    public boolean changeApplicationStatus(int applicationId, Application.Status applicationStatus) throws ServiceException {
        boolean isChanged;
        try {
            isChanged = applicationDao.changeApplicationStatus(applicationId, applicationStatus);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return isChanged;
    }

    @Override
    public List<Application> findAllApplications() throws ServiceException {
        List<Application> applicationList;
        try {
            applicationList = applicationDao.findAllApplications();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return applicationList;
    }

    @Override
    public List<Application> findApplicationsByUserId(int userId) throws ServiceException {
        List<Application> applicationList;
        try {
            applicationList = applicationDao.findApplicationsByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return applicationList;
    }

    @Override
    public List<Application> findApplicationsByStatus(Application.Status applicationStatus) throws ServiceException {
        List<Application> applicationList;
        try {
            applicationList = applicationDao.findApplicationsByStatus(applicationStatus);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return applicationList;
    }

    @Override
    public List<Application> findApplicationsByDate(Date conferenceDate) throws ServiceException {
        List<Application> applicationList;
        try {
            applicationList = applicationDao.findApplicationsByDate(conferenceDate);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return applicationList;
    }
}
