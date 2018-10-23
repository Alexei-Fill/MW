package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.database.UserDAO;
import com.epam.movie_warehouse.entity.Language;
import com.epam.movie_warehouse.entity.User;
import com.epam.movie_warehouse.enumiration.UserRole;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;
import com.epam.movie_warehouse.exception.ValidationException;
import com.epam.movie_warehouse.util.MovieWarehouseConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.movie_warehouse.validator.UserValidator.*;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class EditUserService implements Service {
    private static final Logger USER_LOGGER = LogManager.getLogger(MovieWarehouseConstant.USER_LOGGER);
    private final UserDAO userDAO = new UserDAO();
    private User user;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException,
            IOException, ValidationException, ConnectionNotFoundException {
        final Language language = getLanguage(request, response);
        long userId = validateId(request.getParameter(USER_ID));
        user = userDAO.getUserById(userId);
        if (user.getId() == 0) {
            request.setAttribute(EXCEPTION, SC_NOT_FOUND);
            response.sendError(SC_NOT_FOUND);
        } else {
            String requestURI = request.getRequestURI();
            if (requestURI.equalsIgnoreCase(EDIT_USER_URI)) {
                user.setMail(validateMail(request.getParameter(MAIL)));
                user.setBirthDate(validateBirthDate(request.getParameter(BIRTH_DATE), language));
                user.setImageURL(request.getParameter(IMG_URL));
                user.setRoleId(UserRole.getUserRole(validateRoleId(request.getParameter(USER_ROLE_ID))));
            } else if (requestURI.equalsIgnoreCase(EDIT_USER_PASSWORD_URI)) {
                if (checkOldUserPassword(request, response) && checkNewPasswordAndRepeat(request, response)) {
                    user.setPassword(hashingPassword(request.getParameter(NEW_PASSWORD)));
                }
            }
            userDAO.updateUser(user);
            USER_LOGGER.info("User was changed " + user);
            request.setAttribute(USER, user);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(EDIT_USER_JSP);
            requestDispatcher.forward(request, response);
        }
    }

    private Boolean checkOldUserPassword(HttpServletRequest request, HttpServletResponse response) throws ValidationException {
        boolean check;
        String oldPassword = validatePassword(request.getParameter(OLD_PASSWORD));
        String userPassword = user.getPassword();
        if (userPassword == null || !userPassword.startsWith(PREFIX_FOR_PASSWORD)) {
            throw new IllegalArgumentException(INVALID_HASH_PROVIDED);
        }
        check = BCrypt.checkpw(oldPassword, userPassword);
        return check;
    }

    private Boolean checkNewPasswordAndRepeat(HttpServletRequest request, HttpServletResponse response) throws ValidationException {
        boolean check = false;
        String newPassword = validateLogin(request.getParameter(NEW_PASSWORD));
        String newPasswordRepeat = validateLogin(request.getParameter(NEW_PASSWORD_REPEAT));
        if (newPassword.equalsIgnoreCase(newPasswordRepeat)) {
            check = true;
        }
        return check;
    }

    private String hashingPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(SALT_FOR_PASSWORD);
        return BCrypt.hashpw(password_plaintext, salt);
    }
}
