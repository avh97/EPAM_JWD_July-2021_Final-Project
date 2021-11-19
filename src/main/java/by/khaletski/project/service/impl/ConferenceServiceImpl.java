package by.khaletski.project.service.impl;

import by.khaletski.project.dao.ConferenceDao;
import by.khaletski.project.dao.TopicDao;
import by.khaletski.project.dao.exception.DaoException;
import by.khaletski.project.entity.Conference;
import by.khaletski.project.entity.Topic;
import by.khaletski.project.entity.User;
import by.khaletski.project.service.exception.ServiceException;
import by.khaletski.project.service.util.Validator;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ConferenceServiceImpl implements by.khaletski.project.service.ConferenceService {
    private final ConferenceDao conferenceDao;
    private final TopicDao topicDao;

    public ConferenceServiceImpl(ConferenceDao conferenceDao, TopicDao topicDao) {
        this.conferenceDao = conferenceDao;
        this.topicDao = topicDao;
    }

    @Override
    public List<Conference> findAllConferences() throws ServiceException {
        List<Conference> conferenceList;
        try {
            conferenceList = conferenceDao.findAllConferences();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return conferenceList;
    }

    @Override
    public List<Conference> findConferencesByName(String conferenceName) throws ServiceException {
        List<Conference> conferenceList = new ArrayList<>();
        if (Validator.isValidName(conferenceName)) {
            try {
                conferenceList = conferenceDao.findConferencesByName(conferenceName);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return conferenceList;
    }

    @Override
    public boolean addConference(Map<String, String> conferenceData) throws ServiceException {
        boolean isAdded;
        Topic topic = null;
        if (Validator.isValidName(conferenceData.get("conference_name"))
                && Validator.isValidName(conferenceData.get("conference_description"))
                && Validator.isDateFormatValid(conferenceData.get("date"))) {
            try {
                topic = topicDao.findTopicById(Integer.parseInt("topic_id"));
                if (topic == null) {
                    return false;
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
            Conference conference = new Conference.Builder()
                    .setName(conferenceData.get("conference_name"))
                    .setTopic(topic)
                    .setDescription(conferenceData.get("conference_description"))
                    .setDate(Date.valueOf(conferenceData.get(("date"))))
                    .setStatus(Conference.Status.PENDING)
                    .build();
            try {
                isAdded = conferenceDao.addConference(conference);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            isAdded = false;
        }
        return isAdded;
    }

    @Override
    public boolean removeConference(int id) throws ServiceException {
        boolean isRemoved;
        try {
            isRemoved = conferenceDao.removeConference(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return isRemoved;
    }

    @Override
    public boolean editConference(Conference conference, Map<String, String> conferenceData) throws ServiceException {
        boolean isEdited;
        if (Validator.isValidName(conferenceData.get("conference_name"))
                && Validator.isValidName(conferenceData.get("conference_description"))
                && Validator.isDateFormatValid(conferenceData.get("date"))) {
            try {
                Topic topic = topicDao.findTopicById(Integer.parseInt("topic_id"));
                if (topic == null) {
                    return false;
                }
                conference = new Conference.Builder()
                        .setName(conferenceData.get("conference_name"))
                        .setTopic(topic)
                        .setDescription(conferenceData.get("conference_description"))
                        .setDate(Date.valueOf(conferenceData.get(("date"))))
                        .setStatus(Conference.Status.valueOf("conference_status"))
                        .build();
            } catch (DaoException e) {
                e.printStackTrace();
            }
            try {
                isEdited = conferenceDao.addConference(conference);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            isEdited = false;
        }
        return isEdited;
    }

    @Override
    public boolean changeConferenceStatus(int id, Conference.Status status) throws ServiceException {
        boolean isChanged;
        try {
            isChanged = conferenceDao.changeConferenceStatus(id, status);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return isChanged;
    }
}
