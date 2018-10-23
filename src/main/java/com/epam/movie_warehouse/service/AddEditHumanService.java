package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.database.HumanDAO;
import com.epam.movie_warehouse.entity.Human;
import com.epam.movie_warehouse.entity.Language;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;
import com.epam.movie_warehouse.exception.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.movie_warehouse.validator.HumanValidator.*;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public class AddEditHumanService implements Service {
    private static final Logger ROOT_LOGGER = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
            ValidationException, SQLException, ConnectionNotFoundException {
        final Language language = getLanguage(request, response);
        final int LANGUAGE_ID = language.getId();
        HumanDAO humanDAO = new HumanDAO();
        Human human = new Human();
        long humanId = validateId(request.getParameter(HUMAN_ID_ATTRIBUTE));
        if (humanId != 0) {
            human = humanDAO.getHumanById(humanId, LANGUAGE_ID);
        }
        human.setImageURL(request.getParameter(IMG_URL_ATTRIBUTE));
        human.setBirthDate(validateBirthDate(request.getParameter(BIRTH_DATE_ATTRIBUTE), language));
        if (humanId != 0) {
            String[] languages = request.getParameterValues(CHARACTERISTIC_LANGUAGE_ID);
            if (languages != null) {
                for (String s : languages) {
                    int languageId = Integer.parseInt(s.trim());
                    setMultiLanguageParameters(human, request, languageId);
                }
            }
            humanDAO.updateHuman(human, LANGUAGE_ID);
            ROOT_LOGGER.info("Human was changed " + human);
        } else {
            humanDAO.addHuman(human);
            String[] languages = request.getParameterValues(CHARACTERISTIC_LANGUAGE_ID);
            if (languages != null) {
                for (String s : languages) {
                    int languageId = Integer.parseInt(s.trim());
                    setMultiLanguageParameters(human, request, languageId);
                    humanDAO.addHumanMultiLanguageParameters(human, languageId);
                }
            }
            ROOT_LOGGER.info("Human was added " + human);
        }
        request.setAttribute(HUMAN_ATTRIBUTE, human);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(SHOW_HUMAN_BY_ID_URL + human.getId());
        requestDispatcher.forward(request, response);
    }

    private void setMultiLanguageParameters(Human human, HttpServletRequest request, int languageId) throws ValidationException {
        human.setName(validateName(request.getParameter(NAME_ATTRIBUTE + languageId)));
        human.setSurname(validateSurname(request.getParameter(SURNAME_ATTRIBUTE + languageId)));
        human.setPatronymic(validatePatronymic(request.getParameter(PATRONYMIC_ATTRIBUTE + languageId)));
        human.setBiography(validateBiography(request.getParameter(BIOGRAPHY_ATTRIBUTE + languageId)));
    }
}
