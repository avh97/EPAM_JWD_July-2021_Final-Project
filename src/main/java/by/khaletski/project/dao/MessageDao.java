package by.khaletski.project.dao;

import by.khaletski.project.dao.exception.DaoException;
import by.khaletski.project.entity.Message;

import java.util.List;
import java.util.Optional;

public interface MessageDao {
    List<Message> findAll() throws DaoException;

    Optional<Message> find(int id) throws DaoException;

    List<Message> findByUserId(int id) throws DaoException;

    boolean add(Message message) throws DaoException;

    boolean edit(Message message) throws DaoException;

    boolean remove(int id) throws DaoException;
}
