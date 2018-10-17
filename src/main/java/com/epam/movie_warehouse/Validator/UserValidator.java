package com.epam.movie_warehouse.Validator;

import com.epam.movie_warehouse.entity.Language;
import com.epam.movie_warehouse.exception.ValidationException;
import com.epam.movie_warehouse.util.MovieWarehouseConstant;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public class UserValidator extends AbstractValidator {

    public static String validateLogin (String login) throws ValidationException {
        if ((login == null) || (EMPTY_STRING.equals(login) || (SPACE.equals(login)) ||
                (login.length() > MovieWarehouseConstant.STRING_MAX_LENGTH) || (login.length() < LOGIN_MIN_LENGTH) ||
                !doMatch(login, LOGIN_PATTERN))){
            throw new ValidationException(INCORRECT_DATA);
        }
        return login;
    }

    public static String validatePassword (String password) throws ValidationException {
        if ((password == null) || (EMPTY_STRING.equals(password) || (SPACE.equals(password)) ||
                (password.length() > PASSWORD_MAX_LENGTH) || (password.length() < PASSWORD_MIN_LENGTH))){
            throw new ValidationException(INCORRECT_DATA);
        }
        return password;
    }

    public static String validateMail (String mail) throws ValidationException {
        if ((mail == null) || (EMPTY_STRING.equals(mail) || (SPACE.equals(mail)) ||
                (mail.length() > MAIL_MAX_LENGTH) || (mail.length() < MAIL_MIN_LENGTH)  ||
                !doMatch(mail, MAIL_PATTERN))){
            throw new ValidationException(INCORRECT_DATA);
        }
        return mail;
    }

    public static LocalDate validateBirthDate (String birthDate, Language language) throws ValidationException {
        return validateDate(birthDate, language);
    }

    public static Integer validateRoleId(String roleId) throws ValidationException {
        return validateInteger(roleId);
    }


    private static Boolean doMatch (String parameter, String regex) {
        boolean isMatch;
        final Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(parameter);
        isMatch = matcher.matches();
        return isMatch;
    }
}
