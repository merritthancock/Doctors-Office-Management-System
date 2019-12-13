package sprint2;

import java.sql.*;

public class DatabaseDriver{
	
	private static String url = "jdbc:mysql://35.231.135.99:3306/doms";
	private static String username = "root";
	private static String password = "toor";
	
	public static Connection getConnection() throws Exception{
		Connection con = DriverManager.getConnection(url, username, password);
		return con;
	}	
	
}