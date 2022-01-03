package by.khaletski.platform.service.util;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ValidatorTest {

    @DataProvider
    public Object[][] isValidEmailData() {
        return new Object[][]{
                {"admin@gmail.com", true},
                {"admin1997@yandex.by", true},
                {"admin.google.com", false},
                {"admin@nosuchmail.com", true},
                {"admin@mail.com", true},
                {"@.a", false}
        };
    }

    @DataProvider
    public Object[][] isValidPasswordData() {
        return new Object[][]{
                {"1", false},
                {"12345678", true},
                {"123456789101112131415", false},
                {"Y", false},
                {"", false},
                {"  ", false},
                {"12 912", false},
                {"OneTwoThreeFourFive", true},
                {"OneTwoThreeFourFiveSix", false},
                {"admin", false},
                {"password", true},
                {" password", false}
        };
    }

    @DataProvider
    public Object[][] isValidIdData() {
        return new Object[][]{
                {"1", true},
                {"-1", false},
                {"0", true},
                {"01", false},
                {"0.1", false},
                {"123", true},
                {"2147483647", true},
                {"2147483648", false},
                {"еее", false},
                {"123еее", false},
                {"FiftySeven", false},
                {".,^73kdg", false},
                {" ", false},
                {"", false}
        };
    }

    @DataProvider
    public Object[][] isValidNameData() {
        return new Object[][]{
                {"Anton", true},
                {" Anton", false},
                {"Anton ", true},
                {"Антон", true},
                {"Римский-Корсаков", true},
                {"Rimsky-Korsakov", true},
                {"LongStrangeName", true},
                {"ThisNameIsTooLongForRealPersons", false},
                {"", false},
                {" ", false},
                {"<SomethingInBrackets>", false},
                {"Name567", false},
                {"name.,name", false}
        };
    }

    @DataProvider
    public Object[][] isValidTextData() {
        return new Object[][]{
                {"Lucius Cornelius Sulla Felix[7] (/ˈsʌlə/; 138–78 BC), commonly known as Sulla, "
                        + "was a Roman general and statesman. He won the first large-scale civil war "
                        + "in Roman history, and became the first man of the republic to seize power "
                        + "through force.", true},
                {"Gaius Marius (Latin: [ˈɡaːijʊs ˈmarijʊs]; c. 157 BC – 13 January 86 BC) "
                        + "was a Roman general and statesman. Victor of the Cimbric and Jugurthine wars, "
                        + "he held the office of consul an unprecedented seven times during his career.[1] "
                        + "He was also noted for his important reforms of Roman armies. He set the precedent "
                        + "for the shift from the militia levies of the middle Republic to the professional "
                        + "soldiery of the late Republic; he also improved the pilum, a javelin, and made large-scale "
                        + "changes to the logistical structure of the Roman army.[2]", false}
        };
    }

    @DataProvider
    public Object[][] isValidDateFormatData() {
        return new Object[][]{
                {"2021-12-12", true},
                {"2021-15-12", false},
                {"2021-12-15", true},
                {"2021/12/15", false},
                {"2021 12 15", false},
                {"2021-12", false},
                {"2021-1215", false},
                {"1234567890", false},
                {"25 2 5", false},
                {"sss121", false},
                {"", false},
                {" ", false},
                {"ThisIsNotADate", false},
        };
    }

    @Test(dataProvider = "isValidEmailData")
    public void testIsValidEmail(String string, boolean expected) {
        boolean actual = Validator.isValidEmail(string);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "isValidPasswordData")
    public void testIsValidPassword(String string, boolean expected) {
        boolean actual = Validator.isValidPassword(string);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "isValidIdData")
    public void testIsValidId(String string, boolean expected) {
        boolean actual = Validator.isValidId(string);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "isValidNameData")
    public void testIsValidName(String string, boolean expected) {
        boolean actual = Validator.isValidName(string);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "isValidTextData")
    public void testIsValidText(String string, boolean expected) {
        boolean actual = Validator.isValidText(string);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "isValidDateFormatData")
    public void testIsValidDateFormat(String string, boolean expected) {
        boolean actual = Validator.isValidDateFormat(string);
        assertEquals(actual, expected);
    }
}