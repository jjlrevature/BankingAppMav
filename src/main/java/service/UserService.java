package service;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import common.Account;
import common.Transfer;
import common.User;
import dao.UserDao;

public class UserService implements UserDao {
	
		
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
		
		public Connection connect() {
			Connection conn = null;
			try {
				conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","pOOkiebear2!");
			} catch (SQLException e) {
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
				Printer.userCreatedSuccesfully();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		public ArrayList<String> getAllUsers() throws SQLException {
			ArrayList<String> users = new ArrayList<String>();
			String getAllUsers = "SELECT username FROM users";
			Connection conn = connect();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(getAllUsers);
			while(rs.next()) {
				users.add(rs.getString("username"));
			}
			return users;
		}
		
		public User getUser(User user) throws SQLException {
			String findUser = "SELECT id,username,password FROM users WHERE password=? AND username=?";
			String findAccounts = "SELECT nicname,accountowner,balance, isapproved FROM accounts INNER JOIN users ON accounts.accountowner = users.id";
			PreparedStatement pstmt = connect().prepareStatement(findUser);
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
						userList.add(userAcc);
						currentUser.setUserAccounts(userList);		
					}				
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return currentUser;
		}

		public boolean checkForTransfer(User user) throws SQLException {
			boolean b = false;
			
			for(int x = 0; x < user.getUserAccounts().size(); x++) {
				String checkTransfers = "SELECT recipientaccid,amount FROM transfers WHERE recipientaccid=?";
				double userAccId = user.getUserAccounts().get(x).getId();
				PreparedStatement cT = connect().prepareStatement(checkTransfers);
				cT.setDouble(1, userAccId);
				ResultSet rs = cT.executeQuery();			
				while(rs.next()) {
					b = true;
				}			
			}
			return b;
		}

		public ArrayList<Transfer> getTransfer(User user) throws SQLException {
			ArrayList<Account> userAccs = user.getUserAccounts();
			ArrayList<Transfer> transfers = new ArrayList<Transfer>();
			for(int y = 0; y < userAccs.size(); y++) {
				double userAccId = user.getUserAccounts().get(y).getId();				
				String buildTransfers = "SELECT recipientaccid,amount FROM transfers WHERE recipientaccid=?";
				PreparedStatement pending = connect().prepareStatement(buildTransfers);
				pending.setDouble(1, userAccId);
				ResultSet rs = pending.executeQuery();
				while(rs.next()) {
					for (int x = 0; x < userAccs.size(); x++) {	
						if(userAccs.get(x).getId() == rs.getInt("recipientaccid")) {
							Transfer newTransfer = new Transfer(generateAcc(),userAccs.get(x), rs.getDouble("amount"));
							transfers.add(newTransfer);						
						}
					}
				}
			}
			return transfers;
		}

		private Account generateAcc() {
			return new Account(0,"placeholder");		
		}
		
}
