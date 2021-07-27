package dao;
import java.sql.Connection;
import java.sql.SQLException;
import common.Account;
import common.User;

// Data access object
public interface AccountDao {
	
	public Connection connect();
	
	public void createAccount(User user, String nicname) throws SQLException;
	
	public void addBalance(User user, Account acc, int deposit ) throws SQLException;
	
	public void removeBalance(User user, Account acc, int withdraw ) throws SQLException;
	
}
