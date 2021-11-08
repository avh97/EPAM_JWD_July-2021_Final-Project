package by.khaletski.project.service.impl;

import by.khaletski.project.dao.TopicDao;
import by.khaletski.project.entity.Topic;
import by.khaletski.project.service.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TopicServiceImpl {
    private static final Logger LOGGER = LogManager.getLogger();
    private final TopicDao topicDao;

    public TopicServiceImpl(TopicDao topicDao) {
        this.topicDao = topicDao;
    }

    List<Topic> findAllTopics() {
        List<Topic> topicList;
        topicList = topicDao.findAllTopics();
        return topicList;
    }

    public List<Topic> findTopicsByName(String topicName) {
        List<Topic> topicList = new ArrayList<>();
        if (Validator.isValidName(topicName)) {
            topicList = topicDao.findTopicsByName(topicName);
        }
        return topicList;
    }

    boolean addTopic(Topic topic) {
        boolean isAdded;
        isAdded = topicDao.addTopic(topic);
        return isAdded;
    }

    boolean removeTopic(int topicId) {
        boolean isRemoved;
        isRemoved = topicDao.removeTopic(topicId);
        return isRemoved;
    }

    boolean editTopic(Topic topic) {
        boolean isEdited;
        isEdited = topicDao.editTopic(topic);
        return isEdited;
    }
}
