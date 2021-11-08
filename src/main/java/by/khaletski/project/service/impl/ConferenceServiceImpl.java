package by.khaletski.project.service.impl;

import by.khaletski.project.dao.ConferenceDao;
import by.khaletski.project.entity.Conference;
import by.khaletski.project.service.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ConferenceServiceImpl {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ConferenceDao conferenceDao;

    public ConferenceServiceImpl(ConferenceDao conferenceDao) {
        this.conferenceDao = conferenceDao;
    }

    public List<Conference> findAllConferences() {
        List<Conference> conferenceList;
        conferenceList = conferenceDao.findAllConferences();
        return conferenceList;
    }

    public List<Conference> findConferencesByName(String conferenceName){
        List<Conference> conferenceList = new ArrayList<>();
        if (Validator.isValidName(conferenceName)) {
            conferenceList = conferenceDao.findConferencesByName(conferenceName);
        }
        return conferenceList;
    }

    public boolean addConference(Conference conference){
        boolean isAdded;
        isAdded = conferenceDao.addConference(conference);
        return isAdded;
    }

    public boolean removeConference(int id){
        boolean isRemoved;
        isRemoved = conferenceDao.removeConference(id);
        return isRemoved;
    }

    public boolean editConference(Conference conference){
        boolean isEdited;
        isEdited = conferenceDao.editConference(conference);
        return isEdited;
    }

    public boolean changeConferenceStatus(int id, Conference.Status status){
        boolean isChanged;
        isChanged = conferenceDao.changeConferenceStatus(id, status);
        return isChanged;
    }
}
