package by.khaletski.project.dao;

import by.khaletski.project.entity.Topic;
import by.khaletski.project.dao.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface TopicDao {
    List<Topic> findAll() throws DaoException;

    Optional<Topic> find(int id) throws DaoException;

    boolean add(Topic topic) throws DaoException;

    boolean edit(Topic topic) throws DaoException;

    boolean remove(int id) throws DaoException;
}
