package by.khaletski.project.dao;

import by.khaletski.project.entity.Application;
import by.khaletski.project.dao.exception.DaoException;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ApplicationDao {
    boolean addApplication(Application application) throws DaoException;

    boolean editApplication(Application application) throws DaoException;

    boolean changeApplicationStatus(int applicationId, Application.Status applicationStatus) throws DaoException;

    List<Application> findAllApplications() throws DaoException;

    Optional<Application> findApplicationById(int applicationId) throws DaoException;

    List<Application> findApplicationsByStatus(Application.Status applicationStatus) throws DaoException;

    List<Application> findApplicationsByDate(Date conferenceDate) throws DaoException;
}
