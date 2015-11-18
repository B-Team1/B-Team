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
public boolean InsertPlayersIntoDB(String NName, String VName, String NickName){
	boolean PlayerAdd = true;
	Statement stmt;
	Connection conn;
	String sqlcontrol;
	try{
			 // Execute a query
    conn = DBConnect();
    stmt = conn.createStatement();
    sqlcontrol = "Select * from user where NickName = '"+NickName+"' && NName = '"+NName+"'";
    ResultSet rs = stmt.executeQuery(sqlcontrol);
    // Checkt ob Resultset mit Daten gefüllt wurde
    if(rs.first()) {
    	//ResultSet beinhaltet Daten
    	PlayerAdd = false;
    	// Checkt ob es tatsächlich die gleichen Daten auf der Datenbank bereits vorhanden sind
        if (NickName.equals(rs.getString("NickName"))  && NName.equals(rs.getString("NName"))){
            PlayerAdd = false;        
        }
    } else {
    	//ResultSet leer ->>
        PlayerAdd = true;
    }
    //verhindert Doppelte Einträge von Nicknammen
    if (PlayerAdd == true){
    	String sql = "INSERT INTO user(NName, VName, NickName) " +
				"VALUES ('"+NName+"', '"+VName+"', '"+NickName+"')";
        stmt.executeUpdate(sql);
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
public boolean UserValidation(String NickName, String Passwort ){
	boolean Loginstatus = false;
	Connection conn;
	Statement stmt;
	try {
		conn = DBConnect();
	// Select query
    stmt = conn.createStatement();

    String sql = "SELECT * FROM user where NickName = '"+NickName +"' && Passwort = '"+ Passwort +"'";
    ResultSet rs = stmt.executeQuery(sql);
    rs.next();
    if (NickName.equals(rs.getString("NickName"))  && Passwort.equals(rs.getString("Passwort"))){
    Loginstatus = true;
    System.out.println(Loginstatus);
    }
    	
	}
	catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }
	System.out.println(Loginstatus);
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
public String getSecurityQuestion (String NickName, String NName, String VName){
	String SecurityQuestion = "";
	Connection conn;
	PreparedStatement stmt;
	try {
		conn = DBConnect();
	// Select query
    //stmt = conn.createStatement();
    //neu
    String sql = "SELECT Sicherheitsfrage FROM user WHERE NickName = ? && NName = ? && VName = ?";
    stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    stmt.setString(1, NickName);
    
    //neu

    //String sql = "SELECT Sicherheitsfrage FROM user where NickName = '"+NickName +"' && NName = '"+ NName +"' && VName = '"+ VName +"'";
    ResultSet rs = stmt.executeQuery(sql);
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
public String getSecurityAnswer (String NickName, String NName, String VName){
	String SecurityAnswer = "";
	Connection conn;
	Statement stmt;
	try {
		conn = DBConnect();
	// Select query
    stmt = conn.createStatement();

    String sql = "SELECT Antwort FROM user where NickName = '"+NickName +"' && NName = '"+ NName +"' && VName = '"+ VName +"'";
    ResultSet rs = stmt.executeQuery(sql);
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
public Stats getStats (String NickName){
	Stats UserStats = new Stats("",0,0,0);
	Connection conn;
	Statement stmt;
	try {
		conn = DBConnect();
		// Select query
		stmt = conn.createStatement();

		String sql = "SELECT AnzahlSpiele, Gewonnen, Verloren from user where NickName = '"+ NickName+"'";
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		UserStats.setSumGames(rs.getInt("AnzahlSpiele"));
		UserStats.setWonGames(rs.getInt("Gewonnen"));
		UserStats.setLosedGames(rs.getInt("Verloren"));
		System.out.println(UserStats.getSumGames());
		System.out.println(UserStats.getWonGames());
		System.out.println(UserStats.getLosedGames());
    	}
	catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }
	
		return UserStats;
}

/**
 * Update der Statistik in der Datenbank
 * @author Luzian
 * @param UserStats
 */
public void setStats (Stats UserStats){
		Connection conn;
	Statement stmt;
	try {
		conn = DBConnect();
		// Update query
		stmt = conn.createStatement();
		String sql = "Update user set AnzahlSpiele = '"+UserStats.getSumGames()+"' , Gewonnen = '"+UserStats.getWonGames()+"', Verloren = '"+UserStats.getLosedGames()+"' where NickName = '"+UserStats.getNickName()+"'";
		stmt.executeUpdate(sql);
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