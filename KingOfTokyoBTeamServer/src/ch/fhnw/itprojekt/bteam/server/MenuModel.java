package ch.fhnw.itprojekt.bteam.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class MenuModel {
	private ArrayList<GameModel> openGameList= new ArrayList<GameModel>();
	private ArrayList<GameModel> startedGameList= new ArrayList<GameModel>();
	private static MenuModel singleton;
	
	public static MenuModel getInstance() {
	     if(singleton == null) {	        
	         singleton = new MenuModel();
	      }	     
	      return singleton;
	}	
	
	/**
	 * Erstellt ein neues Spiel und fügt es der openGameList hinzu
	 * @param numPlayer
	 * @param famePointsWin
	 * @param winFamePoints
	 * @return gameId des neu erstellten Spiels
	 * @author Tobias
	 */
	public int newGame(int numPlayer, boolean famePointsWin, int winFamePoints, String nickName, Socket socket){
		GameModel game = new GameModel(numPlayer, winFamePoints, famePointsWin, new User(nickName, socket));
		openGameList.add(game);
		return game.getGameId();
	}
	
	public void test(Socket socket){
		//newGame(2, true, 11, "Tobias", socket);
	}
	
	/**
	 * Entfernt ein Spiel wieder aus der openGameList
	 * @param gameId
	 * @author Tobias
	 */
	public void deleteGame(int gameId){
		for(int i = 0; i < openGameList.size() ; i++){
			if(openGameList.get(i).getGameId() == gameId){
				openGameList.remove(i);
			}
		}
	}

	public int[] getGameIdList(){
		int[] l = new int[openGameList.size()];
		for(int i = 0; i < openGameList.size(); i++){
			l[i] = openGameList.get(i).getGameId();
		}
		return l;
	}
	
	public String[] getGameList(){
		int[] idList = this.getGameIdList();
		ArrayList<String> gameList = new ArrayList<String>();
		for(int i = 0; i < idList.length; i ++){
			String[] playerList = this.getPlayerFromOpenGame(idList[i]);
			gameList.add(idList[i]+ "");
			for(int c = 0; c < playerList.length; c ++){
				gameList.add(playerList[c]);
			}
			gameList.add("<EndArray>");
		}
		return gameList.toArray(new String[gameList.size()]);		
	}
	

	public String[] getPlayerFromOpenGame(int gameId){
		GameModel game = searchOpenGame(gameId);
		String[] s = new String[game.getPlayers().size()];
		for(int c = 0; c < game.getPlayers().size() ; c++){
			s[c] = game.getPlayers().get(c).getNickname();
		}
		return s;
	}
	
	public String[] getPlayerFromStartedGame(int gameId){
		GameModel game = searchStartedGame(gameId);
		String[] s = new String[game.getPlayers().size()];
		for(int c = 0; c < game.getPlayers().size() ; c++){
			s[c] = game.getPlayers().get(c).getNickname();
		}
		return s;
	}
	
	public void addPlayerToGame(int gameId, String nickName, Socket socket){
		for(int i = 0; i < openGameList.size() ; i++){
			if(openGameList.get(i).getGameId() == gameId){
				openGameList.get(i).addPlayer(new User(nickName, socket));
			}
		}
	}
	
	public void sendNewPlayer(int gameId, String nickName, Socket socket){
		for(int i = 0; i < openGameList.size() ; i++){
			if(openGameList.get(i).getGameId() == gameId){
				openGameList.get(i).sendNewPlayer(new User(nickName, socket));
			}
		}
	}
	
	
	public void getSpiel(String chat, int gameId){
		GameModel game = searchStartedGame(gameId);
		game.sendChat(chat, game.getPlayers());
	}
	
	public void sendGameStats(Message msg, int gameId){
		for(int i = 0; i < openGameList.size() ; i++){
			if(openGameList.get(i).getGameId() == gameId){
				openGameList.get(i).sendGameStats(msg);
			}
		}
	}
	
	public void startGame(int gameId){
		GameModel game = searchOpenGame(gameId);
		startedGameList.add(game);
		openGameList.remove(game);
		game.startGame();
	}
	
	public void changeGameMove(int gameId){
		searchStartedGame(gameId).changeGameMove();
	}
	
	private GameModel searchOpenGame(int gameId){
		for(int i = 0; i < openGameList.size() ; i++){
			if(openGameList.get(i).getGameId() == gameId){
				return openGameList.get(i);
			}
		}
		return null;
	}
	
	private GameModel searchStartedGame(int gameId){
		for(int i = 0; i < startedGameList.size() ; i++){
			if(startedGameList.get(i).getGameId() == gameId){
				return startedGameList.get(i);
			}
		}
		return null;
	}

}
