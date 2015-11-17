package ch.fhnw.itprojekt.bteam.appClasses;

import java.io.IOException;
import java.net.Socket;

public class ConnectionModel {
	Socket socket;

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
}
