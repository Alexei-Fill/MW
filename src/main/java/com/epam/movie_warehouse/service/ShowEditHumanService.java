package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.database.HumanDAO;
import com.epam.movie_warehouse.database.LanguageDAO;
import com.epam.movie_warehouse.entity.Human;
import com.epam.movie_warehouse.entity.Language;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;
import com.epam.movie_warehouse.exception.ValidationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.movie_warehouse.validator.AbstractValidator.validateId;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class ShowEditHumanService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException,
            IOException, ValidationException, ConnectionNotFoundException {
        final int LANGUAGE = getLanguageId(request, response);
        long humanId = validateId(request.getParameter(HUMAN_ID));
        HumanDAO humanDAO = new HumanDAO();
        Human human = humanDAO.showHumanById(humanId, LANGUAGE);
        if (human.getId() == 0) {
            request.setAttribute(EXCEPTION, SC_NOT_FOUND);
            response.sendError(SC_NOT_FOUND);
        } else {
            request.setAttribute(HUMAN, human);
            LanguageDAO languageDAO = new LanguageDAO();
            List<Language> languages = new ArrayList<>();
            languages.add(languageDAO.showLanguageById(LANGUAGE));
            request.setAttribute(HUMAN, human);
            request.setAttribute(LANGUAGES, languages);
            saveCurrentPageURLToSession(request, response);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(EDIT_HUMAN_JSP);
            requestDispatcher.forward(request, response);
        }
    }
}
