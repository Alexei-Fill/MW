package com.epam.movie_warehouse.database;

import com.epam.movie_warehouse.entity.User;
import com.epam.movie_warehouse.enumiration.UserRole;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.epam.movie_warehouse.util.DAOConstant.*;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.NO_ENTRY_EXISTS_VALUE;

public class UserDAO {
    private final ConnectionPool CONNECTION_POOL = ConnectionPool.getUniqueInstance();

    public List<User> listUser() throws SQLException, ConnectionNotFoundException {
        List<User> userList = new ArrayList<>();
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(LIST_USER_SQL_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                user = setParametersToUser(user, resultSet);
                userList.add(user);
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return userList;
    }

    public User getUserByLogin(String userLogin) throws SQLException, ConnectionNotFoundException {
        User user = new User();
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_USER_BY_LOGIN_SQL_QUERY)) {
            preparedStatement.setString(1, userLogin);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user = setParametersToUser(user, resultSet);
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return user;
    }

    public User getUserById(long userId) throws SQLException, ConnectionNotFoundException {
        User user = new User();
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_USER_BY_ID_SQL_QUERY)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user = setParametersToUser(user, resultSet);
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return user;
    }

    public void addUser(User user) throws SQLException, ConnectionNotFoundException {
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_SQL_QUERY)) {
            getUserParameters(user, preparedStatement);
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    public void updateUser(User user) throws SQLException, ConnectionNotFoundException {
        final int USER_ID = 8;
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL_QUERY)) {
            getUserParameters(user, preparedStatement);
            preparedStatement.setLong(USER_ID, user.getId());
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    public void deleteUser(User user) throws SQLException, ConnectionNotFoundException {
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL_QUERY)) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    public void deleteMoviesLinks(User user) throws SQLException, ConnectionNotFoundException {
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_LINKS_MOVIES_OF_USER_SQL_QUERY)) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    public void addMoviesLinks(long userId, long movieId) throws SQLException, ConnectionNotFoundException {
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_LINKS_MOVIES_OF_USER_SQL_QUERY)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, movieId);
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    public Integer checkMoviesLinksByLikedField(long userId, long movieId) throws SQLException, ConnectionNotFoundException {
        Integer itsLiked = NO_ENTRY_EXISTS_VALUE;
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_MOVIES_LINKS_BY_LIKED_FIELD_SQL_QUERY)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, movieId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                itsLiked = resultSet.getInt("ITS_LIKED");
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return itsLiked;
    }

    public Integer checkMoviesLinksByVotedField(long userId, long movieId) throws SQLException, ConnectionNotFoundException {
        Integer itsVoted = NO_ENTRY_EXISTS_VALUE;
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_MOVIES_LINKS_BY_VOTED_FIELD_SQL_QUERY)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, movieId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                itsVoted = resultSet.getInt("ITS_VOTED");
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return itsVoted;
    }

    public void updateMoviesLinksByLikedField(long userId, long movieId, int itsLiked) throws SQLException,
            ConnectionNotFoundException {
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MOVIES_LINKS_BY_LIKED_FIELD_SQL_QUERY)) {
            preparedStatement.setLong(1, itsLiked);
            preparedStatement.setLong(2, userId);
            preparedStatement.setLong(3, movieId);
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    public void updateMoviesLinksByVotedField(long userId, long movieId, int grade) throws SQLException,
            ConnectionNotFoundException {
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MOVIES_LINKS_BY_VOTED_FIELD_SQL_QUERY)) {
            preparedStatement.setLong(1, grade);
            preparedStatement.setLong(2, userId);
            preparedStatement.setLong(3, movieId);
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    private User setParametersToUser(User user, ResultSet resultSet) throws SQLException {
        user.setId(resultSet.getLong("USER_ID"));
        user.setLogin(resultSet.getString("USER_LOGIN"));
        user.setPassword(resultSet.getString("USER_PASSWORD"));
        user.setMail(resultSet.getString("USER_MAIL"));
        user.setBirthDate(LocalDate.parse(resultSet.getDate("USER_BIRTH_DATE").toString()));
        user.setRegistrationDate(LocalDate.parse(resultSet.getDate("USER_REGISTRATION_DATE").toString()));
        user.setRoleId(UserRole.getUserRole(resultSet.getInt("USER_ROLE")));
        user.setImageURL(resultSet.getString("USER_IMAGE_URL"));
        return user;
    }

    private void getUserParameters(User user, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getMail());
        preparedStatement.setDate(4, Date.valueOf(user.getBirthDate()));
        preparedStatement.setDate(5, Date.valueOf(user.getRegistrationDate()));
        preparedStatement.setInt(6, user.getRoleId().getId());
        preparedStatement.setString(7, user.getImageURL());
    }
}
