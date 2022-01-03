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
import java.util.Map;
import java.util.Optional;

/**
 * Service class "TopicService"
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
        List<Topic> topicList;
        try {
            topicList = topicDao.findAll();
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return topicList;
    }

    @Override
    public Optional<Topic> find(int id) throws ServiceException {
        Optional<Topic> optionalTopic;
        try {
            optionalTopic = topicDao.find(id);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return optionalTopic;
    }

    @Override
    public boolean add(Map<String, String> topicData) throws ServiceException {
        boolean isAdded;
        if (Validator.isValidText(topicData.get("topic_name"))
                && Validator.isValidText(topicData.get("topic_description"))) {
            Topic topic = new Topic.Builder()
                    .setName(topicData.get("topic_name"))
                    .setDescription(topicData.get("topic_description"))
                    .build();
            try {
                isAdded = topicDao.add(topic);
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
    public boolean edit(Topic topic, Map<String, String> topicData) throws ServiceException {
        boolean isEdited;
        if (Validator.isValidText(topicData.get("topic_name"))
                && Validator.isValidText(topicData.get("topic_description"))) {
            topic.setName(topicData.get("topic_name"));
            topic.setDescription(topicData.get("topic_description"));
            try {
                isEdited = topicDao.edit(topic);
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
            isRemoved = topicDao.remove(id);
        } catch (DaoException e) {
            LOGGER.error(e);
            throw new ServiceException(e);
        }
        return isRemoved;
    }
}
