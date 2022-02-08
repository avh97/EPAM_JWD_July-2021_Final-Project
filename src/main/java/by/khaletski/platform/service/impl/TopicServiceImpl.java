package by.khaletski.platform.service.impl;

import by.khaletski.platform.dao.TopicDao;
import by.khaletski.platform.dao.exception.DaoException;
import by.khaletski.platform.entity.Topic;
import by.khaletski.platform.service.TopicService;
import by.khaletski.platform.service.exception.ServiceException;
import by.khaletski.platform.service.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * Service class "TopicService".
 * The methods in this class validate the received string values and pass them to the DAO methods.
 *
 * @author Anton Khaletski
 */

public class TopicServiceImpl implements TopicService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final TopicDao topicDao;

    public TopicServiceImpl(TopicDao topicDao) {
        this.topicDao = topicDao;
    }

    @Override
    public List<Topic> findAll() throws ServiceException {
        List<Topic> topics;
        try {
            topics = topicDao.findAll();
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return topics;
    }

    @Override
    public Optional<Topic> find(String id) throws ServiceException {
        Optional<Topic> optional = Optional.empty();
        if (Validator.isValidId(id)) {
            try {
                optional = topicDao.find(Integer.parseInt(id));
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        }
        return optional;
    }

    @Override
    public boolean add(String name, String description) throws ServiceException {
        boolean isAdded = false;
        if (Validator.isValidText(name) && Validator.isValidText(description)) {
            try {
                Topic topic = new Topic.Builder()
                        .setName(name)
                        .setDescription(description)
                        .build();
                isAdded = topicDao.add(topic);
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        }
        return isAdded;
    }

    @Override
    public boolean edit(Topic topic, String name, String description) throws ServiceException {
        boolean isEdited = false;
        if (Validator.isValidText(name) && Validator.isValidText(description)) {
            topic.setName(name);
            topic.setDescription(description);
            try {
                isEdited = topicDao.edit(topic);
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
                isRemoved = topicDao.remove(Integer.parseInt(id));
            } catch (DaoException e) {
                LOGGER.error(e);
                throw new ServiceException(e);
            }
        }
        return isRemoved;
    }
}
