package ua.com.ua.com.homebudget;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;

/**
 * Created by artyom on 03.11.15.
 */
public class Helper {
    String baseEmail = "qatestemail@testdomain.com";
    String basePass= "Qwerty123456";

    public String generateEmail(int local_part, int domain_part, int subdomain){
        String email = "qa" + RandomStringUtils.randomAlphanumeric(local_part - 3) + "@" + RandomStringUtils.randomAlphanumeric(domain_part) + "." + RandomStringUtils.randomAlphanumeric(subdomain) + ".com";
        return email;

    }

    public String generateEmail(int local_part, int domain_part){
        String email = "qa"+ RandomStringUtils.randomAlphanumeric(local_part - 3) + "@"+RandomStringUtils.randomAlphanumeric(domain_part-4)+".com";
        return email;
    }

    public String generatePass(int length){
        String pass =  RandomStringUtils.randomAlphanumeric(length);
        //String pass =  RandomStringUtils.randomAscii(length);
        return pass;
    }

    @DataProvider(name = "dataVerification")
    public Object[][] dataVerification() {
        return new Object[][]{
                {"Exist Email",baseEmail, basePass ,400,"This email is already taken"},
                {"Empty Email","", generatePass(10), 400, "The email must be between 6 and 60 characters"},
                {"One Space Email", " ", generatePass(10), 400, "The email must be between 6 and 60 characters"},

        };
    }
}
