package by.khaletski.project.dao;

import by.khaletski.project.entity.Application;
import by.khaletski.project.dao.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface ApplicationDao {
    List<Application> findAll() throws DaoException;

    Optional<Application> find(int id) throws DaoException;

    boolean add(Application application) throws DaoException;

    boolean changeStatus(int id, Application.Status status) throws DaoException;

    boolean edit(Application application) throws DaoException;

    boolean remove(int id) throws DaoException;
}
