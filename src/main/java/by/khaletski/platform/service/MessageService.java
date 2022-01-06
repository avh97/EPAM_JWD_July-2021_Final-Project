package by.khaletski.platform.service;

import by.khaletski.platform.entity.Message;
import by.khaletski.platform.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    List<Message> findAll() throws ServiceException;

    Optional<Message> find(String id) throws ServiceException;

    List<Message> findByUserId(String userId) throws ServiceException;

    boolean add(String userId, String question) throws ServiceException;

    boolean edit(String id, String answer) throws ServiceException;

    boolean remove(String id) throws ServiceException;
}
