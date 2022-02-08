package by.khaletski.platform.service.impl;

import by.khaletski.platform.dao.MessageDao;
import by.khaletski.platform.dao.UserDao;
import by.khaletski.platform.dao.exception.DaoException;
import by.khaletski.platform.entity.Message;
import by.khaletski.platform.entity.User;
import by.khaletski.platform.service.MessageService;
import by.khaletski.platform.service.exception.ServiceException;
import by.khaletski.platform.service.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class "MessageService".
 * The methods in this class validate the received string values and pass them to the DAO methods.
 *
 * @author Anton Khaletski
 */

public class MessageServiceImpl implements MessageService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final MessageDao messageDao;
    private final UserDao userDao;

    public MessageServiceImpl(MessageDao messageDao, UserDao userDao) {
        this.messageDao = messageDao;
        this.userDao = userDao;
    }

    @Override
    public List<Message> findAll() throws ServiceException {
        List<Message> messages;
        try {
            messages = messageDao.findAll();
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return messages;
    }

    @Override
    public Optional<Message> find(String id) throws ServiceException {
        Optional<Message> optional = Optional.empty();
        if (Validator.isValidId(id)) {
            try {
                optional = messageDao.find(Integer.parseInt(id));
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        }
        return optional;
    }

    @Override
    public List<Message> findByUserId(String userId) throws ServiceException {
        List<Message> messages = new ArrayList<>();
        if (Validator.isValidId(userId)) {
            try {
                messages = messageDao.findByUserId(Integer.parseInt(userId));
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        }
        return messages;
    }

    @Override
    public boolean add(String userId, String question) throws ServiceException {
        boolean isAdded = false;
        if (Validator.isValidId(userId) && Validator.isValidText(question)) {
            try {
                Optional<User> optional = userDao.find(Integer.parseInt(userId));
                if (optional.isEmpty()) {
                    return false;
                }
                User user = optional.get();
                Message message = new Message.Builder()
                        .setUser(user)
                        .setQuestion(question)
                        .build();
                isAdded = messageDao.add(message);
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        }
        return isAdded;
    }

    @Override
    public boolean edit(String id, String answer) throws ServiceException {
        boolean isEdited = false;
        if (Validator.isValidId(id) && Validator.isValidText(answer)) {
            try {
                Optional<Message> optional = messageDao.find(Integer.parseInt(id));
                if (optional.isEmpty()) {
                    return false;
                }
                Message message = optional.get();
                message.setAnswer(answer);
                isEdited = messageDao.edit(message);
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
                isRemoved = messageDao.remove(Integer.parseInt(id));
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        }
        return isRemoved;
    }
}
