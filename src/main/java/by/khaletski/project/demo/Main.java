package by.khaletski.project.demo;

import by.khaletski.project.dao.impl.ApplicationDaoImpl;
import by.khaletski.project.entity.Application;
import by.khaletski.project.dao.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Main {
    static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        List<Application> applicationList = null;
        try {
            applicationList = new ApplicationDaoImpl().findAllApplications();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        System.out.println(applicationList);
    }
}
