package by.khaletski.project.service.impl;

import by.khaletski.project.dao.ApplicationDao;
import by.khaletski.project.dao.ConferenceDao;
import by.khaletski.project.dao.UserDao;
import by.khaletski.project.entity.Application;
import by.khaletski.project.dao.exception.DaoException;
import by.khaletski.project.entity.Conference;
import by.khaletski.project.entity.User;
import by.khaletski.project.service.exception.ServiceException;
import by.khaletski.project.service.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class ApplicationServiceImpl implements by.khaletski.project.service.ApplicationService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ApplicationDao applicationDao;
    private final ConferenceDao conferenceDao;
    private final UserDao userDao;

    public ApplicationServiceImpl(ApplicationDao applicationDao, ConferenceDao conferenceDao, UserDao userDao) {
        this.applicationDao = applicationDao;
        this.conferenceDao = conferenceDao;
        this.userDao = userDao;
    }

    @Override
    public boolean addApplication(Map<String, String> applicationData) throws ServiceException {
        boolean isAdded;
        if (Validator.isValidName(applicationData.get("application_description"))) {
            User user;
            Conference conference;
            try {
                user = userDao.findUserById(Integer.parseInt("user_id"));
                conference = conferenceDao.findConferenceById(Integer.parseInt("conference_id"));
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
            if (conference == null || user == null) {
                return false;
            }
            Application application = new Application.Builder()
                    .setUser(user)
                    .setConference(conference)
                    .setDescription(applicationData.get("application_description"))
                    .setStatus(Application.Status.CLAIMED)
                    .build();
            try {
                isAdded = applicationDao.addApplication(application);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            isAdded = false;
        }
        return isAdded;
    }

    @Override
    public boolean editApplication(Map<String, String> applicationData) throws ServiceException {
        boolean isEdited;
        if (Validator.isValidName(applicationData.get("application_description"))) {
            User user;
            Conference conference;
            try {
                user = userDao.findUserById(Integer.parseInt("user_id"));
                conference = conferenceDao.findConferenceById(Integer.parseInt("conference_id"));
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
            if (conference == null || user == null) {
                return false;
            }
            Application application = new Application.Builder()
                    .setUser(user)
                    .setConference(conference)
                    .setDescription(applicationData.get("application_description"))
                    .build();
            try {
                isEdited = applicationDao.addApplication(application);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            isEdited = false;
        }
        return isEdited;
    }

    @Override
    public boolean changeApplicationStatus(int applicationId, Application.Status applicationStatus)
            throws ServiceException {
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
