package by.khaletski.project.service;

import by.khaletski.project.entity.Message;
import by.khaletski.project.service.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MessageService {
    List<Message> findAll() throws ServiceException;

    Optional<Message> find(int id) throws ServiceException;

    List<Message> findByUserId(int id) throws ServiceException;

    boolean add(Map<String, String> messageData) throws ServiceException;

    boolean edit(Map<String, String> messageData) throws ServiceException;

    boolean remove(int id) throws ServiceException;
}
