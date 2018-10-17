package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.dao.GenreDAO;
import com.epam.movie_warehouse.entity.Genre;
import com.epam.movie_warehouse.exception.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.movie_warehouse.Validator.MovieValidator.*;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public class AddEditGenreService implements Service {
    private static volatile Long maxGenreId;
    private GenreDAO genreDAO = new GenreDAO();
    private static final Logger logger = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException, ValidationException, SQLException {
        final int LANGUAGE = getLanguageId(request,response);
        Genre genre = new Genre();
        long genreId = validateId(request.getParameter(GENRE_ID));
        if (genreId != 0) {
            genre = genreDAO.showGenreById(genreId, LANGUAGE);
            genre.setName(validateName(request.getParameter(NAME)));
            genreDAO.updateGenre(genre, LANGUAGE);
            logger.info("Genre was changed " + genre);
        } else {
            getMaxGenreId();
            genre.setId(maxGenreId);
            String[] languages = request.getParameterValues(CHARACTERISTIC_LANGUAGE_ID);
            if (languages != null) {
                for (String s : languages) {
                    int languageId = Integer.parseInt(s.trim());
                    genre.setName(validateName(request.getParameter(NAME + languageId)));
                    genreDAO.addGenre(genre, languageId);
                }
            }
            logger.info("Genre was added " + genre);
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(LIST_GENRE_URI);
        requestDispatcher.forward(request, response);
    }

   private void getMaxGenreId() throws SQLException {
        maxGenreId = genreDAO.getMAXGenreId();
   }
}
