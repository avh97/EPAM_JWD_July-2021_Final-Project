package by.khaletski.platform.service.impl;

import by.khaletski.platform.dao.UserDao;
import by.khaletski.platform.entity.User;
import by.khaletski.platform.dao.exception.DaoException;
import by.khaletski.platform.service.UserService;
import by.khaletski.platform.service.exception.ServiceException;
import by.khaletski.platform.service.util.Encoder;
import by.khaletski.platform.service.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * The methods in this class validate the received string values and pass them to the DAO methods.
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
        List<User> users;
        try {
            users = userDao.findAll();
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return users;
    }

    @Override
    public Optional<User> find(String id) throws ServiceException {
        Optional<User> optional = Optional.empty();
        if (Validator.isValidId(id)) {
            try {
                optional = userDao.find(Integer.parseInt(id));
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        }
        return optional;
    }

    @Override
    public boolean add(String password, String email, String name, String patronymic, String surname)
            throws ServiceException {
        boolean isAdded = false;
        if (Validator.isValidPassword(password)
                && Validator.isValidEmail(email)
                && Validator.isValidName(name)
                && Validator.isValidName(patronymic)
                && Validator.isValidName(surname)) {
            String encodedPassword = Encoder.encode(password);
            User user = new User.Builder()
                    .setEmail(email)
                    .setName(name)
                    .setPatronymic(patronymic)
                    .setSurname(surname)
                    .build();
            try {
                isAdded = userDao.add(user, encodedPassword);
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        }
        return isAdded;
    }

    @Override
    public boolean changeRole(String id, String role) throws ServiceException {
        boolean isChanged = false;
        if (Validator.isValidId(id) && Validator.isValidText(role)) {
            int newId = Integer.parseInt(id);
            User.Role newRole = User.Role.valueOf(role);
            try {
                isChanged = userDao.changeRole(newId, newRole);
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        }
        return isChanged;
    }

    @Override
    public boolean edit(User user, String email, String name, String patronymic, String surname)
            throws ServiceException {
        boolean isEdited = false;
        if (Validator.isValidEmail(email)
                && Validator.isValidName(name)
                && Validator.isValidName(patronymic)
                && Validator.isValidName(surname)) {
            try {
                user.setEmail(email);
                user.setName(name);
                user.setPatronymic(patronymic);
                user.setSurname(surname);
                isEdited = userDao.edit(user);
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        }
        return isEdited;
    }

    @Override
    public boolean remove(String id) throws ServiceException {
        boolean isRemoved = false;
        if (Validator.isValidId(id)) {
            try {
                isRemoved = userDao.remove(Integer.parseInt(id));
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        }
        return isRemoved;
    }

    @Override
    public Optional<User> findByEmail(String email) throws ServiceException {
        Optional<User> optional = Optional.empty();
        if (Validator.isValidEmail(email)) {
            try {
                optional = userDao.findUserByEmail(email);
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        }
        return optional;
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) throws ServiceException {
        Optional<User> optionalUser = Optional.empty();
        if (Validator.isValidEmail(email)) {
            try {
                String encodedPassword = Encoder.encode(password);
                Optional<String> optionalPassword = userDao.findPasswordByEmail(email);
                if (optionalPassword.isPresent() && optionalPassword.get().equals(encodedPassword)) {
                    optionalUser = userDao.findUserByEmail(email);
                }
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        }
        return optionalUser;
    }
}
