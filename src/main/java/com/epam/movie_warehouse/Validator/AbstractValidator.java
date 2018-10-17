package com.epam.movie_warehouse.Validator;

import com.epam.movie_warehouse.entity.Language;
import com.epam.movie_warehouse.exception.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public abstract class AbstractValidator{

    public static Long validateId(String id) throws ValidationException {
        if (EMPTY_STRING.equals(id)){
            id = "0";
        }
        return validateLong(id);
    }

    public static String validateName (String name) throws ValidationException {
        return validateString(name, STRING_MAX_LENGTH);
    }

    static Long validateLong(String stringParameter) throws ValidationException {
        long longParameter;
        try {
            longParameter = Long.parseLong(stringParameter);
        } catch (NumberFormatException e){
            throw new ValidationException(INCORRECT_DATA);
        }
        return longParameter;
    }

    static Integer validateInteger (String stringParameter) throws ValidationException {
        int intParameter;
        try {
            intParameter = Integer.parseInt(stringParameter);
        } catch (NumberFormatException e){
            throw new ValidationException(INCORRECT_DATA);
        }
        return intParameter;
    }

    static String validateString (String stringParameter, Integer maxLength) throws ValidationException {
        if ((stringParameter == null) || (EMPTY_STRING.equals(stringParameter) || (SPACE.equals(stringParameter)) ||
                (stringParameter.length() > maxLength))){
            throw new ValidationException(INCORRECT_DATA);
        }
        return stringParameter;
    }

    static LocalDate validateDate(String stringParameter, Language language) throws ValidationException {
        LocalDate localDateParameter;
        try {
            Locale locale = new Locale(language.getLocal());
            final DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern(language.getDateFormat(), locale);
            localDateParameter = LocalDate.parse(stringParameter, dateTimeFormatter);
        } catch (DateTimeParseException e){
            throw new ValidationException(INCORRECT_DATA);
        }
        return localDateParameter;
    }
}
