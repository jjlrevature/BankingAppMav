package service;

import java.util.ArrayList;

import common.Account;
import common.Employee;
import common.Transfer;
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
		System.out.println("\nWelcome " + username + ": Please choose an action\n"
				+ "1) View Accounts\n"
				+ "2) Actions\n"
				+ "3) Request New Account\n"
				+ "4) Exit");
	}
	
	// =================================================================================
	// =======================		Employees		====================================
	// =================================================================================
	
	
	public static void employeeMenu(Employee e) {
		System.out.println("Welcome " + e.getUsername() + ", please select an option.\n"
				+ "1) find all users\n"
				+ "2) find user by username\n"
				+ "3) view all logs");
	}
	
	public static void printAllUsers(ArrayList<String> all) {
		for(int x = 0; x < all.size(); x++) {
			System.out.println(all.get(x));
		}
		System.out.println("\n");
	}
	
	public static void printUserForEmployee(User user) {
		System.out.println("\nUsername: " + user.getUsername() + "\nPassword: " + user.getPassword());
		for(int x = 0; x < user.getUserAccounts().size(); x++) {
			System.out.println("Account Nicname: " + user.getUserAccounts().get(x).getActName()
					+ ". Balance: " + user.getUserAccounts().get(x).getBalance()
					+ " Approved: " + user.getUserAccounts().get(x).isApproved());
		}
		System.out.println("\n");
	}
	
	public static void askForCredsForEmp() {
		System.out.println("Please enter a username and password to search for.");
	}
	
	// =================================================================================
	// =======================		Account Actions		================================
	// =================================================================================
	
	public static void accountCreatedSuccesfully() {
		System.out.println("User's account has been successfully created! Please wait for an employee to review your account for approval.");
	}
	
	public static void selectAccount() {
		System.out.println("Please select an account");
	}
	
	public static void accountActions() {
		System.out.println("Please seleect and action to perform.\n"
				+ "1) Deposit\n"
				+ "2) Withdraw\n"
				+ "3) Accept Transfer\n"
				+ "4) Send Transfer\n"
				+ "5) Exit");
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
	
	// =================================================================================
	// =======================		Transfers			================================
	// =================================================================================
	
	public static void transferSuccess() {
		System.out.println("Transfer Complete.");
	}
	
	public static void transferSend() {
		System.out.println("Money sent! Recipient must accept.");
	}
	
	public static void transferFail() {
		System.out.println("Transfer has failed, try again or contact an employee.");
	}
	
	public static void youHaveATransfer(ArrayList<Transfer> arrayT) {
		System.out.println("You have a transfer waiting!");
		
	}
	
	public static void acceptFundsQ(Transfer transfer) {
		System.out.println("Accept transfer of: " + transfer.getAmount());
	}
	
	public static void sendTransferId() {
		System.out.println("Please input account id.");
	}
	
	public static void sendTransferAmount() {
		System.out.println("Please enter an amount.");
	}
}
