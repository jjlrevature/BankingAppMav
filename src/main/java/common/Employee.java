package common;

public class Employee extends User {

	private boolean isEmployee = true;
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Employee(String username, String password) {
		super(username, password);
	}

	public Employee(String[] creds) {
		super(creds[0],creds[1]);
	}

	public boolean isEmployee() {
		return isEmployee;
	}

	public void setEmployee(boolean isEmployee) {
		this.isEmployee = isEmployee;
	}
	
	
}
