package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.dao.GenreDAO;
import com.epam.movie_warehouse.dao.HumanDAO;
import com.epam.movie_warehouse.dao.MovieDAO;
import com.epam.movie_warehouse.entity.Genre;
import com.epam.movie_warehouse.entity.Human;
import com.epam.movie_warehouse.entity.Movie;
import com.epam.movie_warehouse.exception.ValidationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.epam.movie_warehouse.validator.AbstractValidator.validateId;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public class ShowMovieByGenreService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, ValidationException {
        final int LANGUAGE = getLanguageId(request,response);
        long genreId = validateId(request.getParameter(GENRE_ID));
        MovieDAO movieDAO = new MovieDAO();
        GenreDAO genreDAO = new GenreDAO();
        HumanDAO humanDAO = new HumanDAO();
        List<Movie> movies = movieDAO.listMovieByGenre(genreId, LANGUAGE);
        for (Movie movie:movies){
            List<Genre> movieGenres = genreDAO.showGenresOfTheMovie(movie.getId(), LANGUAGE);
            List<Human> movieCrew = humanDAO.showMovieCrew(movie.getId(), LANGUAGE);
            movie.setGenres(movieGenres);
            movie.setMovieCrew(movieCrew);
            movie.setCountOfLikes(movieDAO.showCountOfLikesByMovieId(movie.getId()));
            movie.setRating(movieDAO.showRatingByMovieId(movie.getId()));
        }
        request.setAttribute(MOVIES, movies);
        if (movies.isEmpty()){
            request.setAttribute(HIDDEN_MESSAGE, SHOW_HIDDEN_MESSAGE);
        }
        String requestDispatch = LIST_MOVIE_JSP;
        String requestURL = (String) request.getSession().getAttribute(CURRENT_URL);
        if(requestURL.equalsIgnoreCase(LIST_MOVIES_ADMIN_URI)) {
            requestDispatch = LIST_MOVIES_ADMIN_URI;
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(requestDispatch);
        requestDispatcher.forward(request, response);
    }
}
