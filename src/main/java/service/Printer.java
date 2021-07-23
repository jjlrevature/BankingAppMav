package service;

import common.User;

public class Printer {
	
	public static void firstMenu() {
		System.out.println("Welcom to Xbank! Please Login.\n\n"
				+ "1) User Login\n"
				+ "2) Employee Login\n"
				+ "3) Register\n"
				+ "4) Exit");
	}
	
	public static void printExit() {
		System.out.println("\n"
				+ "Goodbye.\n");
	}
	
	public static void askForLoginCreds() {
		System.out.println("Please enter your username and then your password");
	}
	
	public static void askForEmployeeCreds() {
		System.out.println("Please enter your Employee username and password");
	}
	
	public static void createUsername() {
		System.out.println("Please enter a username");
	}
	
	public static void createPassword() {
		System.out.println("Please enter a password");
	}
	
	public static void confirmPassword() {
		System.out.println("Please confirm your password");
	}
	
	public static void userCreatedSuccesfully() {
		System.out.println("User account has been successfully created! Welcome to Xbank, you champion.");
	}
	
	
	public static void userMainMenu(User user) {
		String username = user.getUsername();
		System.out.println("Welcome " + username + ": Please choose an action\n"
				+ "1) View Accounts\n"
				+ "2) Open New Account");
	}


	public static void errorCreds() {
		System.out.println("Error with input, please Enter username and password");
	}
	
}
