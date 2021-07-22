package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import common.User;
import dao.UserDao;

public class UserService {
	private UserDao user = new UserDao();
	private Connection conn = user.connect();
	
	// Create
		public void registerUser(User newUser) throws SQLException  {
			UserDao.createUser(newUser,conn);
		}
}
