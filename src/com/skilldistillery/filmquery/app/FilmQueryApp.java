package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {
	DatabaseAccessor db = new DatabaseAccessorObject();
	Scanner input = new Scanner(System.in);

	// main 
	public static void main(String[] args) throws SQLException {
		FilmQueryApp app = new FilmQueryApp();
		app.launch();
	}

	private void launch() {
		startUserInterface();
		int choice = getInput();
		performAction(choice);
		input.close();
	}

	// main menu
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

	// logic for main menu
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
	
	// logic for menu option 1
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
			System.out.println("ID: " + film.getId());
			System.out.println("\nActors: ");
			int id = 0;
			id = film.getId();
			List<Actor> a = db.getActorsByFilmId(id);

			for (Actor act : a) {
				System.out.println(act.getFirstName() + " " + act.getLastName());
			}
		}
		submenu();
//		launch(); 
	}

	// logic for menu option 2
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
				System.out.println("ID: " + film.getId());
				System.out.println("\nActors: ");
				id = film.getId();
				List<Actor> a = db.getActorsByFilmId(id);

				for (Actor act : a) {
					System.out.println(act.getFirstName() + " " + act.getLastName());
				}
				System.out.println("- - - - - - - - - - - - - - - - - - - - - - -");
			}
		}
		submenu();
//		launch(); 
	}

	// stretch goal 1 - submenu
	private void submenu() {
		System.out.println("\n---------------------------------------");
		System.out.println("|      What would you like to do?     |");
		System.out.println("|   (1) Return to the main menu       |");
		System.out.println("|   (2) View complete film details    |");
		System.out.println("---------------------------------------");
		int choice = input.nextInt();

		if (choice == 1) {
			launch();
		}
		if (choice == 2) {
			System.out.print("\nEnter the ID of the film you want to see the full details for.\n");
			int id = input.nextInt();

			Film f = db.getFilmById(id);
			System.out.println("ID: " + f.getId());
			System.out.println("Title: " + f.getTitle());
			System.out.println("Description: " + f.getDescription());
			System.out.println("Year: " + f.getReleaseYear());
			System.out.println("Language ID: " + f.getLanguageId());
			System.out.println("Rental Duration: " + f.getRentalDuration());
			System.out.println("Rental Rate: " + f.getRentalRate());
			System.out.println("Length: " + f.getLength());
			System.out.println("Replacement Cost: " + f.getReplacementCost());
			System.out.println("Rating: " + f.getRating());
			System.out.println("Special Features: " + f.getSpecialFeatures());
			
			// stretch goal 2 - film's categories
			List<String> categories = db.findCategoryByFilmId(id); 
			
			for (String s : categories) {
				System.out.println("Category: " + s);
			}
			
			// stretch goal 3 - copies of film and their condition
			Map<Integer, String> copiesAndCondition = db.findCopiesAndCondition(id); 
			
			System.out.println("\nCopies By\nInventory Number:\tCondition:\n");
			for (Map.Entry<Integer, String> entry : copiesAndCondition.entrySet()) {
				System.out.println(entry.getKey() + "\t\t\t" + entry.getValue());
			}
			launch();
		}
	}
}
