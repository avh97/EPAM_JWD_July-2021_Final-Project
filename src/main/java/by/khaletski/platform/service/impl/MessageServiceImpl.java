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

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service class "MessageService"
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
    public Optional<Message> find(int id) throws ServiceException {
        Optional<Message> optional;
        try {
            optional = messageDao.find(id);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return optional;
    }

    @Override
    public List<Message> findByUserId(int id) throws ServiceException {
        List<Message> messages;
        try {
            messages = messageDao.findByUserId(id);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return messages;
    }

    @Override
    public boolean add(Map<String, String> messageData) throws ServiceException {
        boolean isAdded;
        String question = messageData.get("question");
        int id = Integer.parseInt(messageData.get("user_id"));
        if (Validator.isValidText(question)) {
            try {
                Optional<User> optional = userDao.find(id);
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
        } else {
            isAdded = false;
        }
        return isAdded;
    }

    @Override
    public boolean edit(Map<String, String> messageData) throws ServiceException {
        boolean isEdited;
        String answer = messageData.get("answer");
        if (Validator.isValidText(answer)) {
            try {
                Optional<Message> optional = messageDao.find(Integer.parseInt(messageData.get("id")));
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
        } else {
            isEdited = false;
        }
        return isEdited;
    }

    @Override
    public boolean remove(int id) throws ServiceException {
        boolean isRemoved;
        try {
            isRemoved = messageDao.remove(id);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return isRemoved;
    }
}
