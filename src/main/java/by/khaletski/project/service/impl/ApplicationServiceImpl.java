package by.khaletski.project.service.impl;

import by.khaletski.project.dao.ApplicationDao;
import by.khaletski.project.entity.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.util.List;

public class ApplicationServiceImpl {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ApplicationDao applicationDao;

    public ApplicationServiceImpl(ApplicationDao applicationDao) {
        this.applicationDao = applicationDao;
    }

    public boolean addApplication(Application application) {
        boolean isAdded;
        isAdded = applicationDao.addApplication(application);
        return isAdded;
    }

    public boolean editApplication(Application application) {
        boolean isEdited;
        isEdited = applicationDao.editApplication(application);
        return isEdited;
    }

    public boolean changeApplicationStatus(int applicationId, Application.Status applicationStatus) {
        boolean isChanged;
        isChanged = applicationDao.changeApplicationStatus(applicationId, applicationStatus);
        return isChanged;
    }

    public List<Application> findAllApplications() {
        List<Application> applicationList;
        applicationList = applicationDao.findAllApplications();
        return applicationList;
    }

    public List<Application> findApplicationsByUserId(int userId) {
        List<Application> applicationList;
        applicationList = applicationDao.findApplicationsByUserId(userId);
        return applicationList;
    }

    public List<Application> findApplicationsByStatus(Application.Status applicationStatus) {
        List<Application> applicationList;
        applicationList = applicationDao.findApplicationsByStatus(applicationStatus);
        return applicationList;
    }

    public List<Application> findApplicationsByDate(Date conferenceDate) {
        List<Application> applicationList;
        applicationList = applicationDao.findApplicationsByDate(conferenceDate);
        return applicationList;
    }
}
