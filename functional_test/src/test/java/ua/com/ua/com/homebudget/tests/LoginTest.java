package ua.com.ua.com.homebudget.tests;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ua.com.ua.com.homebudget.Helper;

/**
 * Created by artyom on 04.11.15.
 */
public class LoginTest extends Helper{




    @Features("Login")
    @Stories("Positive Login")
    @Test
    public void positiveLogin(){
        loginSteps.openLoginPage();
        loginSteps.enterData("aa","bb");
        loginSteps.sumbitData();
    }

    @Features("Login")
    @Stories("Positive Login")
    @Test
    public void positiveLogin2(){
        loginSteps.openLoginPage();
        loginSteps.enterData("qqqqqqqq","cccc");
        loginSteps.sumbitData();
    }


    @AfterMethod
    public void setScreenshot(ITestResult result) {
        //make screenshot if not success
        if (!result.isSuccess()) {
            loginSteps.makeScreenshot();
        }
    }


    @AfterClass
    public void afterTest() {
        //Close the browser
        loginSteps.quit();
    }
}