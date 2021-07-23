package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import common.Account;
import common.User;
import dao.AccountDao;
import dao.UserDao;

public class AccountService {
	private AccountDao currentAccount = new AccountDao();
	private ArrayList<Account> currentUsersAccounts = new ArrayList<Account>();
	private Connection conn = currentAccount.connect();
	// crud operations and find stuff methods go here
	
	// Create
//	public void registerUser(User newUser) throws SQLException  {
//		UserDao.createUser(newUser,conn);
//	}
	
	
	public void addBalance(User user, Account acc, double deposit) throws SQLException {
		currentAccount.addBalance(null, null, 0);
	}
	
}
