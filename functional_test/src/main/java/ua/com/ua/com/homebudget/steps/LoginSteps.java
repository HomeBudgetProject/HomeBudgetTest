package ua.com.ua.com.homebudget.steps;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by Anohin Artyom on 04.11.15.
 */
public class LoginSteps {
    public WebDriver driver;

    public LoginSteps(WebDriver driver) {
        this.driver = driver;
    }
    WebDriverWait wait = new WebDriverWait(driver, 2);

    By loginForm = By.xpath("//*[@name='loginform']");
    By emailInput = By.xpath(".//*[@name='email']");
    By passInput = By.xpath("//*[@name='password']");
    By loginButton = By.xpath("//input[@value='Log In']");


    @Step
    public void openLoginPage() {
        driver.get("https://homebudget-hb2.rhcloud.com/#/login");
        wait.until(ExpectedConditions.elementToBeClickable(loginForm));
        driver.findElement(loginForm).isDisplayed();
        driver.navigate().refresh();
    }

    @Step("Enter data \"{0}\" - \"{1}\"")
    public void enterData(String email, String pass) {
        driver.findElement(emailInput).clear();
        driver.findElement(passInput).clear();
        driver.findElement(emailInput).sendKeys(email);
        driver.findElement(passInput).sendKeys(pass);
    }
    @Step
    public void sumbitData() {
        //softAssert.assertFalse(driver.findElement(registerButton).isEnabled(), "Registration button is disabled");
        //assertTrue("Register button is disabled",!driver.findElement(registerButton).isEnabled());
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        driver.findElement(loginButton).click();
    }

    @Attachment
    public byte[] makeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public void quit() {
        driver.quit();
    }
}
