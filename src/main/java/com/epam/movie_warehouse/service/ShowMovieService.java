package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.dao.GenreDAO;
import com.epam.movie_warehouse.dao.HumanDAO;
import com.epam.movie_warehouse.dao.MovieDAO;
import com.epam.movie_warehouse.dao.UserDAO;
import com.epam.movie_warehouse.entity.Genre;
import com.epam.movie_warehouse.entity.Human;
import com.epam.movie_warehouse.entity.Movie;
import com.epam.movie_warehouse.entity.User;

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
            SQLException, IOException, NumberFormatException {
        final int LANGUAGE = getLanguageId(request,response);
        long movieId = Long.parseLong(request.getParameter(MOVIE_ID));
        MovieDAO movieDAO = new MovieDAO();
        GenreDAO genreDAO = new GenreDAO();
        HumanDAO humanDAO = new HumanDAO();
        UserDAO userDAO = new UserDAO();
        User user =  (User) request.getSession().getAttribute(AUTHORIZED_USER);
        Integer like = null;
        Integer grade = null;
        Movie movie = movieDAO.showMovieById(movieId, LANGUAGE);
        if (movie == null){
            request.setAttribute(EXCEPTION, SC_NOT_FOUND);
            response.sendError(SC_NOT_FOUND);
        } else {
            if (user != null) {
                like = userDAO.checkLikedLinkMovieOfUser(user.getId(), movieId);
                grade = userDAO.checkVotedLinkMovieOfUser(user.getId(), movieId);
            }
            if (grade == null) {
                grade = 0;
            }
            if (like == null) {
                like = 0;
            }
            List<Genre> movieGenres = genreDAO.showGenresOfTheMovie(movie.getId(), LANGUAGE);
            List<Human> movieCrew = humanDAO.showMovieCrew(movie.getId(), LANGUAGE);
            movie.setGenres(movieGenres);
            movie.setMovieCrew(movieCrew);
            movie.setCountOfLikes(movieDAO.showCountOfLikesByMovieId(movie.getId()));
            movie.setRating(movieDAO.showRatingByMovieId(movie.getId()));
            request.setAttribute(MOVIE, movie);
            request.setAttribute(LIKED, like);
            request.setAttribute(GRADE, grade);
            saveCurrentPageURLToSession(request, response);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(MOVIE_JSP);
            requestDispatcher.forward(request, response);
        }
    }
}
