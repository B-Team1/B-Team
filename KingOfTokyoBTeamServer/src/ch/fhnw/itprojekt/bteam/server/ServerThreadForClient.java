package ch.fhnw.itprojekt.bteam.server;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;


public class ServerThreadForClient extends Thread {
    private final Logger logger = Logger.getLogger("");
    private Socket clientSocket;
    DBModel dbconnect = new DBModel();
    ConnectionModel connectionModel;
    MenuModel menuModel = new MenuModel();

    public ServerThreadForClient(Socket clientSocket) {
        this.clientSocket = clientSocket;
        connectionModel = ConnectionModel.getInstance();
    }

    /**
     * Process messages until the client says "Goodbye"
     */
    @Override
    public void run() {  
        logger.info("Request from client " + clientSocket.getInetAddress().toString()
                + " for server " + clientSocket.getLocalAddress().toString());
        try {
            while (true) {
				// Read a message from the client
				Message msgIn = Message.receive(clientSocket);							
				Message msgOut = processMessage(msgIn);
				if(msgIn.getType() != Message.MessageType.Broadcast){
					if(msgIn.getType() != Message.MessageType.Chat){
						msgOut.send(clientSocket);
					}
				}
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
				//Tobias
				msgOut = new Message(Message.MessageType.Login);
				msgOut.setCheckLogin(dbconnect.UserValidation(new User(msgIn.getNickname(), msgIn.getPassword())));
				break;			
			case SecurityQuestion:
				//Luzian
				msgOut = new Message(Message.MessageType.SecurityQuestion);
				User userQuestion = new User(msgIn.getNickname(),msgIn.getNname(), msgIn.getVName());
				String securityQuestion = dbconnect.getSecurityQuestion(userQuestion);
				msgOut.setSecurityQuestion(securityQuestion);
				break;
			case SecurityAnswer:
				//Luzian
				msgOut = new Message(Message.MessageType.SecurityAnswer);
				User userAnswer = new User(msgIn.getNickname(),msgIn.getNname(), msgIn.getVName());
				String securityAnswer = dbconnect.getSecurityAnswer(userAnswer);
				msgOut.setSecurityAnswer(securityAnswer);
				break;
			case Password:
				//Luzian
				msgOut = new Message(Message.MessageType.Password);
				User userPassword = new User(msgIn.getNickname(),msgIn.getNname(), msgIn.getVName());
				String password = dbconnect.getPassword(userPassword);
				msgOut.setPassword(password);
				break;
			case Registration:
				//Tobias
				msgOut = new Message(Message.MessageType.Registration);
				User user = new User(msgIn.getNickname(),
									msgIn.getVName(), 
									msgIn.getNname(), 
									msgIn.getPassword(), 
									msgIn.getSecurityAnswer(), 
									msgIn.getSecurityQuestion());
				msgOut.setWriteCheck(dbconnect.InsertPlayersIntoDB(user));
				break;
			case getStat:
				//Tobias
				msgOut = new Message(Message.MessageType.getStat);
				Stats stats = dbconnect.getStats(new Stats(msgIn.getNickname()));
				msgOut.setNickname(stats.getNickName());
				msgOut.setPlayedGames(stats.getSumGames());
				msgOut.setWonGames(stats.getWonGames());
				break;
			case openNewGame:
				//Tobias
				msgOut = new Message(Message.MessageType.openNewGame);				
				msgOut.setGameId(menuModel.newGame(msgIn.getNumPlayer(), msgIn.getFamePointsWin(), msgIn.getWinFamePoins()));
				break;
			case deleteGame:
				//Tobias
				menuModel.deleteGame(msgIn.getGameId());
				//Wird nur zurück gegeben, damit es kein Fehler gibt
				msgOut = new Message(Message.MessageType.deleteGame);
				msgOut.setWriteCheck(true);
				break;
			case Chat:
				//Luzian
				connectionModel.sendChat(msgIn.getChat());
				break;
		default:
			msgOut = new Message(Message.MessageType.Error);
		}
    	
    	return msgOut;
    }       
}
