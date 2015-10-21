package ua.com.ua.com.homebudget.steps.Login;

import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import ua.com.ua.com.homebudget.pages.LoginPage;
import ua.com.ua.com.homebudget.pages.MainPage;
import ua.com.ua.com.homebudget.pages.RegistrationPage;
import ua.com.ua.com.homebudget.pages.ResetPassword;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;

/**
 * Created by comp on 21.10.2015.
 */
public class LoginSteps extends ScenarioSteps {

    LoginPage loginPage;
    MainPage mainPage;

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
    public void verifyCredentials(String email) {
        assertThat(mainPage.getCredentials(), hasItem(containsString(email)));
    }
}
