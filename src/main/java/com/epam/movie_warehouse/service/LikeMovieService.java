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

public class LikeMovieService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException,
            IOException, ValidationException, ConnectionNotFoundException {
        UserDAO userDAO = new UserDAO();
        User user = (User) request.getSession().getAttribute(AUTHORIZED_USER_ATTRIBUTE);
        long movieId = validateId(request.getParameter(MOVIE_ID_ATTRIBUTE));
        int like = ITS_NOT_LIKED_VALUE;
        if ((user != null) && (movieId != 0)) {
            like = userDAO.checkMoviesLinksByLikedField(user.getId(), movieId);
            switch (like) {
                case (ITS_LIKED_VALUE): {
                    userDAO.updateMoviesLinksByLikedField(user.getId(), movieId, ITS_NOT_LIKED_VALUE);
                    like = ITS_NOT_LIKED_VALUE;
                    break;
                }
                case (ITS_NOT_LIKED_VALUE): {
                    userDAO.updateMoviesLinksByLikedField(user.getId(), movieId, ITS_LIKED_VALUE);
                    like = ITS_LIKED_VALUE;
                    break;
                }
                case (NO_ENTRY_EXISTS_VALUE):
                default: {
                    userDAO.addMoviesLinks(user.getId(), movieId);
                    userDAO.updateMoviesLinksByLikedField(user.getId(), movieId, ITS_LIKED_VALUE);
                    like = ITS_LIKED_VALUE;
                    break;
                }
            }
        }
        String requestURL = (String) request.getSession().getAttribute(CURRENT_URL_ATTRIBUTE);
        request.setAttribute(LIKED_ATTRIBUTE, like);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(requestURL);
        requestDispatcher.forward(request, response);
    }
}

