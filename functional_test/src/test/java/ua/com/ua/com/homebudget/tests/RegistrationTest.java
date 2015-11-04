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
            registrationStep = new RegistrationSteps(new RemoteWebDriver(new URL(url), caps));
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
            registrationStep = new RegistrationSteps(new RemoteWebDriver(new URL(url), caps));
        }
        if (browser.equalsIgnoreCase("Opera")) {
            DesiredCapabilities caps = DesiredCapabilities.opera();
            caps.setCapability("platform", "Windows 7");
            caps.setCapability("version", "12.12");
            caps.setCapability("screenResolution", "1024x768");
            caps.setCapability("name", "Functional test");
            registrationStep = new RegistrationSteps(new RemoteWebDriver(new URL(url), caps));
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
            registrationStep = new RegistrationSteps(new RemoteWebDriver(new URL(url), caps));
        }


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