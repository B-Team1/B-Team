package ch.fhnw.itprojekt.bteam.appClasses;

import java.io.IOException;
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
	 */
	public static ConnectionModel getInstance() {
	     if(singleton == null) {	        
	         singleton = new ConnectionModel();
	      }	     
	      return singleton;
	   }
	
	
	/**
	 * 
	 * @param ipAddress
	 * @param port
	 * @return Boolean ob es Funktioniert hat
	 */
	public boolean connect(String ipAddress, Integer port) {
		boolean success = false;
		try {
			socket = new Socket(ipAddress, port);
			success = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		return success;
	}


	/**
	 * Diese Methode sendet die Login Informatinen an den Server und erhält die Antwort von ihm.
	 * @author Tobias
	 */
	public boolean sendLogin(User user){
		Message msgOut = new Message(Message.MessageType.Login);
		msgOut.setNickname(user.getNickname());
		msgOut.setPassword(user.getPassword());
		boolean result = false;
		try {
			msgOut.send(socket);
			Message msgIn = Message.receive(socket);
			result = msgIn.getCheckLogin();
		} catch (Exception e) {
			serviceLocator.getLogger().warning(e.toString());
		}
		return result;
	}

	/**
	 * holt die Sicherheitsfrage über den Server auf der Datebank wieder zurück an den Client
	 * @author Luzian
	 * @param user
	 * @return
	 */
	public String getSecurityQuestion(User user){
		Message msgOut = new Message(Message.MessageType.SecurityQuestion);
		msgOut.setNickname(user.getNickname());
		msgOut.setNname(user.getnName());
		msgOut.setVname(user.getvName());
		String securityQuestion = null;
		try {
			msgOut.send(socket);
			Message msgIn = Message.receive(socket);
			securityQuestion = msgIn.getSecurityQuestion();
		} catch (Exception e) {
			serviceLocator.getLogger().warning(e.toString());
		}
		return securityQuestion;
	}
	
	public boolean sendRegistration(User user){
		Message msgOut = new Message(Message.MessageType.Registration);
		msgOut.setNickname(user.getNickname());
		msgOut.setPassword(user.getPassword());
		msgOut.setVname(user.getvName());
		msgOut.setNname(user.getnName());
		msgOut.setSecurityAnswer(user.getSecurityAnswer());
		msgOut.setSecurityQuestion(user.getSecurityQuestion());
		boolean result = false;
		try {
			msgOut.send(socket);
			Message msgIn = Message.receive(socket);
			result = msgIn.getWriteCheck();
		} catch (Exception e) {
			serviceLocator.getLogger().warning(e.toString());
		}
		return result;
	}


	
	/**
	 * holt die Sicherheitsanwort über den Server auf der Datebank wieder zurück an den Client
	 * @author Luzian
	 * @param user
	 * @return
	 */
	public String getSecurityAnswer(User user){
		Message msgOut = new Message(Message.MessageType.SecurityAnswer);
		msgOut.setNickname(user.getNickname());
		msgOut.setNname(user.getnName());
		msgOut.setVname(user.getvName());
		String securityAnswer = null;
		try {
			msgOut.send(socket);
			Message msgIn = Message.receive(socket);
			securityAnswer = msgIn.getSecurityAnswer();
			} catch (Exception e) {
				serviceLocator.getLogger().warning(e.toString());
	}
	return securityAnswer;
}

	
	public String getPassword(User user){
		Message msgOut = new Message(Message.MessageType.Password);
		msgOut.setNickname(user.getNickname());
		msgOut.setNname(user.getnName());
		msgOut.setVname(user.getvName());
		String password = null;
		try {
			msgOut.send(socket);
			Message msgIn = Message.receive(socket);
			password = msgIn.getPassword();
			} catch (Exception e) {
				serviceLocator.getLogger().warning(e.toString());
	}
	return password;
	}
}


