package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.database.GenreDAO;
import com.epam.movie_warehouse.database.HumanDAO;
import com.epam.movie_warehouse.database.LanguageDAO;
import com.epam.movie_warehouse.database.MovieDAO;
import com.epam.movie_warehouse.entity.Genre;
import com.epam.movie_warehouse.entity.Human;
import com.epam.movie_warehouse.entity.Language;
import com.epam.movie_warehouse.entity.Movie;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class ShowEditMovieService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException,
            IOException, ConnectionNotFoundException {
        final int LANGUAGE_ID = getLanguageId(request, response);
        MovieDAO movieDAO = new MovieDAO();
        long movieId = Long.parseLong(request.getParameter(MOVIE_ID_ATTRIBUTE));
        Movie movie = movieDAO.getMovieById(movieId, LANGUAGE_ID);
        if (movie.getId() == 0) {
            request.setAttribute(EXCEPTION, SC_NOT_FOUND);
            response.sendError(SC_NOT_FOUND);
        } else {
            GenreDAO genreDAO = new GenreDAO();
            HumanDAO humanDAO = new HumanDAO();
            LanguageDAO languageDAO = new LanguageDAO();
            List<Genre> movieGenre = genreDAO.listGenresOfTheMovie(movie.getId(), LANGUAGE_ID);
            List<Human> movieCrew = humanDAO.listMovieCrew(movie.getId(), LANGUAGE_ID);
            List<Genre> genreList = genreDAO.listGenres(LANGUAGE_ID);
            List<Human> humanList = humanDAO.listHuman(LANGUAGE_ID);
            List<Language> languageList = new ArrayList<>();
            languageList.add(languageDAO.getLanguageById(LANGUAGE_ID));
            movie.setGenres(movieGenre);
            movie.setMovieCrew(movieCrew);
            request.setAttribute(MOVIE_ATTRIBUTE, movie);
            request.setAttribute(GENRES_ATTRIBUTE, genreList);
            request.setAttribute(HUMANS_ATTRIBUTE, humanList);
            request.setAttribute(LANGUAGES_ATTRIBUTE, languageList);
            writeCurrentPageToSession(request, response);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(EDIT_MOVIE_JSP);
            requestDispatcher.forward(request, response);
        }
    }
}
