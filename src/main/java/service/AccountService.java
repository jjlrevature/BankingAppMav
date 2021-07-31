package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.DriverManager;

import client.Input;
import common.Account;
import common.Transfer;
import common.User;
import dao.AccountDao;
import dao.UserDao;

public class AccountService implements AccountDao {
	
	
	// Called methods
	public Account createAccount(User newUser,Scanner sc) throws SQLException  {
		Printer.accountNicname();
		String nicname = Input.getString(sc);
		Account currentAccount = createAccountQ(newUser, nicname);
		return currentAccount;
	}	
	
	public void addNewBalanceServ(User user, Account acc, int deposit) throws SQLException {
		int newBalance = acc.getBalance() + deposit;
		if(newBalance > 0) {
			addBalance(user, acc, newBalance);
			acc.setBalance(newBalance);			
		}
	}
	
	public void removeBalanceServ(User user, Account acc, int withdraw) throws SQLException {
		int newBalance = acc.getBalance() - withdraw; 
		if(newBalance > 0) {
			removeBalance(user, acc, newBalance);
			acc.setBalance(newBalance);			
		} else {
			System.out.println("Cannot complete transaction. Not Enough Funds.");			
		}
	}

	
	//============== From Interface ===============
	public Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","pOOkiebear2!");
		} catch (SQLException e) {
			e.printStackTrace();
		}			
		return conn;
	}
	
	public void addBalance(User user, Account acc, double deposit ) throws SQLException {
		PreparedStatement pstmt = null;
		String accNicname = acc.getActName();
		try {
			String updateCommand = "UPDATE accounts " + "SET balance=? WHERE nicname=?";
			pstmt = connect().prepareStatement(updateCommand);
			pstmt.setDouble(1, deposit);
			pstmt.setString(2, accNicname);
			pstmt.executeUpdate();	
		} catch (SQLException e) {
			e.printStackTrace();
		}					
	}

	public Account createAccountQ(User user, String nicname) throws SQLException {
		PreparedStatement pstmt = null;
		String createAccount = "INSERT INTO accounts (nicname,accountowner,balance) VALUES (?,?,?)";
		int userId = user.getId();
		try {
			pstmt = connect().prepareStatement(createAccount);
			pstmt.setString(1, nicname);
			pstmt.setInt(2, userId);
			pstmt.setInt(3, 0);
			pstmt.executeUpdate();
			Printer.accountCreatedSuccesfully();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Account acc = new Account(user.getId(),nicname);
		return acc;
	}
	
	public void removeBalance(User user, Account acc, double withdraw ) throws SQLException {
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
				double newTotal = currentAccBal - withdraw;
				if(newTotal > 0) {
					pstmt = connect().prepareStatement(updateCommand);
					pstmt.setDouble(1, withdraw);
					pstmt.setString(2, accNicname);
					pstmt.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}

//	public Account createAccount(User user, String nicname) throws SQLException {
//		return null;
//	}
	
	public ArrayList<Account> getAllAccts(User user) throws SQLException {
		ArrayList<Account> accounts = new ArrayList<Account>();
		String getAllAccts = "SELECT id,isapproved FROM accounts WHERE isapproved=false";
		Statement stmt = connect().createStatement();
		ResultSet rs = stmt.executeQuery(getAllAccts);
		while(rs.next()) {
			Account userAcc = new Account(user.getId(), "placeholder");
			userAcc.setActName(rs.getString("nicname"));
			userAcc.setBalance(rs.getInt("balance"));
			accounts.add(userAcc);
		}
		return accounts;
	}

	public void approveAccount(User user, Account acc) {
		PreparedStatement appAcc = null;
		int accId = (int) acc.getId();
		String action = "UPDATE accounts SET isapproved=true WHERE id=?";
		try {
			appAcc = connect().prepareStatement(action);
			appAcc.setInt(1, accId);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public void transferFunds(User user, double amount, Account acc) throws SQLException {
		Transfer transfer = new Transfer(acc, generateAcc(),amount);
		User sender = transfer.getSender();
		Account senderAcc = transfer.getSenderAcc();
		if(senderAcc.getBalance() - amount > 0) {
			removeBalance(sender,senderAcc,amount);
			addToTransferTable(transfer);
			Printer.transferSend();
		} else {
			Printer.transferFail();
		}
	}
	
	public void acceptFunds(Transfer transfer) throws SQLException {
		User recipient = transfer.getRecipient();
		Account recipAcc = transfer.getRecipientAcc();
		double amount = transfer.getAmount();
		if(recipAcc.getBalance() + amount > 0) {
			addBalance(recipient,recipAcc,amount);
			Printer.transferSuccess();
		}
	}
	
	public void addToTransferTable(Transfer transfer) {
		PreparedStatement pstmt = null;
		double senderAccId = transfer.getSenderAccId();
		double recipientAccId = transfer.getRecipientAccId();
		double amount = transfer.getAmount();
		try {
			String updateCommand = "INSERT INTO transfers (senderaccid,recipientaccid,amount) VALUES (?,?,?)";
			pstmt = connect().prepareStatement(updateCommand);
			pstmt.setDouble(1, senderAccId);
			pstmt.setDouble(2, recipientAccId);
			pstmt.setDouble(3, amount);
			pstmt.executeUpdate();	
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	private Account generateAcc() {
		return new Account(0,"placeholder");		
	}

}
