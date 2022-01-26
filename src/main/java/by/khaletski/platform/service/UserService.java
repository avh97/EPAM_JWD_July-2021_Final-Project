package by.khaletski.platform.service;

import by.khaletski.platform.entity.User;
import by.khaletski.platform.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll() throws ServiceException;

    Optional<User> find(String id) throws ServiceException;

    boolean add(String password, String email, String name, String patronymic, String surname) throws ServiceException;

    boolean changeRole(String id, String role) throws ServiceException;

    boolean edit(User user, String email, String name, String patronymic, String surname) throws ServiceException;

    boolean remove(String id) throws ServiceException;

    Optional<User> findByEmail(String email) throws ServiceException;

    Optional<User> findByEmailAndPassword(String email, String password) throws ServiceException;
}
