package com.epam.movie_warehouse.service;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.LOG_IN_JSP;

public class ShowUserLoginService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        saveCurrentPageURLToSession(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(LOG_IN_JSP);
        requestDispatcher.forward(request, response);
    }
}
