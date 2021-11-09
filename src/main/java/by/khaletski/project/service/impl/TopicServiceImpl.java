package by.khaletski.project.service.impl;

import by.khaletski.project.dao.TopicDao;
import by.khaletski.project.entity.Topic;
import by.khaletski.project.dao.exception.DaoException;
import by.khaletski.project.service.exception.ServiceException;
import by.khaletski.project.service.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TopicServiceImpl implements by.khaletski.project.service.TopicService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final TopicDao topicDao;

    public TopicServiceImpl(TopicDao topicDao) {
        this.topicDao = topicDao;
    }

    @Override
    public List<Topic> findAllTopics() throws ServiceException {
        List<Topic> topicList;
        try {
            topicList = topicDao.findAllTopics();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return topicList;
    }

    @Override
    public List<Topic> findTopicsByName(String topicName) throws ServiceException {
        List<Topic> topicList = new ArrayList<>();
        if (Validator.isValidName(topicName)) {
            try {
                topicList = topicDao.findTopicsByName(topicName);
            } catch (DaoException e) {
                throw new ServiceException(e.getMessage());
            }
        }
        return topicList;
    }

    @Override
    public boolean addTopic(Topic topic) throws ServiceException {
        boolean isAdded;
        try {
            isAdded = topicDao.addTopic(topic);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return isAdded;
    }

    @Override
    public boolean removeTopic(int topicId) throws ServiceException {
        boolean isRemoved;
        try {
            isRemoved = topicDao.removeTopic(topicId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return isRemoved;
    }

    @Override
    public boolean editTopic(Topic topic) throws ServiceException {
        boolean isEdited;
        try {
            isEdited = topicDao.editTopic(topic);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return isEdited;
    }
}
