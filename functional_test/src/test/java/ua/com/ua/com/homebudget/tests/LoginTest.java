package ua.com.ua.com.homebudget.tests;

import org.openqa.selenium.Platform;
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
    public void setup(String browser,String platform, String url) throws MalformedURLException {

        if (browser.equalsIgnoreCase("Chrome")) {
            DesiredCapabilities caps = DesiredCapabilities.chrome();
            if (platform.equalsIgnoreCase("Windows 7"))
                caps.setCapability("platform", "Windows 7");
            if (platform.equalsIgnoreCase("Windows 8"))
                caps.setCapability("platform", "Windows 8.1");
            if (platform.equalsIgnoreCase("Windows 10"))
                caps.setCapability("platform", "Windows 10");

            caps.setCapability("version", "46.0");
            caps.setCapability("screenResolution", "1024x768");
            caps.setCapability("name", "Functional test");
            loginSteps = new LoginSteps(new RemoteWebDriver(new URL(url), caps));
        }
        if (browser.equalsIgnoreCase("IE")) {
            DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
            if (platform.equalsIgnoreCase("Windows 7")) {
                caps.setCapability("platform", "Windows 7");
                caps.setCapability("version", "9.0");
            }
            if (platform.equalsIgnoreCase("Windows 8")) {
                caps.setCapability("platform", "Windows 8");
                caps.setCapability("version", "10.0");
            }
            if (platform.equalsIgnoreCase("Windows 10")) {
                caps.setCapability("platform", "Windows 10");
                caps.setCapability("version", "11.0");
            }
            caps.setCapability("screenResolution", "1024x768");
            caps.setCapability("name", "Functional test");
            loginSteps = new LoginSteps(new RemoteWebDriver(new URL(url), caps));
        }
        if (browser.equalsIgnoreCase("Opera")) {
            DesiredCapabilities caps = DesiredCapabilities.opera();
            caps.setCapability("platform", "Windows 7");
            caps.setCapability("version", "12.12");
            caps.setCapability("screenResolution", "1024x768");
            caps.setCapability("name", "Functional test");
            loginSteps = new LoginSteps(new RemoteWebDriver(new URL(url), caps));
        }
        if (browser.equalsIgnoreCase("Firefox")) {
            DesiredCapabilities caps = DesiredCapabilities.firefox();
            if (platform.equalsIgnoreCase("Windows 7")) {
                caps.setCapability("platform", "Windows 7");
                caps.setCapability("version", "41.0");
            }
            if (platform.equalsIgnoreCase("Windows 8")) {
                caps.setCapability("platform", "Windows 8");
                caps.setCapability("version", "41.0");
            }
            if (platform.equalsIgnoreCase("Windows 10")) {
                caps.setCapability("platform", "Windows 10");
                caps.setCapability("version", "41.0");
            }
            caps.setCapability("screenResolution", "1024x768");
            caps.setCapability("name", "Functional test");
            loginSteps = new LoginSteps(new RemoteWebDriver(new URL(url), caps));
        }


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
