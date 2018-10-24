package com.epam.movie_warehouse.listener;

import com.epam.movie_warehouse.entity.Genre;
import com.epam.movie_warehouse.entity.Language;
import com.epam.movie_warehouse.enumiration.UserRole;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;
import com.epam.movie_warehouse.service.ListGenreService;
import com.epam.movie_warehouse.service.ListLanguageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public class MovieWarehouseContextListener implements ServletContextListener {
    private static final Logger ROOT_LOGGER = LogManager.getRootLogger();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        List<Language> languageList = new ArrayList<>();
        List<Genre> genreList = new ArrayList<>();
        ServletContext context = servletContextEvent.getServletContext();
        try {
            languageList = new ListLanguageService().listLanguage();
            genreList = new ListGenreService().listGenre();
        } catch (SQLException | ConnectionNotFoundException e) {
            ROOT_LOGGER.error(e);
        }
        context.setAttribute(SITE_LANGUAGE_ATTRIBUTE, languageList);
        context.setAttribute(GENRES_ATTRIBUTE, genreList);
        context.setAttribute(ADMIN_ATTRIBUTE, UserRole.ADMIN);
        context.setAttribute(COMMON_USER_ATTRIBUTE, UserRole.USER);
        context.setAttribute(LIKE_ATTRIBUTE, ITS_LIKED_VALUE);
        context.setAttribute(NOT_LIKE_ATTRIBUTE, ITS_NOT_LIKED_VALUE);
        context.setAttribute(ACTOR_ATTRIBUTE, ACTOR_ROLE_ID);
        context.setAttribute(DIRECTOR_ATTRIBUTE, DIRECTOR_ROLE_ID);
        context.setAttribute(SCREENWRITER_ATTRIBUTE, SCREENWRITER_ROLE_ID);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
