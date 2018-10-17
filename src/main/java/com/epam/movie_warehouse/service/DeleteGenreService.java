package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.dao.GenreDAO;
import com.epam.movie_warehouse.exception.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.movie_warehouse.Validator.AbstractValidator.validateId;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class DeleteGenreService implements Service {
    private static final Logger logger = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, ValidationException {
        final int LANGUAGE = getLanguageId(request,response);
        long genreId = validateId(request.getParameter(GENRE_ID));
        GenreDAO genreDAO = new GenreDAO();
        if (genreDAO.showGenreById(genreId, LANGUAGE) == null){
            request.setAttribute(EXCEPTION, SC_NOT_FOUND);
            response.sendError(SC_NOT_FOUND);
        } else {
            if (!genreDAO.checkLinksGenreToMovie(genreId)) {
                genreDAO.deleteGenre(genreId);
                logger.info("Genre was deleted  genreId =" +  genreId);
                response.sendRedirect(LIST_GENRE_URI);

            }else {
                logger.info("Genre has not been deleted genreId = " +  genreId);
                throw new SQLException(YOU_HAVE_LINKS);
            }
        }
    }
}

