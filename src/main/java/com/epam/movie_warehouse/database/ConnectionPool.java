package com.epam.movie_warehouse.database;

import com.epam.movie_warehouse.exception.ConnectionNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.CONNECTION_NOT_FOUND_EXCEPTION;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.DEFAULT_LOCALE;

public class ConnectionPool {
    private static final String CONNECTION_PULL_BUNDLE = "connectionPull";
    private static final String CONNECTION_PULL_URL = "url";
    private static final String CONNECTION_PULL_USER = "user";
    private static final String CONNECTION_PULL_PASSWORD = "password";
    private static final String CONNECTION_PULL_DRIVER = "driver";
    private static final String CONNECTION_PULL_INIT_CONNECTION_COUNT = "initConnectionCount";
    private static final ConnectionPool UNIQUE_INSTANCE = new ConnectionPool();
    private final Locale LOCALE = new Locale(DEFAULT_LOCALE);
    private final ResourceBundle BUNDLE = ResourceBundle.getBundle(CONNECTION_PULL_BUNDLE, LOCALE);
    private final String URL = BUNDLE.getString(CONNECTION_PULL_URL);
    private final String USER = BUNDLE.getString(CONNECTION_PULL_USER);
    private final String PASSWORD = BUNDLE.getString(CONNECTION_PULL_PASSWORD);
    private final String DRIVER = BUNDLE.getString(CONNECTION_PULL_DRIVER);
    private final int INIT_CONNECTION_COUNT = Integer.parseInt(BUNDLE.getString(CONNECTION_PULL_INIT_CONNECTION_COUNT));
    private final ArrayBlockingQueue<Connection> CONNECTION_QUEUE = new ArrayBlockingQueue<>(INIT_CONNECTION_COUNT);
    private static final Logger ROOT_LOGGER = LogManager.getRootLogger();

    private ConnectionPool() {
        try {
            Class.forName(DRIVER);
        } catch (Exception e) {
            ROOT_LOGGER.error(e);
        }
        for (int i = 1; i <= INIT_CONNECTION_COUNT; i++) {
            try {
                CONNECTION_QUEUE.put(getConnection());
            } catch (InterruptedException e) {
                ROOT_LOGGER.error(e);
            }
        }
    }

    public static ConnectionPool getUniqueInstance() {
        return UNIQUE_INSTANCE;
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            ROOT_LOGGER.error(e);
        }
        return connection;
    }

    public synchronized Connection retrieve() {
        Connection connection = null;
        try {
            connection = CONNECTION_QUEUE.take();
        } catch (InterruptedException e) {
            ROOT_LOGGER.error(e);
        }
        return connection;
    }

    public synchronized void putBack(Connection connection) throws ConnectionNotFoundException {
        if (connection != null) {
            try {
                CONNECTION_QUEUE.put(connection);
            } catch (InterruptedException e) {
                ROOT_LOGGER.error(e);
            }
        } else {
            ROOT_LOGGER.error(CONNECTION_NOT_FOUND_EXCEPTION);
            throw new ConnectionNotFoundException();
        }
    }
}
