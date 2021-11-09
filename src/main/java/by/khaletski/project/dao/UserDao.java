package by.khaletski.project.dao;

import by.khaletski.project.entity.User;
import by.khaletski.project.dao.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> findAllUsers() throws DaoException;

    List<User> findUsersBySurname(String userSurname) throws DaoException;

    List<User> findUsersByRole(User.Role userRole) throws DaoException;

    boolean addUser(User user, String userPassword) throws DaoException;

    boolean removeUser(int userId) throws DaoException;

    boolean changeUserRole(int userId, User.Role userRole) throws DaoException;

    boolean editUserInfo(User user) throws DaoException;

    Optional<String> findPasswordByEmail(String userEmail) throws DaoException;

    Optional<User> findUserByEmail(String userEmail) throws DaoException;
}
