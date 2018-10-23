package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.database.LanguageDAO;
import com.epam.movie_warehouse.entity.Language;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.SITE_LANGUAGE;

public class ListLanguageService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ConnectionNotFoundException {
        List<Language> languages = listLanguage();
        request.getServletContext().setAttribute(SITE_LANGUAGE, languages);
    }

    public List<Language> listLanguage() throws SQLException, ConnectionNotFoundException {
        LanguageDAO languageDAO = new LanguageDAO();
        return languageDAO.listLanguage();
    }
}

