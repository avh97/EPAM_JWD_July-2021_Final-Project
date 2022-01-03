package by.khaletski.platform.dao.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Connection factory class
 *
 * @author Anton Khaletski
 */

public final class ConnectionFactory {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Properties PROPERTIES = new Properties();
    private static final String DATABASE_PROPERTIES = "database.properties";
    private static final String PROPERTIES_URL = "db.url";
    private static final String PROPERTIES_PASSWORD = "db.password";
    private static final String PROPERTIES_USER = "db.user";
    private static final String PROPERTIES_DRIVER = "db.driver";
    private static String url;
    private static String password;
    private static String user;

    private ConnectionFactory() {
    }

    static {
        try (InputStream inputStream = ConnectionFactory.class.getClassLoader()
                .getResourceAsStream(DATABASE_PROPERTIES)) {
            PROPERTIES.load(inputStream);
            url = PROPERTIES.getProperty(PROPERTIES_URL);
            password = PROPERTIES.getProperty(PROPERTIES_PASSWORD);
            user = PROPERTIES.getProperty(PROPERTIES_USER);
            String driver = PROPERTIES.getProperty(PROPERTIES_DRIVER);
            Class.forName(driver);
        } catch (ClassNotFoundException | IOException e) {
            LOGGER.error("Cannot get information from database.properties file");
        }
    }

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
