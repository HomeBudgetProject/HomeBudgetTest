package ua.com.ua.com.homebudget.steps;

import com.jayway.restassured.RestAssured;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import static com.thoughtworks.selenium.SeleneseTestNgHelper.assertEquals;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.net.URLEncoder;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.equalTo;
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
    By generalWarning = By.xpath("/html/body/div/div/form/div[1]/div/div[3]");
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
        //assertFalse("Register button is disabled", driver.findElement(registerButton).isEnabled());
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
    @Step
    public void cleanAfterTest(String email, String password) {
        String auth_key=null;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = "http://homebudget-hb2.rhcloud.com/api";
        //RestAssured.port=8080;
        RestAssured.urlEncodingEnabled = false;
        int scode = expect() //try login and get statuscode
                .when()
                .post("/login?username=" + URLEncoder.encode(email.trim()) + "&password=" + password.trim())
                .then()
                .log().ifValidationFails()
                .extract().statusCode();
        if (scode==200) {
            //Delete Section
            auth_key = expect() //login process and get auth_key
                    .statusCode(200)
                    .when()
                    .post("/login?username=" + URLEncoder.encode(email.trim()) + "&password=" + password.trim())
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
        //Reporter.getCurrentTestResult().setStatus(ITestResult.SUCCESS);//manually set pass
    }
}
