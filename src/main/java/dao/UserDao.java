package dao;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.Account;
import common.User;
import service.Printer;

public class UserDao {

	private static Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","pOOkiebear2!");
			System.out.println("Connectioned!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return conn;
	}
	
	// Read
	static void queryIfUsernameExists(String str) throws SQLException {
		Statement stmt = null;
		stmt = connect().createStatement();
		ResultSet rs = stmt.executeQuery("SELECT username FROM users Where username=?");
	
		while(rs.next()) {
			String name = rs.getString("username");
			System.out.println(name);
		}
	}
	
	// Create
	public void createUser(User user) throws SQLException {
		PreparedStatement pstmt = null;
		String createUser = "INSERT INTO users (username,password) VALUES (?,?)";
		String username = user.getUsername();
		String password = user.getPassword();
		try {
			pstmt = connect().prepareStatement(createUser);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.executeUpdate();
			Printer.userCreatedSuccesfully();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public User getUser(User user) throws SQLException {
		String findUserCommand = "SELECT id,username,password FROM users WHERE password=? AND username=?";
		String findAccounts = "SELECT nicname,accountowner,balance FROM accounts INNER JOIN users ON accounts.accountowner = users.id";
		PreparedStatement pstmt = connect().prepareStatement(findUserCommand);
		PreparedStatement pst = connect().prepareStatement(findAccounts);
		String username = user.getUsername();
		String password = user.getPassword();
		ArrayList<Account> userList = new ArrayList<Account>();
		User currentUser = null;
		int userId = 0;
		try {
			// Finds User in database			
			pstmt.setString(1, password);
			pstmt.setString(2, username);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {				
				currentUser = new User(null,null);
				// set new user = database-user's data
				currentUser.setUsername(rs.getString("username"));
				currentUser.setPassword(rs.getString("password"));
				currentUser.setId(rs.getInt("id"));
				userId = rs.getInt("id");
			}
			// Find accounts
			ResultSet rss = pst.executeQuery();			
			while(rss.next()) {
				int k = rss.getInt("accountowner");
				if (k == userId) {
					int accOwner = rss.getInt("accountowner");
					Account userAcc = new Account(accOwner, "placeholder");
					userAcc.setActName(rss.getString("nicname"));
					userAcc.setBalance(rss.getInt("balance"));
					userList.add(userAcc);
				}				
			}
			currentUser.setUserAccounts(userList);		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentUser;
	}
	
	
}
