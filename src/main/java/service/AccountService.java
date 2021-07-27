package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.DriverManager;

import client.Input;
import common.Account;
import common.User;
import dao.AccountDao;
import dao.UserDao;

public class AccountService implements AccountDao {
	private AccountDao currentAccount;
	
	// Called methods
	public void createAccount(User newUser,Scanner sc) throws SQLException  {
		Printer.accountNicname();
		String nicname = Input.getString(sc);
		currentAccount.createAccount(newUser, nicname);
	}	
	
	public void addNewBalanceServ(User user, Account acc, int deposit) throws SQLException {
		int newBalance = acc.getBalance() + deposit;
		currentAccount.addBalance(user, acc, newBalance);
		acc.setBalance(newBalance);
	}
	
	public void removeBalanceServ(User user, Account acc, int withdraw) throws SQLException {
		int newBalance = acc.getBalance() - withdraw; 
		currentAccount.removeBalance(user, acc, newBalance);
		acc.setBalance(newBalance);
	}

	
	//============== From Interface ===============
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
	
	public void addBalance(User user, Account acc, int deposit ) throws SQLException {
		PreparedStatement pstmt = null;
		String accNicname = acc.getActName();
		try {
			String updateCommand = "UPDATE accounts " + "SET balance=? WHERE nicname=?";
			pstmt = connect().prepareStatement(updateCommand);
			pstmt.setDouble(1, deposit);
			pstmt.setString(2, accNicname);
			pstmt.executeUpdate();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}					
	}

	public void createAccount(User user, String nicname) throws SQLException {
		PreparedStatement pstmt = null;
		String createUser = "INSERT INTO accounts (nicname,accountowner,balance) VALUES (?,?,?)";
		int userId = user.getId();
		try {
			pstmt = connect().prepareStatement(createUser);
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
	
	public void removeBalance(User user, Account acc, int withdraw ) throws SQLException {
		PreparedStatement pstmt = null;
		PreparedStatement balCheck = null;
		String accNicname = acc.getActName();
		String balCheckStr = "SELECT balance FROM accounts WHERE id=?";
		String updateCommand = "UPDATE accounts " + "SET balance=? WHERE nicname=?";
		int accId = (int) acc.getId();
		int currentAccBal = 0;
		try {			
			balCheck = connect().prepareStatement(balCheckStr);
			balCheck.setInt(1, accId);
			ResultSet rs = balCheck.executeQuery();
			while(rs.next()) {
				currentAccBal = rs.getInt("balance");
				int newTotal = currentAccBal - withdraw;
				if(newTotal > 0) {
					pstmt = connect().prepareStatement(updateCommand);
					pstmt.setDouble(1, withdraw);
					pstmt.setString(2, accNicname);
					pstmt.executeUpdate();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
}
