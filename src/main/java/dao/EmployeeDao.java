package dao;

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

	Connection connect();
	
	public Employee getEmployee(Employee emp) throws SQLException;
}
