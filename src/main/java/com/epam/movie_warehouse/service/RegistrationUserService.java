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

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException,
            IOException, ValidationException, ConnectionNotFoundException {
        Language language = getLanguage(request, response);
        UserDAO userDAO = new UserDAO();
        User user = new User();
        String password = validatePassword(request.getParameter(PASSWORD_ATTRIBUTE));
        String passwordRepeat = validatePassword(request.getParameter(PASSWORD_REPEAT_ATTRIBUTE));
        user.setLogin(validateLogin(request.getParameter(LOGIN_ATTRIBUTE)));
        user.setMail(validateMail(request.getParameter(MAIL_ATTRIBUTE)));
        user.setBirthDate(validateBirthDate(request.getParameter(BIRTH_DATE_ATTRIBUTE), language));
        user.setRegistrationDate(LocalDate.now(ZoneId.of(DEFAULT_TIME_ZONE)));
        user.setRoleId(UserRole.USER);
        String requestDispatch = LOG_IN_URI;
        if (checkPasswordAndPasswordRepeat(password, passwordRepeat)) {
            user.setPassword(hashingPassword(password));
            userDAO.addUser(user);
            requestDispatch = AUTHORIZATION_URI;
            USER_LOGGER.info("Registration completed successfully user =" + user);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(requestDispatch);
        requestDispatcher.forward(request, response);
    }

    private Boolean checkPasswordAndPasswordRepeat(String password, String passwordRepeat) {
        boolean isCheck = false;
        if (password != null && !EMPTY_STRING.equals(password.trim()) &&
                passwordRepeat != null && !EMPTY_STRING.equals(passwordRepeat.trim()) && password.equals(passwordRepeat)) {
            isCheck = true;
        }
        return isCheck;
    }

    private String hashingPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(SALT_FOR_PASSWORD);
        return BCrypt.hashpw(password_plaintext, salt);
    }
}

