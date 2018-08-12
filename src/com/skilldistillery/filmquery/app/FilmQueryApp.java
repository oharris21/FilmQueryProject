package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {
	DatabaseAccessor db = new DatabaseAccessorObject();
	Scanner input = new Scanner(System.in);

	public static void main(String[] args) throws SQLException {
		FilmQueryApp app = new FilmQueryApp();
//    app.test();
		app.launch();
	}

	private void test() {

	}

	private void launch() {

		startUserInterface();

		input.close();
	}

	private void startUserInterface() {
		String option;

		System.out.println("------------------------------------------");
		System.out.println("|                MENU                    |");
		System.out.println("| (1) Look up a film by it's ID          |");
		System.out.println("| (2) Look up a film by a search keyword |");
		System.out.println("| (3) Exit                               |");
		System.out.println("------------------------------------------\n");
		option = input.nextLine();

		if (option.equals("1")) {
			System.out.print("Enter the ID: ");
			int idNumber = input.nextInt();

			Film film = db.getFilmById(idNumber);

			if (film.getTitle() == null) {
				System.out.println("We couldn't find any films with that ID number.");
			} else {
				System.out.println(film);
				int id = 0;
				id = film.getId();
				System.out.println(db.getActorsByFilmId(id));
			}
			startUserInterface();
		}
		if (option.equals("2")) {
			System.out.print("Enter the keyword: ");
			String keyword = input.next();

			List<Film> films = db.findFilmByKeyword(keyword);

			if (films.size() == 0) {
				System.out.println("We couldn't find any films matching your search.");
			} else {
				int id = 0;
				for (Film film : films) {
					System.out.println(film);
					id = film.getId();
					System.out.println(db.getActorsByFilmId(id));
				}
			}
			startUserInterface();
		}
		if (option.equals("3")) {
			System.out.println("Bye");
			System.exit(0);
		}
	}
}
