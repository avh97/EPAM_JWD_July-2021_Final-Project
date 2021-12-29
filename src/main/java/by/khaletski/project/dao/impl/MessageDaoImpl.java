package by.khaletski.project.dao.impl;

import by.khaletski.project.dao.MessageDao;
import by.khaletski.project.dao.exception.DaoException;
import by.khaletski.project.dao.pool.ConnectionPool;
import by.khaletski.project.entity.Message;
import by.khaletski.project.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageDaoImpl implements MessageDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SQL_FIND_ALL_MESSAGES
            = "SELECT messages.id, messages.user_id, messages.timestamp, messages.question, messages.answer,\n" +
            "users.email, users.name, users.patronymic, users.surname, users.role FROM messages\n" +
            "INNER JOIN users ON messages.user_id = users.id";
    private static final String SQL_FIND_MESSAGE_BY_ID
            = "SELECT messages.id, messages.user_id, messages.timestamp, messages.question, messages.answer,\n" +
            "users.email, users.name, users.patronymic, users.surname, users.role FROM messages\n" +
            "INNER JOIN users ON messages.user_id = users.id WHERE messages.id=?";
    private static final String SQL_FIND_MESSAGES_BY_USER_ID
            = "SELECT messages.id, messages.user_id, messages.timestamp, messages.question, messages.answer,\n" +
            "users.email, users.name, users.patronymic, users.surname, users.role FROM messages\n" +
            "INNER JOIN users ON messages.user_id = users.id WHERE messages.user_id=?";
    private static final String SQL_ADD_MESSAGE
            = "INSERT INTO messages (user_id, question) values(?,?)";
    private static final String SQL_EDIT_MESSAGE_BY_ID
            = "UPDATE messages SET answer=? WHERE id=?";
    private static final String SQL_REMOVE_MESSAGE_BY_ID
            = "DELETE FROM messages WHERE id=?";

    @Override
    public List<Message> findAll() throws DaoException {
        LOGGER.info("Attempt to find all messages in the database");
        List<Message> messages = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_MESSAGES)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                messages.add(retrieve(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find all messages in the database");
            throw new DaoException(e);
        }
        return messages;
    }

    @Override
    public Optional<Message> find(int id) throws DaoException {
        LOGGER.info("Attempt to find message by message ID in the database");
        Optional<Message> optional = Optional.empty();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_MESSAGE_BY_ID)){
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Message message = retrieve(resultSet);
                optional = Optional.of(message);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find message by message ID in the database");
            throw new DaoException(e);
        }
        return optional;
    }

    @Override
    public List<Message> findByUserId(int id) throws DaoException {
        LOGGER.info("Attempt to find all messages in the database");
        List<Message> messages = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_MESSAGES_BY_USER_ID)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                messages.add(retrieve(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to find all messages in the database");
            throw new DaoException(e);
        }
        return messages;
    }

    @Override
    public boolean add(Message message) throws DaoException {
        LOGGER.info("Attempt to add new message to the database");
        boolean isAdded = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_MESSAGE)) {
            statement.setInt(1, message.getUser().getId());
            statement.setString(2, message.getQuestion());
            int counter = statement.executeUpdate();
            if (counter != 0) {
                isAdded = true;
                LOGGER.info("New message has been added");
            } else {
                LOGGER.error("New message has not been added");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to add new conference to the database");
            throw new DaoException(e);
        }
        return isAdded;
    }

    @Override
    public boolean edit(Message message) throws DaoException {
        LOGGER.info("Attempt to change message in the database");
        boolean isChanged = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_EDIT_MESSAGE_BY_ID)) {
            statement.setString(1, message.getAnswer());
            statement.setInt(2, message.getId());
            int counter = statement.executeUpdate();
            if (counter != 0) {
                isChanged = true;
                LOGGER.info("Message has been changed");
            } else {
                LOGGER.error("Message has not been changed");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to change message in the database");
            throw new DaoException(e);
        }
        return isChanged;
    }

    @Override
    public boolean remove(int id) throws DaoException {
        LOGGER.info("Attempt to remove message from the database");
        boolean isRemoved = false;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_REMOVE_MESSAGE_BY_ID)) {
            statement.setInt(1, id);
            int counter = statement.executeUpdate();
            if (counter != 0) {
                isRemoved = true;
                LOGGER.info("Message has been removed");
            } else {
                LOGGER.info("Message has not been removed");
            }
        } catch (SQLException e) {
            LOGGER.error("Failed attempt to remove message from the database");
            throw new DaoException(e);
        }
        return isRemoved;
    }

    private Message retrieve(ResultSet resultSet) throws SQLException {
        return new Message.Builder()
                .setId(resultSet.getInt("id"))
                .setUser(new User.Builder()
                        .setId(resultSet.getInt("user_id"))
                        .setEmail(resultSet.getString("email"))
                        .setName(resultSet.getString("name"))
                        .setPatronymic(resultSet.getString("patronymic"))
                        .setSurname(resultSet.getString("surname"))
                        .setRole(User.Role.valueOf(resultSet.getString("role")))
                        .build())
                .setTimestamp(resultSet.getTimestamp("timestamp"))
                .setQuestion(resultSet.getString("question"))
                .setAnswer(resultSet.getString("answer"))
                .build();
    }
}
