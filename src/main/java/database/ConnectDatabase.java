package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnectDatabase {
	private static ConnectDatabase instance;
	
	public Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://localhost:1433; databaseName = FAST_FOOD; trustServerCertificate = true; instanceName=QUOCBAO";
			String username = "sa";
			String password = "123";
			
			connection = DriverManager.getConnection(url, username, password);
			System.out.println("Ket Noi Thanh Cong!");
		} catch (Exception e) {
			System.out.println("Khong the ket noi!");
			e.printStackTrace();
		}
		return connection;
	}
	
	public void closeConnection(Connection connection) {
    	try {
    		if(connection != null) {
    			connection.close();
    		}
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
	public static ConnectDatabase getInstance() {
		if(instance == null) {
			instance = new ConnectDatabase();
		}
		return instance;
	}

}


