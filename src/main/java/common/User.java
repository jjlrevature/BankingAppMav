package common;
import java.util.ArrayList;

public class User {
	private String username;
	private String password;
	private ArrayList<Account> userAccounts = new ArrayList<Account>();
	
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;		
	}
	
	
	
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<Account> getUserAccounts() {
		return userAccounts;
	}

	public void setUserAccounts(ArrayList<Account> userAccounts) {
		this.userAccounts = userAccounts;
	}
	
}
