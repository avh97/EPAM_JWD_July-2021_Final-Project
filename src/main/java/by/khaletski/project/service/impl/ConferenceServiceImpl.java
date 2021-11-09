package by.khaletski.project.service.impl;

import by.khaletski.project.dao.ConferenceDao;
import by.khaletski.project.entity.Conference;
import by.khaletski.project.dao.exception.DaoException;
import by.khaletski.project.service.exception.ServiceException;
import by.khaletski.project.service.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ConferenceServiceImpl implements by.khaletski.project.service.ConferenceService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ConferenceDao conferenceDao;

    public ConferenceServiceImpl(ConferenceDao conferenceDao) {
        this.conferenceDao = conferenceDao;
    }

    @Override
    public List<Conference> findAllConferences() throws ServiceException {
        List<Conference> conferenceList;
        try {
            conferenceList = conferenceDao.findAllConferences();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
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
                throw new ServiceException(e.getMessage());
            }
        }
        return conferenceList;
    }

    @Override
    public boolean addConference(Conference conference) throws ServiceException {
        boolean isAdded;
        try {
            isAdded = conferenceDao.addConference(conference);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
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
    public boolean editConference(Conference conference) throws ServiceException {
        boolean isEdited;
        try {
            isEdited = conferenceDao.editConference(conference);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
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
