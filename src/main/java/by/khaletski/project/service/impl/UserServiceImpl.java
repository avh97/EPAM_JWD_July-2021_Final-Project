package by.khaletski.project.service.impl;

import by.khaletski.project.dao.UserDao;
import by.khaletski.project.entity.User;
import by.khaletski.project.dao.exception.DaoException;
import by.khaletski.project.service.exception.ServiceException;
import by.khaletski.project.service.util.PasswordEncoder;
import by.khaletski.project.service.util.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserServiceImpl implements by.khaletski.project.service.UserService {
    private static final Logger logger = LogManager.getLogger();
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        List<User> userList;
        try {
            userList = userDao.findAllUsers();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return userList;
    }

    @Override
    public List<User> findUsersBySurname(String userSurname) throws ServiceException {
        List<User> userList = new ArrayList<>();
        if (Validator.isValidName(userSurname)) {
            try {
                userList = userDao.findUsersBySurname(userSurname);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return userList;
    }

    @Override
    public List<User> findUsersByRole(User.Role userRole) throws ServiceException {
        List<User> userList;
        try {
            userList = userDao.findUsersByRole(userRole);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return userList;
    }

    @Override
    public boolean addUser(Map<String, String> userData) throws ServiceException {
        boolean isAdded;
        if (Validator.isValidPassword(userData.get("password"))
                && Validator.isValidEmail(userData.get("email"))
                && Validator.isValidName(userData.get("name"))
                && Validator.isValidName(userData.get("patronymic"))
                && Validator.isValidName(userData.get("surname"))) {
            String encodedPassword = PasswordEncoder.encodePassword(userData.get("password"));
            User user = new User.Builder()
                    .setEmail(userData.get("email"))
                    .setName(userData.get("name"))
                    .setPatronymic(userData.get("patronymic"))
                    .setSurname(userData.get("surname"))
                    .build();
            try {
                isAdded = userDao.addUser(user, encodedPassword);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            isAdded = false;
        }
        return isAdded;
    }

    @Override
    public boolean removeUser(int id) throws ServiceException {
        boolean isRemoved;
        try {
            isRemoved = userDao.removeUser(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isRemoved;
    }

    @Override
    public boolean changeUserRole(int userId, User.Role userRole) throws ServiceException {
        boolean isChanged;
        try {
            isChanged = userDao.changeUserRole(userId, userRole);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isChanged;
    }

    @Override
    public boolean editUserInfo(User user, Map<String, String> userData) throws ServiceException {
        boolean isEdited = false;
        if (Validator.isValidName(userData.get("name"))
                && Validator.isValidName(userData.get("patronymic"))
                && Validator.isValidName(userData.get("surname"))) {
            user.setName(userData.get("name"));
            user.setName(userData.get("patronymic"));
            user.setSurname(userData.get("surname"));
            try {
                isEdited = userDao.editUserInfo(user);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return isEdited;
    }

    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) throws ServiceException {
        logger.log(Level.DEBUG, "findUserByEmailPassword()");
        Optional<User> optionalUser = null;
        if (Validator.isValidEmail(email)) {
            String encodedPassword = PasswordEncoder.encodePassword(password);
            logger.log(Level.DEBUG, "Encoded password: " + encodedPassword);
            try {
                Optional<String> passwordFromDBOptional = userDao.findPasswordByEmail(email);
                if (passwordFromDBOptional.isPresent()) {
                    String passwordFromDB = passwordFromDBOptional.get();
                    logger.log(Level.DEBUG, "passwordFromDB: " + passwordFromDB);
                    if (passwordFromDB.equals(encodedPassword)) {
                        logger.log(Level.INFO, "passwords equals, authorization is successful for user: " + email);
                        optionalUser = userDao.findUserByEmail(email);
                    } else {
                        optionalUser = Optional.empty();
                    }
                } else {
                    optionalUser = Optional.empty();
                }
            } catch (DaoException e) {
                logger.log(Level.ERROR, "dao exception in method FindUsersByLoginPassword" + e);
                throw new ServiceException(e);
            }
        } else {
            optionalUser = Optional.empty();
        }
        return optionalUser;
    }
}
