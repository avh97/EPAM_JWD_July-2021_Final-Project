package by.khaletski.platform.service;

import by.khaletski.platform.entity.User;
import by.khaletski.platform.service.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    List<User> findAll() throws ServiceException;

    Optional<User> find(int id) throws ServiceException;

    boolean add(Map<String, String> userData) throws ServiceException;

    boolean changeRole(int id, User.Role role) throws ServiceException;

    boolean edit(User user, Map<String, String> userData) throws ServiceException;

    boolean remove(int id) throws ServiceException;

    Optional<User> findByEmail(String email) throws ServiceException;

    Optional<User> findByEmailAndPassword(String email, String password) throws ServiceException;
}
