package com.todo.app.factory;

import com.todo.app.aplication.BusinessObjectSQLImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLAdapterImpl implements DBAdapter {
    private static final Logger LOGGER = Logger.getLogger(BusinessObjectSQLImpl.class.getName());

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
        try {
            String connectionString = "jdbc:mysql://localhost:3306/todo-app?zeroDateTimeBehavior=convertToNull&serverTimezone=UTC";
            String user = "root";
            String password = "";
            connection = DriverManager.getConnection(connectionString, user, password);

        }
        catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "Error while getting DB conection", exception);

        } finally {
            return connection;
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
