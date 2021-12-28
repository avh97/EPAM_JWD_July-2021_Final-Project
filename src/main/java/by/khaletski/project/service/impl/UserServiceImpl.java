package by.khaletski.project.service.impl;

import by.khaletski.project.dao.UserDao;
import by.khaletski.project.entity.User;
import by.khaletski.project.dao.exception.DaoException;
import by.khaletski.project.service.UserService;
import by.khaletski.project.service.exception.ServiceException;
import by.khaletski.project.service.util.Encoder;
import by.khaletski.project.service.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service class "UserService"
 *
 * @author Anton Khaletski
 */

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> findAll() throws ServiceException {
        List<User> userList;
        try {
            userList = userDao.findAll();
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return userList;
    }

    @Override
    public Optional<User> find(int id) throws ServiceException {
        Optional<User> optionalUser;
        try {
            optionalUser = userDao.find(id);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return optionalUser;
    }

    @Override
    public boolean add(Map<String, String> userData) throws ServiceException {
        boolean isAdded;
        if (Validator.isValidPassword(userData.get("password"))
                && Validator.isValidEmail(userData.get("email"))
                && Validator.isValidName(userData.get("name"))
                && Validator.isValidName(userData.get("patronymic"))
                && Validator.isValidName(userData.get("surname"))) {
            String encodedPassword = Encoder.encode(userData.get("password"));
            User user = new User.Builder()
                    .setEmail(userData.get("email"))
                    .setName(userData.get("name"))
                    .setPatronymic(userData.get("patronymic"))
                    .setSurname(userData.get("surname"))
                    .build();
            try {
                isAdded = userDao.add(user, encodedPassword);
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        } else {
            isAdded = false;
        }
        return isAdded;
    }

    @Override
    public boolean changeRole(int id, User.Role role) throws ServiceException {
        boolean isChanged;
        try {
            isChanged = userDao.changeRole(id, role);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return isChanged;
    }

    @Override
    public boolean edit(User user, Map<String, String> userData) throws ServiceException {
        boolean isEdited = false;
        if (Validator.isValidEmail(userData.get("email"))
                && Validator.isValidName(userData.get("name"))
                && Validator.isValidName(userData.get("patronymic"))
                && Validator.isValidName(userData.get("surname"))) {
            user.setEmail(userData.get("email"));
            user.setName(userData.get("name"));
            user.setPatronymic(userData.get("patronymic"));
            user.setSurname(userData.get("surname"));
            try {
                isEdited = userDao.edit(user);
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        }
        return isEdited;
    }

    @Override
    public boolean remove(int id) throws ServiceException {
        boolean isRemoved;
        try {
            isRemoved = userDao.remove(id);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return isRemoved;
    }

    @Override
    public Optional<User> findByEmail(String email) throws ServiceException {
        Optional<User> user;
        try {
            if (Validator.isValidEmail(email)) {
                user = userDao.findUserByEmail(email);
            } else {
                user = Optional.empty();
            }
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) throws ServiceException {
        LOGGER.info("Attempt to find user by email and password");
        Optional<User> optionalUser;
        if (Validator.isValidEmail(email)) {
            String encodedPassword = Encoder.encode(password);
            // FIXME: 12.12.2021 remove logger later
            LOGGER.debug("Encoded password: " + encodedPassword);
            try {
                Optional<String> passwordFromDBOptional = userDao.findPasswordByEmail(email);
                if (passwordFromDBOptional.isPresent()) {
                    String passwordFromDB = passwordFromDBOptional.get();
                    if (passwordFromDB.equals(encodedPassword)) {
                        // FIXME: 12.12.2021 remove logger later
                        LOGGER.debug("Authorization is successful for user: " + email);
                        optionalUser = userDao.findUserByEmail(email);
                    } else {
                        optionalUser = Optional.empty();
                    }
                } else {
                    optionalUser = Optional.empty();
                }
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        } else {
            optionalUser = Optional.empty();
        }
        return optionalUser;
    }
}
