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
        User user;
        User authorizedUser = (User) request.getSession().getAttribute(AUTHORIZED_USER_ATTRIBUTE);
        String requestURI = request.getRequestURI();
        switch (requestURI) {
            case (SHOW_USER_URI): {
                long userId = validateId(request.getParameter(USER_ID_ATTRIBUTE));
                user = userDAO.getUserById(userId);
                break;
            }
            case (SHOW_MY_USER_URI):
            default: {
                user = userDAO.getUserById(authorizedUser.getId());
                break;
            }
        }
        if (user.getId() == 0) {
            request.setAttribute(EXCEPTION, SC_NOT_FOUND);
            response.sendError(SC_NOT_FOUND);
        } else {
            writeCurrentPageToSession(request, response);
            request.setAttribute(USER_ATTRIBUTE, user);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(USER_JSP);
            requestDispatcher.forward(request, response);
        }
    }
}
