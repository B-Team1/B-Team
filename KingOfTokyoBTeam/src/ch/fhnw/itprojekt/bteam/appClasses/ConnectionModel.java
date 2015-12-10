package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.Socket;
import ch.fhnw.itprojekt.bteam.template.ServiceLocator;


public class ConnectionModel {
	Socket socket;
	ServiceLocator serviceLocator;
	private static ConnectionModel singleton;
	Message msgIn = null;
	
	
	/**
	 * Konstruktor darf nicht von aussen aufgerufen werden!
	 * @author Tobias
	 */
	protected ConnectionModel(){
		serviceLocator = ServiceLocator.getServiceLocator();
		msgIn = new Message(Message.MessageType.Error);
		
	}
	
	/**
	 * Diese Methode prüft, ob bereits eine Instanz besteht und gibt dann eine zurück.
	 * Wenn keine vorhanden ist wird eine neue erzeugt. Dies ist wichtig, dass nicht mehrere 
	 * Verbindungen zum Server erstellt werden.
	 * @return Connection Model mit bestehender Verbindung
	 * @author Tobias
	 */
	public static ConnectionModel getInstance() {
	     if(singleton == null) {	        
	         singleton = new ConnectionModel();
	      }	     
	      return singleton;
	}	

	//Getter und Setter für den Thread
	public Message getMsgIn() {
		return msgIn;
	}

	public void setMsgIn(Message mi) {
		this.msgIn = mi;
	}
	
	
	/**
	 * Diese Methode stellt die Verbindung zum Server her und startet den Thread um Messages zu erhalten.
	 * @param ipAddress
	 * @param port
	 * @return Boolean ob es Funktioniert hat
	 * @author Tobias
	 */
	public boolean connect(String ipAddress, Integer port) {
		boolean success = false;
		try {
			socket = new Socket(ipAddress, port);
			success = true;	
			 //new Thread(serverTask).start();
			new ThreadHandler(socket).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		return success;
	}
	   	   
    
	/**
	 * Diese Methode sendet die Login Informatinen an den Server und erhält die Antwort von ihm.
	 * @author Tobias
	 */
	public void sendLogin(User user){
		Message msgOut = new Message(Message.MessageType.Login);
		msgOut.setNickname(user.getNickname());
		msgOut.setPassword(user.getPassword());
		try {
			msgOut.send(socket);
		} catch (Exception e) {
			serviceLocator.getLogger().warning(e.toString());
		}
	}

	/**
	 * holt die Sicherheitsfrage über den Server auf der Datebank wieder zurück an den Client
	 * @author Luzian
	 * @param user
	 * @return
	 */
	public void getSecurityQuestion(User user){
		Message msgOut = new Message(Message.MessageType.SecurityQuestion);
		msgOut.setNickname(user.getNickname());
		msgOut.setNname(user.getnName());
		msgOut.setVname(user.getvName());
		try {
			msgOut.send(socket);
		} catch (Exception e) {
			serviceLocator.getLogger().warning(e.toString());
		}
		
	}
	
	/**
	 * Sendet einen neuen Benutzer zum Schreiben in die DB an den Server
	 * @author Tobias
	 * @param user
	 * @return true wenn der User erfolgreich in die DB geschrieben wurde.
	 */
	public void sendRegistration(User user){
		Message msgOut = new Message(Message.MessageType.Registration);
		msgOut.setNickname(user.getNickname());
		msgOut.setPassword(user.getPassword());
		msgOut.setVname(user.getvName());
		msgOut.setNname(user.getnName());
		msgOut.setSecurityAnswer(user.getSecurityAnswer());
		msgOut.setSecurityQuestion(user.getSecurityQuestion());
		try {
			msgOut.send(socket);
		} catch (Exception e) {
			serviceLocator.getLogger().warning(e.toString());
		}
	}


	
	/**
	 * holt die Sicherheitsanwort über den Server auf der Datebank wieder zurück an den Client
	 * @author Luzian
	 * @param user
	 * @return
	 */
	public void getSecurityAnswer(User user){
		Message msgOut = new Message(Message.MessageType.SecurityAnswer);
		msgOut.setNickname(user.getNickname());
		msgOut.setNname(user.getnName());
		msgOut.setVname(user.getvName());
		try {
			msgOut.send(socket);
		} catch (Exception e) {
			serviceLocator.getLogger().warning(e.toString());
		}
		
	}

	/**
	 * @author Luzian
	 * @param user
	 */
	public void getPassword(User user){
		Message msgOut = new Message(Message.MessageType.Password);
		msgOut.setNickname(user.getNickname());
		msgOut.setNname(user.getnName());
		msgOut.setVname(user.getvName());
		try {
			msgOut.send(socket);
			} catch (Exception e) {
				serviceLocator.getLogger().warning(e.toString());
		}
	
	}
	
	/**
	 * Holt vom Server anhand des Nicknames die Statistik
	 * @author Tobias
	 * @param nickname
	 * @return Statistik Objekt mit Nickname, gewonnene Spiele und gespielte Spiele
	 */
	public Stats getStat(String nickname){
		Message msgOut = new Message(Message.MessageType.getStat);
		msgOut.setNickname(nickname);
		Stats respons = null;
		try {
			msgOut.send(socket);
			Thread.sleep(1000);
			respons = new Stats(nickname, msgIn.getPlayedGames(), msgIn.getWonGames(), 0);
			} catch (Exception e) {
				serviceLocator.getLogger().warning(e.toString());
		}
		return respons;
	}
	
	/**
	 * Sendet die Informationen für ein neues Spiel dem Server
	 * @author Tobias
	 * @param numPlayer
	 * @param famePointsWin
	 * @param winFamePoins
	 */
	public void sendNewGame(int numPlayer, boolean famePointsWin, int winFamePoins, String nickName){
		Message msgOut = new Message(Message.MessageType.openNewGame);
		msgOut.setNumPlayer(numPlayer);
		msgOut.setFamePointsWin(famePointsWin);
		msgOut.setWinFamePoins(winFamePoins);
		msgOut.setNickname(nickName);
		try {
			msgOut.send(socket);
			} catch (Exception e) {
				serviceLocator.getLogger().warning(e.toString());
		}		
	}
	
	/**
	 * Schickt dem Server den Auftrag, das entsprechende Spiel zu löschen.
	 * @param gameId
	 */
	public void deleteGame(int gameId){
		Message msgOut = new Message(Message.MessageType.deleteGame);
		msgOut.setGameId(gameId);
		try {
			msgOut.send(socket);			
			} catch (Exception e) {
				serviceLocator.getLogger().warning(e.toString());
		}
	}
	
	/**
	 * Schickt dem Server die Anfrage, für die Liste aller offenen Spiele
	 * @author Tobias
	 */
	public void sendOpenGameRequest(){
		Message msgOut = new Message(Message.MessageType.OpenGameRequest);		
		try {
			msgOut.send(socket);			
			} catch (Exception e) {
				serviceLocator.getLogger().warning(e.toString());
			}
	}
	
	/**			
	 * @author Luzian
	 */
	public void sendChat(String chat, String nickName){
		Message msgOut = new Message(Message.MessageType.Chat);
		msgOut.setChat(chat);
		msgOut.setNickname(nickName);
		try {
			msgOut.send(socket);
		} catch (Exception e) {
			serviceLocator.getLogger().warning(e.toString());
		}
	}

	public void getPlayers(int gameId){
		Message msgOut = new Message(Message.MessageType.Players);
		msgOut.setGameId(gameId);
		try {
			msgOut.send(socket);
		} catch (Exception e) {
			serviceLocator.getLogger().warning(e.toString());
		}
	}
	
}


