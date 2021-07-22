package main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import client.Input;
import client.Parser;
import common.User;
import dao.AccountDao;
import service.Printer;


public class Main {
	
	// variable to control do-while loop
	public static boolean b = true;
	
	public static void main(String[] args) {
		
		// instantiate new scanner
		Scanner sc = new Scanner(System.in);
		// Prompt user to login or create an account
		Printer.firstMenu();
		int i = Input.collectIntInput(sc);
		User currentUser = Parser.parseLogin(sc, i);
		
		
		sc.close();
	}
	
	public void setB(boolean c) {
		Main.b = c;
	}
	
	
}