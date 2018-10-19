package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.dao.UserDAO;
import com.epam.movie_warehouse.entity.User;
import com.epam.movie_warehouse.exception.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.movie_warehouse.validator.AbstractValidator.validateId;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class DeleteUserService implements Service {
    private static final Logger userLogger = LogManager.getLogger(USER_LOGGER);
    private UserDAO userDAO = new UserDAO();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, ValidationException {
        User userById = null;
        String requestForward = LOG_OUT_URI;
        User user = (User) request.getSession().getAttribute(AUTHORIZED_USER);
        String requestURI = request.getRequestURI();
        if ((requestURI.equalsIgnoreCase(DELETE_USER_URI)) && (user.getRoleId() == ADMIN_ROLE_ID)) {
            long userId = validateId(request.getParameter(USER_ID));
            userById = userDAO.showUserById(userId);
            requestForward = LIST_USER_URI;
        } else {
            if (checkPassword(request)) {
                userById = userDAO.showUserById(user.getId());
            }
        }
        if (userById == null){
            request.setAttribute(EXCEPTION, SC_NOT_FOUND);
            response.sendError(SC_NOT_FOUND);
        } else {
            userDAO.deleteLinksMoviesOfUser(userById);
            userDAO.deleteUser(userById);
            userLogger.info("User was deleted " + user);
            response.sendRedirect(requestForward);
        }
    }

    private Boolean checkPassword(HttpServletRequest request) {
        boolean isCheck = false;
        String password = request.getParameter(PASSWORD);
        String passwordRepeat = request.getParameter(PASSWORD_REPEAT);
        User user = (User) request.getSession().getAttribute(AUTHORIZED_USER);
        if (((password != null) && (!password.trim().equals(EMPTY_STRING))) &&
                ((passwordRepeat != null) && (!passwordRepeat.trim().equals(EMPTY_STRING)))){
            if (password.equals(passwordRepeat)){
                if (user.getPassword().equals(password)) {
                    isCheck = true;
                }
            }
        }
        return isCheck;
    }
}

