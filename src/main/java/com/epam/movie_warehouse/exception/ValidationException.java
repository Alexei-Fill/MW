package com.epam.movie_warehouse.exception;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.CURRENT_URL_ATTRIBUTE;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.EXCEPTION;

public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }

    public void showMessageHere(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentURL = (String) request.getSession().getAttribute(CURRENT_URL_ATTRIBUTE);
        request.setAttribute(EXCEPTION, getMessage());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(currentURL);
        requestDispatcher.forward(request, response);
    }
}
