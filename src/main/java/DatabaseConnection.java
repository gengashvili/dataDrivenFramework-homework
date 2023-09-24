import java.io.FileInputStream;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    public static Connection getConnection() throws SQLException {
        Connection connection = null;

        try (FileInputStream file = new FileInputStream("src/main/resources/db.properties")) {

            Properties properties = new Properties();
            properties.load(file);

            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");

            connection = DriverManager.getConnection(url,user,password);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
