package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.database.GenreDAO;
import com.epam.movie_warehouse.database.HumanDAO;
import com.epam.movie_warehouse.database.MovieDAO;
import com.epam.movie_warehouse.entity.Genre;
import com.epam.movie_warehouse.entity.Human;
import com.epam.movie_warehouse.entity.Movie;
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
import static com.epam.movie_warehouse.validator.MovieValidator.validateName;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public class ShowMovieByNameOrGenreService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
            ValidationException, SQLException, ConnectionNotFoundException {
        final int LANGUAGE = getLanguageId(request, response);
        String requestURI = request.getRequestURI();
        MovieDAO movieDAO = new MovieDAO();
        GenreDAO genreDAO = new GenreDAO();
        HumanDAO humanDAO = new HumanDAO();
        List<Movie> movies = new ArrayList<>();
        if (LIST_MOVIE_BY_GENRE_URI.equalsIgnoreCase(requestURI)) {
            long genreId = validateId(request.getParameter(GENRE_ID_ATTRIBUTE));
            movies = movieDAO.listMovieByGenre(genreId, LANGUAGE);
        } else if (LIST_MOVIE_BY_NAME_URI.equalsIgnoreCase(requestURI)) {
            String searchString = validateName(request.getParameter(SEARCH_STRING_ATTRIBUTE));
            movies = movieDAO.listMovieByName(searchString, LANGUAGE);
            request.setAttribute(SEARCH_STRING_ATTRIBUTE, searchString);
        }
        for (Movie movie : movies) {
            List<Genre> movieGenres = genreDAO.listGenresOfTheMovie(movie.getId(), LANGUAGE);
            List<Human> movieCrew = humanDAO.listMovieCrew(movie.getId(), LANGUAGE);
            movie.setGenres(movieGenres);
            movie.setMovieCrew(movieCrew);
            movie.setCountOfLike(movieDAO.getCountOfLikesByMovieId(movie.getId()));
            movie.setRating(movieDAO.getRatingByMovieId(movie.getId()));
        }
        request.setAttribute(MOVIES_ATTRIBUTE, movies);
        if (movies.isEmpty()) {
            request.setAttribute(HIDDEN_MESSAGE_ATTRIBUTE, PARAMETER_SHOW_MESSAGE_TRUE);
        }
        String requestDispatch = LIST_MOVIE_JSP;
        String requestURL = (String) request.getSession().getAttribute(CURRENT_URL_ATTRIBUTE);
        System.out.println(requestURL);
        if (requestURL.equalsIgnoreCase(LIST_MOVIES_ADMIN_URI)) {
            requestDispatch = LIST_MOVIE_ADMIN_JSP;
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(requestDispatch);
        requestDispatcher.forward(request, response);
    }
}
