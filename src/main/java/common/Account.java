package common;

public class Account {
	private boolean isApproved;
	private double balance;
	private String accounttName;
	private User accountOwner;
	private double id;
	
	public Account(User user, String accountName) {
		this.accountOwner = user;
		this.accounttName = accountName;
		this.balance = 0;
		this.isApproved = false;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		// if(current user is employee)
		this.isApproved = isApproved;
	}

	public String getActName() {
		return accounttName;
	}

	public void setActName(String actName) {
		this.accounttName = actName;
	}

	public User getAccountOwner() {
		return accountOwner;
	}

	public void setAccountOwner(User accountOwner) {
		this.accountOwner = accountOwner;
	}
	
	public void actionDeposit(double amount) {
		this.balance = this.balance + amount;
	}
	
	public void actionWithdrawl(double amount) {
		this.balance = this.balance - amount;
	}

	public double getId() {
		return id;
	}

	public void setId(double id) {
		this.id = id;
	}

}
