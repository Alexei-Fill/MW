package com.epam.movie_warehouse.controllerr;

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


public class MovieController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getRootLogger();
    public MovieController() {
        super();
    }

    @Override
    public void init() throws ServletException {
        super.init();
        logger.info("Servlet started");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String serviceRequest = request.getRequestURI();
        ServiceFactory factory = ServiceFactory.getInstance();
        Service service = factory.getService(serviceRequest);
        try {
            service.execute(request, response);
        }catch (SQLException | NumberFormatException e){
            logger.error(e);
            throw new ServletException(e);
        }
        catch (ValidationException e) {
            logger.error(e);
            e.showMessageHere(request,response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
