package ua.com.ua.com.homebudget.steps.ResetPass;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import ua.com.ua.com.homebudget.pages.ResetPasswordPage;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Anohin Artyom on 21.10.2015.
 */
public class ResetPassSteps extends ScenarioSteps {
    ResetPasswordPage resetPasswordPage;

    @Step
    public void openResetPasswordPage(){
        resetPasswordPage.open();
    }

    @Step
    public void enterEmailandSend(String email){
        resetPasswordPage.enterData(email);
        resetPasswordPage.submitData();
    }

    @Step
    public void verifyMessage(String message){
        assertThat("Warning message does not match", resetPasswordPage.element(resetPasswordPage.popupNotification).getText().equals(message));
    }
}
