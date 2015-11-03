package ua.com.ua.com.homebudget.steps;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.concurrent.TimeUnit;

/**
 * Created by Anohin Artyom on 02.11.15.
 */
public class RegistrationSteps {
    public WebDriver driver;

    public RegistrationSteps(WebDriver driver) {
        this.driver = driver;
    }
    private SoftAssert softAssert = new SoftAssert();

    By registrationForm = By.xpath("/html/body/div/div/form/div[1]/div");
    By emailInput = By.xpath("//input[@name='email']");
    By passInput = By.xpath("//input[@name='password']");
    By registerButton = By.xpath("//input[@value='Register']");
    By generalMessage = By.xpath("/html/body/div/div/form/div[1]/div/div[3]");

    @Step
    public void openRegistrationPage() {
        driver.get("https://homebudget-hb2.rhcloud.com/#/register");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.findElement(registrationForm).isDisplayed();
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
        //assertTrue("Registration button is disabled", driver.findElement(registerButton).isEnabled());
        driver.findElement(registerButton).click();
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
    }
    @Step
    public void verifyMessage(String message) {
        //Assert.assertTrue("Warning message does not match", driver.findElement(generalMessage).getText().equals(message));
//        assertEquals(driver.findElement(generalMessage).getText(), message);


        softAssert.assertEquals(driver.findElement(generalMessage).getText(), message);
    }
}
