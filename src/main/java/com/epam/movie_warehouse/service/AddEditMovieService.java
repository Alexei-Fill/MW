package com.epam.movie_warehouse.service;

import com.epam.movie_warehouse.database.GenreDAO;
import com.epam.movie_warehouse.database.HumanDAO;
import com.epam.movie_warehouse.database.MovieDAO;
import com.epam.movie_warehouse.entity.Genre;
import com.epam.movie_warehouse.entity.Human;
import com.epam.movie_warehouse.entity.Language;
import com.epam.movie_warehouse.entity.Movie;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.epam.movie_warehouse.validator.MovieValidator.*;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public class AddEditMovieService implements Service {
    private static final Logger ROOT_LOGGER = LogManager.getRootLogger();
    private List<Human> humans;
    private final HumanDAO humanDAO = new HumanDAO();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
            ValidationException, SQLException, ConnectionNotFoundException {
        final Language language = getLanguage(request, response);
        final int LANGUAGE_ID = language.getId();
        MovieDAO movieDAO = new MovieDAO();
        GenreDAO genreDAO = new GenreDAO();
        Movie movie = new Movie();
        Genre genre;
        humans = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();
        long movieId = validateId(request.getParameter(MOVIE_ID_ATTRIBUTE));
        if (movieId != 0) {
            movie = movieDAO.getMovieById(movieId, LANGUAGE_ID);
        }
        movie.setImageURL(request.getParameter(IMG_URL_ATTRIBUTE));
        movie.setImdbID(validateImdbId(request.getParameter(IMDB_ID_ATTRIBUTE)));
        movie.setBudget(validateBudget(request.getParameter(BUDGET_ATTRIBUTE)));
        movie.setDues(validateDues(request.getParameter(DUES_ATTRIBUTE)));
        movie.setAgeLimit(validateAgeLimit(request.getParameter(AGE_LIMIT_ATTRIBUTE)));
        movie.setDuration(validateDuration(request.getParameter(DURATION_ATTRIBUTE)));
        movie.setReleaseDate(validateReleaseDate(request.getParameter(RELEASE_DATE_ATTRIBUTE), language));
        String[] genreIdString = request.getParameterValues(GENRE_ATTRIBUTE);
        if (genreIdString != null) {
            for (String s : genreIdString) {
                long genreId = Long.parseLong(s.trim());
                genre = genreDAO.getGenreById(genreId, LANGUAGE_ID);
                genres.add(genre);
            }
        }
        movie.setGenres(genres);
        setMovieCrew(request, ACTOR_ATTRIBUTE, ACTOR_ROLE_ID, LANGUAGE_ID);
        setMovieCrew(request, DIRECTOR_ATTRIBUTE, DIRECTOR_ROLE_ID, LANGUAGE_ID);
        setMovieCrew(request, SCREENWRITER_ATTRIBUTE, SCREENWRITER_ROLE_ID, LANGUAGE_ID);
        movie.setMovieCrew(humans);
        if (movieId != 0) {
            String[] languages = request.getParameterValues(CHARACTERISTIC_LANGUAGE_ID);
            if (languages != null) {
                for (String s : languages) {
                    int languageId = Integer.parseInt(s.trim());
                    movie = setMultiLanguageParameters(request, movie, languageId);
                }
            }
            movieDAO.updateMovie(movie, LANGUAGE_ID);
            movieDAO.deleteGenresLinks(movie.getId());
            movieDAO.deleteHumansLinks(movie.getId());
            movieDAO.addGenresLinks(movie);
            movieDAO.addHumansLinks(movie);
            ROOT_LOGGER.info("Movie was changed " + movie);
        } else {
            movie.setUploadDate(LocalDate.now(ZoneId.of(DEFAULT_TIME_ZONE)));
            movieDAO.addMovie(movie);
            movieDAO.addGenresLinks(movie);
            movieDAO.addHumansLinks(movie);
            String[] languages = request.getParameterValues(CHARACTERISTIC_LANGUAGE_ID);
            if (languages != null) {
                for (String s : languages) {
                    int languageId = Integer.parseInt(s.trim());
                    movie = setMultiLanguageParameters(request, movie, languageId);
                    movieDAO.addMovieMultiLanguageParameters(movie, languageId);
                }
            }
            ROOT_LOGGER.info("Movie was added " + movie);
        }
        request.setAttribute(MOVIE_ATTRIBUTE, movie);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(SHOW_MOVIE_BY_ID_URL + movie.getId());
        requestDispatcher.forward(request, response);
    }

    private void setMovieCrew(HttpServletRequest request, String parameterName, int roleId, int languageId)
            throws SQLException, ConnectionNotFoundException  {
        Human human;
        String[] movieCrew = request.getParameterValues(parameterName);
        if (movieCrew != null) {
            for (String s : movieCrew) {
                long humanId = Long.parseLong(s.trim());
                human = humanDAO.getHumanById(humanId, languageId);
                human.setRoleId(roleId);
                humans.add(human);
            }
        }
    }

    private Movie setMultiLanguageParameters(HttpServletRequest request, Movie movie, int languageId) throws ValidationException {
        movie.setName(validateName(request.getParameter(NAME_ATTRIBUTE + languageId)));
        movie.setDescription(validateDescription(
                request.getParameter(DESCRIPTION_ATTRIBUTE + languageId)));
        movie.setCountry(validateCountry(request.getParameter(COUNTRY_ATTRIBUTE + languageId)));
        return movie;
    }
}
