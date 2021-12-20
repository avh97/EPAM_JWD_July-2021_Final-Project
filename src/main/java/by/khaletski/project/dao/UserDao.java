package by.khaletski.project.dao;

import by.khaletski.project.entity.User;
import by.khaletski.project.dao.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> findAll() throws DaoException;

    Optional<User> find(int id) throws DaoException;

    boolean add(User user, String password) throws DaoException;

    boolean changeRole(int id, User.Role role) throws DaoException;

    boolean edit(User user) throws DaoException;

    boolean remove(int id) throws DaoException;

    Optional<User> findUserByEmail(String email) throws DaoException;

    Optional<String> findPasswordByEmail(String email) throws DaoException;
}
