package ua.com.ua.com.homebudget.jbehave;

import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import ua.com.ua.com.homebudget.steps.EndUserSteps;

/**
 * Created by Anohin Artyom on 10.09.2015.
 */

public class DefinitionSteps {

    @Steps
    EndUserSteps endUser;

    @Given("open registration page")
    public void openRegistrationPage() {endUser.openRegisterPage();}

    @When("enter email '$email' password '$pass' and send")
    public void enterEmailPasswordRegistrationPage(String email, String pass){endUser.registration(email, pass);}

    @Then("redirect to main page")
    public void redirectMainPage(){endUser.redirectMainPage();}

    @Then("in menu displays the caption '$email' in page")
    public void verifyCredentials(String email){endUser.verifyCredentials(email);}

    //Login section
    @Given("open login page")
    public void openLoginPage(){endUser.openLoginPage();}

    @When("enter email '$email' password '$pass' in Login page")
    public void enterEmailPasswordLoginPage(String email, String pass){endUser.login(email, pass);}

    //Resset passwoed section
    @Given("open resset password page")
    public void openRessetPage(){endUser.openRessetPage();}
    @When("enter email '$email' in Resset page")
    public void enterEmailRessetPage(String email){endUser.resetPassword(email);}

    @Then("show popup notification '$message'")
    public void verifyNotification(String message){endUser.verifyNotification(message);}
}
