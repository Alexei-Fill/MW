package com.epam.movie_warehouse.util;

public final class DAOConstant {
    public static final String SHOW_GENRE_OF_THE_MOVIE  = "SELECT G.GENRE_ID, G.GENRE_NAME FROM GENRE_OF_MOVIE GM, GENRE G WHERE " +
            "G.GENRE_ID = GM.GENRE_ID  and MOVIE_ID = ? and LANGUAGE_ID = ? ";
    public static final String SHOW_ALL_AVAILABLE_GENRE = "SELECT * FROM GENRE WHERE LANGUAGE_ID = ?  ORDER BY GENRE_NAME";
    public static final String SHOW_GENRE_BY_ID = "SELECT * FROM GENRE WHERE GENRE_ID = ? and LANGUAGE_ID = ?";
    public static final String GET_ID = "SELECT MAX(GENRE_ID)+1 FROM GENRE  ";
    public static final String DELETE_GENRE_BY_ID = "DELETE FROM GENRE WHERE GENRE_ID = ? ";
    public static final String ADD_GENRE = "INSERT INTO GENRE (GENRE_ID, GENRE_NAME, LANGUAGE_ID) VALUES (?, ?, ?) ";
    public static final String UPDATE_GENRE = "UPDATE GENRE SET GENRE_NAME = ? WHERE GENRE_ID = ? and LANGUAGE_ID = ?";
    public static final String CHECK_LINKS_GENRE_TO_MOVIE = "SELECT * FROM GENRE_OF_MOVIE WHERE GENRE_ID = ?";

    public static final String SHOW_MOVIE_CREW = "SELECT * from ROLE_HUMAN_IN_MOVIE RH, HUMAN H, CHARACTERISTICS_OF_HUMAN CH where" +
            " RH.HUMAN_ID = H.HUMAN_ID and  CH.HUMAN_ID = H.HUMAN_ID  and RH.MOVIE_ID = ? and CH.LANGUAGE_ID = ? ORDER BY CH.HUMAN_NAME";
    public static final String SHOW_ALL_AVAILABLE_HUMAN = "SELECT * FROM HUMAN H, CHARACTERISTICS_OF_HUMAN CH where  CH.HUMAN_ID = H.HUMAN_ID  " +
            " and CH.LANGUAGE_ID = ? ORDER BY CH.HUMAN_NAME";
    public static final String SHOW_HUMAN_BY_ID = "SELECT * FROM HUMAN H, CHARACTERISTICS_OF_HUMAN CH where  CH.HUMAN_ID = H.HUMAN_ID  " +
            " and H.HUMAN_ID = ? and CH.LANGUAGE_ID = ?";
    public static final String ADD_HUMAN = "INSERT INTO HUMAN (HUMAN_BIRTH_DATE, HUMAN_IMAGE_URL) VALUES (?, ?)";
    public static final String ADD_CHARACTERISTIC_OF_HUMAN = "INSERT INTO CHARACTERISTICS_OF_HUMAN (HUMAN_NAME, HUMAN_SURNAME," +
            " HUMAN_PATRONYMIC, HUMAN_BIOGRAPHY, HUMAN_ID, LANGUAGE_ID)  VALUES (?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_HUMAN = "UPDATE HUMAN H, CHARACTERISTICS_OF_HUMAN CH SET CH.HUMAN_NAME = ?, " +
            " CH.HUMAN_SURNAME = ?, CH.HUMAN_PATRONYMIC = ?, CH.HUMAN_BIOGRAPHY = ?, H.HUMAN_BIRTH_DATE = ?, H.HUMAN_IMAGE_URL = ?  " +
            "WHERE CH.HUMAN_ID = H.HUMAN_ID and H.HUMAN_ID = ? and CH.LANGUAGE_ID = ?";
    public static final String CHECK_LINKS_HUMAN_TO_MOVIE = "SELECT * FROM ROLE_HUMAN_IN_MOVIE WHERE HUMAN_ID = ?";
    public static final String DELETE_HUMAN = "DELETE from HUMAN WHERE HUMAN_ID = ?";
    public static final String DELETE_HUMAN_CHARACTERISTIC = "DELETE from CHARACTERISTICS_OF_HUMAN WHERE HUMAN_ID = ?";

    public static final String SHOW_ALL_LANGUAGE = "SELECT * FROM  LANGUAGE";
    public static final String SHOW_LANGUAGE_BY_ID = "SELECT * FROM  LANGUAGE WHERE LANGUAGE_ID = ?";

    public static final String LIST_MOVIE = "SELECT * from MOVIE M, CHARACTERISTICS_OF_MOVIE CM where" +
            " CM.MOVIE_ID = M.MOVIE_ID and CM.LANGUAGE_ID = ? ORDER BY M.MOVIE_UPLOAD_DATE DESC";
    public static final String SHOW_MOVIE_BY_ID = "SELECT * from MOVIE M, CHARACTERISTICS_OF_MOVIE CM where CM.MOVIE_ID = M.MOVIE_ID and  " +
            "M.MOVIE_ID = ? and CM.LANGUAGE_ID = ?";
    public static final String SHOW_MOVIE_BY_NAME = "SELECT * from MOVIE M, CHARACTERISTICS_OF_MOVIE CM where  M.MOVIE_ID = CM.MOVIE_ID  and " +
            " CM.MOVIE_NAME LIKE ? and  CM.LANGUAGE_ID = ? ORDER BY M.MOVIE_UPLOAD_DATE DESC";
    public static final String SHOW_MOVIE_BY_GENRE = "SELECT * from GENRE_OF_MOVIE GM, MOVIE M, CHARACTERISTICS_OF_MOVIE CM where" +
            " CM.MOVIE_ID = M.MOVIE_ID and M.MOVIE_ID = GM.MOVIE_ID and GM.GENRE_ID = ? and CM.LANGUAGE_ID = ? ORDER BY M.MOVIE_UPLOAD_DATE DESC";
    public static final String SHOW_MOVIE_BY_HUMAN = "SELECT * from ROLE_HUMAN_IN_MOVIE RHM, MOVIE M, CHARACTERISTICS_OF_MOVIE CM where" +
            " CM.MOVIE_ID = M.MOVIE_ID and M.MOVIE_ID = RHM.MOVIE_ID and RHM.HUMAN_ID = ? and CM.LANGUAGE_ID = ? ORDER BY M.MOVIE_UPLOAD_DATE DESC";
    public static final String SHOW_COUNT_OF_LIKES = "SELECT * from VOTED_LIKED_THE_MOVIE  where MOVIE_ID = ? and ITS_LIKED != 0";
    public static final String SHOW_COUNT_OF_VOTES = "SELECT * from VOTED_LIKED_THE_MOVIE  where MOVIE_ID = ? and ITS_VOTED != 0";
    public static final String UPDATE_MOVIE = "UPDATE MOVIE M,  CHARACTERISTICS_OF_MOVIE CM SET M.MOVIE_IMDBID = ?," +
            " M.MOVIE_IMAGE_URL = ?,  M.MOVIE_BUDGET = ?,  M.MOVIE_AGE_LIMIT = ?, M.MOVIE_RELEASE_DATE = ?, M.MOVIE_UPLOAD_DATE = ?," +
            " M.MOVIE_DURATION = ?, M.MOVIE_DUES = ?,  CM.MOVIE_NAME =?, CM.MOVIE_DESCRIPTION = ?, CM.MOVIE_COUNTRY = ? " +
            " WHERE CM.MOVIE_ID = M.MOVIE_ID and M.MOVIE_ID = ?  and CM.LANGUAGE_ID = ?";
    public static final String DELETE_MOVIE = "DELETE from MOVIE where MOVIE_ID = ?";
    public static final String DELETE_CHARACTERISTIC_OF_MOVIE = "DELETE from CHARACTERISTICS_OF_MOVIE where MOVIE_ID = ?";
    public static final String DELETE_LINKS_GENRES_OF_MOVIE = "DELETE from GENRE_OF_MOVIE where MOVIE_ID = ?";
    public static final String DELETE_LINKS_HUMANS_OF_MOVIE = "DELETE from ROLE_HUMAN_IN_MOVIE where MOVIE_ID = ?";
    public static final String DELETE_LINKS_USERS_OF_MOVIE = "DELETE from VOTED_LIKED_THE_MOVIE where MOVIE_ID = ?";
    public static final String ADD_MOVIE = "INSERT INTO MOVIE (MOVIE_IMDBID, MOVIE_IMAGE_URL, " +
            " MOVIE_BUDGET,  MOVIE_AGE_LIMIT, MOVIE_RELEASE_DATE," +
            "MOVIE_UPLOAD_DATE, MOVIE_DURATION, MOVIE_DUES) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String ADD_CHARACTERISTIC_OF_MOVIE = "INSERT INTO CHARACTERISTICS_OF_MOVIE (MOVIE_NAME, MOVIE_DESCRIPTION, MOVIE_COUNTRY, " +
            "LANGUAGE_ID, MOVIE_ID ) VALUES (?, ?, ?, ?, ?)";
    public static final String ADD_LINKS_GENRES_OF_MOVIE = "INSERT INTO GENRE_OF_MOVIE (MOVIE_ID, GENRE_ID) VALUES (?, ?)";
    public static final String ADD_LINKS_HUMANS_OF_MOVIE = "INSERT INTO ROLE_HUMAN_IN_MOVIE (HUMAN_ID, MOVIE_ID, HUMAN_ROLE) VALUES (?, ?, ?)";
    public static final String LIST_USER = "SELECT * FROM USER ORDER BY USER_LOGIN";
    public static final String SHOW_USER_BY_LOGIN = "SELECT * FROM USER where USER_LOGIN = ?";
    public static final String SHOW_USER_BY_ID = "SELECT * FROM USER where USER_ID = ?";
    public static final String ADD_USER = "INSERT INTO USER (USER_LOGIN, USER_PASSWORD, USER_MAIL, USER_BIRTH_DATE, " +
            "USER_REGISTRATION_DATE, USER_ROLE, USER_IMAGE_URL) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String ADD_LINKS_MOVIES_OF_USER = "INSERT INTO VOTED_LIKED_THE_MOVIE (USER_ID, MOVIE_ID) VALUES (?, ?)";
    public static final String UPDATE_USER = "UPDATE USER SET USER_LOGIN = ?, USER_PASSWORD = ?, USER_MAIL = ?, USER_BIRTH_DATE = ?,  " +
            " USER_REGISTRATION_DATE = ?, USER_ROLE = ?, USER_IMAGE_URL = ? where USER_ID = ?";
    public static final String DELETE_USER = "DELETE from USER where USER_ID = ?";
    public static final String DELETE_LINKS_MOVIES_OF_USER = "DELETE from VOTED_LIKED_THE_MOVIE where USER_ID = ?";
    public static final String CHECK_LIKED_LINK_MOVIES_OF_USER = "SELECT * from VOTED_LIKED_THE_MOVIE where USER_ID = ? "+
            " and MOVIE_ID = ? ";
    public static final String CHECK_VOTED_LINK_MOVIES_OF_USER = "SELECT * from VOTED_LIKED_THE_MOVIE where USER_ID = ? "+
            " and MOVIE_ID = ? ";
    public static final String UPDATE_LIKED_LINK_MOVIES_OF_USER = "UPDATE VOTED_LIKED_THE_MOVIE SET ITS_LIKED = ? " +
            " where USER_ID = ? and  MOVIE_ID = ?";
    public static final String UPDATE_VOTED_LINK_MOVIES_OF_USER = "UPDATE VOTED_LIKED_THE_MOVIE SET ITS_VOTED = ? " +
            " where USER_ID = ? and  MOVIE_ID = ?";
}
