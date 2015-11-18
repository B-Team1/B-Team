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
		case Hello:
			msgOut = new Message(Message.MessageType.Hello);
			break;
		case NewClient:
			msgOut = new Message(Message.MessageType.NewClientAccepted);
			msgOut.setName(msgIn.getName());
			break;
		case Goodbye:
			msgOut = new Message(Message.MessageType.Goodbye);
			break;
		
		default:
			msgOut = new Message(Message.MessageType.Error);
		}
    	logger.info("Message answered: " + msgOut.toString());
    	return msgOut;
    }
}
