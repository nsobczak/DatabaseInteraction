package fr.isen.java2.db.daos;

import fr.isen.java2.db.entities.Movie;

import java.util.List;

public interface MovieDao
{

    List<Movie> listMovies() throws Exception;

    List<Movie> listMoviesByGenre(String genreName) throws Exception;

    Movie addMovie(Movie movie) throws Exception;
}
