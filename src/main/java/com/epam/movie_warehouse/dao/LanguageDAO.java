package com.epam.movie_warehouse.dao;

import com.epam.movie_warehouse.connectionPull.ConnectionPull;
import com.epam.movie_warehouse.entity.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.movie_warehouse.util.DAOConstant.SHOW_ALL_LANGUAGE;
import static com.epam.movie_warehouse.util.DAOConstant.SHOW_LANGUAGE_BY_ID;

public class LanguageDAO {
    private ConnectionPull connectionPull = ConnectionPull.getUniqueInstance();

    public List<Language> showAllLanguages() throws SQLException {
        List<Language> languages = new ArrayList<>();
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_ALL_LANGUAGE);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()){
                Language language = new Language();
                setLanguageParameters(language, resultSet);
                languages.add(language);
            }
        }
        connectionPull.putBack(connection);
        return languages;
    }

    public Language showLanguageById(int languageId) throws SQLException {
        Language language = new Language();
        Connection connection = connectionPull.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_LANGUAGE_BY_ID)) {
            preparedStatement.setLong(1, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                setLanguageParameters(language, resultSet);
            }
            resultSet.close();
        }
        connectionPull.putBack(connection);
        return language;
    }
    private void setLanguageParameters (Language language, ResultSet resultSet)  throws SQLException{
        language.setId(resultSet.getInt("LANGUAGE_ID"));
        language.setName(resultSet.getString("LANGUAGE_NAME"));
        language.setLocal(resultSet.getString("LANGUAGE_LOCALE"));
        language.setDateFormat(resultSet.getString("LANGUAGE_DATE_FORMAT"));
    }
}
