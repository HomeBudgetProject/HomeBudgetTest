package ua.com.ua.com.homebudget.tests;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ua.com.ua.com.homebudget.Helper;

/**
 * Created by Anohin Artyom on 02.11.15.
 */
public class RegistrationTest extends Helper{


/*
    @AfterMethod
    public void setScreenshot(ITestResult result) {
        //make screenshot if not success
        if (result.isSuccess()) {
            registrationSteps.makeScreenshot();
        }
    }
*/

    @AfterMethod
    public void onTestFailure(ITestResult result) {
        registrationSteps.makeScreenshot();
    }


    @AfterClass
    public void afterTest() {
        //Close the browser
        registrationSteps.quit();
    }

    @Features("Registration")
    @Stories("Negative Email Verification")
    @Test(dataProvider = "negativeEmailVerification")
    public void negativePassVerificationTest(String testName, String email, String password, int statuscode, String errorMessage){
        registrationSteps.openRegistrationPage();
        registrationSteps.enterData(email, password);
        registrationSteps.sumbitData();
        registrationSteps.verifyEmailWarningMessage(errorMessage);
        registrationSteps.cleanAfterTest(email, password);
    }
/*
    @Features("Registration")
    @Stories("Negative Password Verification")
    @Test(dataProvider = "negativePassVerification")
    public void negativePassVerificationTest(String testName, String email, String password, int statuscode, String errorMessage){
        registrationStep.openRegistrationPage();
        registrationStep.enterData(email, password);
        registrationStep.sumbitData();
        registrationStep.verifyPassWarningMessage(errorMessage);
        registrationStep.cleanAfterTest(email, password);
    }

    @Features("Registration")
    @Stories("Positive Verification")
    @Test(dataProvider = "positiveVerification")
    public void positiveVerificationTest(String testName, String email, String password, int statuscode, String errorMessage){
        registrationStep.openRegistrationPage();
        registrationStep.enterData(email, password);
        registrationStep.sumbitData();
        registrationStep.verifyGeneralWarningMessage(errorMessage);
        registrationStep.cleanAfterTest(email, password);
    }

*/


}