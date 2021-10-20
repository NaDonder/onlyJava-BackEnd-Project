package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPool {
    /**
     * Connection pool instance
     */
    private static ConnectionPool instance = null;

    /**
     * The max number of connections for this class is 10 (but can be 20)
     */
    public static final int NUM_OF_CONNECTIONS = 10;

    /**
     * Stack of connection
     */
    private Stack<Connection> connections = new Stack<>();

    /**
     * Default constructor with open all connection method inside
     * @throws SQLException needs to be checked.
     */
    public ConnectionPool() throws SQLException {
        openAllConnections();
    }

    /**
     * Pure singleton.
     * @return getting 1 connection at a time
     * @throws SQLException needs to be checked.
     */
    public static ConnectionPool getInstance() throws SQLException { // SingleTon
        if (instance == null) { // first check
            synchronized (ConnectionPool.class) {
                if (instance == null) { // double check
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    /**
     * a method to open connection by url, username and password to the data base
     * @throws SQLException needs to be checked.
     */
    private void openAllConnections() throws SQLException {
        for (int index = 0; index < NUM_OF_CONNECTIONS; index += 1) {
            //DATABASE credentials - ?
            Connection connection = DriverManager.getConnection(DataBaseManager.URL,DataBaseManager.USER_NAME,DataBaseManager.PASSWORD);
            connections.push(connection);
        }
    }

    /**
     * a method to get connection getting a single connection
     * checks if the stack if empty
     * and if the stack is empty we will wait until we will get a connection
     * @return pop if the stack if empty
     * @throws InterruptedException needs to be checked.
     */
    public Connection getConnection() throws InterruptedException {
        synchronized (connections) {
            if (connections.isEmpty()) {
                connections.wait();
            }
            return connections.pop();
        }
    }

    /**
     * a method to return the connection from the user
     * it will notify that we got back a connection from the user
     * and will notify all connections that are waiting to connection to be released
     * @param connection returns the connection
     */
    public void returnConnection(Connection connection) {
        synchronized (connections) {
            connections.push(connection);
            connections.notify();
        }
    }

    /**
     * a method to close all connection
     * @throws InterruptedException needs to be checked.
     */
    public void closeAllConnection() throws InterruptedException {
        synchronized (connections) {
            while (connections.size() < NUM_OF_CONNECTIONS) {
                connections.wait();
            }
            connections.removeAllElements();
        }
    }
}
