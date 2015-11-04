package ua.com.ua.com.homebudget.tests;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ua.com.ua.com.homebudget.Helper;
import ua.com.ua.com.homebudget.steps.RegistrationSteps;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Anohin Artyom on 02.11.15.
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

        if(browser.equalsIgnoreCase("Chrome"))
            caps = DesiredCapabilities.chrome();

        if (browser.equalsIgnoreCase("iPad"))
            caps = DesiredCapabilities.ipad();

        if (browser.equalsIgnoreCase("Android"))
            caps = DesiredCapabilities.android();

        //Version
        caps.setVersion(version);

        registrationStep = new RegistrationSteps(new RemoteWebDriver(new URL(url), caps));

    }



    @Features("Registration")
    @Stories("Negative Email Verification")
    @Test(dataProvider = "negativeEmailVerification")
    public void negativeEmailVerificationTest(String testName, String email, String password, int statuscode, String errorMessage){
        registrationStep.openRegistrationPage();
        registrationStep.enterData(email, password);
        registrationStep.sumbitData();
        registrationStep.verifyEmailWarningMessage(errorMessage);
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
    }

    @Features("Registration")
    @Stories("Positive Verification")
    @Test(dataProvider = "positiveVerification")
    public void positiveVerificationTest(String testName, String email, String password, int statuscode, String errorMessage){
        registrationStep.openRegistrationPage();
        registrationStep.enterData(email, password);
        registrationStep.sumbitData();
        registrationStep.verifyGeneralWarningMessage(errorMessage);
    }
     */


    @AfterMethod
    public void setScreenshot(ITestResult result) {
        //make screenshot if not success
        if (!result.isSuccess()) {
            registrationStep.makeScreenshot();
        }
    }


    @AfterClass
    public void afterTest() {
        //Close the browser
        registrationStep.quit();
    }
}