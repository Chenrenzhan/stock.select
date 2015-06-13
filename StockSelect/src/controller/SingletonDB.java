package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SingletonDB {
	private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String PROTOCOL = "jdbc:derby:";
	private static final String DB_NAME = "data/stockdb";
	
	private static SingletonDB singletoDBInstance = null;
	
	private static Connection connection;
	private static Statement statement;
	
	private SingletonDB(){
		loadDriver();
		statement = connect();
	}
	
	public static SingletonDB Instance(){
		if(singletoDBInstance == null){
			singletoDBInstance = new SingletonDB();
		}
		return singletoDBInstance;
	}
	
	static void loadDriver() {
		try {
			Class.forName(DRIVER).newInstance();
			System.out.println("Loaded the appropriate driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Statement connect(){
		try { 
//			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			System.out.println("Load the embedded driver");
			connection = null;
			Properties props = new Properties();
			connection=DriverManager.getConnection(PROTOCOL + DB_NAME+ ";create=true");
			System.out.println("create and connect to helloDB");
			connection.setAutoCommit(false);
			// create a table and insert two records
			Statement s = connection.createStatement();
			System.out.println("connections finish");
			return s;
		}
		catch (SQLException e) {
		    for (Throwable t : e) {
		        System.out.println("Message: " + t.getMessage());
		    }

		}
		return null;
	}

	public static Connection getConnection() {
		return connection;
	}

	public static Statement getStatement() {
		return statement;
	}
	
}
