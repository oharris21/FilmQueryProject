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

//	private void test() {
//
//	}

	private void launch() {

		startUserInterface();
		int choice = getInput();
		performAction(choice);

		input.close();
	}

	private void startUserInterface() {
		System.out.println("------------------------------------------");
		System.out.println("|                MENU                    |");
		System.out.println("| (1) Look up a film by it's ID          |");
		System.out.println("| (2) Look up a film by a search keyword |");
		System.out.println("| (3) Exit                               |");
		System.out.println("------------------------------------------\n");
	}

	private int getInput() {
		int option = 0;
		try {
			option = input.nextInt();
		} catch (Exception e) {
			System.out.println("Please enter a valid choice.");
			startUserInterface();
		}
		return option;
	}

	private void performAction(int choice) {

		if (choice < 1 || choice > 3) {
			System.out.println("Please enter a valid choice.");
			startUserInterface();
		}

		switch (choice) {
		case 1:
			choice1App();
			break;
		case 2:
			choice2App();
			break;
		case 3:
			System.out.println("Bye.");
			System.exit(0);
			break;
		}
	}

	private void choice1App() {
		System.out.print("Enter the ID: ");
		int idNumber = input.nextInt();

		Film film = db.getFilmById(idNumber);

		if (film.getTitle() == null) {
			System.out.println("We couldn't find any films with that ID number.");
		} else {
			System.out.println("\nTitle: " + film.getTitle());
			System.out.println("Year: " + film.getReleaseYear());
			System.out.println("Rating: " + film.getRating());
			System.out.println("Description: " + film.getDescription());
			System.out.println("Language: " + film.getLanguage());
			System.out.println("\nActors: ");
			int id = 0;
			id = film.getId();
			List<Actor> a = db.getActorsByFilmId(id);
			
			for (Actor act : a) {
				System.out.println(act.getFirstName() + " " + act.getLastName());
			}
		}
	}

	private void choice2App() {
		System.out.print("Enter the keyword: ");
		String keyword = input.next();

		List<Film> films = db.findFilmByKeyword(keyword);

		if (films.size() == 0) {
			System.out.println("We couldn't find any films matching your search.");
		} else {
			int id = 0;
			for (Film film : films) {
				System.out.println("\n- - - - - - - - - - - - - - - - - - - - - - -");
				System.out.println("\nTitle: " + film.getTitle());
				System.out.println("Year: " + film.getReleaseYear());
				System.out.println("Rating: " + film.getRating());
				System.out.println("Description: " + film.getDescription());
				System.out.println("Language: " + film.getLanguage());
				System.out.println("\nActors: ");
				id = film.getId();
				List<Actor> a = db.getActorsByFilmId(id);
				
				for (Actor act : a) {
					System.out.println(act.getFirstName() + " " + act.getLastName());
				}
				System.out.println("- - - - - - - - - - - - - - - - - - - - - - -");
			}
		}
	}
}
