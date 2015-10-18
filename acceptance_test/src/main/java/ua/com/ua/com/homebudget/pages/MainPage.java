package ua.com.ua.com.homebudget.pages;

import ch.lambdaj.function.convert.Converter;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.NamedUrl;
import net.thucydides.core.annotations.NamedUrls;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static ch.lambdaj.Lambda.convert;

/**
 * Created by Anohin Artyom on 10.09.2015.
 */
@DefaultUrl("http://52.19.25.73/hb/")
@NamedUrls(
        {
                @NamedUrl(name = "index", url = "http://52.19.25.73/hb/index.html"),
                @NamedUrl(name = "register", url = "http://52.19.25.73/hb/login.html"),
                @NamedUrl(name = "reset", url = "http://52.19.25.73/hb/resset.html")
        }
)

    public class MainPage extends PageObject {

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

}
