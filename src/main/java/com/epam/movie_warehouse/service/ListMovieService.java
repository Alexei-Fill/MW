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
import java.util.List;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;
import static com.epam.movie_warehouse.validator.AbstractValidator.validateId;
import static com.epam.movie_warehouse.validator.AbstractValidator.validateName;

public class ListMovieService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
            SQLException, ConnectionNotFoundException, ValidationException {
        final int LANGUAGE_ID = getLanguageId(request, response);
        String requestURI = request.getRequestURI();
        MovieDAO movieDAO = new MovieDAO();
        GenreDAO genreDAO = new GenreDAO();
        HumanDAO humanDAO = new HumanDAO();
        List<Movie> movieList;
        switch (requestURI) {
            case (LIST_MOVIE_BY_GENRE_URI): {
                long genreId = validateId(request.getParameter(GENRE_ID_ATTRIBUTE));
                movieList = movieDAO.listMovieByGenre(genreId, LANGUAGE_ID);
                requestURI = (String) request.getSession().getAttribute(CURRENT_URL_ATTRIBUTE);
                break;
            }
            case (LIST_MOVIE_BY_NAME_URI): {
                String searchString = validateName(request.getParameter(SEARCH_STRING_ATTRIBUTE));
                movieList = movieDAO.listMovieByName(searchString, LANGUAGE_ID);
                request.setAttribute(SEARCH_STRING_ATTRIBUTE, searchString);
                requestURI = (String) request.getSession().getAttribute(CURRENT_URL_ATTRIBUTE);
                break;
            }
            default: {
                movieList = movieDAO.listMovie(LANGUAGE_ID);
                writeCurrentPageToSession(request, response);
                break;
            }
        }
        for (Movie movie : movieList) {
            List<Genre> genreList = genreDAO.listGenresOfTheMovie(movie.getId(), LANGUAGE_ID);
            movie.setGenres(genreList);
            List<Human> humanList = humanDAO.listMovieCrew(movie.getId(), LANGUAGE_ID);
            movie.setMovieCrew(humanList);
            movie.setCountOfLike(movieDAO.getCountOfLikesByMovieId(movie.getId()));
            movie.setRating(movieDAO.getRatingByMovieId(movie.getId()));
        }
        if (movieList.isEmpty()) {
            request.setAttribute(HIDDEN_MESSAGE_ATTRIBUTE, PARAMETER_SHOW_MESSAGE_TRUE);
        }
        request.setAttribute(MOVIES_ATTRIBUTE, movieList);
        String requestDispatch = LIST_MOVIE_JSP;
        if (requestURI.equalsIgnoreCase(LIST_MOVIES_ADMIN_URI)) {
            requestDispatch = LIST_MOVIE_ADMIN_JSP;
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(requestDispatch);
        requestDispatcher.forward(request, response);
    }
}
