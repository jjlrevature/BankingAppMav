package client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import main.Main;
import common.Account;
import common.Employee;
import common.User;
import dao.UserDao;
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
			ph = us.getUser(ph);
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
	
	
	public static void parseUserMenu(Scanner sc, UserService us, User user, AccountService as) throws SQLException {
		Printer.userMainMenu(user);
		int i = Input.collectIntInput(sc);
		ArrayList<Account> accounts = new ArrayList<Account>();
		Account acct = new Account(0, "placeholder");
		accounts = user.getUserAccounts();
		switch(i) {
		case 1:
			// view accounts
			for (int x = 0; x < accounts.size(); x++) {
				Account acc = accounts.get(x);
				String accNicname = acc.getActName();
				double bal = acc.getBalance();
				System.out.println(accNicname + " has a balance of: " + bal + ".");
			}
			System.out.println("\n");
			break;
		case 2:
			// actions on account
			//choose account
			Printer.selectAccount();
			int y = 1;
			for (int x = 0; x < accounts.size(); x++) {
				Account acc = accounts.get(x);
				String accNicname = acc.getActName();
				double bal = acc.getBalance();				
				System.out.println( y + ") " + accNicname + " has a balance of: " + bal + ".");
				y++;
			}
			System.out.println("\n");
			int k = Input.collectIntInput(sc) - 1;
			acct = accounts.get(k);
			parseAccountActions(sc, acct,user, as);			
			break;
		case 3:
			// open new account
			as.createAccount(user, sc);
			break;
		case 4:
			// exit
			Main.b = false;
			break;
		}
	}
	
	private static void parseAccountActions(Scanner sc, Account acc, User user, AccountService as) throws SQLException {
		Printer.accountActions();
		int j = Input.collectIntInput(sc);
		switch(j) {
		case 1:
			// Deposit
			Printer.depositAmount();
			int deposit = Input.collectIntInput(sc);			
			as.addBalance(user, acc, deposit);
			break;
		case 2:
			// Withdraw
			Printer.withdrawAmount();
			int withdraw = Input.collectIntInput(sc);
			as.removeBalance(user, acc, withdraw);
			break;
		case 3:
			// Exit
			Main.b = false;
			break;
		}
	}
	
	
	public static void parseEmployeeMenu() {
		Printer.employeeMenu();	
	}
	
}	
