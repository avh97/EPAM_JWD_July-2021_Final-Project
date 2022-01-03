package by.khaletski.platform.dao;

import by.khaletski.platform.entity.Topic;
import by.khaletski.platform.dao.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface TopicDao {
    List<Topic> findAll() throws DaoException;

    Optional<Topic> find(int id) throws DaoException;

    boolean add(Topic topic) throws DaoException;

    boolean edit(Topic topic) throws DaoException;

    boolean remove(int id) throws DaoException;
}
