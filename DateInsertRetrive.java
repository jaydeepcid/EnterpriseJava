package dateoperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import util.JdbcConnection;

public class DateInsertRetrive {

	public static void main(String[] args) throws ParseException, SQLException {
		// TODO Auto-generated method stub
		
		// resource used
		Connection connection = null;
		Statement smt = null;
		ResultSet resultset =null;
		boolean flag = true;
		 while(flag) {  
		    	connection = JdbcConnection.getJdbcConnection(); 
			
				System.out.println("Enter 1 for insert \n Enter 2 for select\n Enter 3 for Exit");
				Scanner sc = new Scanner(System.in);
				int option =sc.nextInt();
			
				switch (option) {
				case 1:
					    insert(connection);
					    break;
				case 2:
			            select(connection);
			            break;             
				case 3:
					   flag= false;
					   JdbcConnection.closeConnection(resultset,smt, connection);
					   break;
				 }
			
			   
				
		}	
		 
		 
	}
  


private static void insert(Connection connection) throws ParseException {
	PreparedStatement pstmt = null;
	Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the name:: ");
	String name = scanner.next();
	System.out.print("Enter the address:: ");
	String address = scanner.next();
	System.out.print("Enter the gender:: ");
	String gender = scanner.next();
	
	System.out.print("Enter the DOB::(dd-MM-yyyy) ");
	String dob = scanner.next();
	
	System.out.print("Enter the DOJ::(MM-dd-yyyy) ");
	String doj = scanner.next();
	
	System.out.print("Enter the DOM::(yyyy-MM-dd) ");
	String dom = scanner.next();
	
	SimpleDateFormat sdfdob = new SimpleDateFormat("dd-MM-yyyy");
	java.util.Date uDatedob = sdfdob.parse(dob);
	long timedob = uDatedob.getTime();
	java.sql.Date sqlDatedob = new java.sql.Date(timedob);
	
	
	
	SimpleDateFormat sdf2doj = new SimpleDateFormat("MM-dd-yyyy");
	java.util.Date uDatedoj = sdf2doj.parse(doj);
	long timedoj = uDatedoj.getTime();
	java.sql.Date sqlDatedoj = new java.sql.Date(timedoj);
	
	
	java.sql.Date sqlDatedom = java.sql.Date.valueOf(dom);//converting user input into sql date  
	
	/*
	 * SimpleDateFormat sdf2dom = new SimpleDateFormat("yyyy-MM-dd"); java.util.Date
	 * uDatedom = sdf2doj.parse(dom); long timedom = uDatedom.getTime();
	 * java.sql.Date sqlDatedom = new java.sql.Date(timedom);
	 */
	
	System.out.println("DOB SQL  date is   :: " + sqlDatedob);
	
	System.out.println("DOJ SQL  date is   :: " + sqlDatedoj);
	
	System.out.println("DOM SQL  date is   :: " + sqlDatedom);

	String sqlInsertQuery = "insert into employee(name,address,gender,dob,doj,dom) values (?,?,?,?,?,?)";

	try {

		if (connection != null)
			pstmt = connection.prepareStatement(sqlInsertQuery);

		if (pstmt != null) {
			pstmt.setString(1, name);
			pstmt.setString(2,address);
			pstmt.setString(3, gender);
			pstmt.setDate(4, sqlDatedob);
			pstmt.setDate(5, sqlDatedoj);
			pstmt.setDate(6, sqlDatedom);

			int rowAffected = pstmt.executeUpdate();

			System.out.println("No of rows affected is:: " + rowAffected);
		}

	} catch (SQLException se) {
		se.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {

		try {
			JdbcConnection.closeConnection(null, pstmt, connection);
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}


private static void select(Connection connection) {
	PreparedStatement pstmt = null;
	ResultSet resultset = null;
	
	Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the name:: ");
    String name  = scanner.next();

	String sqlSelectQuery = "select name,address,gender,dob,doj,dom from employee where name = ?";

	try {
		
		if (connection != null)
			pstmt = connection.prepareStatement(sqlSelectQuery);
		    
		    pstmt.setString(1,name);

		if (pstmt != null) {
			
            resultset = pstmt.executeQuery();
            
            if(resultset!=null) {
            	System.out.println("NAME\taddress\tdob\tdoj\tdom");
            	while(resultset.next()) {
            		
	            	String empname = resultset.getString(1);
	            	String address = resultset.getString(2);
	            	String gender = resultset.getString(3);
	            	
	            	java.sql.Date dobdate = resultset.getDate(4);
	            	SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy");
	            	String dob = sdf.format(dobdate);
	            	
	            	
	            	java.sql.Date dojdate = resultset.getDate(5);
	            	String doj = sdf.format(dojdate);
	            	
	            	java.sql.Date domdate = resultset.getDate(6);
	            	String dom = sdf.format(domdate);
	            	
	            	System.out.println(empname+"\t"+address+""+dob+""+doj+""+dom);
	            	
            	}
            	
            }

		
		}

	} catch (SQLException se) {
		se.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {

		try {
			JdbcConnection.closeConnection(null, pstmt, connection);
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	
	
	
}
}
