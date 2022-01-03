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

import java.util.ArrayList;
import java.util.List;
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
    public List<Application> findByUserId(String id) throws ServiceException {
        List<Application> applications = new ArrayList<>();
        if (Validator.isValidId(id)) {
            try {
                applications = applicationDao.findByUserId(Integer.parseInt(id));
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        }
        return applications;
    }

    @Override
    public List<Application> findByConferenceId(String id) throws ServiceException {
        List<Application> applications = new ArrayList<>();
        if (Validator.isValidId(id)) {
            try {
                applications = applicationDao.findByConferenceId(Integer.parseInt(id));
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        }
        return applications;
    }

    @Override
    public boolean add(String userId, String conferenceId, String description) throws ServiceException {
        boolean isAdded = false;
        if (Validator.isValidId(userId) && Validator.isValidId(conferenceId) && Validator.isValidText(description)) {
            try {
                Optional<User> optionalUser = userDao.find(Integer.parseInt(userId));
                Optional<Conference> optionalConference = conferenceDao.find(Integer.parseInt(conferenceId));
                if (optionalUser.isEmpty() || optionalConference.isEmpty()) {
                    return false;
                }
                Application application = new Application.Builder()
                        .setUser(optionalUser.get())
                        .setConference(optionalConference.get())
                        .setDescription(description)
                        .setStatus(Application.Status.CLAIMED)
                        .build();
                isAdded = applicationDao.add(application);
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        }
        return isAdded;
    }

    @Override
    public boolean changeStatus(String id, String status)
            throws ServiceException {
        boolean isChanged = false;
        if (Validator.isValidId(id) && Validator.isValidText(status)) {
            int applicationId = Integer.parseInt(id);
            Application.Status applicationStatus = Application.Status.valueOf(status);
            try {
                isChanged = applicationDao.changeStatus(applicationId, applicationStatus);
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        }
        return isChanged;
    }

    @Override
    public boolean remove(String id) throws ServiceException {
        boolean isRemoved = false;
        if (Validator.isValidId(id)) {
            try {
                isRemoved = applicationDao.remove(Integer.parseInt(id));
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        }
        return isRemoved;
    }
}
