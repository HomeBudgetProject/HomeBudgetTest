package ua.com.ua.com.homebudget.steps;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import ua.com.ua.com.homebudget.pages.LoginPage;
import ua.com.ua.com.homebudget.pages.MainPage;
import ua.com.ua.com.homebudget.pages.RegistrationPage;
import ua.com.ua.com.homebudget.pages.ResetPasswordPage;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Anohin Artyom on 10.09.2015.
 */

public class EndUserSteps extends ScenarioSteps {

    RegistrationPage registrationPage;
    LoginPage loginPage;
    MainPage mainPage;
    ResetPasswordPage resetPasswordPage;

    @Step
    public void isOnMainPage(){
        mainPage.isOnThisPage();
    }
}