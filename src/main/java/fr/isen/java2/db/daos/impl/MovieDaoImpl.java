package fr.isen.java2.db.daos.impl;

import fr.isen.java2.db.entities.Movie;
import fr.isen.java2.db.mapper.ResultMapper;
import fr.isen.java2.db.mapper.impl.MovieResultMapper;
import fr.isen.java2.db.util.QueryExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolas on 13/02/17.
 */
public class MovieDaoImpl implements fr.isen.java2.db.daos.MovieDao
{
    @Override
    public List<Movie> listMovies() throws Exception
    {
        List<Movie> returnedList = new ArrayList<>();
        MovieResultMapper movieResultMapper = new MovieResultMapper(returnedList);
        String sqlQuery = "SELECT * FROM movie JOIN genre ON movie.genre_id = genre.idgenre";
        QueryExecutor.executeSelectQuery(sqlQuery, movieResultMapper);
        return movieResultMapper.getParsedList();
    }


    @Override
    public List<Movie> listMoviesByGenre(String genreName) throws Exception
    {
        MovieResultMapper movieResultMapper = new MovieResultMapper();
        String sqlQuery = "SELECT * FROM movie JOIN genre " +
                "ON movie.genre_id = genre.idgenre WHERE genre.name = ?";
        QueryExecutor.executeSelectQuery(sqlQuery, movieResultMapper, genreName);
        return (movieResultMapper.getParsedList());
    }


    @Override
    public Movie addMovie(Movie movie) throws Exception
    {
        String sqlQuery = "INSERT INTO movie(title,release_date,genre_id,duration,director,summary) " +
                "VALUES(?,?,?,?,?,?)";
        ResultMapper resultMapper = QueryExecutor.executeUpdateQuery(sqlQuery,
                movie.getTitle(), movie.getReleaseDate(),
                movie.getGenre(), movie.getDuration(),
                movie.getDirector(), movie.getSummary());

        //TODO: set movie id

        return movie;
    }


}
