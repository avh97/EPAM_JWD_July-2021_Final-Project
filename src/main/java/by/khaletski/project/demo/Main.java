package by.khaletski.project.demo;

import by.khaletski.project.dao.ApplicationDao;
import by.khaletski.project.entity.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Main {
    static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        List<Application> applicationList = new ApplicationDao().findAllApplications();
        System.out.println(applicationList);
    }
}
