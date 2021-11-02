package by.khaletski.project.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import by.khaletski.project.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionDemo {
    static final Logger LOGGER = LogManager.getLogger(ConnectionDemo.class);

    public static void main(String[] args) {
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
        try (Connection connection = DriverManager.getConnection(url, properties);
             Statement statement = connection.createStatement()) {
            String sqlRequest = "SELECT id, email, name, patronymic, surname, role FROM users";
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String email = resultSet.getString(2);
                String name = resultSet.getString("name");
                String patronymic = resultSet.getString("patronymic");
                String surname = resultSet.getString("surname");
                String role = resultSet.getString("role");
                userList.add(new User.Builder()
                        .setUserId(id)
                        .setEmail(email)
                        .setName(name)
                        .setPatronymic(patronymic)
                        .setSurname(surname)
                        .setRole(User.Role.valueOf(role))
                        .build());
            }
            System.out.println(userList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LOGGER.info("Connection established");
    }
}
