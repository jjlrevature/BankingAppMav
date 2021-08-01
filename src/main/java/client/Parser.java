package client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import main.Main;
import common.Account;
import common.Employee;
import common.Transfer;
import common.User;
import service.AccountService;
import service.EmployeeService;
import service.Printer;
import service.UserService;
public class Parser {
	
	
	
	public static User parseUserLogin( Scanner sc, UserService us) {
		String[] creds = gatherCredentials(sc);		
		User ph = new User(creds);
		
		// check database if user exists
		try {
			ph = us.getCurrentUser(ph);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ph;
	}
	
	public static User parseUserReload(User user, UserService us) {
		User ph = user;		
		// check database if user exists
		try {
			ph = us.getCurrentUser(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ph;
	}
	
	public static Employee parseEmployeeLogin( Scanner sc, EmployeeService es) {
		String[] creds = gatherCredentials(sc);
		Employee currentEmployee = new Employee(creds);
		return currentEmployee;
	}
	
	private static String[] gatherCredentials(Scanner sc) {
		String[] creds = null;
		Printer.askForLoginCreds();
		creds = Input.credentials(sc);
		return creds;
	}
	
	public static User parseCreateUser(Scanner sc) {
		Printer.createUsername();
		String a = Input.getString(sc);
		Printer.createPassword();
		String b = Input.getString(sc);
		String[] creds = new String[2];
		creds[0] = a;
		creds[1] = b;
		User newUser = new User(creds);
		return newUser;
	}
	
	
	public static boolean parseUserMenu(Scanner sc, UserService us, User user, AccountService as) throws SQLException {
		ArrayList<Account> accounts = new ArrayList<Account>();
		ArrayList<Transfer> transfers = new ArrayList<Transfer>();
		Account acct = new Account(0, "placeholder");
		boolean b = us.checkForTransfer(user);
		boolean exit = true;
		accounts = user.getUserAccounts();	
		if(b) {
			transfers = us.getTransfer(user);			
			Printer.youHaveATransfer(transfers,user);
		} else {
			System.out.println("No Transfers");
		}
		Printer.userMainMenu(user);
		int i = Input.collectIntInput(sc);
		switch(i) {
		case 1:
			// view accounts
			Printer.viewUserAccounts(accounts, transfers);			
			break;
		case 2:
			// actions on account
			//choose account
			Printer.selectAccount();
			Printer.viewUserAccounts(accounts, transfers);
			int k = Input.collectIntInput(sc) - 1;
			acct = accounts.get(k);
			parseAccountActions(sc, acct,user, as,transfers);			
			break;
		case 3:
			// open new account
			as.createAccount(user, sc);
			break;
		case 4:
			// exit
			exit = false;
			break;
		}
		return exit;
	}
	
	private static void parseAccountActions(Scanner sc, Account acc, User user, AccountService as, ArrayList<Transfer> transfers) throws SQLException {
		if(acc.isApproved()) {		
			Printer.accountActions();
			int j = Input.collectIntInput(sc);
			switch(j) {
			case 1:
				// Deposit
				Printer.depositAmount();
				int deposit = Input.collectIntInput(sc);			
				as.addNewBalanceServ(user, acc, deposit);
				break;
			case 2:
				// Withdraw
				Printer.withdrawAmount();
				int withdraw = Input.collectIntInput(sc);
				as.removeBalanceServ(user, acc, withdraw);
				break;
			case 3:
				// Accept Transfer
				if (transfers.size() > 0) {					
					for(int x = 0; x < transfers.size(); x++) {
						Printer.acceptFundsQ(transfers.get(x));
						int k = Input.collectIntInput(sc);						
						if(k == 1) 
							as.acceptFunds(transfers.get(x));
					}
					
				} else {
					System.out.println("You have no transfers to accept.");
				}
				break;
			case 4:
				// Send Transfer
				Printer.sendTransferId();
				int accId = Input.collectIntInput(sc);
				Printer.sendTransferAmount();
				double amount = Input.transfer(sc);
				as.transferFunds(user, amount, acc, accId);
				break;
			case 5:
				// Exit
				Main.b = false;
				break;
			}
		} else {
			System.out.println("\nAccount waiting for approval before actions can be taken\n");
		}
	}
	
	
	public static void parseEmployeeMenu(Scanner sc, UserService us, AccountService as, Employee e) throws SQLException {
		Printer.employeeMenu(e);	
		int choice = Input.collectIntInput(sc);
		User currentUser = null;
		switch(choice) {
		case 1:
			// find all users
			ArrayList<String> allUsers = us.getAllUsers();
			Printer.printAllUsers(allUsers);
			break;
		case 2:
			// find user by username
			Printer.askForCredsForEmp();
			String[] username = Input.credentials(sc);
			User queriedUser = new User(username);
			currentUser = us.getCurrentUser(queriedUser);
			Printer.printUserForEmployee(currentUser);
			break;
		case 3:
			// approve accounts
			as.getAllAccts(currentUser);
			break;
		case 4:
			// view all logs
			break;
		}
	}
	
}	
