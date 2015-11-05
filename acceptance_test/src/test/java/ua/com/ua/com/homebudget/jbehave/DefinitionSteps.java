package ua.com.ua.com.homebudget.jbehave;

import com.jayway.restassured.RestAssured;
import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import ua.com.ua.com.homebudget.steps.EndUserSteps;
import ua.com.ua.com.homebudget.steps.Login.LoginSteps;
import ua.com.ua.com.homebudget.steps.Registrarion.RegistrationSteps;
import ua.com.ua.com.homebudget.steps.ResetPass.ResetPassSteps;

import java.net.URLEncoder;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.equalTo;


/**
 * Created by Anohin Artyom on 10.09.2015.
 */

public class DefinitionSteps {


    @BeforeStories
    public void setupREST(){

        //Setup RESR for manage data in system
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = "http://homebudget-hb2.rhcloud.com/api";
        RestAssured.urlEncodingEnabled = false;
        String email = "qathucydides @testdomain.com";
        String pass = "Qwerty123456";
        //check whether there is a test account on the system. if there is - remove
        int scode = expect() //try login and get statuscode
                .when()
                .post("/login?username=" + URLEncoder.encode(email.trim()) + "&password=" + pass.trim())
                .then()
                .log().ifValidationFails()
                .extract().statusCode();
        if (scode==200) {
            //Delete Section
            String auth_key = expect() //login process and get auth_key
                    .statusCode(200)
                    .when()
                    .post("/login?username=" + URLEncoder.encode(email.trim()) + "&password=" + pass.trim())
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

    @AfterStories
    public void cleanUP(){
            //Setup RESR for manage data in system
            RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
            RestAssured.baseURI = "http://homebudget-hb2.rhcloud.com/api";
            RestAssured.urlEncodingEnabled = false;
            String email = "qathucydides @testdomain.com";
            String pass = "Qwerty123456";
            //check whether there is a test account on the system. if there is - remove
            int scode = expect() //try login and get statuscode
                    .when()
                    .post("/login?username=" + URLEncoder.encode(email.trim()) + "&password=" + pass.trim())
                    .then()
                    .log().ifValidationFails()
                    .extract().statusCode();
            if (scode==200) {
                //Delete Section
                String auth_key = expect() //login process and get auth_key
                        .statusCode(200)
                        .when()
                        .post("/login?username=" + URLEncoder.encode(email.trim()) + "&password=" + pass.trim())
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

    @Steps
    EndUserSteps endUser;

    @Then("redirect to main page")
    public void isOnMainPage(){endUser.isOnMainPage();}


    @Steps
    LoginSteps loginSteps;

    @Given("open login page")
    public void openLoginPage(){loginSteps.openLoginPage();}

    @When("enter email '$email' password '$pass' in Login page and send")
    public void enterEmailPasswordLoginPage(String email, String pass){loginSteps.login(email, pass);}

    @Then("in menu displays the caption '$email' in page")
    public void verifyCredentials(String email){loginSteps.verifyCredentials(email);}


    @Steps
    RegistrationSteps registrationSteps;

    @Given("open registration page")
    public void openRegistrationPage() {registrationSteps.openRegisterPage();}

    @When("enter email '$email' password '$pass'") //for validate form
    public void enterEmailPasswordRegistrationPage(String email, String pass) {registrationSteps.validate(email, pass);}

    @When("enter email '$email' password '$pass' and send") //for create record
    public void enterEmailPasswordSendRegistrationPage(String email, String pass){registrationSteps.registration(email, pass);}


    @Then("show warning notification '$message' on Register page")
    public void verifyGeneralWarningAtRegistration(String message){registrationSteps.verifyGeneralRegisterWarning(message);}

    @Then("show password warning notification '$message' on Register page")
    public void verifyPasswordWarningAtRegistration(String message){registrationSteps.verifyPasswordWarningAtRegistration(message);}

    @Then("show email warning notification '$message' on Register page")
    public void verifyEmailWarningAtRegistration(String message){registrationSteps.verifyEmailWarningAtRegistration(message);}

    @Steps
    ResetPassSteps resetPassSteps;

    @Given("open reset password page")
    public void openResetPasswordPage(){resetPassSteps.openResetPasswordPage();}

    @When("enter email '$email' in Resset page and send")
    public void enterEmailResetPasswordPage(String email){resetPassSteps.enterEmailandSend(email);}

    @Then("show popup notification '$message'")
    public void verifyPopupMessage(String message){resetPassSteps.verifyMessage(message);}

}
