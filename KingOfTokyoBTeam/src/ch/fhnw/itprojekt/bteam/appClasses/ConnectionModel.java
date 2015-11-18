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

	
	/**
	 * dfjoasdhfo
	 * @author Tobias
	 * @return
	 */
	public String sayHello() {
		Message msgOut = new Message(Message.MessageType.Hello);
		String result = null;
		try {
			msgOut.send(socket);
			Message msgIn = Message.receive(socket);
			result = msgIn.toString();
		} catch (Exception e) {
			result = e.toString();
		}
		return result;
	}

	public String sayNewClient() {
		Message msgOut = new Message(Message.MessageType.NewClient);
		msgOut.setName("Jennifer Jumpingjacks");
		msgOut.setAge(23);
		String result = null;
		try {
			msgOut.send(socket);
			Message msgIn = Message.receive(socket);
			result = msgIn.toString();
		} catch (Exception e) {
			result = e.toString();
		}
		return result;
	}

	public String sayGoodbye() {
		Message msgOut = new Message(Message.MessageType.Goodbye);
		String result = null;
		try {
			msgOut.send(socket);
			Message msgIn = Message.receive(socket);
			result = msgIn.toString();
		} catch (Exception e) {
			result = e.toString();
		}
		try { if (socket != null) socket.close(); } catch (IOException e) {}
		return result;
	}
	
	public boolean sendLogin(User user){
		LoginMessage msgOut = new LoginMessage(MessageType.Login, user.getNickname(), user.getPassword());
		boolean result = false;
		try {
			msgOut.send(socket);
			LoginMessage msgIn = LoginMessage.receive(socket);
			result = msgIn.getCheckLogin();
		} catch (Exception e) {
			serviceLocator.getLogger().warning(e.toString());
		}
		return result;
	}
}
