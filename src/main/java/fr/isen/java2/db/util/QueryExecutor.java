package fr.isen.java2.db.util;

import fr.isen.java2.db.daos.impl.DataSourceFactory;
import fr.isen.java2.db.mapper.ResultMapper;
import fr.isen.java2.db.mapper.impl.GeneratedKeysMapper;

import java.sql.*;
import java.time.LocalDate;

public class QueryExecutor
{

    public static void executeSelectQuery(String query, ResultMapper mapper, Object... parameters) throws Exception
    {
        try (Connection connection = DataSourceFactory.getDataSource().getConnection())
        {
            try (PreparedStatement statement = connection.prepareStatement(query))
            {
                QueryExecutor.mapParameters(statement, parameters);
                ResultSet resultSet = statement.executeQuery();
                mapper.parseResultSet(resultSet);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    public static ResultMapper executeUpdateQuery(String query, Object... parameters) throws Exception
    {
        GeneratedKeysMapper generatedKeysMapper = new GeneratedKeysMapper();
        try (Connection connection = DataSourceFactory.getDataSource().getConnection())
        {
            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
            {
                QueryExecutor.mapParameters(statement, parameters);
                int nbRows = statement.executeUpdate();
                System.out.println(String.format("%d rows updated", nbRows));

                ResultSet ids = statement.getGeneratedKeys();
                generatedKeysMapper.parseResultSet(ids);
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return generatedKeysMapper;
    }


    private static void mapParameters(final PreparedStatement statement, final Object[] parameters) throws SQLException
    {
        for (int i = 0; i < parameters.length; i++)
        {
            Object currentParameter = parameters[i];
            if (currentParameter instanceof String)
            {
                statement.setString(i + 1, String.valueOf(currentParameter));
            }
            if (currentParameter instanceof Integer)
            {
                statement.setInt(i + 1, (Integer) currentParameter);
            }
            if (currentParameter instanceof LocalDate)
            {
                statement.setObject(i + 1, currentParameter, Types.DATE);
            }
        }
    }


}
