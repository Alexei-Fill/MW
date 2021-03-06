package com.epam.movie_warehouse.util;

public final class MovieWarehouseConstant {
    public static final int SALT_FOR_PASSWORD = 12;
    public static final int DEFAULT_LANGUAGE = 1;
    public static final int PARAMETER_SHOW_MESSAGE_TRUE = 1;
    public static final int ACTOR_ROLE_ID = 1;
    public static final int DIRECTOR_ROLE_ID = 2;
    public static final int SCREENWRITER_ROLE_ID = 3;
    public static final int STRING_MAX_LENGTH = 24;
    public static final int IMDB_MAX_LENGTH = 16;
    public static final int LOGIN_MIN_LENGTH = 4;
    public static final int PASSWORD_MAX_LENGTH = 24;
    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final int MAIL_MAX_LENGTH = 32;
    public static final int SURNAME_MAX_LENGTH = 45;
    public static final int PATRONYMIC_MAX_LENGTH = 32;
    public static final int MAIL_MIN_LENGTH = 6;
    public static final int BIG_TEXT_MAX_LENGTH = 10240;
    public static final int ITS_NOT_LIKED_VALUE = 0;
    public static final int ITS_LIKED_VALUE = 1;
    public static final int NO_ENTRY_EXISTS_VALUE = 100;
    public static final String DEFAULT_LOCALE = "";
    public static final String EXCEPTION = "exception";
    public static final String PREFIX_FOR_PASSWORD = "$2a$";
    public static final String USER_LOGGER = "UserLogger";
    public static final String EMPTY_STRING = "";
    public static final String SPACE = " ";
    public static final String QUESTION_MARK = "?";
    public static final String LOGIN_PATTERN = "^[A-Za-z0-9_-]{4,16}$";
    public static final String MAIL_PATTERN = "^([a-z0-9_\\.-]+)@([a-z0-9_\\.-]+)\\.([a-z\\.]{2,6})$";
    public static final String DEFAULT_TIME_ZONE = "Asia/Dhaka";
    public static final String LIST_MOVIES_ADMIN_URI = "/listMoviesAdmin";
    public static final String LIST_HUMAN_URI = "/listHuman";
    public static final String LIST_HUMAN_ADMIN_URI = "/listHumanAdmin";
    public static final String HUMAN_URI = "/human";
    public static final String DELETE_HUMAN_URI = "/deleteHuman";
    public static final String SHOW_EDIT_HUMAN_URI = "/showEditHuman";
    public static final String EDIT_HUMAN_URI = "/editHuman";
    public static final String ADD_HUMAN_URI = "/addHuman";
    public static final String LIST_MOVIES_URI = "/listMovies";
    public static final String MOVIE_URI = "/movie";
    public static final String SHOW_EDIT_MOVIE_URI = "/showEditMovie";
    public static final String EDIT_MOVIE_URI = "/editMovie";
    public static final String DELETE_MOVIE_URI = "/deleteMovie";
    public static final String ADD_MOVIE_URI = "/addMovie";
    public static final String LIST_MOVIE_BY_GENRE_URI = "/listMovieByGenre";
    public static final String LIST_MOVIE_BY_NAME_URI = "/listMovieByName";
    public static final String LOG_IN_URI = "/login";
    public static final String AUTHORIZATION_URI = "/authorization";
    public static final String REGISTRATION_URI = "/registration";
    public static final String LOG_OUT_URI = "/logOut";
    public static final String SHOW_MY_USER_URI = "/showMyUser";
    public static final String SHOW_USER_URI = "/showUser";
    public static final String SHOW_USER_EDIT_URI = "/showUserEdit";
    public static final String EDIT_USER_URI = "/editUser";
    public static final String LIKE_URI = "/like";
    public static final String PUT_A_GRADE_URI = "/putAGrade";
    public static final String DELETE_USER_URI = "/deleteUser";
    public static final String DELETE_MY_USER_URI = "/deleteMyUser";
    public static final String EDIT_USER_PASSWORD_URI = "/editUserPassword";
    public static final String LIST_USER_URI = "/listUsers";
    public static final String SET_LOCAL_URI = "/setLocal";
    public static final String UPLOAD_MOVIE_IMAGE_URI = "/uploadMovieImage";
    public static final String UPLOAD_USER_IMAGE_URI = "/uploadUserImage";
    public static final String UPLOAD_HUMAN_IMAGE_URI = "/uploadHumanImage";
    public static final String LIST_GENRE_URI = "/listGenre";
    public static final String LIST_EDIT_GENRE_URI = "/listEditGenre";
    public static final String EDIT_GENRE_URI = "/editGenre";
    public static final String ADD_GENRE_URI = "/addGenre";
    public static final String DELETE_GENRE_URI = "/deleteGenre";
    public static final String AUTHORIZED_USER_ATTRIBUTE = "authorizedUser";
    public static final String SESSION_LANGUAGE_ID_ATTRIBUTE = "languageId";
    public static final String SITE_LANGUAGE_ATTRIBUTE = "siteLanguages";
    public static final String CURRENT_URL_ATTRIBUTE = "currentURL";
    public static final String LOCAL_ID_ATTRIBUTE = "localId";
    public static final String MOVIE_ID_ATTRIBUTE = "movieId";
    public static final String USER_ID_ATTRIBUTE = "userId";
    public static final String GENRE_ID_ATTRIBUTE = "genreId";
    public static final String HUMAN_ID_ATTRIBUTE = "humanId";
    public static final String ADMIN_ATTRIBUTE = "admin";
    public static final String COMMON_USER_ATTRIBUTE = "commonUser";
    public static final String NOT_LIKE_ATTRIBUTE = "notLike";
    public static final String LIKE_ATTRIBUTE = "like";
    public static final String SEARCH_STRING_ATTRIBUTE = "searchString";
    public static final String HIDDEN_MESSAGE_ATTRIBUTE = "hiddenMessage";
    public static final String USER_ROLE_ID_ATTRIBUTE = "roleId";
    public static final String PASSWORD_ATTRIBUTE = "password";
    public static final String NEW_PASSWORD_ATTRIBUTE = "newPassword";
    public static final String NEW_PASSWORD_REPEAT_ATTRIBUTE = "newPasswordRepeat";
    public static final String OLD_PASSWORD_ATTRIBUTE = "oldPassword";
    public static final String PASSWORD_REPEAT_ATTRIBUTE = "passwordRepeat";
    public static final String IMG_URL_ATTRIBUTE = "imageURL";
    public static final String IMDB_ID_ATTRIBUTE = "imdbId";
    public static final String BUDGET_ATTRIBUTE = "budget";
    public static final String DUES_ATTRIBUTE = "dues";
    public static final String AGE_LIMIT_ATTRIBUTE = "ageLimit";
    public static final String DURATION_ATTRIBUTE = "duration";
    public static final String RELEASE_DATE_ATTRIBUTE = "releaseDate";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String SURNAME_ATTRIBUTE = "surname";
    public static final String PATRONYMIC_ATTRIBUTE = "patronymic";
    public static final String DESCRIPTION_ATTRIBUTE = "description";
    public static final String BIOGRAPHY_ATTRIBUTE = "biography";
    public static final String COUNTRY_ATTRIBUTE = "country";
    public static final String GENRE_ATTRIBUTE = "genre";
    public static final String GENRES_ATTRIBUTE = "genres";
    public static final String HUMANS_ATTRIBUTE = "humans";
    public static final String HUMAN_ATTRIBUTE = "human";
    public static final String LANGUAGES_ATTRIBUTE = "languages";
    public static final String ACTOR_ATTRIBUTE = "actor";
    public static final String DIRECTOR_ATTRIBUTE = "director";
    public static final String SCREENWRITER_ATTRIBUTE = "screenwriter";
    public static final String CHARACTERISTIC_LANGUAGE_ID = "characteristicLanguageId";
    public static final String MOVIE_ATTRIBUTE = "movie";
    public static final String MOVIES_ATTRIBUTE = "movies";
    public static final String USER_ATTRIBUTE = "user";
    public static final String USERS_ATTRIBUTE = "users";
    public static final String LOGIN_ATTRIBUTE = "login";
    public static final String MAIL_ATTRIBUTE = "mail";
    public static final String BIRTH_DATE_ATTRIBUTE = "birthDate";
    public static final String LIKED_ATTRIBUTE = "liked";
    public static final String GRADE_ATTRIBUTE = "grade";
    public static final String LOCALE_ATTRIBUTE = "local";
    public static final String DATE_FORMAT_ATTRIBUTE = "dateFormat";
    public static final String SHOW_MOVIE_BY_ID_URL = "/movie?movieId=";
    public static final String SHOW_HUMAN_BY_ID_URL = "/human?humanId=";
    public static final String LOG_IN_JSP = "/jsp/login.jsp";
    public static final String EDIT_USER_JSP = "/jsp/editUser.jsp";
    public static final String LIST_MOVIE_JSP = "jsp/listMovie.jsp";
    public static final String LIST_HUMAN_JSP = "jsp/listHuman.jsp";
    public static final String LIST_HUMAN_ADMIN_JSP = "jsp/listHumanAdmin.jsp";
    public static final String HUMAN_JSP = "jsp/human.jsp";
    public static final String LIST_MOVIE_ADMIN_JSP = "jsp/listMovieAdmin.jsp";
    public static final String EDIT_MOVIE_JSP = "/jsp/editMovie.jsp";
    public static final String EDIT_HUMAN_JSP = "/jsp/editHuman.jsp";
    public static final String LIST_USER_JSP = "/jsp/listUser.jsp";
    public static final String LIST_EDIT_GENRE_JSP = "/jsp/listEditGenre.jsp";
    public static final String MOVIE_JSP = "/jsp/movie.jsp";
    public static final String USER_JSP = "/jsp/user.jsp";
    public static final String INCORRECT_DATA = "1001";
    public static final String INCORRECT_LOGIN_OR_PASSWORD = "1002";
    public static final String YOU_HAVE_LINKS = "1003";
    public static final String INVALID_HASH_PROVIDED = "Invalid hash provided for comparison";
    public static final String CONNECTION_NOT_FOUND_EXCEPTION = "Connection not found";
}
