package ua.com.ua.com.homebudget.test;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ua.com.ua.com.homebudget.HelpClass;
import ua.com.ua.com.homebudget.steps.LoginSteps;

/**
 * Created by artyom on 10.11.15.
 */
public class LoginTest extends HelpClass{
    LoginSteps loginSteps;

    @Features("Login")
    @Stories("Negative Email Verification")
    @Test(dataProvider = "negativeEmailVerification")
    public void negativeEmailVerificationTest(String testName, String email, String password, int statuscode, String errorMessage){
        loginSteps = new LoginSteps(driver);
        loginSteps.openLoginPage();
        loginSteps.enterData(email, password);
        loginSteps.verifyEmailNotification(errorMessage);
        loginSteps.submitData();
    }

    @Features("Login")
    @Stories("Negative Email Validation")
    @Test(dataProvider = "negativeEmailValidation")
    public void negativeEmailValidationTest(String testName, String email, String password, int statuscode, String errorMessage){
        loginSteps = new LoginSteps(driver);
        loginSteps.openLoginPage();
        loginSteps.enterData(email, password);
        loginSteps.verifyEmailNotification(errorMessage);
        loginSteps.submitData();
    }

    @Features("Login")
    @Stories("Negative Password Verification")
    @Test(dataProvider = "negativePassVerification")
    public void negativePassVerificationTest(String testName, String email, String password, int statuscode, String errorMessage){
        loginSteps = new LoginSteps(driver);
        loginSteps.openLoginPage();
        loginSteps.enterData(email, password);
        loginSteps.verifyPassNotification(errorMessage);
        loginSteps.submitData();
    }

    @AfterMethod
    public void takeScreenShotOnFailure(ITestResult testResult)
    {
        if ((testResult.getStatus() == ITestResult.FAILURE)|(testResult.getStatus() == ITestResult.SUCCESS_PERCENTAGE_FAILURE))
        {
            loginSteps.makeScreenshot();
        }
    }


}
