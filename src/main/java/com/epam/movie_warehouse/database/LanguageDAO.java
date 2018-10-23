package com.epam.movie_warehouse.database;

import com.epam.movie_warehouse.entity.Language;
import com.epam.movie_warehouse.exception.ConnectionNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.movie_warehouse.util.DAOConstant.SHOW_ALL_LANGUAGE_SQL_QUERY;
import static com.epam.movie_warehouse.util.DAOConstant.SHOW_LANGUAGE_BY_ID_SQL_QUERY;

public class LanguageDAO {
    private final ConnectionPull CONNECTION_PULL = ConnectionPull.getUniqueInstance();

    public List<Language> listLanguage() throws SQLException, ConnectionNotFoundException {
        List<Language> languageList = new ArrayList<>();
        Connection connection = CONNECTION_PULL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_ALL_LANGUAGE_SQL_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Language language = new Language();
                setParametersToLanguage(language, resultSet);
                languageList.add(language);
            }
        }
        CONNECTION_PULL.putBack(connection);
        return languageList;
    }

    public Language getLanguageById(int languageId) throws SQLException, ConnectionNotFoundException {
        Language language = new Language();
        Connection connection = CONNECTION_PULL.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_LANGUAGE_BY_ID_SQL_QUERY)) {
            preparedStatement.setLong(1, languageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                setParametersToLanguage(language, resultSet);
            }
            resultSet.close();
        }
        CONNECTION_PULL.putBack(connection);
        return language;
    }

    private void setParametersToLanguage(Language language, ResultSet resultSet) throws SQLException {
        language.setId(resultSet.getInt("LANGUAGE_ID"));
        language.setName(resultSet.getString("LANGUAGE_NAME"));
        language.setLocal(resultSet.getString("LANGUAGE_LOCALE"));
        language.setDateFormat(resultSet.getString("LANGUAGE_DATE_FORMAT"));
    }
}
