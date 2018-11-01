package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.database.UserDAO;
import com.epam.movie_warehouse.entity.User;
import com.epam.movie_warehouse.enumiration.UserRole;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;
import com.epam.movie_warehouse.exception.ValidationException;
import com.epam.movie_warehouse.util.MovieWarehouseConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

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
        User user = new User();
        String requestForward = LOG_OUT_URI;
        User authorizedUser = (User) request.getSession().getAttribute(AUTHORIZED_USER_ATTRIBUTE);
        String requestURI = request.getRequestURI();
        if ((requestURI.equalsIgnoreCase(DELETE_USER_URI)) && (authorizedUser.getRoleId().equals(UserRole.ADMIN))) {
            long userId = validateId(request.getParameter(USER_ID_ATTRIBUTE));
            user = userDAO.getUserById(userId);
            requestForward = LIST_USER_URI;
        } else if (checkPassword(request)) {
            user = authorizedUser;
        }
        if (user.getId() == 0) {
            request.setAttribute(EXCEPTION, SC_NOT_FOUND);
            response.sendError(SC_NOT_FOUND);
        } else {
            userDAO.deleteUserCompletely(user);
            USER_LOGGER.info("User was deleted " + authorizedUser);
            response.sendRedirect(requestForward);
        }
    }

    private Boolean checkPassword(HttpServletRequest request) {
        boolean isCheck = false;
        String password = request.getParameter(PASSWORD_ATTRIBUTE);
        String passwordRepeat = request.getParameter(PASSWORD_REPEAT_ATTRIBUTE);
        User user = (User) request.getSession().getAttribute(AUTHORIZED_USER_ATTRIBUTE);
        if ((password != null) && (!EMPTY_STRING.equals(password.trim())) && (passwordRepeat != null) &&
                (!EMPTY_STRING.equals(passwordRepeat.trim())) && (password.equals(passwordRepeat))) {
            isCheck = BCrypt.checkpw(password, user.getPassword());
        }
        return isCheck;
    }
}

