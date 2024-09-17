package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Database {
    private Connection connection = null;

    private static Database instance = null;

    private final String DB_URI;
    private final String DB_USER;
    private final String DB_PASSWORD;

    private Database() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.DB_URI = props.getProperty("DB_URI");
        this.DB_USER = props.getProperty("DB_USER");
        this.DB_PASSWORD = props.getProperty("DB_PASSWORD");

        try {
            this.connection = DriverManager.getConnection(DB_URI, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() {
        return connection;
    }

    public static Connection getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new Database();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return instance.getConnection();
    }
}
