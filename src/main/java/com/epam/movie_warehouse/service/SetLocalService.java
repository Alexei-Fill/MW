package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.database.LanguageDAO;
import com.epam.movie_warehouse.entity.Language;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public class SetLocalService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException,
            IOException, ConnectionNotFoundException {
        LanguageDAO languageDAO = new LanguageDAO();
        Language language;
        String localIdString = request.getParameter(LOCAL_ID);
        if ((localIdString != null) && (!EMPTY_STRING.equals(localIdString.trim()))) {
            int localId = Integer.parseInt(localIdString);
            language = languageDAO.showLanguageById(localId);
            if (language.getId() != 0) {
                request.getSession().setAttribute(SESSION_LANGUAGE_ID, language.getId());
                request.getSession().setAttribute(LOCALE, language.getLocal());
                request.getSession().setAttribute(DATE_FORMAT, language.getDateFormat());
            }
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(LIST_GENRE_URI);
        requestDispatcher.forward(request, response);
    }
}

