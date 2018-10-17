package com.epam.movie_warehouse.Validator;

import com.epam.movie_warehouse.entity.Language;
import com.epam.movie_warehouse.exception.ValidationException;

import java.time.LocalDate;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public class HumanValidator extends AbstractValidator {

    public static String validateBiography (String biography) throws ValidationException {
        return validateString(biography, BIG_TEXT_MAX_LENGTH);
    }

    public static LocalDate validateBirthDate (String releaseDate, Language language) throws ValidationException {
        return validateDate(releaseDate, language);
    }

}
