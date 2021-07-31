package dao;
import java.sql.Connection;
import java.sql.SQLException;
import common.Account;
import common.User;

// Data access object
public interface AccountDao {
	
	public Connection connect();
	
	
	public void addBalance(User user, Account acc, double deposit ) throws SQLException;
	
	public void removeBalance(User user, Account acc, double withdraw ) throws SQLException;
	
}
