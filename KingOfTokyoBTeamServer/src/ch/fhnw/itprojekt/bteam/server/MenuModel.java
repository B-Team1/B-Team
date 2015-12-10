package ch.fhnw.itprojekt.bteam.server;

import java.util.ArrayList;
import java.util.Arrays;



public class MenuModel {
	private ArrayList<GameModel> openGameList= new ArrayList<GameModel>();
	
	/**
	 * Erstellt ein neues Spiel und fügt es der openGameList hinzu
	 * @param numPlayer
	 * @param famePointsWin
	 * @param winFamePoints
	 * @return gameId des neu erstellten Spiels
	 * @author Tobias
	 */
	public int newGame(int numPlayer, boolean famePointsWin, int winFamePoints, String nickName){
		GameModel game = new GameModel(numPlayer, winFamePoints, famePointsWin, nickName);
		openGameList.add(game);
		return game.getGameId();
	}
	
	public void test(){
		GameModel t = new GameModel(3, 20, true, "Penis");
		t.addPlayer("Tobias");
		openGameList.add(t);
		openGameList.add(new GameModel(3, 20, true, "Penis1"));
		openGameList.add(new GameModel(3, 20, true, "Penis2"));
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
 				return openGameList.get(i).getPlayers().toArray(s);
			}
		}
		return null;
	}

}
