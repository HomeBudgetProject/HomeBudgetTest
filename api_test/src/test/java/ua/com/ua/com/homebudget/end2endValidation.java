package ua.com.ua.com.homebudget;

import org.apache.commons.lang3.StringEscapeUtils;
import org.testng.Reporter;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

import java.net.URLEncoder;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by Anohin Artyom on 16.10.2015.
 */
public class end2endValidation extends Helper {
    @Features("Registration")
    @Stories("Validation")
    @Test(dataProvider = "dataValidation" )
    public void end2endTest(String testName, String email, String password, int statuscode, String errorMessage){
        String auth_key=null;

        Reporter.log("Test: " + testName, true);
        //Register User Section
        if (statuscode==200) {
            expect().statusCode(statuscode) //register process
                    .when()
                    .given().log().body()
                    .contentType("application/json")
                    .body("{ \"email\":\"" + StringEscapeUtils.escapeJava(email.trim()) + "\", \"password\": \"" + password.trim() + "\"}")
                    .post("/users/register")
                    .then()
                    .log().ifValidationFails();
            Reporter.log("Email: '" + email.trim() + "' - successfully registered", true);
        }
        else {
            expect().statusCode(statuscode) //verify error message
                    .body("message", equalTo(errorMessage))
                    .when()
                    .given().log().body()
                    .contentType("application/json")
                    .body("{ \"email\":\"" + StringEscapeUtils.escapeJava(email.trim()) + "\", \"password\": \"" + password.trim() + "\"}")
                    .post("/users/register")
                    .then()
                    .log().ifValidationFails();
            Reporter.log("Email: '" + email.trim() + "' - not registered with error: " + errorMessage, true);
        }
        //Login Section
        int scode = expect() //try login and get statuscode
                .when()
                .post("/login?username=" + URLEncoder.encode(email.trim()) + "&password=" + password.trim())
                .then()
                .log().ifValidationFails()
                .extract().statusCode();
        if (scode==200) {

            auth_key = expect() //login process and get auth_key
                    .statusCode(200)
                    .when()
                    .post("/login?username=" + URLEncoder.encode(email.trim()) + "&password=" + password.trim())
                    .then()
                    .log().ifValidationFails()
                    .extract().cookie("auth_key");

            expect(). //verify whoami
                    statusCode(200)
                    .when()
                    .given()
                    .cookie("auth_key", auth_key)
                    .get("/users/whoami")
                    .then().assertThat().body(equalTo(email.trim()))
                    .log().ifValidationFails();
            Reporter.log("Email: '" + email.trim() + "' with passrord: '" + password.trim() + "' - successfully logged in", true);
            auth_key = expect() //logout process
                    .given()
                    .cookie("auth_key", auth_key)
                    .post("/logout")
                    .then()
                    .log().ifValidationFails()
                    .extract().cookie("auth_key");
            Reporter.log("User logout", true);
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


            Reporter.log("Email: '" + email.trim() + "' - successfully deleted", true);
        }
        else {
            expect().statusCode(401) //verify error message
                    .body("message", equalTo("Authentication Failed: Bad credentials"))
                    .when()
                    .given()
                    .contentType("application/json")
                    .post("/login?username=" + URLEncoder.encode(email.trim()) + "&password=" + password.trim())
                    .then()
                    .log().ifValidationFails();
            Reporter.log("User '" + email.trim() + "' with passrord: '" + password.trim() + "' not logged in",true);
        }

        Reporter.log("------------------------", true);


    }
}
