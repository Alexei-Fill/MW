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
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class ShowUserService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
            ValidationException, SQLException, ConnectionNotFoundException {
        UserDAO userDAO = new UserDAO();
        User userById = new User();
        User user = (User) request.getSession().getAttribute(AUTHORIZED_USER);
        String requestURI = request.getRequestURI();
        if (requestURI.equalsIgnoreCase(SHOW_USER_URI)) {
            long userId = validateId(request.getParameter(USER_ID));
            userById = userDAO.getUserById(userId);
        } else if (requestURI.equalsIgnoreCase(SHOW_MY_USER_URI)) {
            userById = userDAO.getUserById(user.getId());
        }
        if (userById.getId() == 0) {
            request.setAttribute(EXCEPTION, SC_NOT_FOUND);
            response.sendError(SC_NOT_FOUND);
        } else {
            saveCurrentPageURLToSession(request, response);
            request.setAttribute(USER, userById);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(USER_JSP);
            requestDispatcher.forward(request, response);
        }
    }
}
