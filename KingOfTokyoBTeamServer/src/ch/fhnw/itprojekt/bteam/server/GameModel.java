package ch.fhnw.itprojekt.bteam.server;

import java.util.ArrayList;

public class GameModel {
	private int numPlayer;
	private int winFamePoints;
	private boolean famePointsWin;
	private String nickName;
	private int gameId = 0;
	private static int gameIdCounter = 1;
	private ArrayList<String> players = new ArrayList<String>();

	public GameModel(int numPlayer, int winFamePoints, boolean famePointsWin, String nickName) {
		this.numPlayer = numPlayer;
		this.winFamePoints = winFamePoints;
		this.famePointsWin = famePointsWin;
		this.nickName = nickName;
		gameId = GameModel.gameIdCounter;
		GameModel.gameIdCounter++;
		players.add(nickName);
	}
	

	public void addPlayer(String player){
		players.add(player);
	}

	public int getNumPlayer() {
		return numPlayer;
	}

	public void setNumPlayer(int numPlayer) {
		this.numPlayer = numPlayer;
	}

	public int getWinFamePoints() {
		return winFamePoints;
	}

	public void setWinFamePoints(int winFamePoints) {
		this.winFamePoints = winFamePoints;
	}

	public boolean isFamePointsWin() {
		return famePointsWin;
	}

	public void setFamePointsWin(boolean famePointsWin) {
		this.famePointsWin = famePointsWin;
	}

	public int getGameId() {
		return gameId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public ArrayList<String> getPlayers() {
		players.add("Luzian");
		return players;
	}


	public void setPlayers(ArrayList<String> players) {
		this.players = players;
	}
	
	
}
