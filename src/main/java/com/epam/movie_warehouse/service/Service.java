package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.database.LanguageDAO;
import com.epam.movie_warehouse.entity.Language;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;
import com.epam.movie_warehouse.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public interface Service {

    void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException,
            ServletException, IOException, ValidationException, ConnectionNotFoundException;

    default Integer getLanguageId(HttpServletRequest request, HttpServletResponse response) {
        int language = DEFAULT_LANGUAGE;
        if ((request.getSession().getAttribute(SESSION_LANGUAGE_ID) != null)) {
            language = (Integer) request.getSession().getAttribute(SESSION_LANGUAGE_ID);
        }
        return language;
    }

    default Language getLanguage(HttpServletRequest request, HttpServletResponse response) throws SQLException,
            ConnectionNotFoundException {
        LanguageDAO languageDAO = new LanguageDAO();
        return languageDAO.showLanguageById(getLanguageId(request, response));
    }

    default void saveCurrentPageURLToSession(HttpServletRequest request, HttpServletResponse response) {
        String requestURL = request.getRequestURI() +
                (request.getQueryString() != null ? QUESTION_MARK + request.getQueryString() : EMPTY_STRING);
        request.getSession().setAttribute(CURRENT_URL, requestURL);
    }
}
