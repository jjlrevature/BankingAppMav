package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import common.Account;
import common.User;

// Data access object
public class AccountDao {
	
	Connection conn = null;	
	
	public Connection connect() {
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","pOOkiebear2!");
			System.out.println("Connectioned!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return conn;
	}
	
	
	public void addBalance(User user, Account acc, double deposit ) throws SQLException {
		PreparedStatement pstmt = null;
		double balance = acc.getBalance();
		double newBalance = balance + deposit;
		String username = user.getUsername();
		String accountOwner = acc.getAccountOwner().getUsername();
		if(username == accountOwner) {
			try {
				String updateCommand = "UPDATE accounts " + "SET balance=? Where accountOwner=?";
				pstmt = conn.prepareStatement(updateCommand);
				pstmt.setDouble(1, newBalance);
				pstmt.setString(2, accountOwner);
				pstmt.executeUpdate();
				conn.commit();				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}		
	}
	
	public void removeBalance(User user, Account acc, double deposit ) throws SQLException {
		PreparedStatement pstmt = null;
		double balance = acc.getBalance();
		double newBalance = balance - deposit;
		String username = user.getUsername();
		String accountOwner = acc.getAccountOwner().getUsername();
		if(username == accountOwner) {
			try {
				String updateCommand = "UPDATE accounts " + "SET balance=? Where accountOwner=?";
				pstmt = conn.prepareStatement(updateCommand);
				pstmt.setDouble(1, newBalance);
				pstmt.setString(2, accountOwner);
				pstmt.executeUpdate();
				conn.commit();				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}		
	}
	
}
