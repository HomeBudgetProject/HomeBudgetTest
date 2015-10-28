package ua.com.ua.com.homebudget;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.parsing.Parser;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.collections.Lists;

import java.util.*;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.post;
import static com.jayway.restassured.config.ConnectionConfig.connectionConfig;
import static com.jayway.restassured.config.RestAssuredConfig.newConfig;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by Anohin Artyom on 18.09.2015.
 */
public class Helper {

    Map existUsers = new HashMap< String, String>();

    String baseEmail = "qatestemail@testdomain.com";
    String basePass= "Qwerty123456";
    String authKey;

    @BeforeSuite
    public void setUP(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = "http://homebudget-hb2.rhcloud.com/";
        //RestAssured.port=8080;
        RestAssured.urlEncodingEnabled = false;

        //add base test account
        expect()
                .given()
                .contentType("application/json")
                .body("{ \"email\":\"" + baseEmail + "\", \"password\": \"" + basePass + "\"}")
                .post("/api/users/register")
                .then()
                .log().ifValidationFails();
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


    @DataProvider(name = "dataVerification")
    public Object[][] dataVerification() {
        return new Object[][]{
                {"Exist Email",baseEmail, basePass ,400,"This email is already taken"},
                {"Empty Email","", generatePass(10), 400, "The email must be between 6 and 60 characters"},
                {"One Space Email", " ", generatePass(10), 400, "The email must be between 6 and 60 characters"},
                {"Two Space Email", "  ", generatePass(10), 400, "The email must be between 6 and 60 characters"},
                {"Six Letters Email", "m@e.ua", generatePass(10), 200, null},
                {"Five Letters Email", "m@e.u", generatePass(10), 400, "The email must be between 6 and 60 characters"},
                {"Five Letters + Space Email", "m@e.u ", generatePass(10), 400, "The email must be between 6 and 60 characters"},
                {"Space + Five Letters Email", " " + generateEmail(10, 10), generatePass(10), 200, null},
                {"Last Space Email", generateEmail(10, 10) + " ", generatePass(10), 200, null},
                {"Sixty Letters Email", generateEmail(30, 30), generatePass(10), 200, null},
                {"Space + Sixty Letters Email", " " + generateEmail(30, 30), generatePass(10), 200, null},
                {"60 Letters + Space Email", generateEmail(30, 30) + " ", generatePass(10), 200, null},
                {"61 Letters Email", generateEmail(31, 30), generatePass(10), 400, "The email must be between 6 and 60 characters"},
                {"59 Letters Email", generateEmail(29, 30), generatePass(10), 200, null},
                {"Two Domain Email", generateEmail(10, 10, 10), generatePass(10), 200, null},
                {"Numeric Local Email", "123456@test.com", generatePass(10), 200, null},
                {"Numeric Domain Email", "qatest@123456.com", generatePass(10), 200, null},
                {"Empty Pass", generateEmail(10, 10), "", 400, "The password must be between 6 and 100 characters"},
                {"One SpacePass", generateEmail(10, 10), " ", 400, "The password must be between 6 and 100 characters"},
                {"Two Space Pass", generateEmail(10, 10), "  ", 400, "The password must be between 6 and 100 characters"},
                {"100 Letters Pass", generateEmail(10, 10), generatePass(100), 200, null},
                {"100 + Space Letters Pass", generateEmail(10, 10), generatePass(100) + " ", 200, null},
                {"Space + 100 Letters Pass", generateEmail(10, 10), " " + generatePass(100), 200, null},
                {"6 Letters Pass", generateEmail(10, 10), generatePass(6), 200, null},
                {"5 Letters Pass", generateEmail(10, 10), generatePass(5), 400, "The password must be between 6 and 100 characters"},
                {"5 + Space Letters Pass", generateEmail(10, 10), generatePass(5) + " ", 400, "The password must be between 6 and 100 characters"},
                //{"Only Characters Pass", generateEmail(10, 10), "!@#$%^&*()_-+={}[]:',.<>/`", 200, null}, // some bad request
                {"RFC Email", "qaother.email.with-dash+symbol@example.com", generatePass(6), 200, null},
                {"RFC2 Email", "qaMiles.O'Brian@example.com", generatePass(6), 200, null}
        };
    }

    @DataProvider(name = "dataValidation")
    public Object[][] dataValidation() {
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
                {"","qame@m-e.com", generatePass(10), 200, null},
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
                {"","qam^e@me.com", generatePass(10), 200, null},
                {"","qam&e@me.com", generatePass(10), 200, null},
                {"","qam*e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam(e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam)e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam-e@me.com", generatePass(10), 200, null},
                {"","qam_e@me.com", generatePass(10), 200, null},
                {"","qam+e@me.com", generatePass(10), 200, null},
                {"","qam=e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam[e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam]e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam;e@me.comm", generatePass(10), 400, "Email is not valid"},
                {"","qam:e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam'e@me.com", generatePass(10), 200, null},
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
//                {"","qam/e@me.com", generatePass(10), 200, null}, // after fix Bugs #31
                {"","qame@m/e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m{e.com", generatePass(10), 400, "Email is not valid"},
                {"","qam{e@me.com", generatePass(10), 400, "Email is not valid"}
        };
    }



}
