package ch.fhnw.itprojekt.bteam.server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ServerThreadForClient extends Thread {
    private final Logger logger = Logger.getLogger("");
    private Socket clientSocket;
    DBModel dbconnect = new DBModel();
    ConnectionModel connectionModel;
    MenuModel menuModel;

    public ServerThreadForClient(Socket clientSocket) {
        this.clientSocket = clientSocket;
        connectionModel = ConnectionModel.getInstance();
        menuModel = MenuModel.getInstance();
    }

    /**
     *Thread der horcht, was der Client sendet.
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
				if(msgIn.getType() != Message.MessageType.Chat //Bei diesen Typen darf nichts zur�ck gesendet werden
						&& msgIn.getType() != Message.MessageType.GameStats
						&& msgIn.getType() != Message.MessageType.StartGame
						&& msgIn.getType() != Message.MessageType.ChangeGameMove
						&& msgIn.getType() != Message.MessageType.setStat
						&& msgIn.getType() != Message.MessageType.AddNewPlayerToGame 
						&& msgIn.getType() != Message.MessageType.ChangeTokyo){
					msgOut.send(clientSocket);
				}
            }
        } catch (Exception e) {
            logger.severe(e.toString());
        } finally {
            try { if (clientSocket != null) clientSocket.close(); } catch (IOException e) {}
        }
    }
    
    /**
     * Diese Methode erledigt die Aufgaben je nach Messagetype
     * @param msgIn
     * @return
     */
    private Message processMessage(Message msgIn) {
		logger.info("Message received from client: "+ msgIn.toString());						
		Message msgOut = null;
		switch (msgIn.getType()) {
			case Login:
				//Tobias
				msgOut = new Message(Message.MessageType.Login);
				msgOut.setCheckLogin(dbconnect.UserValidation(new User(msgIn.getNickname(), msgIn.getPassword())));
				msgOut.setNickname(msgIn.getNickname());
				break;
			case OpenGameRequest:
				//Tobias
				msgOut = new Message(Message.MessageType.OpenGameRequest);
				msgOut.setGameIdList(menuModel.getGameIdList());
				msgOut.setUserList(menuModel.getGameList());
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
				msgOut.setGameId(menuModel.newGame(msgIn.getNumPlayer(), msgIn.getFamePointsWin(), msgIn.getWinFamePoints(), msgIn.getNickname(), clientSocket));
				msgOut.setNickname(msgIn.getNickname());
				msgOut.setFamePointsWin(msgIn.getFamePointsWin());
				msgOut.setWinFamePoins(msgIn.getWinFamePoints());
				break;
			case deleteGame:
				//Tobias
				menuModel.deleteGame(msgIn.getGameId());
				//Wird nur zur�ck gegeben, damit es kein Fehler gibt
				msgOut = new Message(Message.MessageType.deleteGame);
				msgOut.setWriteCheck(true);
				break;
			case Chat:
				//Luzian
				msgOut = new Message(Message.MessageType.Chat);
				String text = msgIn.getNickname()+" : "+msgIn.getChat();
				menuModel.getSpiel(text, msgIn.getGameId());
				break;
			case Players:
				//Luzian
				msgOut = new Message(Message.MessageType.Players);
				msgOut.setPlayers(menuModel.getPlayerFromStartedGame(msgIn.getGameId()));				
				break;
			case AddPlayerToGame:
				//Tobias
				msgOut = msgIn;
				msgOut.setPlayers(menuModel.getPlayerFromOpenGame(msgIn.getGameId()));
				msgOut.setWriteCheck(menuModel.addPlayerToGame(msgIn.getGameId(), msgIn.getNickname(), clientSocket));
				msgOut.setFamePointsWin(menuModel.searchOpenGame(msgIn.getGameId()).isFamePointsWin());
				msgOut.setWinFamePoins(menuModel.searchOpenGame(msgIn.getGameId()).getWinFamePoints());
				break;
			case AddNewPlayerToGame:
				//Tobias
				msgOut=msgIn;
				menuModel.sendNewPlayer(msgIn.getGameId(), msgIn.getNickname(), clientSocket);
				break;
			case GameStats:
				//Tobias
				msgOut = msgIn;
				msgOut.setGameMove(menuModel.getMoveId(msgIn.getGameId()));
				menuModel.sendGameStats(msgOut, msgIn.getGameId());
				break;
			case StartGame:
				//Tobias
				msgOut = new Message(Message.MessageType.StartGame);
				menuModel.startGame(msgIn.getGameId());
				break;
			case setStat:
				// Luzian
				System.out.println("PENIS!");
				Stats userstats = new Stats(msgIn.getNickname(), 0,0,0);
				userstats = dbconnect.getStats(userstats);
				if(msgIn.getWonOrLose().equals("lose")){
					menuModel.setDead(msgIn.getGameId(), msgIn.getNickname());
					userstats.setLosedGames(userstats.getLosedGames() + 1);
				}else{
					userstats.setWonGames(userstats.getWonGames() + 1);
				}
				userstats.setSumGames(userstats.getSumGames() + 1);	
				dbconnect.setStats(userstats);
				break;
			case ChangeGameMove:
				//Tobias
				msgOut= msgIn;
				menuModel.changeGameMove(msgIn.getGameId());
				break;
			case ChangeTokyo:
				//Tobias
				msgOut = msgIn;
				menuModel.sendGameStats(msgOut, msgIn.getGameId());
				break;
		default:
			msgOut = new Message(Message.MessageType.Error);
		}
    	
    	return msgOut;
    }       
}
