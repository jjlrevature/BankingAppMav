package main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import client.Input;
import client.Parser;
import common.User;
import dao.AccountDao;
import dao.UserDao;
import service.Printer;
import service.UserService;


public class Main {
	
	// variable to control do-while loop
	public static boolean b = true;
	
	public static void main(String[] args) throws SQLException {
		
		// instantiate new scanner
		Scanner sc = new Scanner(System.in);
		UserService us = new UserService();
		UserDao udao = new UserDao();
		User currentUser = null;
		// Prompt user to login or create an account
		Printer.firstMenu();
		int i = Input.collectIntInput(sc);
		
		switch(i) {
		case 1:
			User existingUser = Parser.parseUserLogin(sc, us); 
			currentUser = existingUser;
			break;
		case 2:
			currentUser = Parser.parseEmployeeLogin(sc);
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
		System.out.println(currentUser.getUsername());
		
		String check = currentUser.getUsername();
		if(check != null) {
			Parser.parseUserMenu(sc, us, currentUser);
		}
		
		
		
		sc.close();
	}
	
	public void setB(boolean c) {
		Main.b = c;
	}
	
	
}