package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.database.GenreDAO;
import com.epam.movie_warehouse.database.HumanDAO;
import com.epam.movie_warehouse.database.MovieDAO;
import com.epam.movie_warehouse.entity.Genre;
import com.epam.movie_warehouse.entity.Human;
import com.epam.movie_warehouse.entity.Movie;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public class ListMovieService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
            SQLException, ConnectionNotFoundException {
        final int LANGUAGE = getLanguageId(request, response);
        MovieDAO movieDAO = new MovieDAO();
        GenreDAO genreDAO = new GenreDAO();
        HumanDAO humanDAO = new HumanDAO();
        List<Movie> movies = movieDAO.listMovie(LANGUAGE);
        for (Movie movie : movies) {
            List<Genre> movieGenres = genreDAO.listGenresOfTheMovie(movie.getId(), LANGUAGE);
            movie.setGenres(movieGenres);
            List<Human> movieCrew = humanDAO.listMovieCrew(movie.getId(), LANGUAGE);
            movie.setMovieCrew(movieCrew);
            movie.setCountOfLike(movieDAO.getCountOfLikesByMovieId(movie.getId()));
            movie.setRating(movieDAO.getRatingByMovieId(movie.getId()));
        }
        request.setAttribute(MOVIES_ATTRIBUTE, movies);
        String requestDispatch = LIST_MOVIE_JSP;
        if (request.getRequestURI().equalsIgnoreCase(LIST_MOVIES_ADMIN_URI)) {
            requestDispatch = LIST_MOVIE_ADMIN_JSP;
        }
        saveCurrentPageURLToSession(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(requestDispatch);
        requestDispatcher.forward(request, response);
    }
}
