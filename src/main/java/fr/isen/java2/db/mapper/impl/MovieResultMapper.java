package fr.isen.java2.db.mapper.impl;

import fr.isen.java2.db.entities.Genre;
import fr.isen.java2.db.entities.Movie;
import fr.isen.java2.db.mapper.ResultMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by nicolas on 13/02/17.
 */
public class MovieResultMapper implements ResultMapper<Movie>
{
    private List<Movie> movieList;


    public MovieResultMapper()
    {
        this.movieList = null;
    }


    public MovieResultMapper(List<Movie> returnedList)
    {
        this.movieList = returnedList;
    }


    @Override
    public List<Movie> getParsedList() throws SQLException
    {
        return this.movieList;
    }


    @Override
    public void parseResultSet(ResultSet resultSet) throws SQLException
    {
        while (resultSet.next())
        {
            //Movie(Integer id, String title, LocalDate releaseDate,
            // Genre genre, Integer duration, String director,String summary)
            Movie movie = new Movie(resultSet.getInt("idmovie"),
                    resultSet.getString("title"),
                    resultSet.getObject("release_date", LocalDate.class), //cast l'objet release_date en local date
                    new Genre(resultSet.getInt("genre_id"), resultSet.getString("name")),
                    resultSet.getInt("duration"),
                    resultSet.getString("director"),
                    resultSet.getString("summary")
            );
            this.movieList.add(movie);
        }
    }


}
