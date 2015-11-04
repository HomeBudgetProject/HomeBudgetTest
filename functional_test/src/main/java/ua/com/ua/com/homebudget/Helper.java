package ua.com.ua.com.homebudget;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;

/**
 * Created by Anohin Artyom on 03.11.15.
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

    @DataProvider(name = "negativeEmailVerification")
    public Object[][] negativeEmailVerification() {
        return new Object[][]{
                {"Empty Email","", generatePass(10), 400, "The email must be between 6 and 60 characters"},
                {"One Space Email", " ", generatePass(10), 400, "The email must be between 6 and 60 characters"},
                {"Two Space Email", "  ", generatePass(10), 400, "The email must be between 6 and 60 characters"},
                {"Five Letters Email", "m@e.u", generatePass(10), 400, "The email must be between 6 and 60 characters"},
                {"Five Letters + Space Email", "m@e.u ", generatePass(10), 400, "The email must be between 6 and 60 characters"},
                {"61 Letters Email", generateEmail(31, 30), generatePass(10), 400, "The email must be between 6 and 60 characters"}
        };
    }
    @DataProvider(name = "negativePassVerification")
    public Object[][] negativePassVerification() {
        return new Object[][]{
                {"Empty Pass", generateEmail(10, 10), "", 400, "The password must be between 6 and 100 characters"},
                {"One SpacePass", generateEmail(10, 10), " ", 400, "The password must be between 6 and 100 characters"},
                {"Two Space Pass", generateEmail(10, 10), "  ", 400, "The password must be between 6 and 100 characters"},
                {"5 Letters Pass", generateEmail(10, 10), generatePass(5), 400, "The password must be between 6 and 100 characters"},
                {"5 + Space Letters Pass", generateEmail(10, 10), generatePass(5) + " ", 400, "The password must be between 6 and 100 characters"}
        };
    }
    @DataProvider(name = "positiveVerification")
    public Object[][] positiveVerification() {
        return new Object[][]{
                {"Six Letters Email", "m@e.ua", generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"Space + Five Letters Email", " " + generateEmail(10, 10), generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"Last Space Email", generateEmail(10, 10) + " ", generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"Sixty Letters Email", generateEmail(30, 30), generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"Space + Sixty Letters Email", " " + generateEmail(30, 30), generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"60 Letters + Space Email", generateEmail(30, 30) + " ", generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"59 Letters Email", generateEmail(29, 30), generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"Two Domain Email", generateEmail(10, 10, 10), generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"Numeric Local Email", "123456@test.com", generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"Numeric Domain Email", "qatest@123456.com", generatePass(10), 200, "Вы успешно зарегистрированы"},
                {"100 Letters Pass", generateEmail(10, 10), generatePass(100), 200, "Вы успешно зарегистрированы"},
                {"100 + Space Letters Pass", generateEmail(10, 10), generatePass(100) + " ", 200, "Вы успешно зарегистрированы"},
                {"Space + 100 Letters Pass", generateEmail(10, 10), " " + generatePass(100), 200, "Вы успешно зарегистрированы"},
                {"6 Letters Pass", generateEmail(10, 10), generatePass(6), 200, "Вы успешно зарегистрированы"},
                {"RFC Email", "qaother.email.with-dash+symbol@example.com", generatePass(6), 200, "Вы успешно зарегистрированы"},
                {"RFC2 Email", "qaMiles.O'Brian@example.com", generatePass(6), 200, "Вы успешно зарегистрированы"}
        };
    }

    @DataProvider(name = "negativeEmailValidation")
    public Object[][] negativeEmailValidation() {
        return new Object[][]{
                {"","qatest@test.123", generatePass(10), 400, "Email is not valid"},
                {"","qatest@test.!#$", generatePass(10), 400, "Email is not valid"},
                {"","qam e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@me@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qame.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@.me.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m`e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m~e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m!e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m#e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m$e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m%e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m^e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m&e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m*e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m(e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m)e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m_e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m=e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m+e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m[e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m]e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m;e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m:e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m'e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m|e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m<e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m>e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m,e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m?e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@me,com", generatePass(10), 400, "Email is not valid"},
                {"","qam`e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam~e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam!e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam#e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam$e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam%e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam*e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam(e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam)e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam=e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam[e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam]e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam;e@me.comm", generatePass(10), 400, "Email is not valid"},
                {"","qam:e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam,e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam<e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam>e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam|e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m\\e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m\"e.com", generatePass(10), 400, "Email is not valid"},
                {"","qam\\e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam\"e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qam}e@me.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m}e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m/e.com", generatePass(10), 400, "Email is not valid"},
                {"","qame@m{e.com", generatePass(10), 400, "Email is not valid"},
                {"","qam{e@me.com", generatePass(10), 400, "Email is not valid"}
        };
    }
    @DataProvider(name = "positiveEmailValidation")
    public Object[][] positiveEmailValidation() {
        return new Object[][]{
                {"","qame@m-e.com", generatePass(10), 200, null},
                {"","qam^e@me.com", generatePass(10), 200, null},
                {"","qam&e@me.com", generatePass(10), 200, null},
                {"","qam-e@me.com", generatePass(10), 200, null},
                {"","qam_e@me.com", generatePass(10), 200, null},
                {"","qam+e@me.com", generatePass(10), 200, null},
                {"","qam'e@me.com", generatePass(10), 200, null},
                {"","qam/e@me.com", generatePass(10), 200, null}
        };
    }

}
