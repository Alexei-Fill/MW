package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.dao.GenreDAO;
import com.epam.movie_warehouse.dao.LanguageDAO;
import com.epam.movie_warehouse.entity.Genre;
import com.epam.movie_warehouse.entity.Language;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public class ListEditGenreService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        final int LANGUAGE = getLanguageId(request,response);
        GenreDAO genreDAO = new GenreDAO();
        LanguageDAO languageDAO = new LanguageDAO();
        List<Genre> genres = genreDAO.showAllAvailableGenres(LANGUAGE);
        List<Language> languages = languageDAO.showAllLanguages();
        request.setAttribute(GENRES, genres);
        request.setAttribute(LANGUAGES, languages);
        saveCurrentPageURLToSession(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(LIST_EDIT_GENRE_JSP);
        requestDispatcher.forward(request, response);
    }
}

