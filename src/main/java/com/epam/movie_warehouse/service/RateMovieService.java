package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.database.UserDAO;
import com.epam.movie_warehouse.entity.User;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;
import com.epam.movie_warehouse.exception.ValidationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.movie_warehouse.validator.AbstractValidator.validateId;
import static com.epam.movie_warehouse.validator.MovieValidator.validateGrade;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public class RateMovieService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException,
            IOException, ValidationException, ConnectionNotFoundException {
        UserDAO userDAO = new UserDAO();
        User user = (User) request.getSession().getAttribute(AUTHORIZED_USER_ATTRIBUTE);
        long movieId = validateId(request.getParameter(MOVIE_ID_ATTRIBUTE));
        int grade = validateGrade(request.getParameter(GRADE_ATTRIBUTE));
        Integer movieGrade;
        if ((user != null) && (movieId != 0)) {
            movieGrade = userDAO.checkMoviesLinksByVotedField(user.getId(), movieId);
            if (movieGrade == NO_ENTRY_EXISTS_VALUE) {
                userDAO.addMoviesVotedLinks(user.getId(), movieId, grade);
            } else if (grade != movieGrade) {
                userDAO.updateMoviesLinksByVotedField(user.getId(), movieId, grade);
            }
        }
        String requestURL = (String) request.getSession().getAttribute(CURRENT_URL_ATTRIBUTE);
        request.setAttribute(GRADE_ATTRIBUTE, grade);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(requestURL);
        requestDispatcher.forward(request, response);
    }
}

