package com.epam.movie_warehouse.database;

import com.epam.movie_warehouse.entity.User;
import com.epam.movie_warehouse.enumiration.UserRole;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.epam.movie_warehouse.util.DAOConstant.*;
import static com.epam.movie_warehouse.util.MovieWarehouseConstant.NO_ENTRY_EXISTS;

public class UserDAO {
    private final ConnectionPull connectionPull = ConnectionPull.getUniqueInstance();

    public List<User> listUser() throws SQLException, ConnectionNotFoundException {
        List<User> users = new ArrayList<>();
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(LIST_USER);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                user = setUserPar(user, resultSet);
                users.add(user);
            }
        }
        connectionPull.putBack(connection);
        return users;
    }

    public User showUserByLogin(String userLogin) throws SQLException, ConnectionNotFoundException {
        User user = new User();
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_USER_BY_LOGIN)) {
            preparedStatement.setString(1, userLogin);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user = setUserPar(user, resultSet);
            }
            resultSet.close();
        }
        connectionPull.putBack(connection);
        return user;
    }

    public User showUserById(long userId) throws SQLException, ConnectionNotFoundException {
        User user = new User();
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_USER_BY_ID)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user = setUserPar(user, resultSet);
            }
            resultSet.close();
        }
        connectionPull.putBack(connection);
        return user;
    }

    public void addUser(User user) throws SQLException, ConnectionNotFoundException {
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER)) {
            getUserParameters(user, preparedStatement);
            preparedStatement.executeUpdate();
        }
        connectionPull.putBack(connection);
    }

    //
    public void updateUser(User user) throws SQLException, ConnectionNotFoundException {
        final int USER_ID = 8;
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement pr = connection.prepareStatement(UPDATE_USER)) {
            getUserParameters(user, pr);
            pr.setLong(USER_ID, user.getId());
            pr.executeUpdate();

        }
        connectionPull.putBack(connection);
    }

    public void deleteUser(User user) throws SQLException, ConnectionNotFoundException {
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement pr = connection.prepareStatement(DELETE_USER)) {
            pr.setLong(1, user.getId());
            pr.executeUpdate();
        }
        connectionPull.putBack(connection);
    }

    public void deleteLinksMoviesOfUser(User user) throws SQLException, ConnectionNotFoundException {
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement pr = connection.prepareStatement(DELETE_LINKS_MOVIES_OF_USER)) {
            pr.setLong(1, user.getId());
            pr.executeUpdate();
        }
        connectionPull.putBack(connection);
    }

    public void addLinksMoviesOfUser(long userId, long movieId) throws SQLException, ConnectionNotFoundException {
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement pr = connection.prepareStatement(ADD_LINKS_MOVIES_OF_USER)) {
            pr.setLong(1, userId);
            pr.setLong(2, movieId);
            pr.executeUpdate();
        }
        connectionPull.putBack(connection);
    }

    public Integer checkLikedLinkMovieOfUser(long userId, long movieId) throws SQLException, ConnectionNotFoundException {
        Integer itsLiked = NO_ENTRY_EXISTS;
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement pr = connection.prepareStatement(CHECK_LIKED_LINK_MOVIES_OF_USER)) {
            pr.setLong(1, userId);
            pr.setLong(2, movieId);
            ResultSet res = pr.executeQuery();
            while (res.next()) {
                itsLiked = res.getInt("ITS_LIKED");
            }
            res.close();
        }
        connectionPull.putBack(connection);
        return itsLiked;
    }

    public void updateLikedLinkMovieOfUser(long userId, long movieId, int itsLiked) throws SQLException,
            ConnectionNotFoundException {
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement pr = connection.prepareStatement(UPDATE_LIKED_LINK_MOVIES_OF_USER)) {
            pr.setLong(1, itsLiked);
            pr.setLong(2, userId);
            pr.setLong(3, movieId);
            pr.executeUpdate();
        }
        connectionPull.putBack(connection);
    }

    public Integer checkVotedLinkMovieOfUser(long userId, long movieId) throws SQLException, ConnectionNotFoundException {
        Integer itsVoted = NO_ENTRY_EXISTS;
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement pr = connection.prepareStatement(CHECK_VOTED_LINK_MOVIES_OF_USER)) {
            pr.setLong(1, userId);
            pr.setLong(2, movieId);
            ResultSet res = pr.executeQuery();
            while (res.next()) {
                itsVoted = res.getInt("ITS_VOTED");
            }
            res.close();
        }
        connectionPull.putBack(connection);
        return itsVoted;
    }

    public void updateVotedLinkMovieOfUser(long userId, long movieId, int grade) throws SQLException,
            ConnectionNotFoundException {
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement pr = connection.prepareStatement(UPDATE_VOTED_LINK_MOVIES_OF_USER)) {
            pr.setLong(1, grade);
            pr.setLong(2, userId);
            pr.setLong(3, movieId);
            pr.executeUpdate();
        }
        connectionPull.putBack(connection);
    }

    private User setUserPar(User user, ResultSet resultSet) throws SQLException {
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
