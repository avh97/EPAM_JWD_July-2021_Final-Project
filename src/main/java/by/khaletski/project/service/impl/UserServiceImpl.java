package by.khaletski.project.service.impl;

import by.khaletski.project.dao.UserDao;
import by.khaletski.project.entity.User;
import by.khaletski.project.dao.exception.DaoException;
import by.khaletski.project.service.exception.ServiceException;
import by.khaletski.project.service.util.PasswordEncoder;
import by.khaletski.project.service.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl {
    private static final Logger LOGGER = LogManager.getLogger();
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> findAllUsers() throws ServiceException {
        List<User> userList;
        try {
            userList = userDao.findAllUsers();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return userList;
    }

    public List<User> findUsersBySurname(String userSurname) throws ServiceException {
        List<User> userList = new ArrayList<>();
        if (Validator.isValidName(userSurname)) {
            try {
                userList = userDao.findUsersBySurname(userSurname);
            } catch (DaoException e) {
                throw new ServiceException(e.getMessage());
            }
        }
        return userList;
    }

    public List<User> findUsersByRole(User.Role userRole) throws ServiceException {
        List<User> userList;
        try {
            userList = userDao.findUsersByRole(userRole);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return userList;
    }

    public boolean addUser(User user, String userPassword) throws ServiceException {
        boolean isAdded;
        try {
            isAdded = userDao.addUser(user, userPassword);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return isAdded;
    }

    public boolean removeUser(int id) throws ServiceException {
        boolean isRemoved;
        try {
            isRemoved = userDao.removeUser(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return isRemoved;
    }

    public boolean changeUserRole(int userId, User.Role userRole) throws ServiceException {
        boolean isChanged;
        try {
            isChanged = userDao.changeUserRole(userId, userRole);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return isChanged;
    }

    public boolean editUserInfo(User user) throws ServiceException {
        boolean isEdited;
        try {
            isEdited = userDao.editUserInfo(user);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return isEdited;
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) throws ServiceException {
        LOGGER.debug("Attempt to authorize");
        Optional<User> optionalUser;
        if (Validator.isValidEmail(email)) {
            String encodedPassword = PasswordEncoder.encodePassword(password);
            try {
                Optional<String> passwordFromDBOptional = userDao.findPasswordByEmail(email);
                if (passwordFromDBOptional.isPresent()) {
                    String passwordFromDB = passwordFromDBOptional.get();
                    if (passwordFromDB.equals(encodedPassword)) {
                        LOGGER.info("Authorization is successful");
                        optionalUser = userDao.findUserByEmail(email);
                    } else {
                        optionalUser = Optional.empty();
                    }
                } else {
                    optionalUser = Optional.empty();
                }
            } catch (DaoException e) {
                throw new ServiceException(e.getMessage());
            }
        } else {
            optionalUser = Optional.empty();
        }
        return optionalUser;
    }
}
