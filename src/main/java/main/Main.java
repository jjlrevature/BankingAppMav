package main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.FileAppender;

import client.Input;
import client.Parser;
import common.Employee;
import common.User;
import dao.AccountDao;
import dao.UserDao;
import service.AccountService;
import service.EmployeeService;
import service.Printer;
import service.UserService;


public class Main {
	
	// variable to control do-while loop
	public static boolean b = true;
	private static final Logger logger = LogManager.getLogger(UserService.class);
	
	public static void main(String[] args) throws SQLException {
		// instantiate new scanner
		
		Scanner sc = new Scanner(System.in);
		UserService us = new UserService();
		AccountService as = new AccountService();
		EmployeeService es = new EmployeeService();
		User currentUser = null;
		Employee currentEmp = null;
		logger.info("Instantiation of initial variables.. complete.");
		// Prompt user to login or create an account
		Printer.firstMenu();
		int i = Input.collectIntInput(sc);
		
		// Create new customer, user login, employee login
		switch(i) {
		case 1:			
			User existingUser = Parser.parseUserLogin(sc, us); 		
			currentUser = existingUser;
			logger.info("User attempted to login");
			break;
		case 2:
			currentEmp = Parser.parseEmployeeLogin(sc,es);
			logger.info("Employee attempted to login");
			break;
		case 3: 
			User newUser = Parser.parseCreateUser(sc);
			currentUser = newUser;
			us.createUser(newUser);
			break;
		case 4:
			b = false;
			break;
		}
		
		
		// while menu loops for different users
		switch(i) {
		case 1:
			logger.info("Displaying user menu");
			while(currentUser != null & b) {
				currentUser = Parser.parseUserReload(currentUser, us);
				b = Parser.parseUserMenu(sc, us, currentUser, as);				
			}
			break;
		case 2:
			logger.info("Displaying employee menu");
			while(currentEmp != null & b) {
				Parser.parseEmployeeMenu(sc,us,as,currentEmp, es);
			}
			break;			
		}
		Printer.printExit();
		sc.close();
	}
	
	public void setB(boolean c) {
		Main.b = c;
	}
	
	
}