import org.testng.annotations.*;

import static com.codeborne.selenide.Selenide.*;

public class FormTest {

    @Test(dataProvider = "studentData", dataProviderClass = StudentsDataProvider.class)
    public void fillForm(String firstName, String lastName, String phone) {
        open("https://demoqa.com/automation-practice-form");

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userNumber").setValue(phone);
    }
}
