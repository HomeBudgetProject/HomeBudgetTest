package ua.com.ua.com.homebudget.pages;

import ch.lambdaj.function.convert.Converter;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.NamedUrl;
import net.thucydides.core.annotations.NamedUrls;
import net.thucydides.core.annotations.WhenPageOpens;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static ch.lambdaj.Lambda.convert;

/**
 * Created by Anohin Artyom on 10.09.2015.
 */
@DefaultUrl("https://homebudget-hb2.rhcloud.com/#/transaction")


    public class MainPage extends PageObject {

    @WhenPageOpens
    public void waitUntilTitleAppears() {
        element(userMenu).waitUntilVisible();
    }

    @FindBy(className = "user clearfix")
    private WebElementFacade userMenu;

    private Converter<WebElement, String> toStrings() {
        return new Converter<WebElement, String>() {
            public String convert(WebElement from) {
                return from.getText();
            }
        };
    }

    public List<String> getCredentials() {
        WebElement results = getDriver().findElement(By.className("user__mail"));

        return convert(results, toStrings());
    }

    public void isOnThisPage(){
        element(userMenu).isDisplayed(); //verify is User Menu present on the page
    }
}
