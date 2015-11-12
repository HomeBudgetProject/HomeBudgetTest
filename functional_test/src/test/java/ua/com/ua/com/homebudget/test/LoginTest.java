package ua.com.ua.com.homebudget.test;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ua.com.ua.com.homebudget.HelpClass;
import ua.com.ua.com.homebudget.steps.LoginSteps;

/**
 * Created by Anohin Artyom on 10.11.15.
 */
public class LoginTest extends HelpClass{
    LoginSteps loginSteps;

    @Features("Login")
    @Stories("Negative Login")
    @Test
    public void negativeEmailVerificationTest(){
        loginSteps = new LoginSteps(driver);
        loginSteps.openLoginPage();
        loginSteps.enterData("blablba@email.com", "123456");
        loginSteps.submitData();
        loginSteps.verifyGeneralEmailNotification("Authentication Failed: Bad credentials");
    }

    @Features("Login")
    @Stories("Exist Login")
    @Test
    public void loginExistEmailTest(){
        loginSteps = new LoginSteps(driver);
        loginSteps.openLoginPage();
        loginSteps.enterData(baseEmail, basePass);
        loginSteps.submitData();
        loginSteps.verifyRedirectTransactionPage();
    }

    @AfterMethod
    public void actionIfTestFailed(ITestResult testResult)
    {
        if ((testResult.getStatus() == ITestResult.FAILURE)|(testResult.getStatus() == ITestResult.SUCCESS_PERCENTAGE_FAILURE))
        {
            loginSteps.makeScreenshot();//take screenshot
        }
    }


}
