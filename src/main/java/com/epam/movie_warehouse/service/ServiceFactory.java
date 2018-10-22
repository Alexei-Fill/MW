package com.epam.movie_warehouse.service;

import java.util.HashMap;
import java.util.Map;

import static com.epam.movie_warehouse.util.MovieWarehouseConstant.*;

public class ServiceFactory {
    private static Map<String, Service> serviceMap = new HashMap<>();
    private static ServiceFactory serviceFactory = new ServiceFactory();

    private ServiceFactory() {
        init();
    }

    private void init(){
        serviceMap.put(LIST_MOVIES_ADMIN_URI,new ListMovieService());
        serviceMap.put(LIST_MOVIES_URI,new ListMovieService());
        serviceMap.put(MOVIE_URI, new ShowMovieService());
        serviceMap.put(SHOW_EDIT_MOVIE_URI, new ShowEditMovieService());
        serviceMap.put(EDIT_MOVIE_URI, new AddEditMovieService());
        serviceMap.put(DELETE_MOVIE_URI, new DeleteMovieService());
        serviceMap.put(ADD_MOVIE_URI, new ShowAddMovieService());
        serviceMap.put(LIST_MOVIE_BY_GENRE_URI, new ShowMovieByNameOrGenreService());
        serviceMap.put(LIST_MOVIE_BY_NAME_URI, new ShowMovieByNameOrGenreService());
        serviceMap.put(LOG_IN_URI, new ShowUserLoginService());
        serviceMap.put(AUTHORIZATION_URI, new LoginUserService());
        serviceMap.put(REGISTRATION_URI, new RegistrationUserService());
        serviceMap.put(LOG_OUT_URI, new LogOutUserService());
        serviceMap.put(SHOW_MY_USER_URI, new ShowUserService());
        serviceMap.put(SHOW_USER_URI, new ShowUserService());
        serviceMap.put(SHOW_USER_EDIT_URI, new ShowEditUserService());
        serviceMap.put(EDIT_USER_URI, new EditUserService());
        serviceMap.put(DELETE_USER_URI, new DeleteUserService());
        serviceMap.put(DELETE_MY_USER_URI, new DeleteUserService());
        serviceMap.put(EDIT_USER_PASSWORD_URI, new EditUserService());
        serviceMap.put(LIST_USER_URI, new ListUserService());
        serviceMap.put(LIKE_URI, new LikeMovieService());
        serviceMap.put(PUT_A_GRADE_URI, new PutGradeMovieService());
        serviceMap.put(SET_LOCAL_URI, new SetLocalService());
        serviceMap.put(UPLOAD_MOVIE_IMAGE_URI, new UploadImageService());
        serviceMap.put(UPLOAD_USER_IMAGE_URI, new UploadImageService());
        serviceMap.put(UPLOAD_HUMAN_IMAGE_URI, new UploadImageService());
        serviceMap.put(LIST_EDIT_GENRE_URI, new ListEditGenreService());
        serviceMap.put(EDIT_GENRE_URI, new AddEditGenreService());
        serviceMap.put(ADD_GENRE_URI, new AddEditGenreService());
        serviceMap.put(DELETE_GENRE_URI, new DeleteGenreService());
        serviceMap.put(LIST_GENRE_URI, new ListGenreService());
        serviceMap.put(LIST_HUMAN_URI, new ListHumanService());
        serviceMap.put(LIST_HUMAN_ADMIN_URI, new ListHumanService());
        serviceMap.put(HUMAN_URI, new ShowHumanService());
        serviceMap.put(DELETE_HUMAN_URI, new DeleteHumanService());
        serviceMap.put(SHOW_EDIT_HUMAN_URI, new ShowEditHumanService());
        serviceMap.put(EDIT_HUMAN_URI, new AddEditHumanService());
        serviceMap.put(ADD_HUMAN_URI, new ShowAddHumanService());
    }

    public static ServiceFactory getInstance() {
        return serviceFactory;
    }

    public Service getService(String serviceRequest){
        return serviceMap.get(serviceRequest);
    }
}
