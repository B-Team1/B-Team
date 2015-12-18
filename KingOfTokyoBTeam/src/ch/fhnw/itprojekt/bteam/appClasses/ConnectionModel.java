package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.Socket;
import ch.fhnw.itprojekt.bteam.template.ServiceLocator;


public class ConnectionModel {
	Socket socket;
	ServiceLocator serviceLocator;
	private static ConnectionModel singleton;
	
	/**
	 * Konstruktor darf nicht von aussen aufgerufen werden!
	 * @author Tobias
	 */
	protected ConnectionModel(){
		serviceLocator = ServiceLocator.getServiceLocator();
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
	 * schickt SecurityQuestion Message an Server
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
	 * schickt SecurityAnswer Message an Server
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
	 * schickt Passwort Message an Server
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
	public void getStat(String nickname){
		Message msgOut = new Message(Message.MessageType.getStat);
		msgOut.setNickname(nickname);
		try {
			msgOut.send(socket);
			} catch (Exception e) {
				serviceLocator.getLogger().warning(e.toString());
		}
	}
	
	/**
	 * schickt dem Server die beendete Spielstatistik des Spielers
	 * @author Luzian
	 */
	public void setStat(String nickname, String WonOrLose, int gameId){
		Message msgOut = new Message(Message.MessageType.setStat);
		msgOut.setNickname(nickname);
		msgOut.setWonOrLose(WonOrLose);
		msgOut.setGameId(gameId);
		try {
			Thread.sleep(1000);
			msgOut.send(socket);
			} catch (Exception e) {
				serviceLocator.getLogger().warning(e.toString());
		}
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
	 * schickt Chat Message an Server	
	 * @author Luzian
	 */
	public void sendChat(String chat, String nickName, int gameId){
		Message msgOut = new Message(Message.MessageType.Chat);
		msgOut.setChat(chat);
		msgOut.setNickname(nickName);
		msgOut.setGameId(gameId);
		try {
			msgOut.send(socket);
		} catch (Exception e) {
			serviceLocator.getLogger().warning(e.toString());
		}
	}

	/**
	 * schickt Players Message an Server um Spielerliste zu erhalten
	 * @author Luzian
	 * @param gameId
	 */
	public void getPlayers(int gameId){
		Message msgOut = new Message(Message.MessageType.Players);
		msgOut.setGameId(gameId);
		try {
			msgOut.send(socket);
		} catch (Exception e) {
			serviceLocator.getLogger().warning(e.toString());
		}
	}
	
	/**
	 * Sendet dem Server einen neuen Spieler um eine Liste der bestehenden Spieler zu erhalten
	 * @author Tobias
	 * @param gameId
	 * @param nickname
	 */
	public void sendAccession(int gameId, String nickname){
		Message msgOut = new Message(Message.MessageType.AddPlayerToGame);
		msgOut.setGameId(gameId);
		msgOut.setNickname(nickname);
		try {
			msgOut.send(socket);
		} catch (Exception e) {
			serviceLocator.getLogger().warning(e.toString());
		}
	}
	
	/**
	 * Sendet dem Server den neuen Spieler um es den anderen Spieler mitzuteilen
	 * @author Tobias
	 * @param gameId
	 * @param nickname
	 */
	public void sendNewPlayerToGame(int gameId, String nickname){
		Message msgOut = new Message(Message.MessageType.AddNewPlayerToGame);
		msgOut.setGameId(gameId);
		msgOut.setNickname(nickname);
		try {
			msgOut.send(socket);
		} catch (Exception e) {
			serviceLocator.getLogger().warning(e.toString());
		}
	}

	/**
	 *
	 * @param gamerName
	 * @param pointsMe
	 * @param lifepoints
	 * @param tokyo
	 * @param gameId
	 */
	public void sendGameStats(String gamerName, int[] pointsMe, int[] lifepoints, boolean[] tokyo, int gameId) {
		Message msgOut = new Message(Message.MessageType.GameStats);
		msgOut.setGamerName(gamerName);
		msgOut.setMyPoints(pointsMe);
		msgOut.setLifepoints(lifepoints);
		msgOut.setTokyo(tokyo);
		msgOut.setGameId(gameId);
		try {
			msgOut.send(socket);
		} catch (Exception e) {
			serviceLocator.getLogger().warning(e.toString());
		}
	}
	
	/**
	 * Started das Spiel für alle Spieler
	 * @author Tobias
	 * @param gameId
	 */
	public void startGame(int gameId){
		Message msgOut = new Message(Message.MessageType.StartGame);
		msgOut.setGameId(gameId);
		try {
			msgOut.send(socket);
		} catch (Exception e) {
			serviceLocator.getLogger().warning(e.toString());
		}
	}

	/**
	 * 
	 * @param tokyo
	 * @param gameId
	 */
	public void sendTokyoChange(boolean[] tokyo, int gameId) {
		Message msgOut = new Message(Message.MessageType.ChangeTokyo);
		msgOut.setTokyo(tokyo);
		msgOut.setGameId(gameId);
		try {
			msgOut.send(socket);
		} catch (Exception e) {
			serviceLocator.getLogger().warning(e.toString());
		}
	}
	
	/**
	 * Sendet, dass der Spieler fertig ist mit seinem Zug
	 * @author Tobias
	 * @param gameId
	 */
	public void sendGameMove(int gameId){
		Message msgOut = new Message(Message.MessageType.ChangeGameMove);
		msgOut.setGameId(gameId);
		try {
			msgOut.send(socket);
		} catch (Exception e) {
			serviceLocator.getLogger().warning(e.toString());
		}
	}
	
}


