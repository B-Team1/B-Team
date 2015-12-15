package ch.fhnw.itprojekt.bteam.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;




public class MenuModel {
	private ArrayList<GameModel> openGameList= new ArrayList<GameModel>();
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
			String[] playerList = this.getGame(idList[i]);
			gameList.add(idList[i]+ "");
			for(int c = 0; c < playerList.length; c ++){
				gameList.add(playerList[c]);
			}
			gameList.add("<EndArray>");
		}
		
		return gameList.toArray(new String[gameList.size()]);		
	}
	

	public String[] getGame(int gameId){
		for(int i = 0; i < openGameList.size() ; i++){
			if(openGameList.get(i).getGameId() == gameId){
				String[] s = new String[openGameList.get(i).getPlayers().size()];
				for(int c = 0; c < openGameList.get(i).getPlayers().size() ; c++){
					s[c] = openGameList.get(i).getNickName();
				}
				return s;
			}
		}
		return null;
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
	
	//test
	public void getSpiel(String chat, int gameId){
		for(int i = 0; i < openGameList.size() ; i++){
			if(openGameList.get(i).getGameId() == gameId){
				openGameList.get(i).sendChat(chat, openGameList.get(i).getPlayers());
			}
		}
	}

}
