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
import java.util.Map;
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
        List<Conference> conferenceList;
        try {
            conferenceList = conferenceDao.findAll();
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return conferenceList;
    }

    @Override
    public Optional<Conference> find(int id) throws ServiceException {
        Optional<Conference> optionalConference;
        try {
            optionalConference = conferenceDao.find(id);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return optionalConference;
    }

    @Override
    public boolean add(Map<String, String> conferenceData) throws ServiceException {
        boolean isAdded;
        Optional<Topic> optionalTopic;
        if (Validator.isValidText(conferenceData.get("conference_name"))
                && Validator.isValidText(conferenceData.get("conference_description"))
                && Validator.isValidDateFormat(conferenceData.get("date"))) {
            try {
                optionalTopic = topicDao.find(Integer.parseInt(conferenceData.get("topic_id")));
                LOGGER.debug(optionalTopic);
                if (optionalTopic.isEmpty()) {
                    return false;
                }
                Topic topic = optionalTopic.get();
                Conference conference = new Conference.Builder()
                        .setTopic(topic)
                        .setName(conferenceData.get("conference_name"))
                        .setDescription(conferenceData.get("conference_description"))
                        .setDate(Date.valueOf(conferenceData.get(("date"))))
                        .setStatus(Conference.Status.PENDING)
                        .build();
                isAdded = conferenceDao.add(conference);
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
    public boolean changeStatus(int id, Conference.Status status) throws ServiceException {
        boolean isChanged;
        try {
            isChanged = conferenceDao.changeStatus(id, status);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return isChanged;
    }

    @Override
    public boolean edit(Conference conference, Map<String, String> conferenceData) throws ServiceException {
        boolean isEdited;
        Optional<Topic> optional;
        if (Validator.isValidId(conferenceData.get("topic_id"))
                && Validator.isValidText(conferenceData.get("conference_name"))
                && Validator.isValidText(conferenceData.get("conference_description"))
                && Validator.isValidDateFormat(conferenceData.get("date"))) {
            try {
                optional = topicDao.find(Integer.parseInt(conferenceData.get("topic_id")));
                if (optional.isEmpty()) {
                    return false;
                }
                conference.setTopic(optional.get());
                conference.setName(conferenceData.get("conference_name"));
                conference.setDescription(conferenceData.get("conference_description"));
                conference.setDate(Date.valueOf(conferenceData.get(("date"))));
                isEdited = conferenceDao.edit(conference);
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
    public boolean remove(int id) throws ServiceException {
        boolean isRemoved;
        try {
            isRemoved = conferenceDao.remove(id);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return isRemoved;
    }
}
