package by.khaletski.project.service;

import by.khaletski.project.entity.User;
import by.khaletski.project.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAllUsers() throws ServiceException;

    List<User> findUsersBySurname(String userSurname) throws ServiceException;

    List<User> findUsersByRole(User.Role userRole) throws ServiceException;

    boolean addUser(User user, String userPassword) throws ServiceException;

    boolean removeUser(int id) throws ServiceException;

    boolean changeUserRole(int userId, User.Role userRole) throws ServiceException;

    boolean editUserInfo(User user) throws ServiceException;

    Optional<User> findUserByEmailAndPassword(String email, String password) throws ServiceException;
}
