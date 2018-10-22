package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.dao.GenreDAO;
import com.epam.movie_warehouse.dao.HumanDAO;
import com.epam.movie_warehouse.dao.LanguageDAO;
import com.epam.movie_warehouse.dao.MovieDAO;
import com.epam.movie_warehouse.entity.Genre;
import com.epam.movie_warehouse.entity.Human;
import com.epam.movie_warehouse.entity.Language;
import com.epam.movie_warehouse.entity.Movie;

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
    public void execute(HttpServletRequest request, HttpServletResponse response)  throws SQLException,ServletException,
            IOException {
        final int LANGUAGE = getLanguageId(request,response);
        long movieId = Long.parseLong(request.getParameter(MOVIE_ID));
        MovieDAO movieDAO = new MovieDAO();
        GenreDAO genreDAO = new GenreDAO();
        HumanDAO humanDAO = new HumanDAO();
        LanguageDAO languageDAO = new LanguageDAO();
        Movie movie = movieDAO.showMovieById(movieId, LANGUAGE);
        if (movie.getId() == 0){
            request.setAttribute(EXCEPTION, SC_NOT_FOUND);
            response.sendError(SC_NOT_FOUND);
        } else {
            List<Genre> movieGenres = genreDAO.showGenresOfTheMovie(movie.getId(), LANGUAGE);
            List<Human> movieCrew = humanDAO.showMovieCrew(movie.getId(), LANGUAGE);
            List<Genre> genres = genreDAO.showAllAvailableGenres(LANGUAGE);
            List<Human> humans = humanDAO.listHuman(LANGUAGE);
            List<Language> languages = new ArrayList<>();
            languages.add(languageDAO.showLanguageById(LANGUAGE));
            movie.setGenres(movieGenres);
            movie.setMovieCrew(movieCrew);
            request.setAttribute(MOVIE, movie);
            request.setAttribute(GENRES, genres);
            request.setAttribute(HUMANS, humans);
            request.setAttribute(LANGUAGES, languages);
            saveCurrentPageURLToSession(request, response);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(EDIT_MOVIE_JSP);
            requestDispatcher.forward(request, response);
        }
    }
}
