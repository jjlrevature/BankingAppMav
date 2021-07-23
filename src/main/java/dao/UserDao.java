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

	public Connection connect() {
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
	static void queryIfUsernameExists(String str, Connection conn) throws SQLException {
		Statement stmt = null;
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT username FROM users Where username=?");
	
		while(rs.next()) {
			String name = rs.getString("username");
			System.out.println(name);
		}
	}
	
	// Create
	public void createUser(User user, Connection conn) throws SQLException {
		PreparedStatement pstmt = null;
		String createUser = "INSERT INTO users (username,password) VALUES (?,?)";
		String username = user.getUsername();
		String password = user.getPassword();
		try {
			pstmt = conn.prepareStatement(createUser);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.executeUpdate();
			Printer.userCreatedSuccesfully();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public User getUser(User user, Connection conn) throws SQLException {
		String findUserCommand = "SELECT id,username,password,accounts FROM users WHERE password=? AND username=?";
		PreparedStatement pstmt = conn.prepareStatement(findUserCommand);
		String username = user.getUsername();
		String password = user.getPassword();
		//ArrayList<Account> userList = new ArrayList<Account>();
		User currentUser = null;
		try {
			// Finds User in database
			pstmt = conn.prepareStatement(findUserCommand);
			pstmt.setString(1, password);
			pstmt.setString(2, username);
			//java.sql.Array accountResults = null;
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				for (int i = 1; i <= columnsNumber; i++) {
			        if (i > 1) System.out.print(",  ");
			        String columnValue = rs.getString(i);
			        System.out.print(columnValue + " " + rsmd.getColumnName(i));
			    }
			    System.out.println("");
				
				
				
				
				currentUser = new User(null,null);
							
				//accountResults = rs.getArray("accounts");
				
				
				// set new user = database-user's data
				currentUser.setUsername(rs.getString("username"));
				currentUser.setPassword(rs.getString("password"));
				currentUser.setId(rs.getDouble("id"));
				//userList = createAccountObjects(accountResults, conn, currentUser);
				//currentUser.setUserAccounts(userList);
			}
			
			
			// iterate through returned list of strings adding each to userList			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return currentUser;
	}
	
//	private static ArrayList<Account> createAccountObjects(java.sql.Array accountInt, Connection conn, User user) throws SQLException {
//		List<Integer> results = new ArrayList();
//		
//		PreparedStatement pstmtt = null;
//		String findAccount = "SELECT accountOwner,accountName,balance FROM accounts WHERE id=?";
//		pstmtt = conn.prepareStatement(findAccount);
//		
//		// for each account, get account data and populate new account obj, then add to arraylist
//		for (int x = 0; x < accountInt.length; x++) {
//			//int accId =  accountInt.get(x);
//			//pstmtt.setDouble(1, accId);
//			ResultSet rss = pstmtt.executeQuery();
//			
//			Account acc = new Account(user, "nicname");
//			
//			// assign values to new accounts object
//			double newBal = rss.getDouble("balance");
//			acc.setBalance(newBal);
//			acc.setAccountOwner(user);
//			
//			results.add(acc);
//			
//		}
//		return results;
//	}
	
}
