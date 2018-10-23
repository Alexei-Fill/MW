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
import java.time.LocalDate;
import java.time.ZoneId;

import static com.epam.movie_warehouse.validator.UserValidator.*;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public class RegistrationUserService implements Service {
    private static final Logger USER_LOGGER = LogManager.getLogger(MovieWarehouseConstant.USER_LOGGER);
    private final UserDAO userDAO = new UserDAO();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException,
            IOException, ValidationException, ConnectionNotFoundException {
        Language language = getLanguage(request, response);
        User newUser = new User();
        newUser.setLogin(validateLogin(request.getParameter(LOGIN_ATTRIBUTE)));
        String password = validatePassword(request.getParameter(PASSWORD_ATTRIBUTE));
        String passwordRepeat = validatePassword(request.getParameter(PASSWORD_REPEAT_ATTRIBUTE));
        newUser.setMail(validateMail(request.getParameter(MAIL_ATTRIBUTE)));
        newUser.setBirthDate(validateBirthDate(request.getParameter(BIRTH_DATE_ATTRIBUTE), language));
        newUser.setRegistrationDate(LocalDate.now(ZoneId.of(DEFAULT_TIME_ZONE)));
        newUser.setRoleId(UserRole.USER);
        String requestDispatch = LOG_IN_URI;
        if (checkPasswordAndPasswordRepeat(password, passwordRepeat)) {
            newUser.setPassword(hashingPassword(password));
            userDAO.addUser(newUser);
            requestDispatch = AUTHORIZATION_URI;
            USER_LOGGER.info("Registration completed successfully user =" + newUser.getLogin());
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(requestDispatch);
        requestDispatcher.forward(request, response);
    }

    private Boolean checkPasswordAndPasswordRepeat(String password, String passwordRepeat) {
        boolean isCheck = false;
        if (((password != null) && (!EMPTY_STRING.equals(password.trim()))) &&
                ((passwordRepeat != null) && (!EMPTY_STRING.equals(passwordRepeat.trim())))) {
            if (password.equals(passwordRepeat)) {
                isCheck = true;
            }
        }
        return isCheck;
    }

    private String hashingPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(SALT_FOR_PASSWORD);
        return BCrypt.hashpw(password_plaintext, salt);
    }
}

