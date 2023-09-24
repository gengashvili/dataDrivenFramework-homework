import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentDataRetrieval {

    public static ResultSet retrieveStudentData() throws SQLException {
        Connection connection;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();

            Statement statement = connection.createStatement();
            String query = "SELECT * FROM students";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return resultSet;
    }
}