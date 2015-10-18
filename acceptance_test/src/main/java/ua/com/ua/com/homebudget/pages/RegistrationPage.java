package ua.com.ua.com.homebudget.pages;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.NamedUrl;
import net.thucydides.core.annotations.NamedUrls;
import net.thucydides.core.pages.WebElementFacade;

import net.thucydides.core.annotations.findby.FindBy;

import net.thucydides.core.pages.PageObject;

/**
 * Created by Anohin Artyom on 10.09.2015.
 */

@DefaultUrl("https://homebudget-hb2.rhcloud.com/#/register")

public class RegistrationPage extends PageObject {



    @FindBy(xpath = "//input[@name='email']")
    private WebElementFacade emailInput;

    @FindBy(xpath = "//input[@name='password']")
    private WebElementFacade passInput;

    @FindBy(xpath = "//input[@value='Register']")
    private WebElementFacade btnSubmit;


    public void enterData(String email, String pass) {
        //emailInput.type(email);
        //emailInput.click();
        //emailInput.hasFocus();
        emailInput.sendKeys(email);
        passInput.type(pass);

    }

    public void sumbitData() {
        btnSubmit.click();
    }

}