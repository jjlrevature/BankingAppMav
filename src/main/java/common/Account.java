package common;

public class Account {
	private boolean isApproved;
	private double balance;
	private String accountName;
	private int accountOwner;
	private double id;
	
	public Account(int userid, String accountName) {
		this.accountOwner = userid;
		this.accountName = accountName;
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
		return accountName;
	}

	public void setActName(String actName) {
		this.accountName = actName;
	}

	public int getAccountOwner() {
		return accountOwner;
	}

	public void setAccountOwner(int accountOwner) {
		this.accountOwner = accountOwner;
	}
	
	public void actionDeposit(int amount) {
		this.balance = this.balance + amount;
	}
	
	public void actionWithdrawl(int amount) {
		this.balance = this.balance - amount;
	}

	public double getId() {
		return id;
	}

	public void setId(double id) {
		this.id = id;
	}

	public String toString() {
		return "AccountName: " + this.getActName() + " isApproved: " + this.isApproved();
	}
	
}
