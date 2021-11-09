package by.khaletski.project.service;

import by.khaletski.project.entity.Conference;
import by.khaletski.project.service.exception.ServiceException;

import java.util.List;

public interface ConferenceService {
    List<Conference> findAllConferences() throws ServiceException;

    List<Conference> findConferencesByName(String conferenceName) throws ServiceException;

    boolean addConference(Conference conference) throws ServiceException;

    boolean removeConference(int id) throws ServiceException;

    boolean editConference(Conference conference) throws ServiceException;

    boolean changeConferenceStatus(int id, Conference.Status status) throws ServiceException;
}
