package DDF_Querying;

import DDF_Querying.steps.DbQueriesSteps;
import common.DatabaseConnection;

import org.testng.annotations.*;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLQueriesTest {
    private Connection connection;
    private DbQueriesSteps dbQueriesSteps;

    public SQLQueriesTest() {
        try {

            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);
            dbQueriesSteps = new DbQueriesSteps(connection);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test()
    public void testStudentsDB() {

        dbQueriesSteps
                .insertStudentNewRow()
                .validateInsertedRowExistence()
                .callCommit()
                .validateInsertedRow()
                .updateFirstName()
                .callCommit()
                .validateUpdatedFirstName();

    }


    @AfterClass
    public void tearDown() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
