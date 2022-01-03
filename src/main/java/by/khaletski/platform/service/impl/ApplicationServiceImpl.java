package by.khaletski.platform.service.impl;

import by.khaletski.platform.dao.ApplicationDao;
import by.khaletski.platform.dao.ConferenceDao;
import by.khaletski.platform.dao.UserDao;
import by.khaletski.platform.entity.Application;
import by.khaletski.platform.dao.exception.DaoException;
import by.khaletski.platform.entity.Conference;
import by.khaletski.platform.entity.User;
import by.khaletski.platform.service.ApplicationService;
import by.khaletski.platform.service.exception.ServiceException;
import by.khaletski.platform.service.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service class "ApplicationService"
 *
 * @author Anton Khaletski
 */

public class ApplicationServiceImpl implements ApplicationService {
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
    public List<Application> findAll() throws ServiceException {
        List<Application> applicationList;
        try {
            applicationList = applicationDao.findAll();
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return applicationList;
    }

    @Override
    public Optional<Application> find(int id) throws ServiceException {
        Optional<Application> optionalApplication;
        try {
            optionalApplication = applicationDao.find(id);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return optionalApplication;
    }

    @Override
    public List<Application> findByUserId(int id) throws ServiceException {
        List<Application> applications;
        try {
            applications = applicationDao.findByUserId(id);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return applications;
    }

    @Override
    public List<Application> findByConferenceId(int id) throws ServiceException {
        List<Application> applications;
        try {
            applications = applicationDao.findByConferenceId(id);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return applications;
    }

    @Override
    public boolean add(User user, Conference conference, Map<String, String> applicationData) throws ServiceException {
        boolean isAdded;
        if (Validator.isValidText(applicationData.get("application_description"))) {
            try {
                Application application = new Application.Builder()
                        .setUser(user)
                        .setConference(conference)
                        .setDescription(applicationData.get("application_description"))
                        .setStatus(Application.Status.CLAIMED)
                        .build();
                isAdded = applicationDao.add(application);
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        } else {
            isAdded = false;
        }
        return isAdded;
    }

    @Override
    public boolean edit(Map<String, String> applicationData) throws ServiceException {
        boolean isEdited;
        if (Validator.isValidText(applicationData.get("application_description"))) {
            try {
                Optional<User> optionalUser = userDao.find(Integer.parseInt("user_id"));
                Optional<Conference> optionalConference = conferenceDao.
                        find(Integer.parseInt("conference_id"));
                if (optionalConference.isEmpty() || optionalUser.isEmpty()) {
                    return false;
                }
                User user = optionalUser.get();
                Conference conference = optionalConference.get();
                Application application = new Application.Builder()
                        .setUser(user)
                        .setConference(conference)
                        .setDescription(applicationData.get("application_description"))
                        .build();
                isEdited = applicationDao.add(application);
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        } else {
            isEdited = false;
        }
        return isEdited;
    }

    @Override
    public boolean changeStatus(int id, Application.Status status)
            throws ServiceException {
        boolean isChanged;
        try {
            isChanged = applicationDao.changeStatus(id, status);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return isChanged;
    }

    @Override
    public boolean remove(int id) throws ServiceException {
        boolean isRemoved;
        try {
            isRemoved = applicationDao.remove(id);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return isRemoved;
    }
}
