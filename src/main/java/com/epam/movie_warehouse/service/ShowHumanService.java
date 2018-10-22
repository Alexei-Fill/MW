package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.database.HumanDAO;
import com.epam.movie_warehouse.database.MovieDAO;
import com.epam.movie_warehouse.entity.Human;
import com.epam.movie_warehouse.entity.Movie;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;
import com.epam.movie_warehouse.exception.ValidationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;
import static com.epam.movie_warehouse.validator.AbstractValidator.validateId;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class ShowHumanService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
            SQLException, ValidationException, ConnectionNotFoundException {
        final int LANGUAGE = getLanguageId(request, response);
        long humanId = validateId(request.getParameter(HUMAN_ID));
        HumanDAO humanDAO = new HumanDAO();
        MovieDAO movieDAO = new MovieDAO();
        Human human = humanDAO.showHumanById(humanId, LANGUAGE);
        if (human.getId() == 0) {
            request.setAttribute(EXCEPTION, SC_NOT_FOUND);
            response.sendError(SC_NOT_FOUND);
        } else {
            List<Movie> movies = movieDAO.listMovieByHuman(humanId, LANGUAGE);
            request.setAttribute(HUMAN, human);
            request.setAttribute(MOVIES, movies);
            saveCurrentPageURLToSession(request, response);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(HUMAN_JSP);
            requestDispatcher.forward(request, response);
        }
    }

}
