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
import service.Printer;
import service.UserService;
public class Parser {
	
	
	public static User parseUserLogin( Scanner sc, UserService us) {
		String[] creds = null;
		Printer.askForLoginCreds();	
		creds = Input.credentials(sc);		
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
	
	public static Employee parseEmployeeLogin( Scanner sc) {
		return null;
		
	}
	
	public static void parseUserMenu(Scanner sc, UserService us, User user) {
		Printer.userMainMenu(user);
		int i = Input.collectIntInput(sc);
		ArrayList<Account> accounts = new ArrayList<Account>();
		switch(i) {
		case 1:
			// view accounts
			accounts = user.getUserAccounts();
			for (int x = 0; x < accounts.size(); x++) {
				Account acc = accounts.get(x);
				String accNicname = acc.getActName();
				double bal = acc.getBalance();
				System.out.println(accNicname + " has a balance of: " + bal + ".");
			}
			break;
		case 2:
			// open new account
			break;
		case 3:
			// exit
			Main.b = false;
			Printer.printExit();
			break;
		}
	}
	


	
}