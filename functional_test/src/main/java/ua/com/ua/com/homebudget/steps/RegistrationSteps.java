package ua.com.ua.com.homebudget.steps;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

import static com.thoughtworks.selenium.SeleneseTestNgHelper.assertEquals;

/**
 * Created by Anohin Artyom on 10.11.15.
 */
public class RegistrationSteps {
    WebDriver driver;
    WebDriverWait wait = null;

    @FindBy(xpath = "//*[@name='registerform']")
    WebElement registrationForm;

    @FindBy(xpath = "//input[@name='email']")
    WebElement emailInput;

    @FindBy(xpath = "//input[@name='password']")
    WebElement passInput;

    @FindBy(xpath = "//input[@value='Register']")
    WebElement registerButton;

    @FindBy(xpath = "//ng-messages[2]/div")
    WebElement passNotification;

    @FindBy(xpath = "//ng-messages[1]/div")
    WebElement emailNotification;

    @Attachment
    public byte[] makeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public RegistrationSteps(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @Step
    public void openRegistrationPage() {
        driver.get("https://homebudget-hb2.rhcloud.com/#/register");
        driver.navigate().refresh();
        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(registrationForm));
    }
    @Step
    public void enterData(String email, String pass) {
    //    emailInput.clear();
        emailInput.sendKeys(email);
    //    passInput.clear();
        passInput.sendKeys(pass);

    }

    public void verifyEmailNotification(String errorMessage) {
        assertEquals(emailNotification.getText(), errorMessage);
    }

    public void submitData() {
        registerButton.click();
    }

    public void verifyPassNotification(String errorMessage) {
        assertEquals(passNotification.getText(), errorMessage);
    }
}
