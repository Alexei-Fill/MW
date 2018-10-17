package com.epam.movie_warehouse.Validator;

import com.epam.movie_warehouse.entity.Language;
import com.epam.movie_warehouse.exception.ValidationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public class MovieValidator extends AbstractValidator {

    public static Long validateBudget (String budget) throws ValidationException {
        return validateLong(budget);
    }

    public static Long validateDues(String dues) throws ValidationException {
        return validateLong(dues);
    }

    public static Integer validateAgeLimit(String ageLimit) throws ValidationException {
        return validateInteger(ageLimit);
    }

    public static String validateImdbId (String imdbId) throws ValidationException {
        return validateString(imdbId, IMDB_MAX_LENGTH);
    }

    public static String validateDescription (String description) throws ValidationException {
        return validateString(description, BIG_TEXT_MAX_LENGTH);
    }

    public static String validateCountry (String country) throws ValidationException {
        return validateString(country, STRING_MAX_LENGTH);
    }

    public static LocalDate validateReleaseDate (String releaseDate, Language language) throws ValidationException {
        return validateDate(releaseDate, language);
    }

    public static LocalTime validateDuration(String duration) throws ValidationException {
        LocalTime localTimeParameter;
        try {
            localTimeParameter = LocalTime.parse(duration);
        } catch (DateTimeParseException e){
            throw new ValidationException(INCORRECT_DATA);
        }
        return localTimeParameter;
    }

    public static Integer validateGrade(String grade) throws ValidationException {
        return validateInteger(grade);
    }
}
