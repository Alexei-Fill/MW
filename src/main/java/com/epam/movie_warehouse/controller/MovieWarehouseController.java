package com.epam.movie_warehouse.controller;

import com.epam.movie_warehouse.exception.ValidationException;
import com.epam.movie_warehouse.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


public class MovieWarehouseController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger ROOT_LOGGER = LogManager.getRootLogger();

    public MovieWarehouseController() {
        super();
    }

    @Override
    public void init() throws ServletException {
        super.init();
        ROOT_LOGGER.info("Servlet started");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String serviceRequest = request.getRequestURI();
        ServiceFactory factory = ServiceFactory.getInstance();
        Service service = factory.getService(serviceRequest);
        try {
            service.execute(request, response);
        } catch (SQLException | NumberFormatException e) {
            ROOT_LOGGER.error(e);
            throw new ServletException(e);
        } catch (ValidationException e) {
            ROOT_LOGGER.error(e);
            e.showMessageHere(request, response);
        } catch (Exception e) {
            ROOT_LOGGER.error(e);
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
