package ua.com.ua.com.homebudget.steps.ResetPassword;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import ua.com.ua.com.homebudget.pages.ResetPasswordPage;

/**
 * Created by comp on 21.10.2015.
 */
public class ResetPassSteps extends ScenarioSteps {
    ResetPasswordPage resetPasswordPage;

    @Step
    public void openRessetPage(){
        resetPasswordPage.open();
    }

    @Step
    public void resetPassword(String email){
        resetPasswordPage.enterData(email);
        resetPasswordPage.submitData();
    }

    @Step
    public void verifyNotification(String message){
        //Add check POPUP
    }
}
