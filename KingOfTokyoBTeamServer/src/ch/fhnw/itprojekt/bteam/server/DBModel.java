package ch.fhnw.itprojekt.bteam.server;
import java.sql.*;



public class DBModel {
	// Klassenvariablen
	public String Nickname;
	
	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost:3306/bteam";

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "1234";
	   
	   
/**
 * Stellt eine Datenbankverbindung her
 * @author Luzian	   
 * @return
 */
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

/** 
 * Fügt neuen Spieler in die Datenbank hinzu
 * @author Luzian
 * @return
 */
public boolean InsertPlayersIntoDB(User user){
	boolean PlayerAdd = true;
	PreparedStatement stmtabfrage;
	PreparedStatement stmt;
	Connection conn;
	String sqlcontrol;
	try{
    conn = DBConnect();
 // Select query    
    sqlcontrol = "SELECT * FROM user WHERE NickName = ? && NName = ?";
    stmtabfrage = conn.prepareStatement(sqlcontrol, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    stmtabfrage.setString(1, user.getNickname());
    stmtabfrage.setString(2, user.getnName());
    ResultSet rs = stmtabfrage.executeQuery();
    // Checkt ob Resultset mit Daten gefüllt wurde
    if(rs.first()) {
    	//ResultSet beinhaltet Daten
    	// Checkt ob es tatsächlich die gleichen Daten auf der Datenbank bereits vorhanden sind
        if (user.getNickname().equals(rs.getString("NickName"))  && user.getnName().equals(rs.getString("NName"))){
            PlayerAdd = false;        
        }
    } else {
    	//ResultSet leer ->>
        PlayerAdd = true;
    }
    //verhindert Doppelte Einträge von Nicknamen
    if (PlayerAdd == true){
    	String sql = "INSERT INTO user (NName, VName, NickName, Passwort, Sicherheitsfrage, Antwort) VALUES (?,?,?,?,?,?)";
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
    }catch(Exception e){
    	//Handle errors for Class.forName
    	e.printStackTrace();
    }
    System.out.println(PlayerAdd);
	return PlayerAdd;
}

/**
 * Validiert die Benutzereingaben beim Login, ob der richtige Nickname mit dem zugehörigen Passwort in der Datenbank vorhanden ist
 * @author Luzian
 * @param NickName
 * @param Passwort
 * @return
 */
public boolean UserValidation(User user ){
	boolean Loginstatus = false;
	Connection conn;
	PreparedStatement stmt;
	ResultSet rs;
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
        	Loginstatus = true;
    }
    	
	}
	catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }
	return Loginstatus;
	}

/**
 * Gibt die Sicherheitsfrage für den entsprechenden Nicknamen,Namen und Vornamen zurück
 * @author Luzian
 * @param NickName
 * @param NName
 * @param VName
 * @return
 */
public String getSecurityQuestion (User user){
	String SecurityQuestion = "";
	Connection conn;
	PreparedStatement stmt;
	ResultSet rs;
	try {
		conn = DBConnect();
	// Select query    
    String sql = "SELECT Sicherheitsfrage FROM user WHERE NickName = ? && NName = ? && VName = ?";
    stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    stmt.setString(1, user.getNickname());
    stmt.setString(2, user.getnName());
    stmt.setString(3, user.getvName());
    rs = stmt.executeQuery();
    rs.next();
    SecurityQuestion = rs.getString("Sicherheitsfrage");
   	}
	catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }
	return SecurityQuestion;
}

/**
 * Gibt die Antwort auf die Sicherheitsfrage für den entsprechenden Nicknamen, Namen und Vornamen zurück
 * @author Luzian
 * @param NickName
 * @param NName
 * @param VName
 * @return
 */
public String getSecurityAnswer (User user){
	String SecurityAnswer = "";
	String sql;
	Connection conn;
	PreparedStatement stmt;
	ResultSet rs;
		try {
		conn = DBConnect();
	// Select query
    sql = "SELECT Antwort FROM user WHERE NickName = ? && NName = ? && VName = ?";
    stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    stmt.setString(1, user.getNickname());
    stmt.setString(2, user.getnName());
    stmt.setString(3, user.getvName());
    rs = stmt.executeQuery();
    rs.next();
    SecurityAnswer = rs.getString("Antwort");
    System.out.println(SecurityAnswer);
    	}
	catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }
	return SecurityAnswer;
}

/**
 * Erstellt ein StatsObjekt, füllt StatsObjekt mit den Daten der DB aufgrund des NickNames und gibt StatsObjekt zurück
 * @author Luzian
 * @param NickName
 * @return
 */
public Stats getStats (Stats userstats){
	String sql;
	Connection conn;
	PreparedStatement stmt;
	ResultSet rs;
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
    	}
	catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }
	
		return userstats;
}

/**
 * Update der Statistik in der Datenbank
 * @author Luzian
 * @param UserStats
 */
public void setStats (Stats userstats){
	Connection conn;
	PreparedStatement stmt;
	String sql;
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
				}
	catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   } 
}

}