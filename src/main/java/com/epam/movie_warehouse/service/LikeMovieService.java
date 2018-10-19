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

import static com.epam.movie_warehouse.validator.AbstractValidator.validateId;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public class LikeMovieService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, ValidationException {
        UserDAO userDAO = new UserDAO();
        User user =  (User) request.getSession().getAttribute(AUTHORIZED_USER);
        long movieId = validateId(request.getParameter(MOVIE_ID));
        Integer like = null;
        if ((user != null) && (movieId !=0)){
            like = userDAO.checkLikedLinkMovieOfUser(user.getId(), movieId);
            if (like == null){
                userDAO.addLinksMoviesOfUser(user.getId(), movieId);
                userDAO.updateLikedLinkMovieOfUser(user.getId(), movieId, itsLiked);
                like = itsLiked;
            } else if (like == itsLiked) {
                userDAO.updateLikedLinkMovieOfUser(user.getId(), movieId, itsNotLiked);
                like = itsNotLiked;
            } else if (like == itsNotLiked) {
                userDAO.updateLikedLinkMovieOfUser(user.getId(), movieId, itsLiked);
                like = itsLiked;
            }
        }
        String requestURL = (String) request.getSession().getAttribute(CURRENT_URL);
        request.setAttribute(LIKED, like);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(requestURL);
        requestDispatcher.forward(request, response);
    }
}

