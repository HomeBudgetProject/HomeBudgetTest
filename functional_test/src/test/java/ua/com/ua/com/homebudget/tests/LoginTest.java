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
    @Parameters({"platform", "browser", "version", "url"})
    @BeforeTest(alwaysRun = true)
    public void setup(String platform, String browser, String
            version, String url) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();

        //Platforms
        if (platform.equalsIgnoreCase("Windows"))
            caps.setPlatform(org.openqa.selenium.Platform.WINDOWS);
        if (platform.equalsIgnoreCase("Linux"))
            caps.setPlatform(Platform.LINUX);
        if (platform.equalsIgnoreCase("MAC"))
            caps.setPlatform(org.openqa.selenium.Platform.MAC);
        if (platform.equalsIgnoreCase("Andorid"))
            caps.setPlatform(org.openqa.selenium.Platform.ANDROID);

        //Browsers
        if (browser.equalsIgnoreCase("Internet Explorer"))
            caps = DesiredCapabilities.internetExplorer();

        if (browser.equalsIgnoreCase("Firefox"))
            caps = DesiredCapabilities.firefox();

        if(browser.equalsIgnoreCase("Chrome"))
            caps = DesiredCapabilities.chrome();

        if (browser.equalsIgnoreCase("iPad"))
            caps = DesiredCapabilities.ipad();

        if (browser.equalsIgnoreCase("Android"))
            caps = DesiredCapabilities.android();

        //Version
        caps.setVersion(version);

        loginSteps = new LoginSteps(new RemoteWebDriver(new URL(url), caps));

    }


    @Features("Login")
    @Stories("Positive Login")
    @Test
    public void positiveLogin(){
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
