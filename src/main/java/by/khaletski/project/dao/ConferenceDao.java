package by.khaletski.project.dao;

import by.khaletski.project.entity.Conference;
import by.khaletski.project.dao.exception.DaoException;

import java.util.List;

public interface ConferenceDao {
    List<Conference> findAllConferences() throws DaoException;

    List<Conference> findConferencesByName(String conferenceName) throws DaoException;

    boolean addConference(Conference conference) throws DaoException;

    boolean removeConference(int conferenceId) throws DaoException;

    boolean editConference(Conference conference) throws DaoException;

    boolean changeConferenceStatus(int conferenceId, Conference.Status status) throws DaoException;
}
