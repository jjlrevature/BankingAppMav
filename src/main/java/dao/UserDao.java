package dao;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import common.User;

public interface UserDao {

	Connection connect() throws FileNotFoundException;
	
	public void createUser(User user) throws SQLException;
	
	public User getUser(User user) throws SQLException;
	
	public boolean checkForTransfer(User user) throws SQLException;
	
}
