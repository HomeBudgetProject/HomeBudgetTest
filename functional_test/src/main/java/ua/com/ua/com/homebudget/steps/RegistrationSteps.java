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

import java.net.URLEncoder;

import static com.jayway.restassured.RestAssured.expect;
import static com.thoughtworks.selenium.SeleneseTestNgHelper.assertEquals;
import static org.hamcrest.Matchers.equalTo;

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

    @FindBy(xpath = "//*[@name='registerform']/div[1]/div/div[3]")
    WebElement generalNotification;

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
        wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOf(registrationForm));
    }
    @Step
    public void enterData(String email, String pass) {
    //    emailInput.clear();
        emailInput.sendKeys(email);
    //    passInput.clear();
        passInput.sendKeys(pass);

    }

    public void verifyNegativeEmailNotification(String notificationMessage) {
        wait.until(ExpectedConditions.visibilityOf(emailNotification));
        assertEquals(emailNotification.getText(), notificationMessage);
    }

    public void submitData() {
        registerButton.click();
        wait.until(ExpectedConditions.visibilityOf(generalNotification)); //wait until  account is created
    }

    public void verifyPassNotification(String notificationMessage) {
        wait.until(ExpectedConditions.visibilityOf(passNotification));
        assertEquals(passNotification.getText(), notificationMessage);
    }


    public void verifyPositiveEmailNotification(String notificationMessage) {

        assertEquals(generalNotification.getText(), notificationMessage);
    }
    String auth_key = "";
    public void clearAfterTest(String email, String password) {
        auth_key = expect() //login process and get auth_key
                .statusCode(200)
                .when()
                .post("/login?username=" + URLEncoder.encode(email.trim()) + "&password=" + password)
                .then()
                .log().ifValidationFails()
                .extract().cookie("auth_key");

        int userId = expect(). //get user info
                statusCode(200)
                .when()
                .given()
                .cookie("auth_key", auth_key)
                .get("/users/userInfo")
                .then()
                .extract().body().path("userId");

        expect(). //delete account
                statusCode(200)
                .when()
                .given()
                .cookie("auth_key", auth_key)
                .delete("/users/" + userId)
                .then()
                .log().ifValidationFails();

        expect(). //verify that session is destroyed
                statusCode(200)
                .when()
                .given()
                .cookie("auth_key", auth_key)
                .get("/users/whoami")
                .then().assertThat().body(equalTo("anonymousUser"))
                .log().ifValidationFails();
    }
}
