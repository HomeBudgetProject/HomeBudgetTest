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
@DefaultUrl("https://homebudget-hb2.rhcloud.com/#/login")

public class LoginPage extends PageObject {
    @FindBy(xpath = "//input[@type='text']")
    private WebElementFacade emailInput;

    @FindBy(xpath = "//input[@type='password']")
    private WebElementFacade passInput;

    @FindBy(xpath = "//button[@type='button']")
    private WebElementFacade btnSubmit;

    public void enterData(String email, String pass) {
        emailInput.clear();
        passInput.clear();
        emailInput.sendKeys(email);
        passInput.sendKeys(pass);

    }

    public void sumbitData() {
        btnSubmit.click();
    }

}
