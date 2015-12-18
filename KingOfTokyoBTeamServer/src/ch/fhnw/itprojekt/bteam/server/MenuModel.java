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

	/**
	 * Gibt Liste mit Game Ids zurück
	 * @author Tobias
	 * @return
	 */
	public int[] getGameIdList(){
		int[] l = new int[openGameList.size()];
		for(int i = 0; i < openGameList.size(); i++){
			l[i] = openGameList.get(i).getGameId();
		}
		return l;
	}
	
	/**
	 * Gibt Liste mit Spielen zurück. Ein Spieler wird in der Liste mit <EndArray> zurück
	 * @author Tobias
	 * @return
	 */
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
	

	/**
	 * Gibt alle Spieler von den offnigen Spielern
	 * @author Tobias
	 * @param gameId
	 * @return
	 */
	public String[] getPlayerFromOpenGame(int gameId){
		GameModel game = searchOpenGame(gameId);
		String[] s = new String[game.getPlayers().size()];
		for(int c = 0; c < game.getPlayers().size() ; c++){
			s[c] = game.getPlayers().get(c).getNickname();
		}
		return s;
	}
	
	/**
	 * Gibt alle Spieler von den gestarteten Spielern
	 * @author Tobias
	 * @param gameId
	 * @return
	 */
	public String[] getPlayerFromStartedGame(int gameId){
		GameModel game = searchStartedGame(gameId);
		String[] s = new String[game.getPlayers().size()];
		for(int c = 0; c < game.getPlayers().size() ; c++){
			s[c] = game.getPlayers().get(c).getNickname();
		}
		return s;
	}
	
	/**
	 * Fügt den Spieler dem Game hinzu
	 * @author Tobias
	 * @param gameId
	 * @param nickName
	 * @param socket
	 * @return
	 */
	public boolean addPlayerToGame(int gameId, String nickName, Socket socket){
		for(int i = 0; i < openGameList.size() ; i++){
			if(openGameList.get(i).getGameId() == gameId){
				return openGameList.get(i).addPlayer(new User(nickName, socket));
			}
		}
		return false;
	}
	
	/**
	 * Sendet den neuen Spieler den anderen Spielern
	 * @author Tobias
	 * @param gameId
	 * @param nickName
	 * @param socket
	 */
	public void sendNewPlayer(int gameId, String nickName, Socket socket){
		for(int i = 0; i < openGameList.size() ; i++){
			if(openGameList.get(i).getGameId() == gameId){
				openGameList.get(i).sendNewPlayer(new User(nickName, socket));
			}
		}
	}
	
	/**
	 * holt Spiel und ruft die sendChat Methode aus dem GameModel auf
	 * @author Luzian
	 * @param chat
	 * @param gameId
	 */
	public void getSpiel(String chat, int gameId){
		GameModel game = searchStartedGame(gameId);
		game.sendChat(chat, game.getPlayers());
	}
	
	public void sendGameStats(Message msg, int gameId){
			searchStartedGame(gameId).sendGameStats(msg);
	}
	
	/**
	 * Startet das Spiel bei allen Spielern
	 * @author Tobias
	 * @param gameId
	 */
	public void startGame(int gameId){
		GameModel game = searchOpenGame(gameId);
		startedGameList.add(game);
		openGameList.remove(game);
		game.startGame();
	}
	
	public void changeGameMove(int gameId){
		searchStartedGame(gameId).changeGameMove();
	}
	
	/**
	 * Sucht die GameId in den offnigen Spielen
	 * @author Tobias
	 * @param gameId
	 * @return
	 */
	public GameModel searchOpenGame(int gameId){
		for(int i = 0; i < openGameList.size() ; i++){
			if(openGameList.get(i).getGameId() == gameId){
				return openGameList.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Sucht die GameId in den gestarteten Spielen
	 * @author Tobias
	 * @param gameId
	 * @return
	 */
	public GameModel searchStartedGame(int gameId){
		for(int i = 0; i < startedGameList.size() ; i++){
			if(startedGameList.get(i).getGameId() == gameId){
				return startedGameList.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Gibt die aktuelle MoveId zurück
	 * @author Tobias
	 * @param gameId
	 * @return
	 */
	public int getMoveId(int gameId){
		return searchStartedGame(gameId).getGameMove();
	}
	
	/**
	 * Setzt den gewünschten Spieler auf Tod
	 * @
	 * 
	 * @param gameId
	 * @param nickname
	 */
	public void setDead(int gameId, String nickname){
		searchStartedGame(gameId).setDeadPlayer(nickname);
	}

}
