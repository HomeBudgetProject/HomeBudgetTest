package ua.com.ua.com.homebudget.steps;

import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import ua.com.ua.com.homebudget.pages.LoginPage;
import ua.com.ua.com.homebudget.pages.MainPage;
import ua.com.ua.com.homebudget.pages.RegistrationPage;
import ua.com.ua.com.homebudget.pages.ResetPassword;

import static net.serenitybdd.core.pages.PageObject.withParameters;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by Anohin Artyom on 10.09.2015.
 */

public class EndUserSteps extends ScenarioSteps {

    RegistrationPage registrationPage;
    LoginPage loginPage;
    MainPage mainPage;
    ResetPassword resetPassword;

    @Step
    public void openRegisterPage(){
        registrationPage.open();
        //assertThat(registrationPage.getTitle(), is("Login page")); //correct
    }



    @Step
    public void registration(String email, String pass){
        registrationPage.enterData(email, pass);
        Serenity.takeScreenshot();
        registrationPage.sumbitData();

    }

    @Step
    public void redirectMainPage() {
        assertThat(mainPage.getTitle(), is("Main page"));
    }

    @Step
    public void verifyCredentials(String email) {
        assertThat(mainPage.getCredentials(), hasItem(containsString(email)));
    }
    @Step
    public void openLoginPage() {
        loginPage.open();
        //assertThat(loginPage.getTitle(), is("Login page"));
    }

    @Step
    public void login(String email, String pass) {
        loginPage.enterData(email, pass);
        Serenity.takeScreenshot();
        loginPage.sumbitData();
    }

    @Step
    public void openRessetPage() {
        resetPassword.open();
        //assertThat(loginPage.getTitle(), is("Reset page"));
    }

    @Step
    public void resetPassword(String email) {
        resetPassword.enterData(email);
        Serenity.takeScreenshot();
        resetPassword.submitData();
    }

    @Step
    public void verifyNotification(String message) {


    }
}