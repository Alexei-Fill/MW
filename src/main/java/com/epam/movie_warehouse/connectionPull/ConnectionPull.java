package com.epam.movie_warehouse.connectionPull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.DEFAULT_LOCALE;

public class ConnectionPull {
    private static final String CONNECTION_PULL_BUNDLE = "ConnectionPull";
    private static final String CONNECTION_PULL_URL = "url";
    private static final String CONNECTION_PULL_USER = "user";
    private static final String CONNECTION_PULL_PASSWORD = "password";
    private static final String CONNECTION_PULL_DRIVER = "driver";
    private static final String CONNECTION_PULL_INIT_CONNECTION_COUNT = "initConnectionCount";
    private static final String CONNECTION_NOT_FOUND_EXCEPTION = "Connection not found";
    private static ConnectionPull uniqueInstance = new ConnectionPull();
    private final Locale locale = new Locale(DEFAULT_LOCALE);
    private final ResourceBundle bundle = ResourceBundle.getBundle(CONNECTION_PULL_BUNDLE, locale);
    private final String url = bundle.getString(CONNECTION_PULL_URL);
    private final String user = bundle.getString(CONNECTION_PULL_USER);
    private final String password = bundle.getString(CONNECTION_PULL_PASSWORD);
    private final String driver = bundle.getString(CONNECTION_PULL_DRIVER);
    private final int initConnectionCount = Integer.parseInt(bundle.getString(CONNECTION_PULL_INIT_CONNECTION_COUNT));
    private final ArrayBlockingQueue<Connection> connectionsQueue = new ArrayBlockingQueue<>(initConnectionCount);

    private ConnectionPull() {
        try {
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 1; i <= initConnectionCount; i++) {
            try {
                connectionsQueue.put(getConnection());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static ConnectionPull getUniqueInstance(){
        return uniqueInstance;
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public synchronized Connection retrieve() {
        Connection connection = null;
        try {
            connection =  connectionsQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public synchronized void putBack(Connection connection) throws NullPointerException {
        if (connection != null) {
            try {
                connectionsQueue.put(connection);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
                throw new NullPointerException(CONNECTION_NOT_FOUND_EXCEPTION);
            }
    }
}
