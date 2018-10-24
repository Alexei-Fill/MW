package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.database.GenreDAO;
import com.epam.movie_warehouse.entity.Genre;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;
import com.epam.movie_warehouse.exception.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.movie_warehouse.validator.MovieValidator.*;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public class EditOrUploadGenreService implements Service {
    private static volatile Long maxGenreId;
    private final GenreDAO GENRE_DAO = new GenreDAO();
    private static final Logger ROOT_LOGGER = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
            ValidationException, SQLException, ConnectionNotFoundException {
        final int LANGUAGE = getLanguageId(request, response);
        Genre genre = new Genre();
        long genreId = validateId(request.getParameter(GENRE_ID_ATTRIBUTE));
        if (genreId != 0) {
            genre = GENRE_DAO.getGenreById(genreId, LANGUAGE);
            genre.setName(validateName(request.getParameter(NAME_ATTRIBUTE)));
            GENRE_DAO.updateGenre(genre, LANGUAGE);
            ROOT_LOGGER.info("Genre was changed " + genre);
        } else {
            getMaxGenreId();
            genre.setId(maxGenreId);
            addGenre(request, response, genre);
            ROOT_LOGGER.info("Genre was added " + genre);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(LIST_GENRE_URI);
        requestDispatcher.forward(request, response);
    }

    private void addGenre(HttpServletRequest request, HttpServletResponse response, Genre genre) throws ValidationException,
            ConnectionNotFoundException, SQLException {
        String[] languageIdValue = request.getParameterValues(CHARACTERISTIC_LANGUAGE_ID);
        if (languageIdValue != null) {
            for (String s : languageIdValue) {
                int languageId = Integer.parseInt(s.trim());
                genre.setName(validateName(request.getParameter(NAME_ATTRIBUTE + languageId)));
                GENRE_DAO.addGenre(genre, languageId);
            }
        }
    }

    private void getMaxGenreId() throws SQLException, ConnectionNotFoundException {
        maxGenreId = GENRE_DAO.getMaxGenreId();
    }
}
