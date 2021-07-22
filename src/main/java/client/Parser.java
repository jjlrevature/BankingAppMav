package client;

import java.util.Scanner;

import main.Main;
import common.User;
import service.Printer;
public class Parser {
	
	
	public static User parseLogin( Scanner sc, int first) {
		User returnedUser = null;
		switch(first) {
		case 1:
			Printer.askForLoginCreds();			
			break;
		case 2:
			Printer.askForEmployeeCreds();
			break;
		case 3:
			User newUser = createUser(sc);
			returnedUser =  newUser;
		case 4:
			Main.b = false;
			Printer.printExit();
			break;
		}
		return returnedUser;
	}
	
	private static void parseUser() {
		
	}
	
	private static void parseEmployee() {
		
	}
	
	public static User createUser(Scanner sc) {
		Printer.createUsername();
		// query database if username exists
		String username = Input.string(sc);
		Printer.createPassword();
		String password = Input.string(sc);
		Printer.confirmPassword();
		String passConfirm = Input.string(sc);
		User user = null;
		// check to make sure passwords are the same
		if(password == passConfirm) {
			user = new User(username,password);			
		}
		return user;
	}
	
	public static User getUser() {
		User user = null;
		
		return user;
		
	}
	
}