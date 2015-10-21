package ua.com.ua.com.homebudget.jbehave;

import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import ua.com.ua.com.homebudget.steps.EndUserSteps;
import ua.com.ua.com.homebudget.steps.Login.LoginSteps;
import ua.com.ua.com.homebudget.steps.Registrarion.RegistrationSteps;
import ua.com.ua.com.homebudget.steps.ResetPass.ResetPassSteps;


/**
 * Created by Anohin Artyom on 10.09.2015.
 */

public class DefinitionSteps {

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
