package com.hospital.connection;

import com.hospital.exception.DatabaseException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static final Logger logger = Logger.getLogger(DBConnection.class);
    private static Connection connection = null;

    public static Connection getDBConnection() throws DatabaseException {
        if(connection == null) {
            logger.info("Connecting to database...");
            try(InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
                logger.info("Loading properties from file..." + input);
                if(input == null) {
                    throw new DatabaseException("db properties not found");
                }

                Properties properties = new Properties();
                properties.load(input);
                logger.info("Loading properties from file..." + properties);
                String url = properties.getProperty("url");
                String user = properties.getProperty("user");
                String password = properties.getProperty("password");
                String driver = properties.getProperty("driver");

                logger.info("Database URL: " + url);
                logger.info("Database Driver: " + driver);

                Class.forName(driver);

                connection = DriverManager.getConnection(url, user, password);
                logger.info("Database connection established");
            } catch (IOException | ClassNotFoundException | SQLException e) {
                logger.error("Database Connection failed: " + e.getMessage());
                logger.error(e);
                throw new DatabaseException("Database Connection failed: " + e.getMessage(), e);
            }
        }

        return connection;
    }
}
