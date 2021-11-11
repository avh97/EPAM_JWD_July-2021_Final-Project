package by.khaletski.project.service.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static final Logger LOGGER = LogManager.getLogger();
    private static final String EMAIL_REGEX = "\\w+@\\p{Alpha}+\\.\\p{Alpha}{2,}";
    private static final String NAME_REGEX = "[\\p{Alpha}А-Яа-яA-Za-z\\s-]{1,255}";
    private static final String PASSWORD_REGEX = "[a-zA-Z\\d]{1,64}";
    private static final String DATE_REGEX = "\\d\\d\\d\\d-\\d\\d-\\d\\d";
    private static final String IMAGE_NAME_REGEX = "[\\p{Alpha}A-Za-z]{1,255}\\.jpg";

    public static boolean isValidName(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }
        return name.matches(NAME_REGEX);
    }

    public static boolean isValidEmail(String email) {
        boolean isValid;
        if (!email.isBlank()) {
            Pattern pattern = Pattern.compile(EMAIL_REGEX);
            Matcher matcher = pattern.matcher(email);
            isValid = matcher.matches();
        } else {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isValidPassword(String password) {
        if (password == null || password.isBlank()) {
            return false;
        }
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean isDateFormatValid(String dateAsString) {
        if (dateAsString == null || dateAsString.isBlank()) {
            return false;
        }
        return dateAsString.matches(DATE_REGEX);
    }

    public static boolean isValidImageName(String imageName) {
        if (imageName == null || imageName.isBlank()) {
            return false;
        }
        return imageName.matches(IMAGE_NAME_REGEX);
    }
}
