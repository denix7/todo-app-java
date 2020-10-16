package com.todo.app.factory;

import com.todo.app.exceptions.PersistentException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class FactoryDBAdapter {

    private static final String DB_TYPE = "dbadaptertype";
    private static final Logger LOGGER = Logger.getLogger(FactoryDBAdapter.class.getName());

    public static DBAdapter getAdapter(){
        DBAdapter dbAdapter;
        try {
            Properties p = loadProperties();
            String dbType = p.getProperty(DB_TYPE);
            dbAdapter = (DBAdapter)Class.forName(dbType).newInstance();

            return dbAdapter;
        }
        catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException exception) {
            LOGGER.log(Level.SEVERE, "Unable to get DB adapter");
            return null;
        }
    }

    private static Properties loadProperties() throws IOException {
            Properties p = new Properties();
            InputStream stream = FactoryDBAdapter.class.getClassLoader().getResourceAsStream("META-INF/dbadapter.properties");
            p.load(stream);
            return p;
    }
}
