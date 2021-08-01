package dao;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import common.Account;
import common.User;

// Data access object
public interface AccountDao {
	
	public Connection connect() throws FileNotFoundException;
	
	
	public void addBalance(User user, Account acc, double deposit ) throws SQLException, FileNotFoundException;
	
	public void removeBalance(User user, Account acc, double withdraw ) throws SQLException;
	
}
