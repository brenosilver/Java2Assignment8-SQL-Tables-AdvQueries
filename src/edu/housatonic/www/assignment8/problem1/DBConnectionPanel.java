//****************************************************************************************
//	Author: Breno Silva		Last Modified: 04/29/14
//
//	CSC*E224				Programming Assignment VIII		Problem 1
//****************************************************************************************

package edu.housatonic.www.assignment8.problem1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionPanel {
	private static Connection conn;
	private static boolean connected;
	
	// Constructor
	public DBConnectionPanel() {
		setConnected(false);
	}
	
	// Connect to DB method
	public void connect(String driver, String dbURL, String userId, String password){
		
		if(!isConnected()){
			// Load driver for DB
			try {
				Class.forName(driver);
				System.out.println("Driver loaded...");
			} catch (ClassNotFoundException e) {
				System.err.println(e.getMessage());
				 System.err.println("Driver could not be loaded...");	
			}
						
			// Connect to DB
			try {
				setConn(DriverManager.getConnection(dbURL, userId, password));
				System.out.println("Connection to database established...");
				setConnected(true);
			} catch (SQLException e) {
				System.err.println(e.getMessage());
				System.err.println("Connection to the database could not be established...");
			}
		}
		else
			System.out.println("Already connected to DB...");
		
	}
	
	// Close method
	public void close(){
		if(getConn() != null)
			try {
				getConn().close();
				setConnected(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	
	// Mutable methods
	public static Connection getConn() {
		return conn;
	}

	public static void setConn(Connection conn) {
		DBConnectionPanel.conn = conn;
	}

	public static boolean isConnected() {
		return connected;
	}

	public static void setConnected(boolean connected) {
		DBConnectionPanel.connected = connected;
	}
	

}
