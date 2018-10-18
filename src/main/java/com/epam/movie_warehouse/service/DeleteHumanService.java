package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.dao.HumanDAO;
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

public class DeleteHumanService implements Service {
    private static final Logger logger = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, ValidationException {
        final int LANGUAGE = getLanguageId(request,response);
        long humanId = validateId(request.getParameter(HUMAN_ID));
        HumanDAO humanDAO = new HumanDAO();
        if (humanDAO.showHumanById(humanId, LANGUAGE) == null){
            request.setAttribute(EXCEPTION, SC_NOT_FOUND);
            response.sendError(SC_NOT_FOUND);
        } else {
            if (!humanDAO.checkLinksHumanToMovie(humanId)) {
                humanDAO.deleteHumanCharacteristic(humanId);
                humanDAO.deleteHuman(humanId);
                logger.info("Human was deleted humanId = " +  humanId);
            } else {
                logger.info("Human has not been deleted humanId = " + humanId);
                request.setAttribute(EXCEPTION,YOU_HAVE_LINKS);
                throw new SQLException(YOU_HAVE_LINKS);
            }
            response.sendRedirect(LIST_HUMAN_ADMIN_URI);
        }
    }
}

