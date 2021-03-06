package ch.fhnw.itprojekt.bteam.server;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;



public class DBModel {
	// Klassenvariablen
	public String Nickname;
	
	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost:3306/bteam";

	   
/**
 * liest aus der ServerConfig.txt den angegebenen UserNamen aus
 * @author Luzian	   
 * @return
 * @throws IOException
 */
public String getSeverConfigUser() throws IOException{
	FileReader fr = new FileReader("ServerConfig.txt");
	BufferedReader br = new BufferedReader(fr);
	String username = br.readLine();
	String[] splitUsername = username.split("=",2);
	br.close();
	return splitUsername[1];
}

/**
 * liest aus der ServerConfig.txt das angegebene Passwort aus
 * @author Luzian
 * @return
 * @throws IOException
 */
public String getSeverConfigPass() throws IOException{
	FileReader fr = new FileReader("ServerConfig.txt");
	BufferedReader br = new BufferedReader(fr);
	String username = br.readLine();
	String password = br.readLine();
	String[] splitPassword = password.split("=",2);
	br.close();
	return splitPassword[1];
}
	   
/**
 * Stellt eine Datenbankverbindung her
 * @author Luzian	   
 * @return
 */
public Connection DBConnect() throws IOException { 
	   Connection conn = null;
	   String pass = getSeverConfigPass();
	   String user = getSeverConfigUser();
	   try{
	      //Registriert JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");

	      //�ffnet eine connection
	      System.out.println("Connecting to database...");
	      conn = DriverManager.getConnection(DB_URL,user,pass);
	         
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }
	return conn;
	   
}

/** 
 * F�gt neuen Spieler in die Datenbank hinzu
 * @author Luzian
 * @return
 */
public boolean InsertPlayersIntoDB(User user){
	boolean playerAdd = true;
	PreparedStatement stmtabfrage = null;
	PreparedStatement stmt = null;
	Connection conn = null;
	ResultSet rs = null;
	String sqlcontrol;
	String sql;
	try{
		conn = DBConnect();
		// Select query    
		sqlcontrol = "SELECT * FROM user WHERE NickName = ? && NName = ?";
		stmtabfrage = conn.prepareStatement(sqlcontrol, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		stmtabfrage.setString(1, user.getNickname());
		stmtabfrage.setString(2, user.getnName());
		rs = stmtabfrage.executeQuery();
		// Checkt ob Resultset mit Daten gef�llt wurde
        if(rs.first()) { 	
        	//ResultSet beinhaltet Daten
        	// Checkt ob es tats�chlich die gleichen Daten auf der Datenbank bereits vorhanden sind
        	if (user.getNickname().equals(rs.getString("NickName"))  && user.getnName().equals(rs.getString("NName"))){
        		playerAdd = false;        
        	}
        }else{
        	//ResultSet leer ->>
        	playerAdd = true;
        }
    
        //verhindert Doppelte Eintr�ge von Nicknamen
        if (playerAdd == true){
        	sql = "INSERT INTO user (NName, VName, NickName, Passwort, Sicherheitsfrage, Antwort) VALUES (?,?,?,?,?,?)";
        	stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        	stmt.setString(1, user.getnName());
        	stmt.setString(2, user.getvName());
        	stmt.setString(3, user.getNickname());
        	stmt.setString(4, user.getPassword());
        	stmt.setString(5, user.getSecurityQuestion());
        	stmt.setString(6, user.getSecurityAnswer());
        	stmt.executeUpdate();
        }
    }catch(SQLException se){
    	//Handle errors for JDBC
    	se.printStackTrace();
    	playerAdd = false;
    }catch(Exception e){
    	//Handle errors for Class.forName
    	e.printStackTrace();
    	playerAdd = false;
    }finally {
    	// Close Conn, rs, stmt, stmtabfrage
        try { if (rs != null) rs.close(); } catch (Exception e) {};
        try { if (stmt != null) stmt.close(); } catch (Exception e) {};
        try { if (stmtabfrage != null) stmtabfrage.close(); } catch (Exception e) {};
        try { if (conn != null) conn.close(); } catch (Exception e) {};
    }
	return playerAdd;
}

/**
 * Validiert die Benutzereingaben beim Login, ob der richtige Nickname mit dem zugeh�rigen Passwort in der Datenbank vorhanden ist
 * @author Luzian
 * @param NickName
 * @param Passwort
 * @return
 */
public boolean UserValidation(User user ){
	boolean loginStatus = false;
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	try {
		conn = DBConnect();
		// Select query
		String sql = "SELECT * FROM user where NickName = ? && Passwort = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, user.getNickname());
        stmt.setString(2, user.getPassword());
        rs = stmt.executeQuery();
        rs.next();
        if (user.getNickname().equals(rs.getString("NickName"))  && user.getPassword().equals(rs.getString("Passwort"))){
        	loginStatus = true;
        }
    }catch(SQLException se){
	    //Handle errors for JDBC
	    se.printStackTrace();
    }catch(Exception e){
	    //Handle errors for Class.forName
	    e.printStackTrace();
    }finally {
	    // Close Conn, rs, stmt
	    try { if (rs != null) rs.close(); } catch (Exception e) {};
	    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
	    try { if (conn != null) conn.close(); } catch (Exception e) {};
	}
	return loginStatus;
	   
	}

/**
 * Gibt die Sicherheitsfrage f�r den entsprechenden Nicknamen,Namen und Vornamen zur�ck
 * @author Luzian
 * @param NickName
 * @param NName
 * @param VName
 * @return
 */
public String getSecurityQuestion (User user){
	String securityQuestion = "";
	String sql;
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	try {
		conn = DBConnect();
		// Select query    
		sql = "SELECT Sicherheitsfrage FROM user WHERE NickName = ? && NName = ? && VName = ?";
		stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, user.getNickname());
		stmt.setString(2, user.getnName());
		stmt.setString(3, user.getvName());
		rs = stmt.executeQuery();
		if (rs != null){
			rs.next();
			securityQuestion = rs.getString("Sicherheitsfrage");
		}else{
			securityQuestion = null;
		}
   	}catch(SQLException se){
	    //Handle errors for JDBC
	    se.printStackTrace();
   	}catch(Exception e){
	    //Handle errors for Class.forName
	    e.printStackTrace();
	}finally {
	    // Close Conn, rs, stmt
	    try { if (rs != null) rs.close(); } catch (Exception e) {};
	    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
	    try { if (conn != null) conn.close(); } catch (Exception e) {};
	   }
	return securityQuestion;
}

/**
 * Gibt das Password f�r den entsprechenden Nicknamen, Namen und Vornamen zur�ck
 * @author Luzian
 * @param user
 * @return
 */
public String getPassword (User user){
	String password = "";
	String sql;
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	try {
		conn = DBConnect();
		// Select query    
		sql = "SELECT Passwort FROM user WHERE NickName = ? && NName = ? && VName = ?";
		stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, user.getNickname());
		stmt.setString(2, user.getnName());
		stmt.setString(3, user.getvName());
		rs = stmt.executeQuery();
		if (rs != null){
			rs.next();
			password = rs.getString("Passwort");
		}else{
			password = null;
		}
   }catch(SQLException se){
	    //Handle errors for JDBC
	    se.printStackTrace();
   }catch(Exception e){
	   	//Handle errors for Class.forName
	    e.printStackTrace();
   }finally {
	   	// Close Conn, rs, stmt
	    try { if (rs != null) rs.close(); } catch (Exception e) {};
	    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
	    try { if (conn != null) conn.close(); } catch (Exception e) {};
	}
	return password;
}

/**
 * Gibt die Antwort auf die Sicherheitsfrage f�r den entsprechenden Nicknamen, Namen und Vornamen zur�ck
 * @author Luzian
 * @param NickName
 * @param NName
 * @param VName
 * @return
 */
public String getSecurityAnswer (User user){
	String securityAnswer = "";
	String sql;
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	try {
		conn = DBConnect();
		// Select query
		sql = "SELECT Antwort FROM user WHERE NickName = ? && NName = ? && VName = ?";
		stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		stmt.setString(1, user.getNickname());
		stmt.setString(2, user.getnName());
		stmt.setString(3, user.getvName());
		rs = stmt.executeQuery();
		if(rs != null){
			rs.next();
			securityAnswer = rs.getString("Antwort");
		}else{
			securityAnswer = null;
		}    
    }catch(SQLException se){
	    //Handle errors for JDBC
	    se.printStackTrace();
    }catch(Exception e){
    	//Handle errors for Class.forName
	    e.printStackTrace();
	}finally {
    	// Close Conn, rs, stmt
		try { if (rs != null) rs.close(); } catch (Exception e) {};
	    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
	    try { if (conn != null) conn.close(); } catch (Exception e) {};
	   }
	return securityAnswer;
}

/**
 * Erstellt ein StatsObjekt, f�llt StatsObjekt mit den Daten der DB aufgrund des NickNames und gibt StatsObjekt zur�ck
 * @author Luzian
 * @param NickName
 * @return
 */
public Stats getStats (Stats userstats){
	String sql;
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	try {
		conn = DBConnect();
		// Select query
		sql = "SELECT AnzahlSpiele, Gewonnen, Verloren from user where NickName = ?";
	    stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    stmt.setString(1, userstats.getNickName());
	    rs = stmt.executeQuery();
		rs.next();
		userstats.setSumGames(rs.getInt("AnzahlSpiele"));
		userstats.setWonGames(rs.getInt("Gewonnen"));
		userstats.setLosedGames(rs.getInt("Verloren"));
    }catch(SQLException se){
    	//Handle errors for JDBC
	    se.printStackTrace();
    }catch(Exception e){
    	//Handle errors for Class.forName
	    e.printStackTrace();
    }finally {
    	// Close Conn, rs, stmt
    	try { if (rs != null) rs.close(); } catch (Exception e) {};
	    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
	    try { if (conn != null) conn.close(); } catch (Exception e) {};
	}
	return userstats;
}

/**
 * Update der Statistik in der Datenbank
 * @author Luzian
 * @param UserStats
 */
public void setStats (Stats userstats){
	Connection conn = null;
	PreparedStatement stmt = null;
	String sql = null;
	try {
		conn = DBConnect();
		// Update query
		sql = "Update user set AnzahlSpiele = ?, Gewonnen = ?, Verloren = ? WHERE NickName = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, userstats.getSumGames());
        stmt.setInt(2, userstats.getWonGames());
        stmt.setInt(3, userstats.getLosedGames());     
        stmt.setString(4, userstats.getNickName());
        stmt.executeUpdate();
	}catch(SQLException se){
	    //Handle errors for JDBC
	    se.printStackTrace();
	}catch(Exception e){
	    //Handle errors for Class.forName
		e.printStackTrace();
	}finally{
	    // Close Conn, stmt
	    try { if (stmt != null) stmt.close(); } catch (Exception e) {};
	    try { if (conn != null) conn.close(); } catch (Exception e) {};
	}
}
}
