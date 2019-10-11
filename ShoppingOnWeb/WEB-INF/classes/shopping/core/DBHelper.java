package shopping.core;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBHelper {
	static Properties info = new Properties();
	static {
		
		InputStream in =DBHelper.class.getClassLoader().getResourceAsStream("jdbc.properties");
	try {
		info.load(in);
		
	}catch(IOException e) {
		e.printStackTrace();
	}
	
	
	}
//	private static String url = "jdbc:mysql://localhost:3306/user?serverTimezone=UTC";
//	private static String username ="root";
//	private static String password = "12345";
	
	public static Connection getconnection() throws SQLException, ClassNotFoundException  {
		
		Class.forName(info.getProperty("Driver")); 
		
	Connection connection = DriverManager.getConnection
			(info.getProperty("url"), info);
	return connection;
	}
}
