package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import client.Input;
import common.Account;
import common.User;
import service.Printer;

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
	
	// Create
	public void createAccount(User user, Connection conn, String nicname) throws SQLException {
		PreparedStatement pstmt = null;
		String createUser = "INSERT INTO accounts (nicname,accountowner,balance) VALUES (?,?,?)";
		int userId = user.getId();
		try {
			pstmt = conn.prepareStatement(createUser);
			pstmt.setString(1, nicname);
			pstmt.setInt(2, userId);
			pstmt.setInt(3, 0);
			pstmt.executeUpdate();
			Printer.accountCreatedSuccesfully();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addBalance(User user, Account acc, int deposit ) throws SQLException {
		PreparedStatement pstmt = null;
		String accNicname = acc.getActName();
		try {
			String updateCommand = "UPDATE accounts " + "SET balance=? WHERE nicname=?";
			pstmt = conn.prepareStatement(updateCommand);
			pstmt.setDouble(1, deposit);
			pstmt.setString(2, accNicname);
			pstmt.executeUpdate();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}					
	}
	
	public void removeBalance(User user, Account acc, int withdraw ) throws SQLException {
		PreparedStatement pstmt = null;
		String accNicname = acc.getActName();
		try {
			String updateCommand = "UPDATE accounts " + "SET balance=? WHERE nicname=?";
			pstmt = conn.prepareStatement(updateCommand);
			pstmt.setDouble(1, withdraw);
			pstmt.setString(2, accNicname);
			pstmt.executeUpdate();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
}
