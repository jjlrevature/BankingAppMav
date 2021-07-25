package service;

import common.User;

public class Printer {
	// =================================================================================
	// ===========================	Credentials		====================================
	// =================================================================================
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
	
	public static void errorCreds() {
		System.out.println("Error with credentials, please Enter username and password");
	}
	// =================================================================================
	// =========================	User Stuff		 ===================================
	// =================================================================================
	
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
		System.out.println("User has been successfully created! Welcome to Xbank, you champion.");
	}
	
	public static void userMainMenu(User user) {
		String username = user.getUsername();
		System.out.println("Welcome " + username + ": Please choose an action\n"
				+ "1) View Accounts\n"
				+ "2) Actions\n"
				+ "3) Request New Account\n"
				+ "4) Exit1");
	}
	
	// =================================================================================
	// =======================		Employees		====================================
	// =================================================================================
	
	
	public static void employeeMenu() {
		System.out.println("Welcome employeename, please select an option.\n"
				+ "1) find user by username\n"
				+ "2) approve accounts\n"
				+ "3) view all logs");
	}
	
	
	// =================================================================================
	// =======================		Account Actions		================================
	// =================================================================================
	
	public static void accountCreatedSuccesfully() {
		System.out.println("User's account has been successfully created! Enjoy your time here at Xbank, you champion.");
	}
	
	public static void selectAccount() {
		System.out.println("Please select an account");
	}
	
	public static void accountActions() {
		System.out.println("Please seleect and action to perform.\n"
				+ "1) Deposit\n"
				+ "2) Withdraw");
	}
	
	public static void depositAmount() {
		System.out.println("Enter amount to deposit");
	}
	
	public static void withdrawAmount() {
		System.out.println("Enter amount to withdraw");
	}

	public static void accountNicname() {
		System.out.println("Please enter your accounts nicname.");
	}
	
}
