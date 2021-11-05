package by.khaletski.project.demo;

import by.khaletski.project.dao.UserDao;
import by.khaletski.project.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Main {
    static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        List<User> userList = new UserDao().findAll();
        LOGGER.info(userList);
    }
}
