package ua.com.ua.com.homebudget.pages;

import net.serenitybdd.core.annotations.findby.By;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.NamedUrl;
import net.thucydides.core.annotations.NamedUrls;
import net.thucydides.core.annotations.WhenPageOpens;
import net.thucydides.core.pages.WebElementFacade;

import net.thucydides.core.annotations.findby.FindBy;

import net.thucydides.core.pages.PageObject;

/**
 * Created by Anohin Artyom on 10.09.2015.
 */

@DefaultUrl("https://homebudget-hb2.rhcloud.com/#/register")

public class RegistrationPage extends PageObject {

    @WhenPageOpens
    public void waitUntilTitleAppears() {
        element(registerForm).waitUntilVisible();
    }

    @FindBy(xpath = "/html/body/div/div/form/div[1]/div") //rewrite
    private WebElementFacade registerForm;

    @FindBy(xpath = "//input[@name='email']")
    private WebElementFacade emailInput;

    @FindBy(xpath = "//input[@name='password']")
    private WebElementFacade passInput;

    @FindBy(xpath = "//input[@value='Register']")
    private WebElementFacade btnSubmit;

    @FindBy(xpath = "/html/body/div/div/form/div[1]/div/div[3]")
    public WebElementFacade generalWarning;

    @FindBy(xpath = "/html/body/div/div/form/div[1]/div/ng-messages[2]/div")
    public WebElementFacade passWarning;

    @FindBy(xpath = "/html/body/div/div/form/div[1]/div/ng-messages[1]/div")
    public WebElementFacade emailWarning;


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

    public void isOnThisPage(){
        element(registerForm).isDisplayed();
    }



}