package com.epam.movie_warehouse.exception;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.CURRENT_URL;

public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }

    public void showMessageHere(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURL = (String) request.getSession().getAttribute(CURRENT_URL);
        request.setAttribute("exception", getMessage());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(requestURL);
        requestDispatcher.forward(request, response);
    }
}
