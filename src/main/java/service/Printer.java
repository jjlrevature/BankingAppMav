package service;


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
}

