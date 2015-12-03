package ch.fhnw.itprojekt.bteam.server;

import java.util.ArrayList;

public class MenuModel {
	ArrayList<GameModel> openGameList= new ArrayList<GameModel>();
	
	public boolean newGame(int numPlayer, boolean famePointsWin, int winFamePoints){
		openGameList.add(new GameModel(numPlayer, winFamePoints, famePointsWin));
		return true;
	}
}
