package service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.DriverManager;

import client.Input;
import common.Account;
import common.Transfer;
import common.User;
import dao.AccountDao;
import dao.UserDao;

public class AccountService implements AccountDao {
	private static final Logger logger = LogManager.getLogger(UserService.class);
	
	
	// Called methods
	public Account createAccount(User newUser,Scanner sc) throws SQLException  {
		Printer.accountNicname();
		String nicname = Input.getString(sc);
		Account currentAccount = createAccountQ(newUser, nicname);
		logger.info("new account created");
		return currentAccount;
	}	
	
	public void addNewBalanceServ(User user, Account acc, double deposit) throws SQLException {
		double newBalance = acc.getBalance() + deposit;
		if(newBalance > 0.0) {
			try {
				addBalance(user, acc, newBalance);
			} catch (FileNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			acc.setBalance(newBalance);			
		}
	}
	
	public void removeBalanceServ(User user, Account acc, double withdraw) throws SQLException {
		double newBalance = acc.getBalance() - withdraw; 
		if(newBalance > 0) {
			removeBalance(user, acc, newBalance);
			acc.setBalance(newBalance);			
		} else {
			System.out.println("Cannot complete transaction. Not Enough Funds.");			
		}
	}

	
	//============== From Interface ===============
	public Connection connect() throws FileNotFoundException {
		logger.info("Connection method invoked from AccountService");
		Connection conn = null;
		String configLocation = "F:/Revature/txt_files/Project0-config.properties";
		try {
			FileInputStream fis = new FileInputStream(configLocation);
			Properties props = new Properties();
			props.load(fis);
			conn = DriverManager.getConnection(props.getProperty("db_url"),props.getProperty("db_user"), props.getProperty("db_pass"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return conn;
	}
	
	public void addBalance(User user, Account acc, double deposit ) throws SQLException, FileNotFoundException {
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
		pstmt.close();
		logger.info("balance added to account");
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
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Account acc = new Account(user.getId(),nicname);
		pstmt.close();
		
		return acc;
	}
	
	public void removeBalance(User user, Account acc, double withdraw ) throws SQLException {
		PreparedStatement pstmt = null;
		String accNicname = acc.getActName();
		String updateCommand = "UPDATE accounts SET balance=? WHERE nicname=?";
		try {	
			pstmt = connect().prepareStatement(updateCommand);
			pstmt.setDouble(1, withdraw);
			pstmt.setString(2, accNicname);
			pstmt.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		pstmt.close();
		logger.info("balance removed from acc");		
	}

//	public Account createAccount(User user, String nicname) throws SQLException {
//		return null;
//	}
	
	public ArrayList<Account> getAllAccts(User user) throws SQLException {
		ArrayList<Account> accounts = new ArrayList<Account>();
		String getAllAccts = "SELECT accid,isapproved,accountowner,nicname,balance FROM accounts WHERE isapproved=? OR (isapproved IS NULL AND ? IS NULL)";
		
		PreparedStatement pstmt = null;
		try {
			pstmt = connect().prepareStatement(getAllAccts);
			pstmt.setNull(1, Types.BIT);
			pstmt.setNull(2, Types.BIT);
		} catch (FileNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			Account userAcc = new Account(rs.getInt("accountowner"), "placeholder");
			userAcc.setActName(rs.getString("nicname"));
			userAcc.setBalance(rs.getInt("balance"));
			accounts.add(userAcc);
		}
		
		logger.info("got all accounts");
		pstmt.close();
		return accounts;
	}

	public void approveAccount(User user, Account acc) {
		PreparedStatement appAcc = null;
		int accId = (int) acc.getId();
		String action = "UPDATE accounts SET isapproved=true WHERE id=?";
		try {
			appAcc = connect().prepareStatement(action);
			appAcc.setInt(1, accId);
			logger.info("account approved");
			appAcc.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void transferFunds(User user, double amount, Account acc, int recipId) throws SQLException {
		Transfer transfer = new Transfer(acc, generateAcc(recipId),amount);
		boolean g = false;
		transfer.setAccepted(g);
		User sender = transfer.getSender();
		Account senderAcc = transfer.getSenderAcc();
		if(senderAcc.getBalance() - amount > 0) {
			double result = senderAcc.getBalance() - amount;
			removeBalance(sender,senderAcc,result);
			addToTransferTable(transfer);
			Printer.transferSend();
			logger.info("funds transferred");
		} else {
			Printer.transferFail();
		}
	}
	
	public void acceptFunds(Transfer transfer) throws SQLException {
		User recipient = transfer.getRecipient();
		Account recipAcc = transfer.getRecipientAcc();
		double amount = transfer.getAmount();
		int tId = transfer.getTransferid();
		if(recipAcc.getBalance() + amount > 0) {
			addNewBalanceServ(recipient,recipAcc,amount);
			Printer.transferSuccess();
			PreparedStatement update = null;
			String updateStr = "UPDATE transfers SET isaccepted=true WHERE transferid=?";
			try {
				update = connect().prepareStatement(updateStr);
				logger.info("funds accepted into account");
			} catch (FileNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			update.setInt(1, tId);
			update.executeUpdate();
			update.close();
		}
	}
	
	public void addToTransferTable(Transfer transfer) {
		PreparedStatement pstmt = null;
		double senderAccId = transfer.getSenderAccId();
		double recipientAccId = transfer.getRecipientAccId();
		double amount = transfer.getAmount();
		try {
			String updateCommand = "INSERT INTO transfers (senderaccid,recipientaccid,amount,isaccepted) VALUES (?,?,?,?)";
			pstmt = connect().prepareStatement(updateCommand);
			pstmt.setDouble(1, senderAccId);
			pstmt.setDouble(2, recipientAccId);
			pstmt.setDouble(3, amount);
			pstmt.setBoolean(4, false);
			pstmt.executeUpdate();	
			logger.info("transfer added to transfer in database");
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	private Account generateAcc(int i) {
		Account nAcc = new Account(0,"placeholder");
		nAcc.setId(i);
		return nAcc;
	}

}
