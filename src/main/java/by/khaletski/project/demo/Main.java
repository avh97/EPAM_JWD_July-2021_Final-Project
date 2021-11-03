package by.khaletski.project.demo;

import java.util.List;

import by.khaletski.project.dao.UserDao;
import by.khaletski.project.exception.DaoException;
import by.khaletski.project.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            List<User> userList = new UserDao().findAll();
            LOGGER.info(userList);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}
