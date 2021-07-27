package dao;

import java.sql.Connection;
import java.sql.SQLException;
import common.User;

public interface UserDao {

	Connection connect();
	
	// Read
//	static void queryIfUsernameExists(String str) throws SQLException {
//		Statement stmt = null;
//		stmt = connect().createStatement();
//		ResultSet rs = stmt.executeQuery("SELECT username FROM users Where username=?");
//	
//		while(rs.next()) {
//			String name = rs.getString("username");
//			System.out.println(name);
//		}
//	}
	
	// Create
	public void createUser(User user) throws SQLException;
	
	public User getUser(User user) throws SQLException;
	
	
}
