package ch.fhnw.itprojekt.bteam.appClasses;

import java.io.IOException;
import java.net.Socket;

import ch.fhnw.itprojekt.bteam.template.ServiceLocator;

public class ConnectionModel {
	Socket socket;
	ServiceLocator serviceLocator;
	
	public ConnectionModel(){
		serviceLocator = ServiceLocator.getServiceLocator();        
	}

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
}
