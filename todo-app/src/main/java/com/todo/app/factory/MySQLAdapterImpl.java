package com.todo.app.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLAdapterImpl implements DBAdapter {
    static{
        try {
            new com.mysql.jdbc.Driver();
        } catch (Exception e) {}
    }

    private Connection connection;

    @Override
    public Connection getConnection() {
        try {
            String connectionString = "jdbc:mysql://localhost:3306/todo-app?zeroDateTimeBehavior=convertToNull&serverTimezone=UTC";
            String user = "root";
            String password = "";
            connection = DriverManager.getConnection(connectionString, user, password);
            return connection;
        }
        catch (SQLException exception) {
            exception.printStackTrace();
            return null;
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
