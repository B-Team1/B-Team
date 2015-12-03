package ch.fhnw.itprojekt.bteam.server;

import java.util.ArrayList;

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
	public int newGame(int numPlayer, boolean famePointsWin, int winFamePoints){
		GameModel game = new GameModel(numPlayer, winFamePoints, famePointsWin);
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
}
