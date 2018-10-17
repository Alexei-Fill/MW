package com.epam.movie_warehouse.dao;

import com.epam.movie_warehouse.connectionPull.ConnectionPull;
import com.epam.movie_warehouse.entity.Human;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HumanDAO {
    private static final String SHOW_MOVIE_CREW = "SELECT * from ROLE_HUMAN_IN_MOVIE RH, HUMAN H, CHARACTERISTICS_OF_HUMAN CH where" +
            " RH.HUMAN_ID = H.HUMAN_ID and  CH.HUMAN_ID = H.HUMAN_ID  and RH.MOVIE_ID = ? and CH.LANGUAGE_ID = ? ORDER BY CH.HUMAN_NAME";
    private static final String SHOW_ALL_AVAILABLE_HUMAN = "SELECT * FROM HUMAN H, CHARACTERISTICS_OF_HUMAN CH where  CH.HUMAN_ID = H.HUMAN_ID  " +
            " and CH.LANGUAGE_ID = ? ORDER BY CH.HUMAN_NAME";
    private static final String SHOW_HUMAN_BY_ID = "SELECT * FROM HUMAN H, CHARACTERISTICS_OF_HUMAN CH where  CH.HUMAN_ID = H.HUMAN_ID  " +
            " and H.HUMAN_ID = ? and CH.LANGUAGE_ID = ?";
    private static final String ADD_HUMAN = "INSERT INTO HUMAN (HUMAN_BIRTH_DATE, HUMAN_IMAGE_URL) VALUES (?, ?)";
    private static final String ADD_CHARACTERISTIC_OF_HUMAN = "INSERT INTO CHARACTERISTICS_OF_HUMAN (HUMAN_NAME, HUMAN_SURNAME," +
            " HUMAN_PATRONYMIC, HUMAN_BIOGRAPHY, HUMAN_ID, LANGUAGE_ID)  VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_HUMAN = "UPDATE HUMAN H, CHARACTERISTICS_OF_HUMAN CH SET CH.HUMAN_NAME = ?, " +
            " CH.HUMAN_SURNAME = ?, CH.HUMAN_PATRONYMIC = ?, CH.HUMAN_BIOGRAPHY = ?, H.HUMAN_BIRTH_DATE = ?, H.HUMAN_IMAGE_URL = ?  " +
            "WHERE CH.HUMAN_ID = H.HUMAN_ID and H.HUMAN_ID = ? and CH.LANGUAGE_ID = ?";
    private static final String CHECK_LINKS_HUMAN_TO_MOVIE = "SELECT * FROM ROLE_HUMAN_IN_MOVIE WHERE HUMAN_ID = ?";
    private static final String DELETE_HUMAN = "DELETE from HUMAN WHERE HUMAN_ID = ?";
    private static final String DELETE_HUMAN_CHARACTERISTIC = "DELETE from CHARACTERISTICS_OF_HUMAN WHERE HUMAN_ID = ?";
    private ConnectionPull connectionPull = ConnectionPull.getUniqueInstance();

    public List<Human> showMovieCrew(Long movieId, int languageId) throws SQLException{
        List<Human> humans = new ArrayList<>();
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_MOVIE_CREW)) {
            preparedStatement.setLong(1, movieId);
            preparedStatement.setInt(2, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Human human = new Human();
                human = setHumanParameters(human, resultSet);
                human.setRoleId(resultSet.getInt("HUMAN_ROLE"));
                humans.add(human);
            }
            resultSet.close();
        }
        connectionPull.putBack(connection);
        return humans;
    }

    public List<Human> listGenre(int languageId) throws SQLException {
        List<Human> humans = new ArrayList<>();
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_ALL_AVAILABLE_HUMAN)) {
            preparedStatement.setInt(1, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Human human = new Human();
                human = setHumanParameters(human, resultSet);
                humans.add(human);
            }
            resultSet.close();
        }
        connectionPull.putBack(connection);
        return humans;
    }

    public Human showHumanById(Long humanId, int languageId) throws SQLException {
        Human human = null;
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_HUMAN_BY_ID)) {
            preparedStatement.setLong(1, humanId);
            preparedStatement.setInt(2, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                human = new Human();
                human = setHumanParameters(human, resultSet);
            }
            resultSet.close();
        }
        connectionPull.putBack(connection);
        return human;
    }

    public Boolean checkLinksHumanToMovie(Long humanId) throws SQLException {
        boolean isChecked = false;
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_LINKS_HUMAN_TO_MOVIE)) {
            preparedStatement.setLong(1, humanId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                isChecked = true;
            }
            resultSet.close();
        }
        connectionPull.putBack(connection);
        return isChecked;
    }

    public void deleteHuman (long humanId) throws SQLException {
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement pr = connection.prepareStatement(DELETE_HUMAN)){
            pr.setLong(1, humanId);
            pr.executeUpdate();
        }
        connectionPull.putBack(connection);
    }

    public void deleteHumanCharacteristic (long humanId) throws SQLException {
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement pr = connection.prepareStatement(DELETE_HUMAN_CHARACTERISTIC)){
            pr.setLong(1, humanId);
            pr.executeUpdate();
        }
        connectionPull.putBack(connection);
    }

    public void addHuman(Human human) throws SQLException {
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_HUMAN, Statement.RETURN_GENERATED_KEYS)) {
            getHumanParameters(human, preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet humanId = preparedStatement.getGeneratedKeys();
            if (humanId.next()){
                human.setId(humanId.getLong(1));
            }
        }
        connectionPull.putBack(connection);
    }

    public void addHumanCharacteristic(Human human, int languageId) throws SQLException {
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_CHARACTERISTIC_OF_HUMAN)) {
            getHumanCharacteristic(human, preparedStatement);
            preparedStatement.setLong(5, human.getId());
            preparedStatement.setLong(6, languageId);
            preparedStatement.executeUpdate();
        }
        connectionPull.putBack(connection);
    }

    public void updateHuman (Human human, int languageId) throws SQLException {
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_HUMAN)){
            getHumanCharacteristic(human, preparedStatement);
            preparedStatement.setDate(5, Date.valueOf(human.getBirthDate()));
            preparedStatement.setString(6, human.getImageURL());
            preparedStatement.setLong(7, human.getId());
            preparedStatement.setLong(8, languageId);
            preparedStatement.executeUpdate();
        }
        connectionPull.putBack(connection);
    }

    private void getHumanParameters (Human human, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setDate(1, Date.valueOf(human.getBirthDate()));
        preparedStatement.setString(2, human.getImageURL());
    }

    private void getHumanCharacteristic (Human human, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, human.getName());
        preparedStatement.setString(2, human.getSurname());
        preparedStatement.setString(3, human.getPatronymic());
        preparedStatement.setString(4, human.getBiography());
    }

    private Human setHumanParameters (Human human, ResultSet resultSet) throws SQLException {
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