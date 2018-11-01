package com.epam.movie_warehouse.database;

import com.epam.movie_warehouse.entity.Genre;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.movie_warehouse.util.DAOConstant.*;

public class GenreDAO {
    private final ConnectionPool CONNECTION_POOL = ConnectionPool.getUniqueInstance();

    public List<Genre> listGenresOfTheMovie(long movieId, int languageId) throws SQLException, ConnectionNotFoundException {
        List<Genre> genreList = new ArrayList<>();
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_GENRE_OF_THE_MOVIE_SQL_QUERY)) {
            preparedStatement.setLong(1, movieId);
            preparedStatement.setLong(2, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Genre genre = new Genre();
                genre = setParametersToGenre(genre, resultSet);
                genreList.add(genre);
            }
            resultSet.close();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return genreList;
    }

    public List<Genre> listGenres(int languageId) throws SQLException, ConnectionNotFoundException {
        List<Genre> genreList = new ArrayList<>();
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_ALL_AVAILABLE_GENRE_SQL_QUERY)) {
            preparedStatement.setLong(1, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Genre genre = new Genre();
                genre = setParametersToGenre(genre, resultSet);
                genreList.add(genre);
            }
            resultSet.close();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return genreList;
    }

    public Genre getGenreById(long genreId, int languageId) throws SQLException, ConnectionNotFoundException {
        Genre genre = new Genre();
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_GENRE_BY_ID_SQL_QUERY)) {
            preparedStatement.setLong(1, genreId);
            preparedStatement.setLong(2, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                genre = new Genre();
                genre = setParametersToGenre(genre, resultSet);
            }
            resultSet.close();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return genre;
    }

    public void updateGenre(Genre genre, int languageId) throws SQLException, ConnectionNotFoundException {
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement pr = connection.prepareStatement(UPDATE_GENRE_SQL_QUERY)) {
            pr.setString(1, genre.getName());
            pr.setLong(2, genre.getId());
            pr.setInt(3, languageId);
            pr.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    public long getMaxGenreId() throws SQLException, ConnectionNotFoundException {
        long genreId = 1;
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ID_SQL_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                genreId = resultSet.getLong(1);
            }
            resultSet.close();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return genreId;
    }

    public void addGenre(Genre genre, int languageId) throws SQLException, ConnectionNotFoundException {
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement pr = connection.prepareStatement(ADD_GENRE_SQL_QUERY)) {
            pr.setLong(1, genre.getId());
            pr.setString(2, genre.getName());
            pr.setInt(3, languageId);
            pr.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    public void deleteGenre(long genreId) throws SQLException, ConnectionNotFoundException {
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_GENRE_BY_ID_SQL_QUERY)) {
            preparedStatement.setLong(1, genreId);
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    public Boolean checkForMovieLinks(long genreId) throws SQLException, ConnectionNotFoundException {
        boolean isChecked = false;
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_LINKS_GENRE_TO_MOVIE_SQL_QUERY)) {
            preparedStatement.setLong(1, genreId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                isChecked = true;
            }
            resultSet.close();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return isChecked;
    }

    private Genre setParametersToGenre(Genre genre, ResultSet resultSet) throws SQLException {
        genre.setId(resultSet.getLong("GENRE_ID"));
        genre.setName(resultSet.getString("GENRE_NAME"));
        return genre;
    }
}