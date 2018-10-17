package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.dao.UserDAO;
import com.epam.movie_warehouse.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.LIST_USER_JSP;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.USERS;

public class ListUserService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        List<User> users  = userDAO.listUser();
        request.setAttribute(USERS, users);
        saveCurrentPageURLToSession(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(LIST_USER_JSP);
        requestDispatcher.forward(request, response);
    }
}
