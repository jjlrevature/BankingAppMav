package main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

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
	
	public static void main(String[] args) throws SQLException {
		
		// instantiate new scanner
		Scanner sc = new Scanner(System.in);
		UserService us = new UserService();
		AccountService as = new AccountService();
		EmployeeService es = new EmployeeService();
		User currentUser = null;
		Employee currentEmp = null;
		// Prompt user to login or create an account
		Printer.firstMenu();
		int i = Input.collectIntInput(sc);
		
		// Create new customer, user login, employee login
		switch(i) {
		case 1:
			User existingUser = Parser.parseUserLogin(sc, us); 
			currentUser = existingUser;
			break;
		case 2:
			currentEmp = Parser.parseEmployeeLogin(sc,es);
			break;
		case 3: 
			User newUser = Parser.parseCreateUser(sc);
			currentUser = newUser;
			us.createUser(newUser);
			break;
		case 4:
			b = false;
			Printer.printExit();
			break;
		}
		
		// while menu loops for different users
		switch(i) {
		case 1:
			while(currentUser != null & b) {
				Parser.parseUserMenu(sc, us, currentUser, as);				
			}
			break;
		case 2:
			while(currentEmp != null & b) {
				Parser.parseEmployeeMenu();
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