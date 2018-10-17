package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.dao.LanguageDAO;
import com.epam.movie_warehouse.entity.Language;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.SITE_LANGUAGE;

public class ListLanguageService implements Service {
    private LanguageDAO languageDAO = new LanguageDAO();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Language> languages = listLanguage();
        request.getServletContext().setAttribute(SITE_LANGUAGE, languages);
    }

    public  List<Language> listLanguage () throws SQLException {
        return languageDAO.showAllLanguages();
    }
}

