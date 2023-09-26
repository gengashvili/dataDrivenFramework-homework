package DDF_Querying.steps;

import DDF_Querying.data.StudentData;
import io.qameta.allure.Step;
import org.testng.Assert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbQueriesSteps {
    private Connection connection;
    private StudentData studentData = new StudentData();

    public DbQueriesSteps(Connection connection) {
        this.connection = connection;
    }

    @Step("Insert new row in students database")
    public DbQueriesSteps insertStudentNewRow() {
        String insertQuery = "INSERT INTO students (id, firstName, lastName, phone) VALUES (?, ?, ?, ?)";

        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            insertStatement.setInt(1, studentData.newStudentId);
            insertStatement.setString(2, studentData.newStudentFirstName);
            insertStatement.setString(3, studentData.newStudentLastName);
            insertStatement.setString(4, studentData.newStudentPhone);
            insertStatement.executeUpdate();

            System.out.println("inserted new student row");

            return this;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return this;
        }
    }

    @Step("Validate that row wasn't created")
    public DbQueriesSteps validateInsertedRowExistence() {
        String selectQuery = "SELECT * FROM students WHERE id = ?";

        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            selectStatement.setInt(1, studentData.newStudentId);
            ResultSet resultSet = selectStatement.executeQuery();

            boolean studentExists = resultSet.next();

            if (studentExists) {
                System.out.println("Row exists before commit.");
            } else {
                System.out.println("Row does not exist before commit.");
            }

            return this;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return this;
        }
    }

    @Step("Call commit")
    public DbQueriesSteps callCommit() {
        try {
            connection.commit();
            System.out.println("Commit called successfully.");
            return this;
        } catch (SQLException e) {
            System.out.println("Error calling commit: " + e.getMessage());
            return this;
        }
    }

    @Step("Validate all values of inserted row using TestNG")
    public DbQueriesSteps validateInsertedRow() {
        String selectQuery = "SELECT * FROM students WHERE id = ?";

        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            selectStatement.setInt(1, studentData.newStudentId);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                String actualFirstName = resultSet.getString("firstName");
                String actualLastName = resultSet.getString("lastName");
                String actualPhone = resultSet.getString("phone");

                String expectedFirstName = studentData.newStudentFirstName;
                String expectedLastName = studentData.newStudentLastName;
                String expectedPhone = studentData.newStudentPhone;

                Assert.assertEquals(actualFirstName, expectedFirstName);
                Assert.assertEquals(actualLastName, expectedLastName);
                Assert.assertEquals(actualPhone, expectedPhone);

                System.out.println("Validated all the values of inserted row.");
            } else {
                System.out.println("Row not found in the database.");
            }

            return this;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return this;
        }
    }

    @Step("Update firstName of added student")
    public DbQueriesSteps updateFirstName() {
        String updateQuery = "UPDATE students SET firstName = ? WHERE id = ?";

        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setString(1, studentData.updatedFirstName);
            updateStatement.setInt(2, studentData.newStudentId);
            updateStatement.executeUpdate();

            System.out.println("Updated inserted student's first name.");
            return this;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return this;
        }
    }

    @Step("Validate updated firstName using TestNG")
    public DbQueriesSteps validateUpdatedFirstName() {
        String selectQuery = "SELECT firstName FROM students WHERE id = ?";

        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            selectStatement.setInt(1, studentData.newStudentId);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                String actualFirstName = resultSet.getString("firstName");
                String expectedFirstName = studentData.updatedFirstName;

                Assert.assertEquals(actualFirstName, expectedFirstName);
                System.out.println("Validated updated firstName.");
            } else {
                System.out.println("Row not found in the database.");
            }

            return this;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return this;
        }
    }
}
