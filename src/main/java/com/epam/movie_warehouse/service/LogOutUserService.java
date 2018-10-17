package com.epam.movie_warehouse.service;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.LOG_IN_URI;

public class LogOutUserService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        request.getSession().invalidate();
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(LOG_IN_URI);
        requestDispatcher.forward(request, response);
    }
}

