package ua.com.ua.com.homebudget;

import com.jayway.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;


import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by Anohin Artyom on 10.11.15.
 */

public class HelpClass  {
    protected WebDriver driver;
    protected String baseEmail = "qatestemail@testdomain.com";
    protected String basePass= "Qwerty123456";
    private String username = System.getenv("SAUCE_USERNAME");
    private String accesskey = System.getenv("SAUCE_ACCESS_KEY");

    private String sessionId;

    @BeforeClass
    public void setup() throws MalformedURLException {
        if (System.getProperty("instance").equals("saucelabs")) {
            //setup capabilities
            if (System.getProperty("instance.device").equals("desktop")) {
                DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                desiredCapabilities.setCapability(CapabilityType.PLATFORM, System.getenv("SELENIUM_PLATFORM"));
                desiredCapabilities.setBrowserName(System.getenv("SELENIUM_BROWSER"));
                desiredCapabilities.setVersion(System.getenv("SELENIUM_VERSION"));
                desiredCapabilities.setCapability("name", this.getClass().getName());
                driver = new RemoteWebDriver(new URL("http://" + username + ":" + accesskey + "@ondemand.saucelabs.com:80/wd/hub"), desiredCapabilities);

                System.out.println(String.format("SauceOnDemandSessionID=%s job-name=%s", ((RemoteWebDriver) driver).getSessionId(), desiredCapabilities.getCapability("name")));
                this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
            }
            else if (System.getProperty("instance.device").equals("android")){
                DesiredCapabilities caps = DesiredCapabilities.android();
                caps.setCapability("platform", "Linux");
                caps.setCapability("version", "5.1");
                caps.setCapability("deviceName","Android Emulator");
                caps.setCapability("deviceOrientation", "portrait");
            }
            else if (System.getProperty("instance.device").equals("iPad")){
                DesiredCapabilities caps = DesiredCapabilities.iphone();
                caps.setCapability("platform", "OS X 10.10");
                caps.setCapability("version", "9.1");
                caps.setCapability("deviceName","iPad Retina");
                caps.setCapability("deviceOrientation", "portrait");
            }
        }
        else {
            if (System.getProperty("instance.browser").equalsIgnoreCase("firefox")) {
                driver = new FirefoxDriver();
            }
            else if (System.getProperty("instance.browser").equalsIgnoreCase("chrome")) {
                driver = new ChromeDriver();
            }
            else if (System.getProperty("instance.browser").equalsIgnoreCase("ie")){
                driver = new InternetExplorerDriver();
            }
            else System.out.println("Bad instance");
            System.out.println("Instance - local. Browser - " + System.getProperty("instance.browser"));
        }
    }

    @AfterClass
    public void down(){
        driver.quit();
    }

    //------------
    //TEST DATA SECTION
    //------------

    private String generateEmail(int local_part, int domain_part, int subdomain){
        return "qa" + RandomStringUtils.randomAlphanumeric(local_part - 3) + "@" + RandomStringUtils.randomAlphanumeric(domain_part) + "." + RandomStringUtils.randomAlphanumeric(subdomain) + ".com";
    }
    private String generateEmail(int local_part, int domain_part){
        return "qa"+ RandomStringUtils.randomAlphanumeric(local_part - 3) + "@"+RandomStringUtils.randomAlphanumeric(domain_part-4)+".com";
    }
    private String generatePass(int length){
        //String pass =  RandomStringUtils.randomAscii(length);
        return RandomStringUtils.randomAlphanumeric(length);
    }
    @DataProvider(name = "negativeEmailVerification")
    public Object[][] negativeEmailVerification() {
        return new Object[][]{
                {"Empty Email","", generatePass(10), 400, "The email must be between 6 and 60 characters"},
                {"One Space Email", " ", generatePass(10), 400, "Введите Email"},
                {"Two Space Email", "  ", generatePass(10), 400, "Введите Email"},
                {"Five Letters Email", "m@e.u", generatePass(10), 400, "Введены некорректные данные"},
                {"Five Letters + Space Email", "m@e.u ", generatePass(10), 400, "Введены некорректные данные"},
                {"61 Letters Email", generateEmail(31, 30), generatePass(10), 400, "Email должен содержать от 6 до 60 символов"},
        };
    }
    @DataProvider(name = "negativePassVerification")
    public Object[][] negativePassVerification() {
        return new Object[][]{
                {"Empty Pass", generateEmail(10, 10), "", 400, "Введены некорректные данные"},
                {"One SpacePass", generateEmail(10, 10), " ", 400, "Введены некорректные данные"},
                {"Two Space Pass", generateEmail(10, 10), "  ", 400, "Введены некорректные данные"},
                {"5 Letters Pass", generateEmail(10, 10), generatePass(5), 400, "Пароль должен содержать от 6 до 100 символов"},
                {"5 + Space Letters Pass", generateEmail(10, 10), generatePass(5) + " ", 400, "Введены некорректные данные"},
                {"100 + Space Letters Pass", generateEmail(10, 10), generatePass(100) + " ", 200, "Введены некорректные данные"},
                {"Space + 100 Letters Pass", generateEmail(10, 10), " " + generatePass(100), 200, "Введены некорректные данные"}
        };
    }
    @DataProvider(name = "positiveRegisterVerification")
    public Object[][] positiveRegisterVerification() {
        return new Object[][]{
                {"Six Letters Email", "m@e.ua", generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"Sixty Letters Email", generateEmail(30, 30), generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"Space + Five Letters Email", " " + generateEmail(10, 10), generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"Last Space Email", generateEmail(10, 10) + " ", generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"Space + Sixty Letters Email", " " + generateEmail(30, 30), generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"60 Letters + Space Email", generateEmail(30, 30) + " ", generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"59 Letters Email", generateEmail(29, 30), generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"Two Domain Email", generateEmail(10, 10, 10), generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"Numeric Local Email", "123456@test.com", generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"Numeric Domain Email", "qatest@123456.com", generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"100 Letters Pass", generateEmail(10, 10), generatePass(100), 200, "Вы успешно зарегистрированы"},
                {"6 Letters Pass", generateEmail(10, 10), generatePass(6), 200, "Вы успешно зарегистрированы"},
                {"RFC Email", "qaother.email.with-dash+symbol@example.com", generatePass(6), 200, "Вы успешно зарегистрированы"},
                {"RFC2 Email", "qaMiles.O'Brian@example.com", generatePass(6), 200, "Вы успешно зарегистрированы"}
        };
    }

    @DataProvider(name = "negativeEmailValidation")
    public Object[][] negativeEmailValidation() {
        return new Object[][]{
                {"","qatest@test.123", generatePass(10), 400, "Введены некорректные данные"},
                {"","qatest@test.!#$", generatePass(10), 400, "Введены некорректные данные"},
                {"","qam e@me.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@me@me.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@.me.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m`e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m~e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m!e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m#e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m$e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m%e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m^e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m&e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m*e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m(e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m)e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m_e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m=e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m+e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m[e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m]e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m;e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m:e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m'e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m|e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m<e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m>e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m,e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m?e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@me,com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qam`e@me.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qam~e@me.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qam!e@me.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qam#e@me.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qam$e@me.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qam%e@me.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qam*e@me.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qam(e@me.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qam)e@me.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qam=e@me.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qam[e@me.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qam]e@me.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qam;e@me.comm", generatePass(10), 400, "Введены некорректные данные"},
                {"","qam:e@me.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qam,e@me.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qam<e@me.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qam>e@me.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qam|e@me.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m\\e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m\"e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qam\\e@me.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qam\"e@me.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qam}e@me.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m}e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m/e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qame@m{e.com", generatePass(10), 400, "Введены некорректные данные"},
                {"","qam{e@me.com", generatePass(10), 400, "Введены некорректные данные"}
        };
    }
    @DataProvider(name = "positiveRegisterEmailValidation")
    public Object[][] positiveRegisterEmailValidation() {
        return new Object[][]{
                {"","qame@m-e.com", generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"","qam^e@me.com", generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"","qam&e@me.com", generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"","qam-e@me.com", generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"","qam_e@me.com", generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"","qam+e@me.com", generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"","qam'e@me.com", generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"","qam/e@me.com", generatePass(10), 200, "Вы успешно зарегистрированы"}
        };
    }

    @BeforeSuite
    public void createTestData(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = "http://homebudget-hb2.rhcloud.com/api";
        //RestAssured.port=8080;
        RestAssured.urlEncodingEnabled = false;

        //add base test account
        expect()
                .given()
                .contentType("application/json")
                .body("{ \"email\":\"" + baseEmail + "\", \"password\": \"" + basePass + "\"}")
                .post("/users/register")
                .then()
                .log().ifValidationFails();
    }

    @AfterSuite
    public void clearAfterAllTests() {
        String auth_key = "";
        auth_key = expect() //login process and get auth_key
                .statusCode(200)
                .when()
                .post("/login?username=" + URLEncoder.encode(baseEmail.trim()) + "&password=" + basePass)
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
