package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded");
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/customer_management_service","root","anm10");
				System.out.println("Connected");
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		return conn;
	}
	
}
