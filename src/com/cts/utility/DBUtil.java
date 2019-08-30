package com.cts.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	
private static Connection conn=null;
	
	private DBUtil() {
		
	}
	
	public static Connection getDBConnection(){
		if(conn == null){

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String msAccDB = "C:\\Users\\613161\\Documents\\NonTicketingEffort.accdb";
        String dbURL = "jdbc:ucanaccess://"+ msAccDB; 
		
		try {
conn=DriverManager.getConnection(dbURL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}	
		return conn;
	}

}
