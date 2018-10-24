package com.epam.movie_warehouse.service;

import java.util.HashMap;
import java.util.Map;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public class ServiceFactory {
    private static final Map<String, Service> SERVICE_MAP = new HashMap<>();
    private static final ServiceFactory SERVICE_FACTORY = new ServiceFactory();

    private ServiceFactory() {
        init();
    }

    private void init() {
        SERVICE_MAP.put(LIST_MOVIES_ADMIN_URI, new ListMovieService());
        SERVICE_MAP.put(LIST_MOVIES_URI, new ListMovieService());
        SERVICE_MAP.put(MOVIE_URI, new ShowMovieService());
        SERVICE_MAP.put(SHOW_EDIT_MOVIE_URI, new ShowEditMovieService());
        SERVICE_MAP.put(EDIT_MOVIE_URI, new EditOrUploadMovieService());
        SERVICE_MAP.put(DELETE_MOVIE_URI, new DeleteMovieService());
        SERVICE_MAP.put(ADD_MOVIE_URI, new ShowAddMovieService());
        SERVICE_MAP.put(LIST_MOVIE_BY_GENRE_URI, new ListMovieService());
        SERVICE_MAP.put(LIST_MOVIE_BY_NAME_URI, new ListMovieService());
        SERVICE_MAP.put(LOG_IN_URI, new ShowUserLoginService());
        SERVICE_MAP.put(AUTHORIZATION_URI, new LoginUserService());
        SERVICE_MAP.put(REGISTRATION_URI, new RegistrationUserService());
        SERVICE_MAP.put(LOG_OUT_URI, new LogOutUserService());
        SERVICE_MAP.put(SHOW_MY_USER_URI, new ShowUserService());
        SERVICE_MAP.put(SHOW_USER_URI, new ShowUserService());
        SERVICE_MAP.put(SHOW_USER_EDIT_URI, new ShowEditUserService());
        SERVICE_MAP.put(EDIT_USER_URI, new EditUserService());
        SERVICE_MAP.put(DELETE_USER_URI, new DeleteUserService());
        SERVICE_MAP.put(DELETE_MY_USER_URI, new DeleteUserService());
        SERVICE_MAP.put(EDIT_USER_PASSWORD_URI, new EditUserService());
        SERVICE_MAP.put(LIST_USER_URI, new ListUserService());
        SERVICE_MAP.put(LIKE_URI, new LikeMovieService());
        SERVICE_MAP.put(PUT_A_GRADE_URI, new RateMovieService());
        SERVICE_MAP.put(SET_LOCAL_URI, new SetLocalService());
        SERVICE_MAP.put(UPLOAD_MOVIE_IMAGE_URI, new UploadImageService());
        SERVICE_MAP.put(UPLOAD_USER_IMAGE_URI, new UploadImageService());
        SERVICE_MAP.put(UPLOAD_HUMAN_IMAGE_URI, new UploadImageService());
        SERVICE_MAP.put(LIST_EDIT_GENRE_URI, new ListEditGenreService());
        SERVICE_MAP.put(EDIT_GENRE_URI, new EditOrUploadGenreService());
        SERVICE_MAP.put(ADD_GENRE_URI, new EditOrUploadGenreService());
        SERVICE_MAP.put(DELETE_GENRE_URI, new DeleteGenreService());
        SERVICE_MAP.put(LIST_GENRE_URI, new ListGenreService());
        SERVICE_MAP.put(LIST_HUMAN_URI, new ListHumanService());
        SERVICE_MAP.put(LIST_HUMAN_ADMIN_URI, new ListHumanService());
        SERVICE_MAP.put(HUMAN_URI, new ShowHumanService());
        SERVICE_MAP.put(DELETE_HUMAN_URI, new DeleteHumanService());
        SERVICE_MAP.put(SHOW_EDIT_HUMAN_URI, new ShowEditHumanService());
        SERVICE_MAP.put(EDIT_HUMAN_URI, new EditOrUploadHumanService());
        SERVICE_MAP.put(ADD_HUMAN_URI, new ShowAddHumanService());
    }

    public static ServiceFactory getInstance() {
        return SERVICE_FACTORY;
    }

    public Service getService(String serviceRequest) {
        return SERVICE_MAP.get(serviceRequest);
    }
}
