package by.khaletski.project.dao;

import by.khaletski.project.exception.DaoException;
import by.khaletski.project.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Dao class "UserDao"
 *
 * @author Anton Khaletski
 */

public class UserDao {
    static final Logger logger = LogManager.getLogger(UserDao.class);
    private static final String SQL_FIND_ALL_USERS
            = "SELECT id, email, name, surname, patronymic, role FROM users";
    private static final String SQL_FIND_USERS_FROM_ROW
            = "SELECT id, email, name, surname, patronymic, role FROM users LIMIT ?,?";
    private static final String SQL_FIND_PASSWORD_BY_EMAIL
            = "SELECT password FROM users WHERE email=?";
    private static final String SQL_FIND_USER_BY_EMAIL
            = "SELECT id, email, name, surname, patronymic, role FROM users WHERE email=?";
    private static final String SQL_FIND_USERS_BY_NAME
            = "SELECT id, email, name, surname, patronymic, role FROM users WHERE name=?";
    private static final String SQL_FIND_USERS_BY_PATRONYMIC
            = "SELECT id, email, name, surname, patronymic, role FROM users WHERE patronymic=?";
    private static final String SQL_FIND_USERS_BY_SURNAME
            = "SELECT id, email, name, surname, patronymic, role FROM users WHERE surname=?";
    private static final String SQL_FIND_USERS_BY_ROLE
            = "SELECT id, email, name, surname, patronymic, role FROM users WHERE role=?";
    private static final String SQL_ADD_USER
            = "INSERT INTO users (id, email, name, surname, patronymic, role) values(?,?,?,?,?,?)";
    private static final String SQL_CHANGE_USER_ROLE
            = "UPDATE users SET role=? WHERE user_id=?";
    private static final String SQL_CHANGE_USER_INFO
            = "UPDATE users SET name=?, surname=?, phone=? WHERE id=?";

    public List<User> findAll() throws DaoException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/platform_db";
        Properties properties = new Properties();
        properties.put("user", "platform_app");
        properties.put("password", "platform_password");
        properties.put("autoReconnect", "true");
        properties.put("characterEncoding", "UTF-8");
        properties.put("useUnicode", "true");
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, properties);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_USERS)) {
            while (resultSet.next()) {
                users.add(createUser(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("Dao exception", e);
        }
        return users;
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String email = resultSet.getString("email");
        String name = resultSet.getString("name");
        String patronymic = resultSet.getString("patronymic");
        String surname = resultSet.getString("surname");
        User.Role role = User.Role.valueOf(resultSet.getString("role").toUpperCase());
        return new User.Builder()
                .setUserId(id)
                .setEmail(email)
                .setName(name)
                .setPatronymic(patronymic)
                .setSurname(surname)
                .setRole(role)
                .build();
    }
}
