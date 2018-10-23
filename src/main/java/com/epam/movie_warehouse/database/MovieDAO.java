package com.epam.movie_warehouse.database;

import com.epam.movie_warehouse.entity.Genre;
import com.epam.movie_warehouse.entity.Human;
import com.epam.movie_warehouse.entity.Movie;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.movie_warehouse.util.DAOConstant.*;

public class MovieDAO {
    private final ConnectionPull CONNECTION_PULL = ConnectionPull.getUniqueInstance();

    public Movie getMovieById(long movieId, int languageId) throws SQLException, ConnectionNotFoundException {
        Connection connection = CONNECTION_PULL.retrieve();
        Movie movie = new Movie();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_MOVIE_BY_ID_SQL_QUERY)) {
            preparedStatement.setLong(1, movieId);
            preparedStatement.setInt(2, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                movie = new Movie();
                movie = setParametersToMovie(movie, resultSet);
            }
            resultSet.close();
        }
        CONNECTION_PULL.putBack(connection);
        return movie;
    }

    public List<Movie> listMovie(int languageId) throws SQLException, ConnectionNotFoundException {
        List<Movie> movieList = new ArrayList<>();
        Connection connection = CONNECTION_PULL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(LIST_MOVIE_SQL_QUERY)) {
            preparedStatement.setInt(1, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Movie movie = new Movie();
                movie = setParametersToMovie(movie, resultSet);
                movieList.add(movie);
            }
            resultSet.close();
        }
        CONNECTION_PULL.putBack(connection);
        return movieList;
    }

    public void updateMovie(Movie movie, int languageId) throws SQLException, ConnectionNotFoundException {
        Connection connection = CONNECTION_PULL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MOVIE_SQL_QUERY)) {
            getMovieParameters(movie, preparedStatement);
            preparedStatement.setString(9, movie.getName());
            preparedStatement.setString(10, movie.getDescription());
            preparedStatement.setString(11, movie.getCountry());
            preparedStatement.setLong(12, movie.getId());
            preparedStatement.setLong(13, languageId);
            preparedStatement.executeUpdate();
        }
        CONNECTION_PULL.putBack(connection);
    }

    public void deleteGenresLinks(long movieId) throws SQLException, ConnectionNotFoundException {
        Connection connection = CONNECTION_PULL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_GENRES_LINKS_SQL_QUERY)) {
            preparedStatement.setLong(1, movieId);
            preparedStatement.executeUpdate();
        }
        CONNECTION_PULL.putBack(connection);
    }

    public void deleteHumansLinks(long movieId) throws SQLException, ConnectionNotFoundException {
        Connection connection = CONNECTION_PULL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_HUMANS_LINKS_SQL_QUERY)) {
            preparedStatement.setLong(1, movieId);
            preparedStatement.executeUpdate();
        }
        CONNECTION_PULL.putBack(connection);
    }

    public void deleteUsersLinks(long movieId) throws SQLException, ConnectionNotFoundException {
        Connection connection = CONNECTION_PULL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_LINKS_SQL_QUERY)) {
            preparedStatement.setLong(1, movieId);
            preparedStatement.executeUpdate();
        }
        CONNECTION_PULL.putBack(connection);
    }

    public void addGenresLinks(Movie movie) throws SQLException, ConnectionNotFoundException {
        Connection connection = CONNECTION_PULL.retrieve();
        for (Genre genre : movie.getGenres()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_GENRES_LINKS_SQL_QUERY)) {
                preparedStatement.setLong(1, movie.getId());
                preparedStatement.setLong(2, genre.getId());
                preparedStatement.executeUpdate();
            }
        }
        CONNECTION_PULL.putBack(connection);
    }

    public void addHumansLinks(Movie movie) throws SQLException, ConnectionNotFoundException {
        Connection connection = CONNECTION_PULL.retrieve();
        for (Human human : movie.getMovieCrew()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_HUMANS_LINKS_SQL_QUERY)) {
                preparedStatement.setLong(1, human.getId());
                preparedStatement.setLong(2, movie.getId());
                preparedStatement.setInt(3, human.getRoleId());
                preparedStatement.executeUpdate();
            }
        }
        CONNECTION_PULL.putBack(connection);
    }

    public void addMovie(Movie movie) throws SQLException, ConnectionNotFoundException {
        Connection connection = CONNECTION_PULL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_MOVIE_SQL_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            getMovieParameters(movie, preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet movieId = preparedStatement.getGeneratedKeys();
            if (movieId.next()) {
                movie.setId(movieId.getLong(1));
            }
        }
        CONNECTION_PULL.putBack(connection);
    }

    public void addMovieMultiLanguageParameters(Movie movie, int languageId) throws SQLException, ConnectionNotFoundException {
        Connection connection = CONNECTION_PULL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_CHARACTERISTIC_OF_MOVIE_SQL_QUERY)) {
            getMovieMultiLanguageParameters(movie, preparedStatement);
            preparedStatement.setLong(4, languageId);
            preparedStatement.setLong(5, movie.getId());
            preparedStatement.executeUpdate();
        }
        CONNECTION_PULL.putBack(connection);
    }

    public void deleteMovie(long movieId) throws SQLException, ConnectionNotFoundException {
        Connection connection = CONNECTION_PULL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_MOVIE_SQL_QUERY)) {
            preparedStatement.setLong(1, movieId);
            preparedStatement.executeUpdate();
        }
        CONNECTION_PULL.putBack(connection);
    }

    public void deleteMovieMultiLanguageParameters(long movieId) throws SQLException, ConnectionNotFoundException {
        Connection connection = CONNECTION_PULL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CHARACTERISTIC_OF_MOVIE_SQL_QUERY)) {
            preparedStatement.setLong(1, movieId);
            preparedStatement.executeUpdate();
        }
        CONNECTION_PULL.putBack(connection);
    }

    public Long getCountOfLikesByMovieId(long movieId) throws SQLException, ConnectionNotFoundException {
        long countOfLikes = 0;
        Connection connection = CONNECTION_PULL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_COUNT_OF_LIKES_SQL_QUERY)) {
            preparedStatement.setLong(1, movieId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                countOfLikes++;
            }
            resultSet.close();
        }
        CONNECTION_PULL.putBack(connection);
        return countOfLikes;
    }

    public Double getRatingByMovieId(long movieId) throws SQLException, ConnectionNotFoundException {
        double rating = 0;
        long countOfGrade = 0;
        long sumOfGrade = 0;
        Connection connection = CONNECTION_PULL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_COUNT_OF_VOTES_SQL_QUERY)) {
            preparedStatement.setLong(1, movieId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                countOfGrade++;
                int grade = resultSet.getInt("ITS_VOTED");
                sumOfGrade = sumOfGrade + grade;
            }
            resultSet.close();
            if ((sumOfGrade > 0) && (countOfGrade > 0)) {
                rating = (double) sumOfGrade / countOfGrade;
            }
        }
        CONNECTION_PULL.putBack(connection);
        return rating;
    }

    public List<Movie> listMovieByGenre(long genreId, int languageId) throws SQLException, ConnectionNotFoundException {
        List<Movie> movieList = new ArrayList<>();
        Connection connection = CONNECTION_PULL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_MOVIE_BY_GENRE_SQL_QUERY)) {
            preparedStatement.setLong(1, genreId);
            preparedStatement.setInt(2, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Movie movie = new Movie();
                movie = setParametersToMovie(movie, resultSet);
                movieList.add(movie);
            }
            resultSet.close();
        }
        CONNECTION_PULL.putBack(connection);
        return movieList;
    }

    public List<Movie> listMovieByHuman(long humanId, int languageId) throws SQLException, ConnectionNotFoundException {
        Map<Long, Movie> movieMap = new HashMap<>();
        Connection connection = CONNECTION_PULL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_MOVIE_BY_HUMAN_SQL_QUERY)) {
            preparedStatement.setLong(1, humanId);
            preparedStatement.setInt(2, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Movie movie = new Movie();
                movie = setParametersToMovie(movie, resultSet);
                movieMap.put(movie.getId(), movie);
            }
            resultSet.close();
        }
        CONNECTION_PULL.putBack(connection);
        return new ArrayList<>(movieMap.values());
    }

    public List<Movie> listMovieByName(String name, int languageId) throws SQLException, ConnectionNotFoundException {
        final String PERCENT = "%";
        List<Movie> movieList = new ArrayList<>();
        name = PERCENT + name + PERCENT;
        Connection connection = CONNECTION_PULL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_MOVIE_BY_NAME_SQL_QUERY)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Movie movie = new Movie();
                movie = setParametersToMovie(movie, resultSet);
                movieList.add(movie);
            }
            resultSet.close();
        }
        CONNECTION_PULL.putBack(connection);
        return movieList;
    }

    private Movie setParametersToMovie(Movie movie, ResultSet resultSet) throws SQLException {
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

    private void getMovieParameters(Movie movie, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, movie.getImdbID());
        preparedStatement.setString(2, movie.getImageURL());
        preparedStatement.setLong(3, movie.getBudget());
        preparedStatement.setInt(4, movie.getAgeLimit());
        preparedStatement.setDate(5, Date.valueOf(movie.getReleaseDate()));
        preparedStatement.setDate(6, Date.valueOf(movie.getUploadDate()));
        preparedStatement.setTime(7, Time.valueOf(movie.getDuration()));
        preparedStatement.setLong(8, movie.getDues());
    }

    private void getMovieMultiLanguageParameters(Movie movie, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, movie.getName());
        preparedStatement.setString(2, movie.getDescription());
        preparedStatement.setString(3, movie.getCountry());
    }
}