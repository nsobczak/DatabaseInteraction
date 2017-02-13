package fr.isen.java2.db.daos;

import fr.isen.java2.db.entities.Genre;

import java.util.List;

public interface GenreDao {

	List<Genre> listGenres() throws Exception;

	Genre getGenre(String name) throws Exception;
	
	void addGenre(String name) throws Exception;
}
