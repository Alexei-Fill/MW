package com.epam.movie_warehouse.controllerr;

import com.epam.movie_warehouse.entity.Genre;
import com.epam.movie_warehouse.entity.Language;
import com.epam.movie_warehouse.service.ListGenreService;
import com.epam.movie_warehouse.service.ListLanguageService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;
import java.util.List;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        List<Language> languages = null;
        List<Genre> genres = null;
        ServletContext context = servletContextEvent.getServletContext();
        try {
            languages = new ListLanguageService().listLanguage();
            genres = new ListGenreService().listGenre();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        context.setAttribute(SITE_LANGUAGE, languages);
        context.setAttribute(GENRES, genres);
        context.setAttribute("admin", ADMIN);
        context.setAttribute("commonUser", COMMON_USER);
        context.setAttribute("notLike", itsNotLiked);
        context.setAttribute("like", itsLiked);
        context.setAttribute(ACTOR, ACTOR_ROLE_ID);
        context.setAttribute(DIRECTOR, DIRECTOR_ROLE_ID);
        context.setAttribute(SCREENWRITER, SCREENWRITER_ROLE_ID);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
