package com.example.test.repository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private String user;
    private String password;
    private String url;

    public DatabaseConnection() throws IOException {
        loadDatabaseConfig();
    }

    private void loadDatabaseConfig() throws IOException {

        try (InputStream databaseConfig = getClass().getClassLoader().getResourceAsStream("Database.properties")) {
            if (databaseConfig == null) {
                throw new IOException("Database.properties file not found");
            }
            Properties properties = new Properties();
            properties.load(databaseConfig);

            user = properties.getProperty("username");
            password = properties.getProperty("password");
            url = properties.getProperty("url");
        }
    }

    public Connection getConnection() throws SQLException {

        return DriverManager.getConnection(url, user, password);
    }
}