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
import java.util.HashMap;
import java.util.Map;

import static com.epam.movie_warehouse.validator.HumanValidator.*;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public class EditOrUploadHumanService implements Service {
    private static final Logger ROOT_LOGGER = LogManager.getRootLogger();
    private final HumanDAO HUMAN_DAO = new HumanDAO();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
            ValidationException, SQLException, ConnectionNotFoundException {
        final Language language = getLanguage(request, response);
        final int LANGUAGE_ID = language.getId();
        Human human = new Human();
        long humanId = validateId(request.getParameter(HUMAN_ID_ATTRIBUTE));
        if (humanId != 0) {
            setHumanParameters(human, request, response, language);
            human = HUMAN_DAO.getHumanById(humanId, LANGUAGE_ID);
            String[] languageIdValue = request.getParameterValues(CHARACTERISTIC_LANGUAGE_ID);
            if (languageIdValue != null) {
                for (String s : languageIdValue) {
                    int languageId = Integer.parseInt(s.trim());
                    setHumanMultiLanguageParameters(human, request, response, languageId);
                }
            }
            HUMAN_DAO.updateHuman(human, LANGUAGE_ID);
            ROOT_LOGGER.info("Human was changed " + human);
        } else {
            Map<Integer, Human> multiLanguageHumanMap = new HashMap<>();
            setHumanParameters(human, request, response, language);
            String[] languageIdValue = request.getParameterValues(CHARACTERISTIC_LANGUAGE_ID);
            if (languageIdValue != null) {
                for (String s : languageIdValue) {
                    int languageId = Integer.parseInt(s.trim());
                    Human multiLanguageHuman = new Human();
                    setHumanMultiLanguageParameters(multiLanguageHuman, request, response, languageId);
                    multiLanguageHumanMap.put(languageId, multiLanguageHuman);
                }
            }
            HUMAN_DAO.addHuman(human, multiLanguageHumanMap);
            ROOT_LOGGER.info("Human was added " + human);
        }
        request.setAttribute(HUMAN_ATTRIBUTE, human);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(SHOW_HUMAN_BY_ID_URL + human.getId());
        requestDispatcher.forward(request, response);
    }

    private void setHumanParameters(Human human, HttpServletRequest request, HttpServletResponse response, Language language)
            throws ValidationException {
        human.setImageURL(request.getParameter(IMG_URL_ATTRIBUTE));
        human.setBirthDate(validateBirthDate(request.getParameter(BIRTH_DATE_ATTRIBUTE), language));
    }

    private void setHumanMultiLanguageParameters(Human human, HttpServletRequest request, HttpServletResponse response,
                                                 int languageId) throws ValidationException {
        human.setName(validateName(request.getParameter(NAME_ATTRIBUTE + languageId)));
        human.setSurname(validateSurname(request.getParameter(SURNAME_ATTRIBUTE + languageId)));
        human.setPatronymic(validatePatronymic(request.getParameter(PATRONYMIC_ATTRIBUTE + languageId)));
        human.setBiography(validateBiography(request.getParameter(BIOGRAPHY_ATTRIBUTE + languageId)));
    }
}
