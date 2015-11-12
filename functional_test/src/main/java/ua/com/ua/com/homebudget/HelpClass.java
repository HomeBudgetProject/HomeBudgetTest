package ua.com.ua.com.homebudget;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;


import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by artyom on 10.11.15.
 */

public class HelpClass  {
    public WebDriver driver;

    public String username = System.getenv("SAUCE_USERNAME");
    public String accesskey = System.getenv("SAUCE_ACCESS_KEY");

    private String sessionId;

    @BeforeClass
    public void setup() throws MalformedURLException {
//        ONLY FOR DEBUG
//        driver = new FirefoxDriver();

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
            System.out.println("Instance - local. Browser - " + System.getProperty("instanceBrowser"));
        }
    }


    @AfterClass
    public void down(){
        driver.quit();
    }


    public String generateEmail(int local_part, int domain_part, int subdomain){
        String email = "qa" + RandomStringUtils.randomAlphanumeric(local_part - 3) + "@" + RandomStringUtils.randomAlphanumeric(domain_part) + "." + RandomStringUtils.randomAlphanumeric(subdomain) + ".com";
        return email;

    }

    public String generateEmail(int local_part, int domain_part){
        String email = "qa"+ RandomStringUtils.randomAlphanumeric(local_part - 3) + "@"+RandomStringUtils.randomAlphanumeric(domain_part-4)+".com";
        return email;
    }

    public String generatePass(int length){
        String pass =  RandomStringUtils.randomAlphanumeric(length);
        //String pass =  RandomStringUtils.randomAscii(length);
        return pass;
    }

    @DataProvider(name = "negativeEmailVerification")
    public Object[][] negativeEmailVerification() {
        return new Object[][]{
                {"Empty Email","", generatePass(10), 400, "The email must be between 6 and 60 characters"},
                {"One Space Email", " ", generatePass(10), 400, "������� Email"},
                {"Two Space Email", "  ", generatePass(10), 400, "������� Email"},
                {"Five Letters Email", "m@e.u", generatePass(10), 400, "Email ������ ��������� �� 6 �� 60 ��������"},
                {"Five Letters + Space Email", "m@e.u ", generatePass(10), 400, "Email ������ ��������� �� 6 �� 60 ��������"},
                {"61 Letters Email", generateEmail(31, 30), generatePass(10), 400, "Email ������ ��������� �� 6 �� 60 ��������"}
        };
    }
    @DataProvider(name = "negativePassVerification")
    public Object[][] negativePassVerification() {
        return new Object[][]{
                {"Empty Pass", generateEmail(10, 10), "", 400, "������� ������"},
                {"One SpacePass", generateEmail(10, 10), " ", 400, "������� ������������ ������"},
                {"Two Space Pass", generateEmail(10, 10), "  ", 400, "������� ������������ ������"},
                {"5 Letters Pass", generateEmail(10, 10), generatePass(5), 400, "������ ������ ��������� �� 6 �� 100 ��������"},
                {"5 + Space Letters Pass", generateEmail(10, 10), generatePass(5) + " ", 400, "������� ������������ ������"},
                {"100 + Space Letters Pass", generateEmail(10, 10), generatePass(100) + " ", 200, "������� ������������ ������"},
                {"Space + 100 Letters Pass", generateEmail(10, 10), " " + generatePass(100), 200, "������� ������������ ������"}
        };
    }
    @DataProvider(name = "positiveVerification")
    public Object[][] positiveVerification() {
        return new Object[][]{
                {"Six Letters Email", "m@e.ua", generatePass(10), 200, "�� ������� ����������������"},
                {"Space + Five Letters Email", " " + generateEmail(10, 10), generatePass(10), 200, "�� ������� ����������������"},
                {"Last Space Email", generateEmail(10, 10) + " ", generatePass(10), 200, "�� ������� ����������������"},
                {"Sixty Letters Email", generateEmail(30, 30), generatePass(10), 200, "�� ������� ����������������"},
                {"Space + Sixty Letters Email", " " + generateEmail(30, 30), generatePass(10), 200, "�� ������� ����������������"},
                {"60 Letters + Space Email", generateEmail(30, 30) + " ", generatePass(10), 200, "�� ������� ����������������"},
                {"59 Letters Email", generateEmail(29, 30), generatePass(10), 200, "�� ������� ����������������"},
                {"Two Domain Email", generateEmail(10, 10, 10), generatePass(10), 200, "�� ������� ����������������"},
                {"Numeric Local Email", "123456@test.com", generatePass(10), 200, "�� ������� ����������������"},
                {"Numeric Domain Email", "qatest@123456.com", generatePass(10), 200, "�� ������� ����������������"},
                {"100 Letters Pass", generateEmail(10, 10), generatePass(100), 200, "�� ������� ����������������"},
                {"6 Letters Pass", generateEmail(10, 10), generatePass(6), 200, "�� ������� ����������������"},
                {"RFC Email", "qaother.email.with-dash+symbol@example.com", generatePass(6), 200, "�� ������� ����������������"},
                {"RFC2 Email", "qaMiles.O'Brian@example.com", generatePass(6), 200, "�� ������� ����������������"}
        };
    }

    @DataProvider(name = "negativeEmailValidation")
    public Object[][] negativeEmailValidation() {
        return new Object[][]{
                {"","qatest@test.123", generatePass(10), 400, "������� ������������ ������"},
                {"","qatest@test.!#$", generatePass(10), 400, "������� ������������ ������"},
                {"","qam e@me.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@me@me.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@.me.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m`e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m~e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m!e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m#e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m$e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m%e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m^e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m&e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m*e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m(e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m)e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m_e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m=e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m+e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m[e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m]e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m;e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m:e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m'e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m|e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m<e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m>e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m,e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m?e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@me,com", generatePass(10), 400, "������� ������������ ������"},
                {"","qam`e@me.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qam~e@me.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qam!e@me.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qam#e@me.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qam$e@me.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qam%e@me.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qam*e@me.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qam(e@me.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qam)e@me.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qam=e@me.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qam[e@me.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qam]e@me.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qam;e@me.comm", generatePass(10), 400, "������� ������������ ������"},
                {"","qam:e@me.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qam,e@me.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qam<e@me.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qam>e@me.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qam|e@me.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m\\e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m\"e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qam\\e@me.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qam\"e@me.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qam}e@me.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m}e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m/e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qame@m{e.com", generatePass(10), 400, "������� ������������ ������"},
                {"","qam{e@me.com", generatePass(10), 400, "������� ������������ ������"}
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
