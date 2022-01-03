package by.khaletski.platform.dao;

import by.khaletski.platform.entity.Application;
import by.khaletski.platform.dao.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface ApplicationDao {
    List<Application> findAll() throws DaoException;

    Optional<Application> find(int id) throws DaoException;

    List<Application> findByUserId(int id) throws DaoException;

    List<Application> findByConferenceId(int id) throws DaoException;

    boolean add(Application application) throws DaoException;

    boolean changeStatus(int id, Application.Status status) throws DaoException;

    boolean edit(Application application) throws DaoException;

    boolean remove(int id) throws DaoException;
}
