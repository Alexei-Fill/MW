package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.database.GenreDAO;
import com.epam.movie_warehouse.database.HumanDAO;
import com.epam.movie_warehouse.database.LanguageDAO;
import com.epam.movie_warehouse.entity.Genre;
import com.epam.movie_warehouse.entity.Human;
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

public class ShowAddMovieService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException,
            IOException, ConnectionNotFoundException {
        final int LANGUAGE_ID = getLanguageId(request, response);
        GenreDAO genreDAO = new GenreDAO();
        HumanDAO humanDAO = new HumanDAO();
        LanguageDAO languageDAO = new LanguageDAO();
        List<Genre> genreList = genreDAO.listGenres(LANGUAGE_ID);
        List<Human> humanList = humanDAO.listHuman(LANGUAGE_ID);
        List<Language> languages = languageDAO.listLanguage();
        request.setAttribute(GENRES_ATTRIBUTE, genreList);
        request.setAttribute(HUMANS_ATTRIBUTE, humanList);
        request.setAttribute(LANGUAGES_ATTRIBUTE, languages);
        writeCurrentPageToSession(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(EDIT_MOVIE_JSP);
        requestDispatcher.forward(request, response);
    }
}
