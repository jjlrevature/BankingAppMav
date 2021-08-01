package service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.Employee;
import common.User;
import dao.EmployeeDao;


public class EmployeeService implements EmployeeDao{
	private static final Logger logger = LogManager.getLogger(UserService.class);

	
	
	public Employee getUser(Employee employee) throws SQLException {
		Employee currentEmployee = getEmployee(employee);
		return currentEmployee;
	}
	
	public Connection connect() throws FileNotFoundException {
		logger.info("Connection method invoked from EmployeeService");
		Connection conn = null;
		String configLocation = "F:/Revature/txt_files/Project0-config.properties";
		try {
			FileInputStream fis = new FileInputStream(configLocation);
			Properties props = new Properties();
			props.load(fis);
			conn = DriverManager.getConnection(props.getProperty("db_url",props.getProperty("db_user", props.getProperty("db_pass"))));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return conn;
	}
	
	public Employee getEmployee(Employee emp) throws SQLException {
		String findUserCommand = "SELECT id,username,password FROM users WHERE password=? AND username=?";
		String findAccounts = "SELECT nicname,accountowner,balance FROM accounts INNER JOIN users ON accounts.accountowner = users.id";
		PreparedStatement pstmt = null;
		try {
			pstmt = connect().prepareStatement(findUserCommand);
		} catch (FileNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			PreparedStatement pst = connect().prepareStatement(findAccounts);
		} catch (FileNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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

		logger.info("returned Employee from getEmployee()");
		return currentEmp;
	}
}
