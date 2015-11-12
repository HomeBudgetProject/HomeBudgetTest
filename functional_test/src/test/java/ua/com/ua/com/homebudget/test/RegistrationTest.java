package ua.com.ua.com.homebudget.test;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ua.com.ua.com.homebudget.HelpClass;
import ua.com.ua.com.homebudget.steps.RegistrationSteps;

/**
 * Created by Anohin Artyom on 10.11.15.
 */
public class RegistrationTest extends HelpClass {
    RegistrationSteps registrationSteps;


    @Features("Registration")
    @Stories("Negative Email Verification")
    @Test(dataProvider = "negativeEmailVerification")
    public void negativeEmailVerificationTest(String testName, String email, String password, int statuscode, String notificationMessage){
        registrationSteps = new RegistrationSteps(driver);
        registrationSteps.openRegistrationPage();
        registrationSteps.enterData(email, password);
        registrationSteps.verifyNegativeEmailNotification(notificationMessage);
        //add verify after fix task #42
    }

    @Features("Registration")
    @Stories("Negative Email Validation")
    @Test(dataProvider = "negativeEmailValidation")
    public void negativeEmailValidationTest(String testName, String email, String password, int statuscode, String notificationMessage){
        registrationSteps = new RegistrationSteps(driver);
        registrationSteps.openRegistrationPage();
        registrationSteps.enterData(email, password);
        registrationSteps.verifyNegativeEmailNotification(notificationMessage);
        //add verify after fix task #42
    }

    @Features("Registration")
    @Stories("Negative Password Verification")
    @Test(dataProvider = "negativePassVerification")
    public void negativePassVerificationTest(String testName, String email, String password, int statuscode, String notificationMessage){
        registrationSteps = new RegistrationSteps(driver);
        registrationSteps.openRegistrationPage();
        registrationSteps.enterData(email, password);
        registrationSteps.verifyPassNotification(notificationMessage);
        //add verify after fix task #42
    }

    @Features("Registration")
    @Stories("Positive Email Verification")
    @Test(dataProvider = "positiveRegisterVerification")
    public void positiveRegisterVerificationTest(String testName, String email, String password, int statuscode, String notificationMessage){
        registrationSteps = new RegistrationSteps(driver);
        registrationSteps.openRegistrationPage();
        registrationSteps.enterData(email, password);
        registrationSteps.submitData();
        registrationSteps.clearAfterTest(email,password);
        registrationSteps.verifyPositiveEmailNotification(notificationMessage);
    }


    @Features("Registration")
    @Stories("Positive Email Validation")
    @Test(dataProvider = "positiveRegisterEmailValidation")
    public void positiveEmailValidationTest(String testName, String email, String password, int statuscode, String notificationMessage){
        registrationSteps = new RegistrationSteps(driver);
        registrationSteps.openRegistrationPage();
        registrationSteps.enterData(email, password);
        registrationSteps.submitData();
        registrationSteps.clearAfterTest(email,password);
        registrationSteps.verifyPositiveEmailNotification(notificationMessage);
    }

    @Features("Registration")
    @Stories("Exist Email")
    @Test
    public void existEmailTest(){
        registrationSteps = new RegistrationSteps(driver);
        registrationSteps.openRegistrationPage();
        registrationSteps.enterData(baseEmail, basePass);
        registrationSteps.submitData();
        registrationSteps.verifyPositiveEmailNotification("This email is already taken");
    }

    @AfterMethod
    public void takeScreenShotOnFailure(ITestResult testResult)
    {
        if ((testResult.getStatus() == ITestResult.FAILURE)|(testResult.getStatus() == ITestResult.SUCCESS_PERCENTAGE_FAILURE))
        {
            registrationSteps.makeScreenshot();
        }
    }
}
