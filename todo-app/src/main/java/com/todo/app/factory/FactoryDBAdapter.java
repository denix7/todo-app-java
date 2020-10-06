package com.todo.app.factory;

import com.todo.app.infrastructure.TaskMySqlDAOImpl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class FactoryDBAdapter {

    private static final String DB_TYPE = "dbadaptertype";
    private static final Logger LOGGER = Logger.getLogger(FactoryDBAdapter.class.getName());

    public static DBAdapter getAdapter(){
        DBAdapter dbAdapter = null;
        try {
            Properties p = loadProperties();
            String dbType = p.getProperty(DB_TYPE);
            dbAdapter = (DBAdapter)Class.forName(dbType).newInstance();
        }
        catch (IOException | ClassNotFoundException exception) {
            LOGGER.log(Level.SEVERE, "Unable to get connection", exception);
        } finally {
            return dbAdapter;
        }
    }

    private static Properties loadProperties() throws IOException {
            Properties p = new Properties();
            InputStream stream = FactoryDBAdapter.class.getClassLoader().getResourceAsStream("META-INF/dbadapter.properties");
            p.load(stream);
            return p;
    }
}
