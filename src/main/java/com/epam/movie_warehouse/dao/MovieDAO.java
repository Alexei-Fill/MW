package com.epam.movie_warehouse.dao;

import com.epam.movie_warehouse.connectionPull.ConnectionPull;
import com.epam.movie_warehouse.entity.Genre;
import com.epam.movie_warehouse.entity.Human;
import com.epam.movie_warehouse.entity.Movie;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieDAO {
    private static final String LIST_MOVIE = "SELECT * from MOVIE M, CHARACTERISTICS_OF_MOVIE CM where" +
            " CM.MOVIE_ID = M.MOVIE_ID and CM.LANGUAGE_ID = ? ORDER BY M.MOVIE_UPLOAD_DATE DESC";
    private static final String SHOW_MOVIE_BY_ID = "SELECT * from MOVIE M, CHARACTERISTICS_OF_MOVIE CM where CM.MOVIE_ID = M.MOVIE_ID and  " +
            "M.MOVIE_ID = ? and CM.LANGUAGE_ID = ?";
    private static final String SHOW_MOVIE_BY_NAME = "SELECT * from MOVIE M, CHARACTERISTICS_OF_MOVIE CM where  M.MOVIE_ID = CM.MOVIE_ID  and " +
            " CM.MOVIE_NAME LIKE ? and  CM.LANGUAGE_ID = ? ORDER BY M.MOVIE_UPLOAD_DATE DESC";
    private static final String SHOW_MOVIE_BY_GENRE = "SELECT * from GENRE_OF_MOVIE GM, MOVIE M, CHARACTERISTICS_OF_MOVIE CM where" +
            " CM.MOVIE_ID = M.MOVIE_ID and M.MOVIE_ID = GM.MOVIE_ID and GM.GENRE_ID = ? and CM.LANGUAGE_ID = ? ORDER BY M.MOVIE_UPLOAD_DATE DESC";
    private static final String SHOW_MOVIE_BY_HUMAN = "SELECT * from ROLE_HUMAN_IN_MOVIE RHM, MOVIE M, CHARACTERISTICS_OF_MOVIE CM where" +
            " CM.MOVIE_ID = M.MOVIE_ID and M.MOVIE_ID = RHM.MOVIE_ID and RHM.HUMAN_ID = ? and CM.LANGUAGE_ID = ? ORDER BY M.MOVIE_UPLOAD_DATE DESC";
    private static final String SHOW_COUNT_OF_LIKES = "SELECT * from VOTED_LIKED_THE_MOVIE  where MOVIE_ID = ? and ITS_LIKED != 0";
    private static final String SHOW_COUNT_OF_VOTES = "SELECT * from VOTED_LIKED_THE_MOVIE  where MOVIE_ID = ? and ITS_VOTED != 0";
    private static final String UPDATE_MOVIE = "UPDATE MOVIE M,  CHARACTERISTICS_OF_MOVIE CM SET M.MOVIE_IMDBID = ?," +
            " M.MOVIE_IMAGE_URL = ?,  M.MOVIE_BUDGET = ?,  M.MOVIE_AGE_LIMIT = ?, M.MOVIE_RELEASE_DATE = ?, M.MOVIE_UPLOAD_DATE = ?," +
            " M.MOVIE_DURATION = ?, M.MOVIE_DUES = ?,  CM.MOVIE_NAME =?, CM.MOVIE_DESCRIPTION = ?, CM.MOVIE_COUNTRY = ? " +
            " WHERE CM.MOVIE_ID = M.MOVIE_ID and M.MOVIE_ID = ?  and CM.LANGUAGE_ID = ?";
    private static final String DELETE_MOVIE = "DELETE from MOVIE where MOVIE_ID = ?";
    private static final String DELETE_CHARACTERISTIC_OF_MOVIE = "DELETE from CHARACTERISTICS_OF_MOVIE where MOVIE_ID = ?";
    private static final String DELETE_LINKS_GENRES_OF_MOVIE = "DELETE from GENRE_OF_MOVIE where MOVIE_ID = ?";
    private static final String DELETE_LINKS_HUMANS_OF_MOVIE = "DELETE from ROLE_HUMAN_IN_MOVIE where MOVIE_ID = ?";
    private static final String DELETE_LINKS_USERS_OF_MOVIE = "DELETE from VOTED_LIKED_THE_MOVIE where MOVIE_ID = ?";
    private static final String ADD_MOVIE = "INSERT INTO MOVIE (MOVIE_IMDBID, MOVIE_IMAGE_URL, " +
            " MOVIE_BUDGET,  MOVIE_AGE_LIMIT, MOVIE_RELEASE_DATE," +
            "MOVIE_UPLOAD_DATE, MOVIE_DURATION, MOVIE_DUES) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String ADD_CHARACTERISTIC_OF_MOVIE = "INSERT INTO CHARACTERISTICS_OF_MOVIE (MOVIE_NAME, MOVIE_DESCRIPTION, MOVIE_COUNTRY, " +
            "LANGUAGE_ID, MOVIE_ID ) VALUES (?, ?, ?, ?, ?)";
    private static final String ADD_LINKS_GENRES_OF_MOVIE = "INSERT INTO GENRE_OF_MOVIE (MOVIE_ID, GENRE_ID) VALUES (?, ?)";
    private static final String ADD_LINKS_HUMANS_OF_MOVIE = "INSERT INTO ROLE_HUMAN_IN_MOVIE (HUMAN_ID, MOVIE_ID, HUMAN_ROLE) VALUES (?, ?, ?)";
    private ConnectionPull connectionPull = ConnectionPull.getUniqueInstance();

    public Movie showMovieById(long movieId, int languageId) throws SQLException {
        Connection connection = connectionPull.retrieve();
        Movie movie = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_MOVIE_BY_ID)) {
            preparedStatement.setLong(1, movieId);
            preparedStatement.setInt(2, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                movie = new Movie();
                movie =  setMovieParameters(movie, resultSet);
            }
            resultSet.close();
        }
        connectionPull.putBack(connection);
        return movie;
    }

    public List<Movie> listMovie(int languageId) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(LIST_MOVIE)) {
            preparedStatement.setInt(1, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Movie movie = new Movie();
                movie =  setMovieParameters(movie, resultSet);
                movies.add(movie);
            }
            resultSet.close();
        }
        connectionPull.putBack(connection);
        return movies;
    }

    public void updateMovie (Movie movie, int languageId)  throws SQLException{
            Connection connection = connectionPull.retrieve();
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MOVIE)){
                getMovieParameters(movie, preparedStatement);
                preparedStatement.setString(9, movie.getName());
                preparedStatement.setString(10, movie.getDescription());
                preparedStatement.setString(11, movie.getCountry());
                preparedStatement.setLong(12, movie.getId());
                preparedStatement.setLong(13, languageId);
                preparedStatement.executeUpdate();
            }
            connectionPull.putBack(connection);
    }

    public void deleteLinksGenresOnMovie(long movieId) throws SQLException {
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_LINKS_GENRES_OF_MOVIE)) {
            preparedStatement.setLong(1, movieId);
            preparedStatement.executeUpdate();
        }
        connectionPull.putBack(connection);
    }

    public void deleteLinksHumansOnMovie(long movieId)  throws SQLException{
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_LINKS_HUMANS_OF_MOVIE)) {
            preparedStatement.setLong(1, movieId);
            preparedStatement.executeUpdate();
        }
        connectionPull.putBack(connection);
    }

    public void deleteLinksUsersOnMovie(long movieId) throws SQLException {
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_LINKS_USERS_OF_MOVIE)) {
            preparedStatement.setLong(1, movieId);
            preparedStatement.executeUpdate();
        }
        connectionPull.putBack(connection);
    }

    public void addLinksGenresOnMovie(Movie movie) throws SQLException {
        Connection connection = connectionPull.retrieve();
        for (Genre genre:movie.getGenres()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_LINKS_GENRES_OF_MOVIE)) {
                preparedStatement.setLong(1, movie.getId());
                preparedStatement.setLong(2, genre.getId());
                preparedStatement.executeUpdate();
            }
        }
        connectionPull.putBack(connection);
    }

    public void addLinksHumansOnMovie(Movie movie) throws SQLException {
        Connection connection = connectionPull.retrieve();
        for (Human human:movie.getMovieCrew()){
            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_LINKS_HUMANS_OF_MOVIE)) {
                preparedStatement.setLong(1, human.getId());
                preparedStatement.setLong(2, movie.getId());
                preparedStatement.setInt(3, human.getRoleId());
                preparedStatement.executeUpdate();
            }
        }
        connectionPull.putBack(connection);
    }

    public void addMovie (Movie movie) throws SQLException {
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_MOVIE, Statement.RETURN_GENERATED_KEYS)){
            getMovieParameters(movie, preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet movieId = preparedStatement.getGeneratedKeys();
            if (movieId.next()){
                movie.setId(movieId.getLong(1));
            }
        }
        connectionPull.putBack(connection);
    }

    public void addCharacteristicsOfMovie(Movie movie, int languageId) throws SQLException {
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_CHARACTERISTIC_OF_MOVIE)){
            getMovieCharacteristic(movie, preparedStatement);
            preparedStatement.setLong(4, languageId);
            preparedStatement.setLong(5, movie.getId());
            preparedStatement.executeUpdate();
        }
        connectionPull.putBack(connection);
    }

    public void deleteMovie(long movieId) throws SQLException {
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_MOVIE)) {
            preparedStatement.setLong(1, movieId);
            preparedStatement.executeUpdate();
        }
        connectionPull.putBack(connection);
    }

    public void deleteCharacteristicsOfMovie(long movieId) throws SQLException {
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CHARACTERISTIC_OF_MOVIE)) {
            preparedStatement.setLong(1, movieId);
            preparedStatement.executeUpdate();
        }
        connectionPull.putBack(connection);
    }

    public Long showCountOfLikesByMovieId(long movieId) throws SQLException {
       long countOfLikes = 0;
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_COUNT_OF_LIKES)) {
            preparedStatement.setLong(1, movieId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                countOfLikes++;
            }
            resultSet.close();
        }
        connectionPull.putBack(connection);
        return countOfLikes;
    }

    public Double showRatingByMovieId(long movieId) throws SQLException {
        double rating = 0;
        long countOfGrades = 0;
        long sumOfGrades = 0;
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_COUNT_OF_VOTES)) {
            preparedStatement.setLong(1, movieId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                countOfGrades++;
                int grade = resultSet.getInt("ITS_VOTED");
                sumOfGrades = sumOfGrades + grade;
            }
            resultSet.close();
            if ((sumOfGrades > 0) && (countOfGrades > 0)) {
                rating = (double)sumOfGrades / countOfGrades;
            }
        }
        connectionPull.putBack(connection);
        return rating;
    }

    public List<Movie> listMovieByGenre(long genreId, int languageId) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_MOVIE_BY_GENRE)) {
            preparedStatement.setLong(1, genreId);
            preparedStatement.setInt(2, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Movie movie = new Movie();
                movie =  setMovieParameters(movie, resultSet);
                movies.add(movie);
            }
            resultSet.close();
        }
        connectionPull.putBack(connection);
        return movies;
    }

    public List<Movie> listMovieByHuman(long humanId, int languageId) throws SQLException {
        Map<Long, Movie> moviesMap = new HashMap<>();
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_MOVIE_BY_HUMAN)) {
            preparedStatement.setLong(1, humanId);
            preparedStatement.setInt(2, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Movie movie = new Movie();
                movie =  setMovieParameters(movie, resultSet);
                moviesMap.put(movie.getId(), movie);
            }
            resultSet.close();
        }
        connectionPull.putBack(connection);
        return new ArrayList<>(moviesMap.values());
    }

    public List<Movie> listMovieByName (String name, int languageId) throws SQLException {
        final String PERCENT = "%";
        List<Movie> movies = new ArrayList<>();
        name = PERCENT + name + PERCENT;
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_MOVIE_BY_NAME)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Movie movie = new Movie();
                movie =  setMovieParameters(movie, resultSet);
                movies.add(movie);
            }
            resultSet.close();
        }
        connectionPull.putBack(connection);
        return movies;
    }

    private Movie setMovieParameters(Movie movie, ResultSet resultSet) throws SQLException{
        movie.setId(resultSet.getLong("MOVIE_ID"));
        movie.setImdbID(resultSet.getString("MOVIE_IMDBID"));
        movie.setImageURL(resultSet.getString("MOVIE_IMAGE_URL"));
        movie.setBudget(resultSet.getLong("MOVIE_BUDGET"));
        movie.setAgeLimit(resultSet.getInt("MOVIE_AGE_LIMIT"));
        movie.setReleaseDate(LocalDate.parse(resultSet.getDate("MOVIE_RELEASE_DATE").toString()));
        movie.setUploadDate(LocalDate.parse(resultSet.getDate("MOVIE_UPLOAD_DATE").toString()));
        movie.setDuration(LocalTime.parse(resultSet.getTime("MOVIE_DURATION").toString()));
        movie.setDues(resultSet.getLong("MOVIE_DUES"));
        movie.setName(resultSet.getString("MOVIE_NAME"));
        movie.setDescription(resultSet.getString("MOVIE_DESCRIPTION"));
        movie.setCountry(resultSet.getString("MOVIE_COUNTRY"));
        return movie;
    }

    private void getMovieParameters (Movie movie, PreparedStatement preparedStatement) throws SQLException{
        preparedStatement.setString(1, movie.getImdbID());
        preparedStatement.setString(2, movie.getImageURL());
        preparedStatement.setLong(3, movie.getBudget());
        preparedStatement.setInt(4, movie.getAgeLimit());
        preparedStatement.setDate(5, Date.valueOf(movie.getReleaseDate()));
        preparedStatement.setDate(6, Date.valueOf(movie.getUploadDate()));
        preparedStatement.setTime(7, Time.valueOf(movie.getDuration()));
        preparedStatement.setLong(8, movie.getDues());
    }

    private void getMovieCharacteristic(Movie movie, PreparedStatement preparedStatement) throws SQLException{
        preparedStatement.setString(1, movie.getName());
        preparedStatement.setString(2, movie.getDescription());
        preparedStatement.setString(3, movie.getCountry());
    }
}