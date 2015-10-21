package ua.com.ua.com.homebudget.steps.Registrarion;

import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import ua.com.ua.com.homebudget.pages.MainPage;
import ua.com.ua.com.homebudget.pages.RegistrationPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

/**
 * Created by Anohin Artyom on 21.10.2015.
 */
public class RegistrationSteps extends ScenarioSteps {


    RegistrationPage registrationPage;
    MainPage mainPage;

    @Step
    public void openRegisterPage(){
        registrationPage.open();
        registrationPage.isOnThisPage();
    }

    @Step
    public void registration(String email, String pass){
        registrationPage.enterData(email, pass);
        Serenity.takeScreenshot();
        registrationPage.sumbitData();
    }

    @Step
    public void validate(String email, String pass){
        registrationPage.enterData(email, pass);
    }



    @Step
    public void verifyGeneralRegisterWarning(String message){
        assertThat("Warning message does not match", registrationPage.element(registrationPage.generalWarning).getText().equals(message));
    }

    @Step
    public void verifyPasswordWarningAtRegistration(String message){
        assertThat("Warning message does not match", registrationPage.element(registrationPage.passWarning).getText().equals(message));
    }

    @Step
    public void verifyEmailWarningAtRegistration(String message){
        assertThat("Warning message does not match", registrationPage.element(registrationPage.emailWarning).getText().equals(message));
    }
}
