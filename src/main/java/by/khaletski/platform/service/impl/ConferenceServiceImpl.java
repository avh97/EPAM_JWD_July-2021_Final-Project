package by.khaletski.platform.service.impl;

import by.khaletski.platform.dao.ConferenceDao;
import by.khaletski.platform.dao.TopicDao;
import by.khaletski.platform.dao.exception.DaoException;
import by.khaletski.platform.entity.Conference;
import by.khaletski.platform.entity.Topic;
import by.khaletski.platform.service.ConferenceService;
import by.khaletski.platform.service.exception.ServiceException;
import by.khaletski.platform.service.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service class "ConferenceService"
 *
 * @author Anton Khaletski
 */

public class ConferenceServiceImpl implements ConferenceService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ConferenceDao conferenceDao;
    private final TopicDao topicDao;

    public ConferenceServiceImpl(ConferenceDao conferenceDao, TopicDao topicDao) {
        this.conferenceDao = conferenceDao;
        this.topicDao = topicDao;
    }

    @Override
    public List<Conference> findAll() throws ServiceException {
        List<Conference> conferences;
        try {
            conferences = conferenceDao.findAll();
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return conferences;
    }

    @Override
    public Optional<Conference> find(String id) throws ServiceException {
        Optional<Conference> optional = Optional.empty();
        if (Validator.isValidId(id)) {
            try {
                optional = conferenceDao.find(Integer.parseInt(id));
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        }
        return optional;
    }

    @Override
    public boolean add(String topicId, String name, String description, String date) throws ServiceException {
        boolean isAdded = false;
        if (Validator.isValidId(topicId)
                && Validator.isValidText(name)
                && Validator.isValidText(description)
                && Validator.isValidDateFormat(date)) {
            try {
                Optional<Topic> optional = topicDao.find(Integer.parseInt(topicId));
                if (optional.isEmpty()) {
                    return false;
                }
                Topic topic = optional.get();
                Conference conference = new Conference.Builder()
                        .setTopic(topic)
                        .setName(name)
                        .setDescription(description)
                        .setDate(Date.valueOf(date))
                        .setStatus(Conference.Status.PENDING)
                        .build();
                isAdded = conferenceDao.add(conference);
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        }
        return isAdded;
    }

    @Override
    public boolean changeStatus(String id, String status) throws ServiceException {
        boolean isChanged = false;
        if (Validator.isValidId(id) && Validator.isValidText(status)) {
            int newId = Integer.parseInt(id);
            Conference.Status newStatus = Conference.Status.valueOf(status);
            try {
                isChanged = conferenceDao.changeStatus(newId, newStatus);
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        }
        return isChanged;
    }

    @Override
    public boolean edit(Conference conference, String topicId, String name, String description, String date)
            throws ServiceException {
        boolean isEdited = false;
        if (Validator.isValidId(topicId)
                && Validator.isValidText(name)
                && Validator.isValidText(description)
                && Validator.isValidDateFormat(date)) {
            try {
                Optional<Topic> optional = topicDao.find(Integer.parseInt(topicId));
                if (optional.isEmpty()) {
                    return false;
                }
                Topic topic = optional.get();
                conference.setTopic(topic);
                conference.setName(name);
                conference.setDescription(description);
                conference.setDate(Date.valueOf(date));
                isEdited = conferenceDao.edit(conference);
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        }
        return isEdited;
    }

    @Override
    public boolean remove(String id) throws ServiceException {
        boolean isRemoved = false;
        if (Validator.isValidId(id)) {
            try {
                isRemoved = conferenceDao.remove(Integer.parseInt(id));
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        }
        return isRemoved;
    }
}
