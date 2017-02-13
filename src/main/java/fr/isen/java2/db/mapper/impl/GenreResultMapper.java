package fr.isen.java2.db.mapper.impl;

import fr.isen.java2.db.entities.Genre;
import fr.isen.java2.db.mapper.ResultMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolas on 13/02/17.
 */
public class GenreResultMapper implements ResultMapper<Genre>
{
    private List<Genre> genreList;


    public GenreResultMapper(List<Genre> genreList)
    {
        this.genreList = genreList;
    }

    public GenreResultMapper()
    {
        this.genreList = null;
    }

    @Override
    public List<Genre> getParsedList() throws SQLException
    {
        return this.genreList;
    }


    @Override
    public void parseResultSet(ResultSet resultSet) throws SQLException
    {
        while (resultSet.next())
        {
            Genre genre = new Genre(resultSet.getInt("idgenre"), resultSet.getString("name"));
            this.genreList.add(genre);
        }
    }


}
