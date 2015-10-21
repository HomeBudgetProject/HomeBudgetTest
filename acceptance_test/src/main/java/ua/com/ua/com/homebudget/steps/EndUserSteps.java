package ua.com.ua.com.homebudget.steps;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import org.openqa.selenium.By;
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
    public void isOnMainPage(){
        mainPage.isOnThisPage();
    }
}