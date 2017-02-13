package fr.isen.java2.db.daos.impl;

import fr.isen.java2.db.daos.GenreDao;
import fr.isen.java2.db.daos.MovieDao;
import fr.isen.java2.db.entities.Movie;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MovieDaoImplTestCase {

    private MovieDao movieDao;
    private GenreDao genreDao;


    @Before
    public void initDb() throws Exception {
        movieDao = new MovieDaoImpl();
        genreDao = new GenreDaoImpl();
        Connection connection = DataSourceFactory.getDataSource().getConnection();
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM movie");
        stmt.executeUpdate("DELETE FROM genre");
        stmt.executeUpdate("INSERT INTO genre(idgenre,name) VALUES (1,'Drama')");
        stmt.executeUpdate("INSERT INTO genre(idgenre,name) VALUES (2,'Comedy')");
        stmt.executeUpdate("INSERT INTO genre(idgenre,name) VALUES (3,'Western')");
        stmt.executeUpdate("INSERT INTO movie(idmovie,title, release_date, genre_id, duration, director, summary) "
                + "VALUES (1, 'Title 1', '2015-11-26', 1, 120, 'director 1', 'summary of the first movie')");
        stmt.executeUpdate("INSERT INTO movie(idmovie,title, release_date, genre_id, duration, director, summary) "
                + "VALUES (2, 'My Title 2', '2015-11-14', 2, 114, 'director 2', 'summary of the second movie')");
        stmt.executeUpdate("INSERT INTO movie(idmovie,title, release_date, genre_id, duration, director, summary) "
                + "VALUES (3, 'Third title', '2015-12-12', 2, 176, 'director 3', 'summary of the third movie')");
        stmt.close();
        connection.close();
    }


    @Test
    public void shouldListMovies() throws Exception {
        // WHEN
        List<Movie> movies = movieDao.listMovies();
        // THEN
        assertThat(movies).hasSize(3);
        assertThat(movies).extracting("director", "title").containsOnly(tuple("director 1", "Title 1"), tuple("director 2", "My Title 2"),
                tuple("director 3", "Third title"));
    }


    @Test
    public void shouldListMoviesByGenre() throws Exception {
        // WHEN
        List<Movie> movies = movieDao.listMoviesByGenre("Comedy");
        // THEN
        assertThat(movies).hasSize(2);
        assertThat(movies).extracting("director", "title").containsOnly(tuple("director 2", "My Title 2"),
                tuple("director 3", "Third title"));
    }


    @Test
    public void shouldAddMovie() throws Exception {
        // GIVEN
        Movie movieToAdd = new Movie();
        movieToAdd.setTitle("TitleTest");
        movieToAdd.setDirector("DirectorTest");
        movieToAdd.setDuration(42);
        movieToAdd.setGenre(genreDao.getGenre("Western"));
        movieToAdd.setReleaseDate(LocalDate.of(2017,2,13));
        movieToAdd.setSummary("SummaryTest");
        // WHEN
        movieDao.addMovie(movieToAdd);
        // THEN
        List<Movie> movies = movieDao.listMoviesByGenre("Western");
        assertThat(movies).hasSize(1);
        assertThat(movies.get(0).getId()).isNotNull();
        assertThat(movies.get(0).getTitle()).isEqualTo(movieToAdd.getTitle());
        assertThat(movies.get(0).getDirector()).isEqualTo(movieToAdd.getDirector());
        assertThat(movies.get(0).getSummary()).isEqualTo(movieToAdd.getSummary());
        assertThat(movies.get(0).getDuration()).isEqualTo(movieToAdd.getDuration());
    }
}
