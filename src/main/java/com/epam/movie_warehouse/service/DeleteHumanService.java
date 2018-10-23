package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.database.HumanDAO;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;
import com.epam.movie_warehouse.exception.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.movie_warehouse.validator.AbstractValidator.validateId;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class DeleteHumanService implements Service {
    private static final Logger ROOT_LOGGER = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException,
            ValidationException, ConnectionNotFoundException {
        final int LANGUAGE = getLanguageId(request, response);
        long humanId = validateId(request.getParameter(HUMAN_ID_ATTRIBUTE));
        HumanDAO humanDAO = new HumanDAO();
        if (humanDAO.getHumanById(humanId, LANGUAGE).getId() == 0) {
            request.setAttribute(EXCEPTION, SC_NOT_FOUND);
            response.sendError(SC_NOT_FOUND);
        } else {
            if (!humanDAO.checkForMovieLinks(humanId)) {
                humanDAO.deleteHumanMultiLanguageParameters(humanId);
                humanDAO.deleteHuman(humanId);
                ROOT_LOGGER.info("Human was deleted humanId = " + humanId);
            } else {
                ROOT_LOGGER.info("Human has not been deleted humanId = " + humanId);
                request.setAttribute(EXCEPTION, YOU_HAVE_LINKS);
                throw new SQLException(YOU_HAVE_LINKS);
            }
            response.sendRedirect(LIST_HUMAN_ADMIN_URI);
        }
    }
}

