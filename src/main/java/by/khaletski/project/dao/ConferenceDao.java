package by.khaletski.project.dao;

import by.khaletski.project.entity.Conference;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ConferenceDao {
    List<Conference> findAllConferences();

    List<Conference> findConferencesByName(String name);

    boolean addConference(Conference conference);

    boolean removeConference(Conference conference);

    boolean editConference(Conference conference);

    boolean changeConferenceStatus(int id, Conference.Status status);
}
