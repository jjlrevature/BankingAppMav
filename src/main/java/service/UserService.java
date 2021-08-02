package service;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import common.Account;
import common.Transfer;
import common.User;
import dao.UserDao;

public class UserService implements UserDao {
		private static final Logger logger = LogManager.getLogger(UserService.class);
		
		public User createNewUser(User user) throws SQLException {
			createUser(user);
			return user;
		}

		public User getCurrentUser(User user) throws SQLException {
			User currentUser =  getUser(user);
			return currentUser;
			
		}
		
		public ArrayList<Account> getUserAccounts(User user) {
			return user.getUserAccounts();
		}
		
		
		
		
		//============ From interface ============
		
		public Connection connect() throws FileNotFoundException { 
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
				logger.info("New User Created");
				Printer.userCreatedSuccesfully();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		public ArrayList<String> getAllUsers() throws SQLException {
			ArrayList<String> users = new ArrayList<String>();
			String getAllUsers = "SELECT username FROM users";
			Connection conn = null;
			try {
				conn = connect();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(getAllUsers);
			
			while(rs.next()) {
				users.add(rs.getString("username"));
			}
			logger.info("got all Users");
			return users;
		}
		
		public User getUser(User user) throws SQLException {
			String findUser = "SELECT id,username,password FROM users WHERE password=? AND username=?";
			String findAccounts = "SELECT nicname,accountowner,balance,isapproved,accid FROM accounts INNER JOIN users ON accounts.accountowner = users.id";
			PreparedStatement pstmt = null;
			try {				
				pstmt = connect().prepareStatement(findUser);
			} catch (FileNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			PreparedStatement pst = null;
			try {
				pst = connect().prepareStatement(findAccounts);
			} catch (FileNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
					if(rs.getString("username") != null) {					
					currentUser = new User(null,null);
					// set new user = database-user's data
					currentUser.setUsername(rs.getString("username"));
					currentUser.setPassword(rs.getString("password"));
					currentUser.setId(rs.getInt("id"));
					userId = rs.getInt("id");
					}
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
						userAcc.setApproved(rss.getBoolean("isapproved"));
						userAcc.setId(rss.getInt("accid"));
						userList.add(userAcc);
						currentUser.setUserAccounts(userList);		
					}				
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("user returned from getUser");
			return currentUser;
		}

		public boolean checkForTransfer(User user) throws SQLException {
			boolean b = false;
			
			for(int x = 0; x < user.getUserAccounts().size(); x++) {
				String checkTransfers = "SELECT recipientaccid,amount FROM transfers WHERE recipientaccid=?";
				double userAccId = user.getUserAccounts().get(x).getId();
				PreparedStatement cT = null;
				try {
					cT = connect().prepareStatement(checkTransfers);
				} catch (FileNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cT.setDouble(1, userAccId);
				ResultSet rs = cT.executeQuery();			
				while(rs.next()) {
					b = true;
				}			
			}
			logger.info("logged in user has checked for transfers");
			return b;
		}

		public ArrayList<Transfer> getTransfer(User user) throws SQLException {
			ArrayList<Account> userAccs = user.getUserAccounts();
			ArrayList<Transfer> transfers = new ArrayList<Transfer>();
			double userAccId = 0;
			for(int y = 0; y < userAccs.size(); y++) {
				userAccId = user.getUserAccounts().get(y).getId();
				String buildTransfers = "SELECT recipientaccid,amount,transferid FROM transfers WHERE recipientaccid=? AND isaccepted=false";
				PreparedStatement pending = null;
				try {
					pending = connect().prepareStatement(buildTransfers);
				} catch (FileNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pending.setDouble(1, userAccId);
				ResultSet rs = pending.executeQuery();
				while(rs.next()) {						
						if(userAccs.get(y).getId() == rs.getInt("recipientaccid")) {
							Transfer newTransfer = new Transfer(generateAcc(),userAccs.get(y), rs.getDouble("amount"));
							newTransfer.setTransferid(rs.getInt("transferid"));
							transfers.add(newTransfer);						
						}
				}
			}
			logger.info("built/returned arraylist of transfers");
			return transfers;
		}

		private Account generateAcc() {
			return new Account(0,"placeholder");		
		}
		
}
