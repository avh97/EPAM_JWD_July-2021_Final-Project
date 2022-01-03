package by.khaletski.platform.dao;

import by.khaletski.platform.entity.Conference;
import by.khaletski.platform.dao.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface ConferenceDao {
    List<Conference> findAll() throws DaoException;

    Optional<Conference> find(int id) throws DaoException;

    boolean add(Conference conference) throws DaoException;

    boolean changeStatus(int id, Conference.Status status) throws DaoException;

    boolean edit(Conference conference) throws DaoException;

    boolean remove(int id) throws DaoException;
}
