package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	private String user = "student";
	private String pass = "student";
	Film f = new Film();

	public Actor getActorById(int actorId) {
		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		Actor actor = new Actor();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet actorResult = stmt.executeQuery();
			if (actorResult.next()) {
				actor.setId(actorResult.getInt(1));
				actor.setFirstName(actorResult.getString(2));
				actor.setLastName(actorResult.getString(3));
				actor.setFilms(getFilmsByActorId(actorId)); 
			}
			actorResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actor;
	}

	public List<Film> getFilmsByActorId(int actorId) {
		List<Film> films = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT film.id, title, description, release_year, language_id, rental_duration,\n" + 
					"			 rental_rate, length, replacement_cost, rating, special_features, name,\n" + 
					"       first_name, last_name\n" + 
					"FROM film \n" + 
					"JOIN film_actor \n" + 
					"ON film.id = film_actor.film_id\n" + 
					"JOIN language \n" + 
					"ON film.language_id = language.id \n" + 
					"JOIN actor \n" + 
					"ON actor.id = film_actor.actor_id\n" + 
					"WHERE actor_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int filmId = rs.getInt(1);
				String title = rs.getString(2);
				String desc = rs.getString(3);
				short releaseYear = rs.getShort(4);
				int langId = rs.getInt(5);
				int rentDur = rs.getInt(6);
				double rate = rs.getDouble(7);
				int length = rs.getInt(8);
				double repCost = rs.getDouble(9);
				String rating = rs.getString(10);
				String features = rs.getString(11);
				String language = rs.getString(12); 
				Film film = new Film(filmId, title, desc, releaseYear, langId, rentDur, rate, length, repCost, rating,
						features, language);
				films.add(film);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}

	@Override
	public Film getFilmById(int filmId) {
		// need list of actors 
		String sql = "SELECT film.id, title, description, release_year, language_id, rental_duration,\n" + 
				"       rental_rate, length, replacement_cost, rating, special_features, name\n" + 
				"FROM film\n" + 
				"JOIN language\n" + 
				"ON film.language_id = language.id\n" + 
				"WHERE film.id = ?";
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet filmResult = stmt.executeQuery();
			if (filmResult.next()) {
				f.setId(filmResult.getInt(1));
				f.setTitle(filmResult.getString(2));
				f.setDescription(filmResult.getString(3));
				f.setReleaseYear(filmResult.getInt(4));
				f.setLanguageId(filmResult.getInt(5));
				f.setRentalDuration(filmResult.getInt(6));
				f.setRentalRate(filmResult.getDouble(7));
				f.setLength(filmResult.getInt(8));
				f.setReplacementCost(filmResult.getDouble(9));
				f.setRating(filmResult.getString(10));
				f.setSpecialFeatures(filmResult.getString(11));
				f.setLanguage(filmResult.getString(12));

				// f.setActors(getActorsByFilmId(filmId));
				
				List<Actor> actors = getActorsByFilmId(filmId);
				f.setActors(actors);
			}
			filmResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return f;
	}

	@Override
	public List<Actor> getActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT a.id, a.first_name, a.last_name \n"
			+ "FROM actor a\n" + "JOIN film_actor fa\n"
					+ "ON a.id = fa.actor_id WHERE fa.film_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int actorId = rs.getInt(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				Actor actor = new Actor(actorId, firstName, lastName);
				actors.add(actor);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		System.out.println(actors);
		return actors;
	}

	public List<Film> findFilmByKeyword(String keyword) {
		List<Film> films = new ArrayList<>(); 
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT film.id, title, description, release_year, language_id, rental_duration, \n" + 
					"			 rental_rate, length, replacement_cost, rating, special_features, language.name\n" + 
					"FROM film\n" + 
					"JOIN language\n" + 
					"ON film.language_id = language.id\n" + 
					"WHERE title LIKE ? OR description LIKE ?";
			PreparedStatement stmt = conn.prepareStatement(sql); 
			stmt.setString(1, "%" + keyword + "%");
			stmt.setString(2, "%" + keyword + "%");
			ResultSet rs = stmt.executeQuery(); 
			while (rs.next()) {
				int filmId = rs.getInt(1);
				String title = rs.getString(2);
				String desc = rs.getString(3);
				short releaseYear = rs.getShort(4);
				int langId = rs.getInt(5);
				int rentDur = rs.getInt(6);
				double rate = rs.getDouble(7);
				int length = rs.getInt(8);
				double repCost = rs.getDouble(9);
				String rating = rs.getString(10);
				String features = rs.getString(11);
				String language = rs.getString(12); 
				Film film = new Film(filmId, title, desc, releaseYear, langId, rentDur, rate, length, repCost, rating,
						features, language);
				films.add(film);
				
				List<Actor> actors = getActorsByFilmId(filmId);
				f.setActors(actors);
//				System.out.println(actors);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}
}