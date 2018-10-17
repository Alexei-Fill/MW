package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.dao.GenreDAO;
import com.epam.movie_warehouse.dao.HumanDAO;
import com.epam.movie_warehouse.dao.MovieDAO;
import com.epam.movie_warehouse.entity.Genre;
import com.epam.movie_warehouse.entity.Human;
import com.epam.movie_warehouse.entity.Movie;

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
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            SQLException, IOException {
        final int LANGUAGE = getLanguageId(request,response);
        MovieDAO movieDAO = new MovieDAO();
        GenreDAO genreDAO = new GenreDAO();
        HumanDAO humanDAO = new HumanDAO();
        List<Movie> movies = movieDAO.listMovie(LANGUAGE);
        for (Movie movie:movies){
            List<Genre> movieGenres = genreDAO.showGenresOfTheMovie(movie.getId(), LANGUAGE);
            movie.setGenres(movieGenres);
            List<Human> movieCrew = humanDAO.showMovieCrew(movie.getId(), LANGUAGE);
            movie.setMovieCrew(movieCrew);
            movie.setCountOfLikes(movieDAO.showCountOfLikesByMovieId(movie.getId()));
            movie.setRating(movieDAO.showRatingByMovieId(movie.getId()));
        }
        request.setAttribute(MOVIES, movies);
        String requestDispatch = LIST_MOVIE_JSP;
        if (request.getRequestURI().equalsIgnoreCase(LIST_MOVIES_ADMIN_URI)){
            requestDispatch = LIST_MOVIE_ADMIN_JSP;
        }
        saveCurrentPageURLToSession(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(requestDispatch);
        requestDispatcher.forward(request, response);
    }
}
