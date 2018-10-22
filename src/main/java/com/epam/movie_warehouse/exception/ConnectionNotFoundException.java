package com.epam.movie_warehouse.exception;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.CONNECTION_NOT_FOUND_EXCEPTION;

public class ConnectionNotFoundException extends Exception {
    public ConnectionNotFoundException() {
        super(CONNECTION_NOT_FOUND_EXCEPTION);
    }
}
