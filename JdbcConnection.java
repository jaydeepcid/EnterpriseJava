package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JdbcConnection {
	  
	
	public static Connection getJdbcConnection() throws SQLException {
		
		Connection connection = null;
		
		// TODO Auto-generated method stub
		String url ="jdbc:mysql://localhost:3306/democourses";
		String user = "root";
		String password ="Root@admin";
		
		connection = DriverManager.getConnection(url, user, password); 
		
		if(connection!=null)
			return connection;
		
		
		return connection;
		
	}
	
	public static void  closeConnection(ResultSet resultset,Statement  smt ,Connection connection) throws SQLException {
		
		
		    if(resultset!=null) {
		    	resultset.close();
			
			}
		    if(smt!=null) {
		    	smt.close();
			
			}
		    
			if(connection!=null) {
		      connection.close();
			
			}
	}

}
