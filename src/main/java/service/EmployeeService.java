package service;

import java.sql.Connection;
import java.sql.SQLException;

import common.Employee;
import common.User;
import dao.EmployeeDao;


public class EmployeeService {

	private EmployeeDao edao = new EmployeeDao();
	
	public Employee getUser(Employee employee) throws SQLException {
		Employee currentEmployee = edao.getEmployee(employee);
		return currentEmployee;
	}
}
