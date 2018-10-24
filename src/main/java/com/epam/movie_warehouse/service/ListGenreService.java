package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.database.GenreDAO;
import com.epam.movie_warehouse.entity.Genre;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.CURRENT_URL_ATTRIBUTE;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.DEFAULT_LANGUAGE;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.GENRES_ATTRIBUTE;

public class ListGenreService implements Service {
    private int languageId = DEFAULT_LANGUAGE;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException,
            ConnectionNotFoundException {
        languageId = getLanguageId(request, response);
        List<Genre> genreList = listGenre();
        request.getServletContext().setAttribute(GENRES_ATTRIBUTE, genreList);
        String requestURL = (String) request.getSession().getAttribute(CURRENT_URL_ATTRIBUTE);
        response.sendRedirect(requestURL);
    }

    public List<Genre> listGenre() throws SQLException, ConnectionNotFoundException {
        GenreDAO genreDAO = new GenreDAO();
        return genreDAO.listGenres(languageId);
    }
}

