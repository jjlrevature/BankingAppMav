package common;
import java.util.ArrayList;
import java.util.List;

public class User {
	protected String username;
	protected String password;
	private int id;
	private ArrayList<Account> userAccounts = new ArrayList<Account>();
	
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;		
	}
	
	public User(String[] credentials) {
		this.username = credentials[0];
		this.password = credentials[1];
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
	
	public Account createAccount(User user, String nickname) {
		int j = this.id;
		Account newAcc = new Account(j,nickname);
		return newAcc;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
