package ua.com.ua.com.homebudget.tests;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ua.com.ua.com.homebudget.Helper;
import ua.com.ua.com.homebudget.steps.RegistrationSteps;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by artyom on 02.11.15.
 */
public class RegistrationTest extends Helper{
    RemoteWebDriver driver;
    private RegistrationSteps registrationStep;

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

        if (browser.equalsIgnoreCase("iPad"))
            caps = DesiredCapabilities.ipad();

        if (browser.equalsIgnoreCase("Android"))
            caps = DesiredCapabilities.android();

        //Version
        caps.setVersion(version);

        registrationStep = new RegistrationSteps(new RemoteWebDriver(new URL(url), caps));

    }

    @Features("Registration")
    @Stories("Verification")
    @Test(dataProvider = "dataVerification")
    public void verificationTest(String testName, String email, String password, int statuscode, String errorMessage){
        registrationStep.openRegistrationPage();
        registrationStep.enterData(email, password);
        registrationStep.sumbitData();
        registrationStep.verifyMessage(errorMessage);
        registrationStep.makeScreenshot();
    }

    @AfterClass
    public void afterTest() {
        //Close the browser
        registrationStep.quit();
    }
}