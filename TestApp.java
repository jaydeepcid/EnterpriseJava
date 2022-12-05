import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.mysql.cj.jdbc.Driver;

import util.JdbcConnection;

public class TestApp {
  
	public static void main(String[] args) throws SQLException {
		boolean flag= true;
		Connection connection = null;
		Statement smt = null;
		ResultSet resultset = null;
		
		    while(flag) {  
		    	connection =JdbcConnection.getJdbcConnection();
				if(connection!= null) {
				System.out.println("Enter 1 for insert \n Enter 2 for update \nEnter 3 for select\nEnter 4 for Delete \nEnter 5 for Exit");
				Scanner sc = new Scanner(System.in);
				int option =sc.nextInt();
				
				switch (option) {
				case 1:
					    insert(connection);
					    break;
				case 2:
				        update(connection);
				        break;
			
				case 3:
			             select(connection);
			             break;
				
				case 4:
			             delete(connection); 
			             break;
			             
				case 5:
					   flag= false;
					   JdbcConnection.closeConnection(resultset,smt, connection);
					   break;
				 }
			}
			   
				
		}			
		
		
            
			
	
    }
	    
		
		

	
	
	
	
	private static void insert(Connection connection ) throws SQLException  {
		PreparedStatement smt=null;
		Scanner sc = new Scanner(System.in);
		try {
		
		System.out.print("Enter course id");
		int sid = sc.nextInt();
		System.out.print("Enter course title");
		String title =  sc.next();
		System.out.print("Enter course descripton");
		String desc = sc.next();
		
		String sql = "insert into course(id,description,title) values (?,?,?)";
		smt = connection.prepareStatement(sql);
        if(smt!=null) {
            
            System.out.println(sql);
            smt.setInt(1,sid);
            smt.setString(2,desc);
            smt.setString(3,title);
            int nub = smt.executeUpdate();
            System.out.println("Number of row affected:--->"+nub);
            }
           
		}catch(SQLException se) {
		      se.printStackTrace();
		}finally {
			JdbcConnection.closeConnection( null,smt,connection);
		}
	
		
	}
	
	private static void update(Connection connection ) throws SQLException {
		PreparedStatement smt=null;
		Scanner sc = new Scanner(System.in);
		try {
		
		System.out.print("Enter course id to update");
		int sid = sc.nextInt();
		System.out.print("Update course title");
		String title =  sc.next();
		System.out.print("Update course descripton");
		String desc = sc.next();
		
		String sql = "update course set title=?,description=? where id = ?";
		smt = connection.prepareStatement(sql);
        if(smt!=null) {
            
            System.out.println(sql);
            smt.setString(1,title);
            smt.setString(2,desc);
            smt.setInt(3,sid);
            System.out.println(smt.toString());
            int nub = smt.executeUpdate();
            System.out.println("Number of row affected:--->"+nub);
            }
           
		}catch(SQLException se) {
		      se.printStackTrace();
		}finally {
			JdbcConnection.closeConnection( null,smt,connection);
		}
	
		
		
	}
    
	private static void select(Connection connection) throws SQLException {
		ResultSet resultset = null;
		PreparedStatement smt=null;
		Scanner sc = new Scanner(System.in);
		try {
		
		System.out.print("Enter student id ");
		int sid = sc.nextInt();
		String sql = "Select id,title,description from course where id = ?";
		smt = connection.prepareStatement(sql);
        if(smt!=null) {
            
            System.out.println(sql);
            smt.setInt(1,sid);
            resultset  = smt.executeQuery();
            System.out.println("ID\tTITLE\tDESCRIPTION");
            if(resultset!=null) {
            	while(resultset.next()) {
            		System.out.println(resultset.getInt(1)+"\t"+resultset.getString(2)+"\t"+resultset.getString(3));
            	}
            	
             }
           }
           
		}catch(SQLException se) {
		      se.printStackTrace();
		}finally {
			JdbcConnection.closeConnection( resultset,smt,connection);
		}
	
		
		
		
		
	}
	
    private static void delete(Connection connection) throws SQLException {
    	
    	PreparedStatement smt=null;
		Scanner sc = new Scanner(System.in);
		try {
		
		System.out.print("Enter course id to delete");
		int sid = sc.nextInt();
		String sql = "Delete from course where id = ?";
		smt = connection.prepareStatement(sql);
        if(smt!=null) {
            
            System.out.println(sql);
            smt.setInt(1,sid);
            int nub = smt.executeUpdate();
            System.out.println("Number of row affected:--->"+nub);
            }
           
		}catch(SQLException se) {
		      se.printStackTrace();
		}finally {
			JdbcConnection.closeConnection( null,smt,connection);
		}
	
		
		
	}

}
