package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import client.Input;
import common.Account;
import common.User;
import dao.AccountDao;
import dao.UserDao;

public class AccountService {
	private AccountDao currentAccount = new AccountDao();
	Connection conn = currentAccount.connect();
	private ArrayList<Account> currentUsersAccounts = new ArrayList<Account>();
	// crud operations and find stuff methods go here
	
	// Create
	public void createAccount(User newUser,Scanner sc) throws SQLException  {
		Printer.accountNicname();
		String nicname = Input.getString(sc);
		currentAccount.createAccount(newUser,conn, nicname);
	}	
	
	public void addBalance(User user, Account acc, int deposit) throws SQLException {
		int newBalance = acc.getBalance() + deposit;
		currentAccount.addBalance(user, acc, newBalance);
		acc.setBalance(newBalance);
	}
	
	public void removeBalance(User user, Account acc, int withdraw) throws SQLException {
		int newBalance = acc.getBalance() - withdraw; 
		currentAccount.removeBalance(user, acc, newBalance);
		acc.setBalance(newBalance);
	}
	
}
