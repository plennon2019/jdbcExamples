package ie.atu.jdbc.pool;

import java.sql.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DatabaseUtils {
    //later we will look at storing this type of data in a better location like a properties file
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseUtils.class);
    private static final HikariDataSource dataSource;
    private static final String URL = "jdbc:mysql://localhost:3306/exampledatabase";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";

    //notice the static has no name?
    //The static block does not have a method name because it is a special block of code that
    // is executed when the class is loaded into memory. It is used to initialize static variables and perform
    // any other one-time setup that the class may require.
    static {
        try
        {
            //configure the connection pool
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(URL);
            config.setUsername(USERNAME);
            config.setPassword(PASSWORD);
            config.setMaximumPoolSize(5);

            // Create the DataSource (connection pool)
            dataSource = new HikariDataSource(config);
        } catch (Exception e)
        {
            LOGGER.error("Error initializing HikariCP DataSource", e);
            throw new ExceptionInInitializerError(e);
        }
    }


    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    public static void close() {
        if (dataSource != null) {
            LOGGER.info("Closing HikariCP DataSource...");
            dataSource.close();
        }
    }
}