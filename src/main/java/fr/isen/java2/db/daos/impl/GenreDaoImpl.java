package fr.isen.java2.db.daos.impl;

import fr.isen.java2.db.entities.Genre;
import fr.isen.java2.db.mapper.impl.GenreResultMapper;
import fr.isen.java2.db.util.QueryExecutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolas on 13/02/17.
 */
public class GenreDaoImpl implements fr.isen.java2.db.daos.GenreDao
{
    @Override
    public List<Genre> listGenres() throws Exception
    {
        List<Genre> returnedList = new ArrayList<>();
        GenreResultMapper genreResultMapper = new GenreResultMapper(returnedList);
        String sqlQuery = "SELECT * FROM genre";
        QueryExecutor.executeSelectQuery(sqlQuery, genreResultMapper);
        return genreResultMapper.getParsedList();
    }


    @Override
    public Genre getGenre(String name) throws Exception
    {
        Genre returnedGenre = null;
        try (Connection connection = DataSourceFactory.getDataSource().getConnection())
        {
            String sqlQuery = "SELECT * FROM genre WHERE name = ?";
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery))
            {
                statement.setString(1, name);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next())
                {
                    returnedGenre = new Genre(resultSet.getInt("idgenre"), resultSet.getString("name"));
                }
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return returnedGenre;
    }


    @Override
    public void addGenre(String name) throws Exception
    {
        try (Connection connection = DataSourceFactory.getDataSource().getConnection())
        {
            String sqlQuery = "INSERT INTO genre(name) VALUES(?)";
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery))
            {
                statement.setString(1, name);
                int nbRows = statement.executeUpdate();
                System.out.println(String.format("%d row(s) has been inserted.", nbRows));
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        } catch (SQLException e)
        {
            System.out.println("not inserted");
            e.printStackTrace();
        }
    }


}
