package com.fdmgroup.models.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connections {
	
	private static Connection connection = null;
	
	private Connections(){
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			connection = DriverManager.getConnection("jdbc:oracle:thin:trainee1/!QAZSE4@localhost:1521:XE");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public static Connection getConnection()  {
		try {
			if (connection == null || connection.isClosed()) {
				new Connections();
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} return connection;
	}
	
	public static void CloseConnection()  {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
