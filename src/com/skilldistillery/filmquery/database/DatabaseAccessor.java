package com.skilldistillery.filmquery.database;

import java.util.List;
import java.util.Map;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public interface DatabaseAccessor {
  public Film getFilmById(int filmId);
  public Actor getActorById(int actorId);
  public List<Actor> getActorsByFilmId(int filmId);
  public List<Film> findFilmByKeyword(String keyword); 
  public List<String> findCategoryByFilmId(int filmId); 
  public Map<Integer, String> findCopiesAndCondition(int filmId); 
}
