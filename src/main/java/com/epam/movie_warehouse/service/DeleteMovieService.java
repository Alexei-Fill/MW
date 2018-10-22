package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.dao.MovieDAO;
import com.epam.movie_warehouse.exception.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.movie_warehouse.validator.AbstractValidator.validateId;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.EXCEPTION;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.LIST_MOVIES_ADMIN_URI;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.MOVIE_ID;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class DeleteMovieService implements Service {
    private static final Logger logger = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, ValidationException {
        final int LANGUAGE = getLanguageId(request,response);
        long movieId = validateId(request.getParameter(MOVIE_ID));
        MovieDAO movieDAO = new MovieDAO();
        if (movieDAO.showMovieById(movieId, LANGUAGE).getId() == 0) {
            request.setAttribute(EXCEPTION, SC_NOT_FOUND);
            response.sendError(SC_NOT_FOUND);
        } else {
            movieDAO.deleteLinksHumansOnMovie(movieId);
            movieDAO.deleteLinksGenresOnMovie(movieId);
            movieDAO.deleteCharacteristicsOfMovie(movieId);
            movieDAO.deleteLinksUsersOnMovie(movieId);
            movieDAO.deleteMovie(movieId);
            logger.info("Movie was deleted movieId = " + movieId);
            response.sendRedirect(LIST_MOVIES_ADMIN_URI);
        }
    }
}

