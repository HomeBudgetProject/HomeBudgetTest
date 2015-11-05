package ua.com.ua.com.homebudget.tests;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ua.com.ua.com.homebudget.Helper;
import ua.com.ua.com.homebudget.steps.LoginSteps;
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
    public void setup() throws MalformedURLException {

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setBrowserName(System.getenv("SELENIUM_BROWSER"));
        desiredCapabilities.setVersion(System.getenv("SELENIUM_VERSION"));
        desiredCapabilities.setCapability(CapabilityType.PLATFORM, System.getenv("SELENIUM_PLATFORM"));
        desiredCapabilities.setCapability("name", "Functional test - Registration"); //name job in saucelab
       // registrationStep = new RegistrationSteps(new RemoteWebDriver(new URL("http://"+System.getenv("SAUCE_USERNAME")+":"+System.getenv("SAUCE_ACCESS_KEY")+"@ondemand.saucelabs.com:80/wd/hub"), desiredCapabilities));
        RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL("http://"+System.getenv("SAUCE_USERNAME")+":"+System.getenv("SAUCE_ACCESS_KEY")+"@ondemand.saucelabs.com:80/wd/hub"), desiredCapabilities);
        System.out.println(String.format("SauceOnDemandSessionID=%s job-name=%s", remoteDriver.getSessionId(), desiredCapabilities.getCapability("name")));
        registrationStep = new RegistrationSteps(remoteDriver);
    }




    @Features("Registration")
    @Stories("Negative Email Verification")
    @Test(dataProvider = "negativeEmailVerification")
    public void negativeEmailVerificationTest(String testName, String email, String password, int statuscode, String errorMessage){
        registrationStep.openRegistrationPage();
        registrationStep.enterData(email, password);
        registrationStep.sumbitData();
        registrationStep.verifyEmailWarningMessage(errorMessage);
        registrationStep.cleanAfterTest(email, password);
    }


    @Features("Registration")
    @Stories("Negative Password Verification")
    @Test(dataProvider = "negativePassVerification")
    public void negativePassVerificationTest(String testName, String email, String password, int statuscode, String errorMessage){
        registrationStep.openRegistrationPage();
        registrationStep.enterData(email, password);
        registrationStep.sumbitData();
        registrationStep.verifyPassWarningMessage(errorMessage);
        registrationStep.cleanAfterTest(email, password);
    }

    @Features("Registration")
    @Stories("Positive Verification")
    @Test(dataProvider = "positiveVerification")
    public void positiveVerificationTest(String testName, String email, String password, int statuscode, String errorMessage){
        registrationStep.openRegistrationPage();
        registrationStep.enterData(email, password);
        registrationStep.sumbitData();
        registrationStep.verifyGeneralWarningMessage(errorMessage);
        registrationStep.cleanAfterTest(email, password);
    }



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