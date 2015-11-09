package ua.com.ua.com.homebudget;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.testng.SauceOnDemandAuthenticationProvider;
import com.saucelabs.testng.SauceOnDemandTestListener;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import ua.com.ua.com.homebudget.steps.LoginSteps;
import ua.com.ua.com.homebudget.steps.RegistrationSteps;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Anohin Artyom on 03.11.15.
 */
@Listeners({SauceOnDemandTestListener.class})
public class Helper implements SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider {
    String baseEmail = "qatestemail@testdomain.com";
    String basePass= "Qwerty123456";

    public WebDriver driver;

    protected LoginSteps loginSteps;
    protected RegistrationSteps registrationSteps;

    public String username = System.getenv("SAUCE_USERNAME");
    public String accesskey = System.getenv("SAUCE_ACCESS_KEY");

    protected ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(username, accesskey);
    private ThreadLocal<String> sessionId = new ThreadLocal<String>();

    @BeforeTest(alwaysRun = true)
    public void setup() throws MalformedURLException {
        if (System.getProperty("instance").equals("saucelabs")) {
            //setup capabilities
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability(CapabilityType.PLATFORM, System.getenv("SELENIUM_PLATFORM"));
            desiredCapabilities.setBrowserName(System.getenv("SELENIUM_BROWSER"));
            desiredCapabilities.setVersion(System.getenv("SELENIUM_VERSION"));


            desiredCapabilities.setCapability("name", "HomeBudget test"); //name job in saucelab
            //create WebDriver
            RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL("http://" + username + ":" + accesskey + "@ondemand.saucelabs.com:80/wd/hub"), desiredCapabilities);
            //print Job
            System.out.println(String.format("SauceOnDemandSessionID=%s job-name=%s", remoteDriver.getSessionId(), desiredCapabilities.getCapability("name")));
            loginSteps = new LoginSteps(remoteDriver);
            registrationSteps = new RegistrationSteps(remoteDriver);
            String id = ((RemoteWebDriver) remoteDriver).getSessionId().toString();
            sessionId.set(id);
        }
        else{
            if (System.getProperty("instanceBrowser").equalsIgnoreCase("firefox")){
                driver = new FirefoxDriver();

            }
            else if (System.getProperty("instanceBrowser").equalsIgnoreCase("chrome")){

                driver = new ChromeDriver();

            }
            else System.out.println("Bad instance");
            System.out.println("Instance - local. Browser - " + System.getProperty("instanceBrowser"));
            loginSteps = new LoginSteps(driver);
            registrationSteps = new RegistrationSteps(driver);
        }
    }


    @Override
    public SauceOnDemandAuthentication getAuthentication() {
        return authentication;
    }

    public String getSessionId() {
        return sessionId.get();
    }








    public String generateEmail(int local_part, int domain_part, int subdomain){
        return "qa" + RandomStringUtils.randomAlphanumeric(local_part - 3) + "@" + RandomStringUtils.randomAlphanumeric(domain_part) + "." + RandomStringUtils.randomAlphanumeric(subdomain) + ".com";

    }

    public String generateEmail(int local_part, int domain_part){
        return "qa"+ RandomStringUtils.randomAlphanumeric(local_part - 3) + "@"+RandomStringUtils.randomAlphanumeric(domain_part-4)+".com";
    }

    public String generatePass(int length){
        //String pass =  RandomStringUtils.randomAscii(length);
        return RandomStringUtils.randomAlphanumeric(length);
    }

    @DataProvider(name = "negativeEmailVerification")
    public Object[][] negativeEmailVerification() {
        return new Object[][]{
                {"Empty Email","", generatePass(10), 400, "The email must be between 6 and 60 characters"},
                {"One Space Email", " ", generatePass(10), 400, "Введите Email"},
                {"Two Space Email", "  ", generatePass(10), 400, "Введите Email"},
                {"Five Letters Email", "m@e.u", generatePass(10), 400, "Email должен содержать от 6 до 60 символов"},
                {"Five Letters + Space Email", "m@e.u ", generatePass(10), 400, "Email должен содержать от 6 до 60 символов"},
                {"61 Letters Email", generateEmail(31, 30), generatePass(10), 400, "Email должен содержать от 6 до 60 символов"}
        };
    }
    @DataProvider(name = "negativePassVerification")
    public Object[][] negativePassVerification() {
        return new Object[][]{
                {"Empty Pass", generateEmail(10, 10), "", 400, "Введите пароль"},
                {"One SpacePass", generateEmail(10, 10), " ", 400, "Введены некорректные данные"},
                {"Two Space Pass", generateEmail(10, 10), "  ", 400, "Введены некорректные данные"},
                {"5 Letters Pass", generateEmail(10, 10), generatePass(5), 400, "Пароль должен содержать от 6 до 100 символов"},
                {"5 + Space Letters Pass", generateEmail(10, 10), generatePass(5) + " ", 400, "Введены некорректные данные"},
                {"100 + Space Letters Pass", generateEmail(10, 10), generatePass(100) + " ", 200, "Введены некорректные данные"},
                {"Space + 100 Letters Pass", generateEmail(10, 10), " " + generatePass(100), 200, "Введены некорректные данные"}
        };
    }
    @DataProvider(name = "positiveVerification")
    public Object[][] positiveVerification() {
        return new Object[][]{
                {"Six Letters Email", "m@e.ua", generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"Space + Five Letters Email", " " + generateEmail(10, 10), generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"Last Space Email", generateEmail(10, 10) + " ", generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"Sixty Letters Email", generateEmail(30, 30), generatePass(10), 200, "Вы успешно зарегистрированы"},
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
                {"","qatest@test.123", generatePass(10), 400, "Email is not valid"},
                {"","qatest@test.!#$", generatePass(10), 400, "Email is not valid"},
                {"","qam e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@me@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qame.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@.me.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m`e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m~e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m!e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m#e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m$e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m%e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m^e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m&e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m*e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m(e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m)e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m_e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m=e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m+e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m[e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m]e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m;e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m:e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m'e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m|e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m<e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m>e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m,e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m?e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@me,com", generatePass(10), 400, "Email is not valid"},
                {"","qam`e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam~e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam!e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam#e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam$e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam%e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam*e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam(e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam)e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam=e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam[e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam]e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam;e@me.comm", generatePass(10), 400, "Email is not valid"},
                {"","qam:e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam,e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam<e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam>e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam|e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m\\e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m\"e.com", generatePass(10), 400, "Email is not valid"},
                {"","qam\\e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam\"e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam}e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m}e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m/e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m{e.com", generatePass(10), 400, "Email is not valid"},
                {"","qam{e@me.com", generatePass(10), 400, "Email is not valid"}
        };
    }
    @DataProvider(name = "positiveEmailValidation")
    public Object[][] positiveEmailValidation() {
        return new Object[][]{
                {"","qame@m-e.com", generatePass(10), 200, null},
                {"","qam^e@me.com", generatePass(10), 200, null},
                {"","qam&e@me.com", generatePass(10), 200, null},
                {"","qam-e@me.com", generatePass(10), 200, null},
                {"","qam_e@me.com", generatePass(10), 200, null},
                {"","qam+e@me.com", generatePass(10), 200, null},
                {"","qam'e@me.com", generatePass(10), 200, null},
                {"","qam/e@me.com", generatePass(10), 200, null}
        };
    }


}
