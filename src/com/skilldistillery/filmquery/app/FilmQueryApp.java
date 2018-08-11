package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {
  
  DatabaseAccessor db = new DatabaseAccessorObject();
  Scanner input = new Scanner(System.in);

  public static void main(String[] args) throws SQLException{
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
      System.out.println("------------------------------------------");
	  System.out.println("|                MENU                    |");
	  System.out.println("| (1) Look up a film by it's ID          |");
	  System.out.println("| (2) Look up a film by a search keyword |");
	  System.out.println("| (3) Exit                               |");
	  System.out.println("------------------------------------------\n");
	  String option = input.nextLine(); 
	  
	  if (option.equals("1")) {
		  System.out.print("Enter the ID: ");
		  int idNumber = input.nextInt(); 
		  
		  Film film = db.getFilmById(idNumber);
		  System.out.println(film);
		  
		  if(film == null) {
			  System.out.println("We couldn't find any films with that ID number.");
		  }
 		
		  // If the user looks up a film by id, they are prompted to enter the film id. If the film is not found, they see a 
		  // message saying so. If the film is found, its title, year, rating, and description are displayed.
	  }
	  if (option.equals("2")) {
		  System.out.print("Enter the keyword: ");
		  String keyword = input.next(); 
		  
		  // If the user looks up a film by search keyword, they are prompted to enter it. If no matching films are found, they 
		  // see a message saying so. Otherwise, they see a list of films for which the search term was found anywhere in the 
		  // title or description, with each film displayed exactly as it is for User Story 2.
		  
	  }
	  if (option.equals("3")) {
		  System.out.println("Bye");
		  System.exit(0);
	  }
	  else {
		  System.out.println("Sorry, I didn't get that.");
		  
	  }
  }

}
