package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.dao.GenreDAO;
import com.epam.movie_warehouse.entity.Genre;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.CURRENT_URL;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.DEFAULT_LANGUAGE;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.GENRES;

public class ListGenreService implements Service {
    GenreDAO genreDAO = new GenreDAO();
    List<Genre> genres;
    int languageId = DEFAULT_LANGUAGE;
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        languageId = getLanguageId(request,response);
        genres = listGenre();
        request.getServletContext().setAttribute(GENRES, genres);
        String requestURL = (String) request.getSession().getAttribute(CURRENT_URL);
        response.sendRedirect(requestURL);
    }

    public List<Genre> listGenre () throws SQLException {
        return genreDAO.showAllAvailableGenres(languageId);
    }
}

