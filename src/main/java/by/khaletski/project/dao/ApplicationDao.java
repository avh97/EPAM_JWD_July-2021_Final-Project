package by.khaletski.project.dao;

import by.khaletski.project.entity.Application;
import by.khaletski.project.entity.Conference;
import by.khaletski.project.entity.User;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ApplicationDao {
    boolean addApplication(Application application);

    boolean editApplication(Application application);

    boolean changeApplicationStatus(int applicationId, Application.Status applicationStatus);

    List<Application> findAllApplications();

    List<Application> findApplicationsByUserId(int userId);

    List<Application> findApplicationsByStatus(Application.Status applicationStatus);

    List<Application> findApplicationsByDate(Date conferenceDate);
}
