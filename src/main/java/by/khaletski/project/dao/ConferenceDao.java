package by.khaletski.project.dao;

import by.khaletski.project.entity.Conference;

import java.util.List;

public interface ConferenceDao {
    List<Conference> findAllConferences();

    List<Conference> findConferencesByName(String conferenceName);

    boolean addConference(Conference conference);

    boolean removeConference(int conferenceId);

    boolean editConference(Conference conference);

    boolean changeConferenceStatus(int conferenceId, Conference.Status status);
}
