package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.database.UserDAO;
import com.epam.movie_warehouse.entity.User;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;
import com.epam.movie_warehouse.exception.ValidationException;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.movie_warehouse.validator.UserValidator.*;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public class LoginUserService implements Service {
    private User user;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
            ValidationException, SQLException, ConnectionNotFoundException {
        String userLogin = validateLogin(request.getParameter(LOGIN));
        String userPassword = validatePassword(request.getParameter(PASSWORD));
        if (checkUserByLoginAndPassword(userLogin, userPassword)) {
            request.getSession().setAttribute(AUTHORIZED_USER, user);
            response.sendRedirect(LIST_MOVIES_URI);
        } else {
            request.setAttribute(EXCEPTION, INCORRECT_LOGIN_OR_PASSWORD);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(LOG_IN_JSP);
            requestDispatcher.forward(request, response);
        }
    }

    private Boolean checkUserByLoginAndPassword(String login, String password) throws SQLException, ConnectionNotFoundException {
        UserDAO userDAO = new UserDAO();
        boolean isCheck = false;
        user = userDAO.getUserByLogin(login);
        if (user.getId() != 0) {
            String userPassword = user.getPassword();
            if (userPassword == null || !userPassword.startsWith(PREFIX_FOR_PASSWORD)) {
                throw new IllegalArgumentException(INVALID_HASH_PROVIDED);
            }
            isCheck = BCrypt.checkpw(password, userPassword);
        }
        return isCheck;
    }
}

