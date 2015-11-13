package ch.fhnw.itprojekt.bteam.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Server {
 ResultSet rs;
 PreparedStatement ps;
 
	public static void main(String [] args){
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con = DriverManager.getConnection("jdbc:odbc:BTeam","","");
			System.out.println("Connected");
			//Statement stm = con.createStatement();
			//String sqlQuerry = "Select * from User";
			
			//ResultSet rs = stm.executeQuery(sqlQuerry);
			
			//while(rs.next()){
			//	System.out.println(rs.getString("NName"));
			//}
			
			
			
			} catch (Exception e){
				System.out.println (e.getMessage());
		}
	}
}
