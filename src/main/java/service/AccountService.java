package service;

import java.sql.Connection;
import java.sql.SQLException;

import common.User;
import dao.AccountDao;
import dao.UserDao;

public class AccountService {
	private AccountDao account = new AccountDao();
	private Connection conn = account.connect();
	// crud operations and find stuff methods go here
	
	
	// Create
	public void registerUser(User newUser) throws SQLException  {
		UserDao.createUser(newUser,conn);
	}
	
	public void returnBalance() {
		
	}
	
}
