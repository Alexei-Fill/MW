package com.epam.movie_warehouse.dao;

import com.epam.movie_warehouse.connectionPull.ConnectionPull;
import com.epam.movie_warehouse.entity.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.epam.movie_warehouse.util.DAOConstant.*;

public class GenreDAO {
    private ConnectionPull connectionPull = ConnectionPull.getUniqueInstance();

    public List<Genre> showGenresOfTheMovie(long movieId, int languageId) throws SQLException {
        List<Genre> genres = new ArrayList<>();
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_GENRE_OF_THE_MOVIE)) {
            preparedStatement.setLong(1, movieId);
            preparedStatement.setLong(2, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
             while (resultSet.next()){
                 Genre genre = new Genre();
                 genre = setGenreParameters(genre, resultSet);
                 genres.add(genre);
            }
            resultSet.close();
        }
        connectionPull.putBack(connection);
        return genres;
    }

    public List<Genre> showAllAvailableGenres(int languageId) throws SQLException {
        List<Genre> genres = new ArrayList<>();
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_ALL_AVAILABLE_GENRE)) {
            preparedStatement.setLong(1, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Genre genre = new Genre();
                genre = setGenreParameters(genre, resultSet);
                genres.add(genre);
            }
            resultSet.close();
        }
        connectionPull.putBack(connection);
        return genres;
    }

    public Genre showGenreById(Long genreId, int languageId)  throws SQLException{
        Genre genre = new Genre();
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_GENRE_BY_ID)) {
            preparedStatement.setLong(1, genreId);
            preparedStatement.setLong(2, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                genre = new Genre();
                genre = setGenreParameters(genre, resultSet);
            }
            resultSet.close();
        }
        connectionPull.putBack(connection);
        return genre;
    }

        public void updateGenre(Genre genre, int languageId) throws SQLException {
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement pr = connection.prepareStatement(UPDATE_GENRE)){
            pr.setString(1, genre.getName());
            pr.setLong(2, genre.getId());
            pr.setInt(3, languageId);
            pr.executeUpdate();
        }
        connectionPull.putBack(connection);
    }

    public long getMAXGenreId() throws SQLException {
        long genreID = 1;
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ID)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
               genreID = resultSet.getLong(1);
            }
            resultSet.close();
        }
        connectionPull.putBack(connection);
        return  genreID;
    }

        public void addGenre(Genre genre, int languageId) throws SQLException {
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement pr = connection.prepareStatement(ADD_GENRE)){
            pr.setLong(1, genre.getId());
            pr.setString(2, genre.getName());
            pr.setInt(3, languageId);
            pr.executeUpdate();
        }
        connectionPull.putBack(connection);
    }

    public void deleteGenre(Long genreId) throws SQLException {
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_GENRE_BY_ID)){
            preparedStatement.setLong(1, genreId);
            preparedStatement.executeUpdate();
        }
        connectionPull.putBack(connection);
    }
    public Boolean checkLinksGenreToMovie(Long genreId) throws SQLException {
        boolean isChecked = false;
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_LINKS_GENRE_TO_MOVIE)) {
            preparedStatement.setLong(1, genreId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                isChecked = true;
            }
            resultSet.close();
        }
        connectionPull.putBack(connection);
        return isChecked;
    }

    private Genre setGenreParameters(Genre genre, ResultSet resultSet) throws SQLException{
        genre.setId(resultSet.getLong("GENRE_ID"));
        genre.setName(resultSet.getString("GENRE_NAME"));
        return genre;
    }
}