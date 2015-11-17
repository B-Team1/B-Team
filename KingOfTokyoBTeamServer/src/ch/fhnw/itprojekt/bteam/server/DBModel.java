package ch.fhnw.itprojekt.bteam.server;
import java.sql.*;

public class DBModel {
	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost:3306/bteam";

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "1234";
	   
	   
	   
public Connection DBConnect() { 
		Connection conn = null;
	   try{
	      //STEP 2: Register JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");

	      //STEP 3: Open a connection
	      System.out.println("Connecting to database...");
	      conn = DriverManager.getConnection(DB_URL,USER,PASS);
	      //InsertPlayersIntoDB();
	   
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }
	return conn;
	   
}

public  boolean InsertPlayersIntoDB(){
	boolean Completed = false;
	String NName = "Lüthi";
	String VName = "Luzian";
	String NickName = "sico";
	String Sicherheitsfrage = "Plan";
	String Antwort = "B";
	Statement stmt;
	Connection conn;
	try{
			 // Execute a query
    conn = DBConnect();
    stmt = conn.createStatement();
    
    String sql = "INSERT INTO user(NName, VName) " +
    				"VALUES ('Lüthi', 'Luzian')";
    stmt.executeUpdate(sql);
    
    System.out.println("Inserted records into the table...");
    
    Completed = true;
	
	 }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }
	      


	return Completed; 
}
}
