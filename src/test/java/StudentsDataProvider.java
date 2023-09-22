import org.testng.annotations.DataProvider;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentsDataProvider {

    @DataProvider(name = "studentData")
    public static Object[][] provideStudentData() {
        try (ResultSet resultSet = StudentDataRetrieval.retrieveStudentData()) {
            List<Object[]> data = new ArrayList<>();

            while (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String phone = resultSet.getString("phone");

                data.add(new Object[]{firstName, lastName, phone});
            }

            return data.toArray(new Object[0][0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Object[0][0];
    }
}
