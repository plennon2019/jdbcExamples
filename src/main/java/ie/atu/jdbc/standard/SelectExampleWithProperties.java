package ie.atu.jdbc.standard;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SelectExampleWithProperties {
    public static void main(String[] args) {
        Properties props = new Properties();

        // Load from the classpath. Note the leading "/".
        try (InputStream input = SelectExampleWithProperties.class
                .getResourceAsStream("/db.properties")) {

            // If the file isn't found, getResourceAsStream returns null
            if (input == null) {
                System.err.println("Unable to find db.properties on the classpath.");
                return;
            }
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return; // Exit if properties can't be loaded
        }

        // Read the properties
        String url = props.getProperty("db.url");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");

        // SQL statement
        String selectSQL = "SELECT u.username, u.password, e.email " +
                "FROM users u " +
                "JOIN emails e ON u.id = e.user_id";

        // Try-with-resources to ensure DB resources are closed
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {

            while (resultSet.next()) {
                String user = resultSet.getString("username");
                String pass = resultSet.getString("password");
                String email = resultSet.getString("email");

                System.out.println("Username: " + user +
                        ", Password: " + pass +
                        ", Email: " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
