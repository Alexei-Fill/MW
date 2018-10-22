package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.database.GenreDAO;
import com.epam.movie_warehouse.entity.Genre;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.CURRENT_URL;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.DEFAULT_LANGUAGE;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.GENRES;

public class ListGenreService implements Service {
    private final GenreDAO genreDAO = new GenreDAO();
    private int languageId = DEFAULT_LANGUAGE;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException,
            ConnectionNotFoundException {
        languageId = getLanguageId(request, response);
        List<Genre> genres = listGenre();
        request.getServletContext().setAttribute(GENRES, genres);
        String requestURL = (String) request.getSession().getAttribute(CURRENT_URL);
        response.sendRedirect(requestURL);
    }

    public List<Genre> listGenre() throws SQLException, ConnectionNotFoundException {
        return genreDAO.showAllAvailableGenres(languageId);
    }
}

