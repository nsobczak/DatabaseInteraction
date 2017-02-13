package fr.isen.java2.db.daos.impl;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import fr.isen.java2.db.entities.Genre;
import fr.isen.java2.db.entities.Movie;

import java.sql.*;
import java.time.LocalDate;
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

        try (Connection connection = DataSourceFactory.getDataSource().getConnection())
        {
            String sqlQuery = "SELECT * FROM movie JOIN genre ON movie.genre_id = genre.idgenre";
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery))
            {
                ResultSet resultSet = statement.executeQuery();
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
                    returnedList.add(movie);
                }

            } catch (SQLException e)
            {
                e.printStackTrace();
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return returnedList;
    }


    @Override
    public List<Movie> listMoviesByGenre(String genreName) throws Exception
    {
        List<Movie> returnedList = new ArrayList<>();

        try (Connection connection = DataSourceFactory.getDataSource().getConnection())
        {
            String sqlQuery = "SELECT * FROM movie JOIN genre " +
                    "ON movie.genre_id = genre.idgenre WHERE genre.name = ?";
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery))
            {
                statement.setString(1, genreName);
                ResultSet resultSet = statement.executeQuery();
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
                    returnedList.add(movie);
                }

            } catch (SQLException e)
            {
                e.printStackTrace();
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return returnedList;
    }


    @Override
    public Movie addMovie(Movie movie) throws Exception
    {
        try (Connection connection = DataSourceFactory.getDataSource().getConnection())
        {
            String sqlQuery = "INSERT INTO movie(title,release_date,genre_id,duration,director,summary) " +
                    "VALUES(?,?,?,?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS))
            {
                statement.setString(1, movie.getTitle());
                statement.setObject(2, movie.getReleaseDate(), Types.DATE);
                statement.setInt(3, movie.getGenre().getId());
                statement.setInt(4, movie.getDuration());
                statement.setString(5, movie.getDirector());
                statement.setString(6, movie.getSummary());
                int nbRows = statement.executeUpdate();
                System.out.println(String.format("%d row(s) has been inserted.", nbRows));

                ResultSet ids = statement.getGeneratedKeys();
                if (ids.next())
                {
                    int generatedId = (int) ids.getLong(1);
                    movie.setId(generatedId);
                }
                else{
                    System.out.println("can't find generated id");
                }
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        } catch (SQLException e)
        {
            System.out.println("not inserted");
            e.printStackTrace();
        }

        return movie;
    }


}
