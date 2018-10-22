package com.epam.movie_warehouse.exception;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.CONNECTION_NOT_FOUND_EXCEPTION;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.CURRENT_URL;

public class ConnectionNotFoundException extends Exception {
    public ConnectionNotFoundException() {
        super(CONNECTION_NOT_FOUND_EXCEPTION);
    }
}
