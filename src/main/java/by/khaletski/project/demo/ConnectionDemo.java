package by.khaletski.project.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDemo {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/platform_db", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connection established");
    }
}
