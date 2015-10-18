package ua.com.ua.com.homebudget.pages;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.NamedUrl;
import net.thucydides.core.annotations.NamedUrls;
import net.thucydides.core.annotations.findby.FindBy;
import net.thucydides.core.pages.PageObject;
import net.thucydides.core.pages.WebElementFacade;

/**
 * Created by Anohin Artyom on 10.09.2015.
 */
@DefaultUrl("https://homebudget-hb2.rhcloud.com/#/reset")

public class ResetPassword extends PageObject {

    @FindBy(xpath = "//input[@type='text']")
    private WebElementFacade emailInput;

    @FindBy(xpath = "//button[@type='button']")
    private WebElementFacade btnSubmit;

    public void enterData(String email) {
        emailInput.type(email);
    }

    public void submitData() {
        btnSubmit.click();
    }

    public void verifyNotification() {

    }
}
