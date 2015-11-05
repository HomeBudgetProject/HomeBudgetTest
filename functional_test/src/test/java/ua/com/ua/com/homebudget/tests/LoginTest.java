package ua.com.ua.com.homebudget.tests;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ua.com.ua.com.homebudget.steps.LoginSteps;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by artyom on 04.11.15.
 */
public class LoginTest {
    RemoteWebDriver driver;
    private LoginSteps loginSteps;
    @Parameters({"browser","platform", "url"})
    @BeforeTest(alwaysRun = true)
    public void setup() throws MalformedURLException {

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setBrowserName(System.getenv("SELENIUM_BROWSER"));
        desiredCapabilities.setVersion(System.getenv("SELENIUM_VERSION"));
        desiredCapabilities.setCapability(CapabilityType.PLATFORM, System.getenv("SELENIUM_PLATFORM"));

            loginSteps = new LoginSteps(new RemoteWebDriver(new URL("http://"+System.getenv("SAUCE_USERNAME")+":"+System.getenv("SAUCE_ACCESS_KEY")+"@ondemand.saucelabs.com:80/wd/hub"), desiredCapabilities));

    }


    @Features("Login")
    @Stories("Positive Login")
    @Parameters({"browser","platform"})
    @Test
    public void positiveLogin(String browser,String platform){
        loginSteps.openLoginPage();
        loginSteps.enterData("aa","bb");
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
