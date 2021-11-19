package by.khaletski.project.service.impl;

import by.khaletski.project.dao.TopicDao;
import by.khaletski.project.dao.exception.DaoException;
import by.khaletski.project.entity.Topic;
import by.khaletski.project.service.exception.ServiceException;
import by.khaletski.project.service.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            throw new ServiceException(e);
        }
        return topicList;
    }

    @Override
    public List<Topic> findTopicsByName(String topicName) throws ServiceException {
        List<Topic> topicList = new ArrayList<>();
        if (Validator.isValidName(topicName)) {
            try {
                topicList = topicDao.findTopicByName(topicName);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return topicList;
    }

    @Override
    public boolean addTopic(Map<String, String> topicData) throws ServiceException {
        boolean isAdded;
        if (Validator.isValidName(topicData.get("topic_name"))
                && Validator.isValidImageName(topicData.get("image_name"))
                && Validator.isValidEmail(topicData.get("topic_description"))) {
            Topic topic = new Topic.Builder()
                    .setName("topic_name")
                    .setImageName("image_name")
                    .setDescription("topic_description")
                    .build();
            try {
                isAdded = topicDao.addTopic(topic);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            isAdded = false;
        }
        return isAdded;
    }

    @Override
    public boolean removeTopic(int topicId) throws ServiceException {
        boolean isRemoved;
        try {
            isRemoved = topicDao.removeTopic(topicId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isRemoved;
    }

    @Override
    public boolean editTopic(Map<String, String> topicData) throws ServiceException {
        boolean isEdited;
        try {
            Topic topic = new Topic.Builder()
                    .setName(topicData.get("topic_name"))
                    .setImageName(topicData.get("image_name"))
                    .setDescription(topicData.get("topic_description"))
                    .build();
            isEdited = topicDao.editTopic(topic);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isEdited;
    }
}
