package ch.fhnw.itprojekt.bteam.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

public class ServerThreadForClient extends Thread {
    private final Logger logger = Logger.getLogger("");
    private Socket clientSocket;
    DBModel dbconnect = new DBModel();

    public ServerThreadForClient(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * Process messages until the client says "Goodbye"
     */
    @Override
    public void run() {
    	Message.MessageType lastMessageType = null;
        
        logger.info("Request from client " + clientSocket.getInetAddress().toString()
                + " for server " + clientSocket.getLocalAddress().toString());

        try {
            while (lastMessageType != Message.MessageType.Goodbye) {
				// Read a message from the client
				Message msgIn = Message.receive(clientSocket);
				lastMessageType = msgIn.getType();
				Message msgOut = processMessage(msgIn);				
				msgOut.send(clientSocket);
            }
        } catch (Exception e) {
            logger.severe(e.toString());
        } finally {
            try { if (clientSocket != null) clientSocket.close(); } catch (IOException e) {}
        }
    }
    
    private Message processMessage(Message msgIn) {
		logger.info("Message received from client: "+ msgIn.toString());						
		Message msgOut = null;
		switch (msgIn.getType()) {
		case Login:
			msgOut = new Message(Message.MessageType.NewClientAccepted);
			msgOut.setCheckLogin(true);
			break;
			
		case SecurityQuestion:
			//Luzian
			msgOut = new Message(Message.MessageType.SecurityQuestion);
			User user = new User(msgIn.getNickname(),msgIn.getNname(), msgIn.getVName());
			String securityQuestion = dbconnect.getSecurityQuestion(user);
			msgOut.setSecurityQuestion(securityQuestion);
			break;
		
		
		default:
			msgOut = new Message(Message.MessageType.Error);
		}
    	logger.info("Message answered: " + msgOut.toString());
    	return msgOut;
    }       
}
