package common;

public class Transfer {
	User sender;
	User recipient;
	Account senderAcc;
	Account recipientAcc;
	double amount;
	
	public Transfer(Account sender, Account recipient, double amount) {
		this.senderAcc = sender;
		this.recipientAcc = recipient;
		this.amount = amount;
	}

	public User getSender() {
		return this.sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getRecipient() {
		return this.recipient;
	}

	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}
	
	public Account getSenderAcc() {
		return this.senderAcc;
	}

	public void setSenderAcc(Account sender) {
		this.senderAcc = sender;
	}

	public Account getRecipientAcc() {
		return this.recipientAcc;
	}

	public void setRecipientAcc(Account recipient) {
		this.recipientAcc = recipient;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public double getSenderAccId() {
		double i = this.senderAcc.getId();
		return i;
	}
	
//	public void setRecipientAccId(double d) {
//		this.recipientAcc = d;
//	}
	
	public double getRecipientAccId() {
		double i = this.recipientAcc.getId();
		return i;
	}
	
	public double getSenderId() {
		return this.senderAcc.getId();
	}
	
	public double getRecipientId() {
		return this.recipientAcc.getId();
	}
}
