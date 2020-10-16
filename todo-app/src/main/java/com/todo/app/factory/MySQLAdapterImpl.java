package com.todo.app.factory;

import com.todo.app.aplication.TaskServiceImp;
import com.todo.app.exceptions.PersistentException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLAdapterImpl implements DBAdapter {
    private static final Logger LOGGER = Logger.getLogger(TaskServiceImp.class.getName());

    static{
        try {
            new com.mysql.jdbc.Driver();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while instiantate JDBC", exception);
        }
    }

    private Connection connection;

    @Override
    public Connection getConnection() {
        String connectionString = "jdbc:mysql://localhost:3306/todo-app?zeroDateTimeBehavior=convertToNull&serverTimezone=UTC";
        String user = "root";
        String password = "";

        try {
            connection = DriverManager.getConnection(connectionString, user, password);

            return connection;
        }
        catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "Error while getting DB conection", exception);
            throw new PersistentException("Cannot connect to DB", exception);
        }
    }

    public void closeConnection() throws SQLException {
        if(connection != null){
            if(!connection.isClosed()){
                connection.close();
            }
        }
    }
}
