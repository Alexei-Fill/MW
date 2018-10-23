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
import java.util.List;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public class ShowAddHumanService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException,
            IOException, ConnectionNotFoundException {
        LanguageDAO languageDAO = new LanguageDAO();
        List<Language> languages = languageDAO.listLanguage();
        request.setAttribute(LANGUAGES_ATTRIBUTE, languages);
        saveCurrentPageURLToSession(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(EDIT_HUMAN_JSP);
        requestDispatcher.forward(request, response);
    }
}
