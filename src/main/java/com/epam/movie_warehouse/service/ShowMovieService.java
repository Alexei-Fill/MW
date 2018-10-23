package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.database.GenreDAO;
import com.epam.movie_warehouse.database.HumanDAO;
import com.epam.movie_warehouse.database.MovieDAO;
import com.epam.movie_warehouse.database.UserDAO;
import com.epam.movie_warehouse.entity.Genre;
import com.epam.movie_warehouse.entity.Human;
import com.epam.movie_warehouse.entity.Movie;
import com.epam.movie_warehouse.entity.User;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class ShowMovieService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException, SQLException, ConnectionNotFoundException {
        final int LANGUAGE = getLanguageId(request, response);
        long movieId = Long.parseLong(request.getParameter(MOVIE_ID_ATTRIBUTE));
        MovieDAO movieDAO = new MovieDAO();
        GenreDAO genreDAO = new GenreDAO();
        HumanDAO humanDAO = new HumanDAO();
        UserDAO userDAO = new UserDAO();
        User user = (User) request.getSession().getAttribute(AUTHORIZED_USER_ATTRIBUTE);
        int like = 0;
        int grade = 0;
        Movie movie = movieDAO.getMovieById(movieId, LANGUAGE);
        if (movie.getId() == 0) {
            request.setAttribute(EXCEPTION, SC_NOT_FOUND);
            response.sendError(SC_NOT_FOUND);
        } else {
            if (user != null) {
                like = userDAO.checkMoviesLinksByLikedField(user.getId(), movieId);
                grade = userDAO.checkMoviesLinksByVotedField(user.getId(), movieId);
            }
            if (NO_ENTRY_EXISTS_VALUE == grade) {
                grade = 0;
            }
            if (NO_ENTRY_EXISTS_VALUE == like) {
                like = 0;
            }
            List<Genre> movieGenres = genreDAO.listGenresOfTheMovie(movie.getId(), LANGUAGE);
            List<Human> movieCrew = humanDAO.listMovieCrew(movie.getId(), LANGUAGE);
            movie.setGenres(movieGenres);
            movie.setMovieCrew(movieCrew);
            movie.setCountOfLike(movieDAO.getCountOfLikesByMovieId(movie.getId()));
            movie.setRating(movieDAO.getRatingByMovieId(movie.getId()));
            request.setAttribute(MOVIE_ATTRIBUTE, movie);
            request.setAttribute(LIKED_ATTRIBUTE, like);
            request.setAttribute(GRADE_ATTRIBUTE, grade);
            saveCurrentPageURLToSession(request, response);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(MOVIE_JSP);
            requestDispatcher.forward(request, response);
        }
    }
}
