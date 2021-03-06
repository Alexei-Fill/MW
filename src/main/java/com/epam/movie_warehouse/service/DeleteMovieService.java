package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.database.MovieDAO;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;
import com.epam.movie_warehouse.exception.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.movie_warehouse.validator.AbstractValidator.validateId;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.EXCEPTION;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.LIST_MOVIES_ADMIN_URI;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.MOVIE_ID_ATTRIBUTE;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class DeleteMovieService implements Service {
    private static final Logger ROOT_LOGGER = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException,
            ValidationException, ConnectionNotFoundException {
        final int LANGUAGE = getLanguageId(request, response);
        long movieId = validateId(request.getParameter(MOVIE_ID_ATTRIBUTE));
        MovieDAO movieDAO = new MovieDAO();
        if (movieDAO.getMovieById(movieId, LANGUAGE).getId() == 0) {
            request.setAttribute(EXCEPTION, SC_NOT_FOUND);
            response.sendError(SC_NOT_FOUND);
        } else {
            movieDAO.deleteMovie(movieId);
            ROOT_LOGGER.info("Movie was deleted movieId = " + movieId);
            response.sendRedirect(LIST_MOVIES_ADMIN_URI);
        }
    }
}

