package ua.com.ua.com.homebudget.steps;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.concurrent.TimeUnit;

import static com.thoughtworks.selenium.SeleneseTestNgHelper.assertEquals;

/**
 * Created by Anohin Artyom on 02.11.15.
 */
public class RegistrationSteps {
    public WebDriver driver;

    public RegistrationSteps(WebDriver driver) {
        this.driver = driver;
    }
    private SoftAssert softAssert = new SoftAssert();

    By registrationForm = By.xpath("//*[@name='registerform']");
    By emailInput = By.xpath(".//*[@name='email']");
    By passInput = By.xpath("//*[@name='password']");
    By registerButton = By.xpath("//input[@value='Register']");
    By generalWarning = By.xpath("//*[@ng-if='ctrl.error']");
    By passWarning = By.xpath("/html/body/div/div/form/div[1]/div/ng-messages[2]/div");
    By emailWarning =By.xpath("/html/body/div/div/form/div[1]/div/ng-messages[1]/div");



    @Step
    public void openRegistrationPage() {
        driver.get("https://homebudget-hb2.rhcloud.com/#/register");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(registrationForm).isDisplayed();
        driver.navigate().refresh();
    }

    @Step("Enter data \"{0}\" - \"{1}\"")
    public void enterData(String email, String pass) {
        driver.findElement(emailInput).clear();
        driver.findElement(passInput).clear();
        driver.findElement(emailInput).sendKeys(email);
        driver.findElement(passInput).sendKeys(pass);
    }

    @Attachment
    public byte[] makeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public void quit() {
        driver.quit();
    }

    @Step
    public void sumbitData() {
        //softAssert.assertFalse(driver.findElement(registerButton).isEnabled(), "Registration button is disabled");
        //assertTrue("Register button is disabled",!driver.findElement(registerButton).isEnabled());
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(registerButton).click();
    }
    @Step
    public void verifyEmailWarningMessage(String message) {
        assertEquals(driver.findElement(emailWarning).getText(), message);
    }
    @Step
    public void verifyPassWarningMessage(String message) {
        assertEquals(driver.findElement(passWarning).getText(), message);
    }
    @Step
    public void verifyGeneralWarningMessage(String message) {
        assertEquals(driver.findElement(generalWarning).getText(), message);
    }
}
