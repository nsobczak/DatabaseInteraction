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
        GenreResultMapper genreResultMapper = new GenreResultMapper();
        String sqlQuery = "SELECT * FROM genre WHERE name = ?";
        QueryExecutor.executeSelectQuery(sqlQuery, genreResultMapper, name);
        return (genreResultMapper.getParsedList().isEmpty()) ? null : genreResultMapper.getParsedList().get(0);
    }


    @Override
    public void addGenre(String name) throws Exception
    {
        String sqlQuery = "INSERT INTO genre(name) VALUES(?)";
        QueryExecutor.executeUpdateQuery(sqlQuery, name);
    }


}
