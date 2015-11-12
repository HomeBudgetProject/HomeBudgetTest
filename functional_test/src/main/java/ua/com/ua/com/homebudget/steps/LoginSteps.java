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
public class LoginSteps {
    WebDriver driver;
    WebDriverWait wait = null;

    @FindBy(xpath = "//*[@name='loginform']")
    WebElement loginForm;

    @FindBy(xpath = "//input[@name='email']")
    WebElement emailInput;

   @FindBy(xpath = "//input[@name='password']")
   WebElement passInput;

    @FindBy(xpath = "//input[@value='Log In']")
    WebElement loginButton;

    @FindBy(xpath = "//ng-messages[1]/div")
    WebElement emailNotification;

    @FindBy(xpath = "//ng-messages[2]/div")
    WebElement passNotification;

    @Attachment
    public byte[] makeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public LoginSteps(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @Step
    public void openLoginPage() {
        driver.get("https://homebudget-hb2.rhcloud.com/#/login");
        driver.navigate().refresh();
        wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOf(loginForm));
    }
    @Step
    public void enterData(String email,String pass) {

        emailInput.sendKeys(email);
        passInput.sendKeys(pass);
    }

    public void submitData() {
        loginButton.click();
    }

    public void verifyEmailNotification(String errorMessage) {
        assertEquals(emailNotification.getText(), errorMessage);
    }

    public void verifyPassNotification(String errorMessage) {
        assertEquals(passNotification.getText(), errorMessage);
    }
}
