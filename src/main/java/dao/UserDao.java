package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import common.User;

public class UserDao {

	public Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","pOOkiebear2!");
			System.out.println("Connectioned!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
		return conn;
	}
	
	// Read
		static void readUsername(Statement stmt) throws SQLException {
			ResultSet rs = stmt.executeQuery("SELECT username FROM users");
		
			while(rs.next()) {
				String name = rs.getString("username");
				System.out.println(name);
			}
		}
		
		// Create
		public static void createUser(User user, Connection conn) throws SQLException {
			Statement stmt = null;
			try {
				stmt = conn.createStatement();
				int inserted = stmt.executeUpdate("INSERT INTO users (username,password) VALUES (user.getUsername(), user.getPassword()");
				System.out.println(inserted);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
