package ch.fhnw.itprojekt.bteam.server;

public class GameModel {
	private int numPlayer;
	private int winFamePoints;
	private boolean famePointsWin;
	private static int gameId = 1;	

	public GameModel(int numPlayer, int winFamePoints, boolean famePointsWin) {
		this.numPlayer = numPlayer;
		this.winFamePoints = winFamePoints;
		this.famePointsWin = famePointsWin;
		gameId = gameId + 1;
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
	
	
}
