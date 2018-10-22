package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.database.UserDAO;
import com.epam.movie_warehouse.entity.User;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;
import com.epam.movie_warehouse.exception.ValidationException;
import com.epam.movie_warehouse.util.MovieWarehouseConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.movie_warehouse.validator.AbstractValidator.validateId;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class DeleteUserService implements Service {
    private static final Logger USER_LOGGER = LogManager.getLogger(MovieWarehouseConstant.USER_LOGGER);
    private final UserDAO userDAO = new UserDAO();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException,
            ValidationException, ConnectionNotFoundException {
        User userById = new User();
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
        if (userById.getId() == 0) {
            request.setAttribute(EXCEPTION, SC_NOT_FOUND);
            response.sendError(SC_NOT_FOUND);
        } else {
            userDAO.deleteLinksMoviesOfUser(userById);
            userDAO.deleteUser(userById);
            USER_LOGGER.info("User was deleted " + user);
            response.sendRedirect(requestForward);
        }
    }

    private Boolean checkPassword(HttpServletRequest request) {
        boolean isCheck = false;
        String password = request.getParameter(PASSWORD);
        String passwordRepeat = request.getParameter(PASSWORD_REPEAT);
        User user = (User) request.getSession().getAttribute(AUTHORIZED_USER);
        if (((password != null) && (!password.trim().equals(EMPTY_STRING))) &&
                ((passwordRepeat != null) && (!passwordRepeat.trim().equals(EMPTY_STRING)))) {
            if (password.equals(passwordRepeat)) {
                if (user.getPassword().equals(password)) {
                    isCheck = true;
                }
            }
        }
        return isCheck;
    }
}

