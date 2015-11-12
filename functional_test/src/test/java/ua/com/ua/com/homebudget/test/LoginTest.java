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
    public void negativeEmailVerificationTest(ITestContext context){
        loginSteps = new LoginSteps(driver);
        loginSteps.openLoginPage();
        loginSteps.enterData("blablba@email.com", "123456");
        loginSteps.submitData();
        context.setAttribute("wrongEmail", "aValue");
        context.setAttribute("wrongPass", "bValue");
        //loginSteps.verifyEmailNotification("Authentication Failed: Bad credentials");
    }



    @AfterMethod
    public void actionIfTestFailed(ITestResult testResult, ITestContext context)
    {
        if ((testResult.getStatus() == ITestResult.FAILURE)|(testResult.getStatus() == ITestResult.SUCCESS_PERCENTAGE_FAILURE))
        {
            loginSteps.makeScreenshot();//take screenshot
            //delete the data if they have been created if the test failed
            //delete this account
            //"wrongEmail" "wrongPass"
        }
    }


}
