package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.database.HumanDAO;
import com.epam.movie_warehouse.entity.Human;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public class ListHumanService implements Service {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException,
            IOException, ConnectionNotFoundException {
        final int LANGUAGE = getLanguageId(request, response);
        HumanDAO humanDAO = new HumanDAO();
        List<Human> humans = humanDAO.listHuman(LANGUAGE);
        request.setAttribute(HUMANS, humans);
        String serviceRequest = request.getRequestURI();
        String requestDispatch = LIST_HUMAN_JSP;
        if (serviceRequest.equalsIgnoreCase(LIST_HUMAN_ADMIN_URI)) {
            requestDispatch = LIST_HUMAN_ADMIN_JSP;
        }
        saveCurrentPageURLToSession(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(requestDispatch);
        requestDispatcher.forward(request, response);
    }
}
