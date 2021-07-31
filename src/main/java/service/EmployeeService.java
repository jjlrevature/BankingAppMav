package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.Employee;
import common.User;
import dao.EmployeeDao;


public class EmployeeService implements EmployeeDao{

	
	
	public Employee getUser(Employee employee) throws SQLException {
		Employee currentEmployee = getEmployee(employee);
		return currentEmployee;
	}
	
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
	
	public Employee getEmployee(Employee emp) throws SQLException {
		String findUserCommand = "SELECT id,username,password FROM users WHERE password=? AND username=?";
		String findAccounts = "SELECT nicname,accountowner,balance FROM accounts INNER JOIN users ON accounts.accountowner = users.id";
		PreparedStatement pstmt = connect().prepareStatement(findUserCommand);
		PreparedStatement pst = connect().prepareStatement(findAccounts);
		String username = emp.getUsername();
		String password = emp.getPassword();
		Employee currentEmp = null;
		try {
			// Finds User in database			
			pstmt.setString(1, password);
			pstmt.setString(2, username);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {				
				currentEmp = new Employee(null,null);
				// set new user = database-user's data
				currentEmp.setUsername(rs.getString("username"));
				currentEmp.setPassword(rs.getString("password"));
				currentEmp.setId(rs.getInt("id"));
				currentEmp.setEmployee(true);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentEmp;
	}
}
