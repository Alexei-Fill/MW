package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.database.UserDAO;
import com.epam.movie_warehouse.entity.User;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.LIST_USER_JSP;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.USERS_ATTRIBUTE;

public class ListUserService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
            SQLException, ConnectionNotFoundException {
        UserDAO userDAO = new UserDAO();
        List<User> userList = userDAO.listUser();
        request.setAttribute(USERS_ATTRIBUTE, userList);
        writeCurrentPageToSession(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(LIST_USER_JSP);
        requestDispatcher.forward(request, response);
    }
}
