package by.khaletski.project.dao.pool;

import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import org.apache.logging.log4j.Logger;

/**
 * Connection pool class via Enum Singleton
 *
 * @author Anton Khaletski
 */

public enum ConnectionPool {
	INSTANCE;

	static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
	private BlockingQueue<ProxyConnection> freeConnections;
	private Queue<ProxyConnection> givenAwayConnections;
	private static final int DEFAULT_POOL_SIZE = 8;

	ConnectionPool() {
		freeConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
		givenAwayConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
		LogManager.getLogger(ConnectionPool.class).info("Attempt to create connection pool");
		for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
			try {
				Connection connection = ConnectionFactory.getConnection();
				ProxyConnection proxyConnection = new ProxyConnection(connection);
				freeConnections.add(proxyConnection);
			} catch (SQLException e) {
				LogManager.getLogger(ConnectionPool.class)
						.error("Cannot create connection pool: %s".formatted(e.getMessage()));
			}
		}
		LogManager.getLogger(ConnectionPool.class).info("Connection pool has been created");
	}

	public Connection getConnection() {
		Connection connection = null;
		try {
			connection = freeConnections.take();
			givenAwayConnections.offer((ProxyConnection) connection);
		} catch (InterruptedException e) {
			LOGGER.warn("Thread has been interrupted!");
			Thread.currentThread().interrupt();
		}
		return connection;
	}

	public void releaseConnection(final Connection connection) {
		if (connection instanceof ProxyConnection) {
			freeConnections.offer((ProxyConnection) connection);
			givenAwayConnections.remove(connection);
		}
	}

	public void destroyPool() {
		for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
			try {
				freeConnections.take().proxyClose();
			} catch (InterruptedException | SQLException e) {
				LOGGER.warn("Thread has been interrupted!");
				Thread.currentThread().interrupt();
			}
		}
		deregisterDrivers();
	}

	private void deregisterDrivers() {
		DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
			try {
				DriverManager.deregisterDriver(driver);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
}
