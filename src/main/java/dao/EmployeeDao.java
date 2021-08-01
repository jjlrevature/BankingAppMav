package dao;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.Account;
import common.Employee;
import common.User;

public interface EmployeeDao {

	Connection connect() throws FileNotFoundException;
	
	public Employee getEmployee(Employee emp) throws SQLException;
}
