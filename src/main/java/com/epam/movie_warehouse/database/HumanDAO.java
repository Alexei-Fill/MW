package com.epam.movie_warehouse.database;

import com.epam.movie_warehouse.entity.Human;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.epam.movie_warehouse.util.DAOConstant.*;

public class HumanDAO {
    private final ConnectionPool CONNECTION_POOL = ConnectionPool.getUniqueInstance();

    public List<Human> listMovieCrew(Long movieId, int languageId) throws SQLException, ConnectionNotFoundException {
        List<Human> humanList = new ArrayList<>();
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_MOVIE_CREW_SQL_QUERY)) {
            preparedStatement.setLong(1, movieId);
            preparedStatement.setInt(2, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Human human = new Human();
                human = setParametersToHuman(human, resultSet);
                human.setRoleId(resultSet.getInt("HUMAN_ROLE"));
                humanList.add(human);
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return humanList;
    }

    public List<Human> listHuman(int languageId) throws SQLException, ConnectionNotFoundException {
        List<Human> humanList = new ArrayList<>();
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_ALL_AVAILABLE_HUMAN_SQL_QUERY)) {
            preparedStatement.setInt(1, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Human human = new Human();
                human = setParametersToHuman(human, resultSet);
                humanList.add(human);
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return humanList;
    }

    public Human getHumanById(Long humanId, int languageId) throws SQLException, ConnectionNotFoundException {
        Human human = new Human();
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_HUMAN_BY_ID_SQL_QUERY)) {
            preparedStatement.setLong(1, humanId);
            preparedStatement.setInt(2, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                human = new Human();
                human = setParametersToHuman(human, resultSet);
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return human;
    }

    public Boolean checkForMovieLinks(Long humanId) throws SQLException, ConnectionNotFoundException {
        boolean isChecked = false;
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_LINKS_HUMAN_TO_MOVIE_SQL_QUERY)) {
            preparedStatement.setLong(1, humanId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                isChecked = true;
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
        return isChecked;
    }

    public void deleteHuman(long humanId) throws SQLException, ConnectionNotFoundException {
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement pr = connection.prepareStatement(DELETE_HUMAN_SQL_QUERY)) {
            pr.setLong(1, humanId);
            pr.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    public void deleteHumanMultiLanguageParameters(long humanId) throws SQLException, ConnectionNotFoundException {
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement pr = connection.prepareStatement(DELETE_HUMAN_CHARACTERISTIC_SQL_QUERY)) {
            pr.setLong(1, humanId);
            pr.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    public void addHuman(Human human) throws SQLException, ConnectionNotFoundException {
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_HUMAN_SQL_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            getHumanParameters(human, preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet humanId = preparedStatement.getGeneratedKeys();
            if (humanId.next()) {
                human.setId(humanId.getLong(1));
            }
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    public void addHumanMultiLanguageParameters(Human human, int languageId) throws SQLException, ConnectionNotFoundException {
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_CHARACTERISTIC_OF_HUMAN_SQL_QUERY)) {
            getHumanMultiLanguageParameters(human, preparedStatement);
            preparedStatement.setLong(5, human.getId());
            preparedStatement.setLong(6, languageId);
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    public void updateHuman(Human human, int languageId) throws SQLException, ConnectionNotFoundException {
        Connection connection = CONNECTION_POOL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_HUMAN_SQL_QUERY)) {
            getHumanMultiLanguageParameters(human, preparedStatement);
            preparedStatement.setDate(5, Date.valueOf(human.getBirthDate()));
            preparedStatement.setString(6, human.getImageURL());
            preparedStatement.setLong(7, human.getId());
            preparedStatement.setLong(8, languageId);
            preparedStatement.executeUpdate();
        } finally {
            CONNECTION_POOL.putBack(connection);
        }
    }

    private void getHumanParameters(Human human, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setDate(1, Date.valueOf(human.getBirthDate()));
        preparedStatement.setString(2, human.getImageURL());
    }

    private void getHumanMultiLanguageParameters(Human human, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, human.getName());
        preparedStatement.setString(2, human.getSurname());
        preparedStatement.setString(3, human.getPatronymic());
        preparedStatement.setString(4, human.getBiography());
    }

    private Human setParametersToHuman(Human human, ResultSet resultSet) throws SQLException {
        human.setId(resultSet.getLong("HUMAN_ID"));
        human.setImageURL(resultSet.getString("HUMAN_IMAGE_URL"));
        human.setBirthDate(LocalDate.parse(resultSet.getDate("HUMAN_BIRTH_DATE").toString()));
        human.setName(resultSet.getString("HUMAN_NAME"));
        human.setSurname(resultSet.getString("HUMAN_SURNAME"));
        human.setPatronymic(resultSet.getString("HUMAN_PATRONYMIC"));
        human.setBiography(resultSet.getString("HUMAN_BIOGRAPHY"));
        return human;
    }
}