package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.dao.UserDAO;
import com.epam.movie_warehouse.entity.User;
import com.epam.movie_warehouse.exception.ValidationException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.movie_warehouse.Validator.AbstractValidator.validateId;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class ShowEditUserService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException,
            ServletException, IOException, ValidationException {
        UserDAO userDAO = new UserDAO();
        long userId = validateId(request.getParameter(USER_ID));
        User user = userDAO.showUserById(userId);
        if (user == null){
            request.setAttribute(EXCEPTION, SC_NOT_FOUND);
            response.sendError(SC_NOT_FOUND);
        } else {
            request.setAttribute(USER, user);
            saveCurrentPageURLToSession(request, response);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(EDIT_USER_JSP);
            requestDispatcher.forward(request, response);
        }
    }
}
