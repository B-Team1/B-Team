	package ch.fhnw.itprojekt.bteam.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Logger;

import javafx.concurrent.Task;

public class ConnectionModel {
    private Integer port;
    private final Logger logger = Logger.getLogger("");
    private static HashSet<Socket> socketList = new HashSet<Socket>();
    private static ConnectionModel singleton;
    
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
    
    final Task<Void> serverTask = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            ServerSocket listener = null;
            try {
                listener = new ServerSocket(port, 10, null);
                logger.info("Listening on port " + port);

                while (true) {
                    // The "accept" method waits for a request, then creates a socket
                    // connected to the requesting client
                    Socket clientSocket = listener.accept();
                    synchronized (clientSocket) {
                    	socketList.add(clientSocket);
                    }
                    
                    ServerThreadForClient client = new ServerThreadForClient(clientSocket);
                    client.start();
                }
            } catch (Exception e) {
                System.err.println(e);
            } finally {
                if (listener != null) listener.close();
            }
            return null;
        }
    };
    
    /**
     * Called by the controller, to start the serverSocket task
     */
    public void startServerSocket(Integer port) {
        this.port = port;
        new Thread(serverTask).start();
    }
    
    /**
     * Schickt die erhaltene Chat Nachricht vom Client an alle Clients
     * @author Luzian
     * @param text
     */
    public void sendChat(String text, ArrayList<User> players){
    	for (User player: players) {
    		Message msgOut = new Message(Message.MessageType.Chat);
    		msgOut.setChat(text);
    		try {
    			msgOut.send(player.getSocket());    			
    		} catch (Exception e) {
    			System.err.println(e);
    			
    		}
		}
    }
    
    public void sendNewPlayerInGame(ArrayList<User> players, User user){
    	for (User player : players) {
    		Message msgOut = new Message(Message.MessageType.AddNewPlayerToGame);
    		msgOut.setNickname(user.getNickname());
    		try {
    			msgOut.send(player.getSocket());    			
    		} catch (Exception e) {
    			System.err.println(e);
    			
    		}
		}
    }
    
    public void sendGameStats(Message msgOut, ArrayList<User> players){
    	for (User player: players) {
    		try {
    			msgOut.send(player.getSocket());    			
    		} catch (Exception e) {
    			System.err.println(e);
    			
    		}
		}
    }
    
    public void startGame(ArrayList<User> players){
    	for (User player: players) {
    		Message msgOut = new Message(Message.MessageType.StartGame);
    		try {
    			msgOut.send(player.getSocket());    			
    		} catch (Exception e) {
    			System.err.println(e);
    			
    		}
		}
    }
    
    public void changeGameMove(ArrayList<User> players, int gameMove){
    	for (User player: players) {
    		Message msgOut = new Message(Message.MessageType.ChangeGameMove);
    		msgOut.setGameMove(gameMove);
    		try {
    			msgOut.send(player.getSocket());    			
    		} catch (Exception e) {
    			System.err.println(e);
    			
    		}
		}
    }
    
}
