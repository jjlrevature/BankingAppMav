package service;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import common.Account;
import common.User;
import dao.UserDao;

public class UserService {
	
		private UserDao udao = new UserDao();
	// Create
		
		public User createUser(User user) throws SQLException {
			udao.createUser(user);
			return user;
		}

		public User getUser(User user) throws SQLException {
			User currentUser = udao.getUser(user);
			return currentUser;
		}
		
		public ArrayList<Account> getUserAccounts(User user) {
			return user.getUserAccounts();
		}
		
		
}
