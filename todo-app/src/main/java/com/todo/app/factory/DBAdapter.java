package com.todo.app.factory;

import java.sql.Connection;

public interface DBAdapter {
        public Connection getConnection();
}
