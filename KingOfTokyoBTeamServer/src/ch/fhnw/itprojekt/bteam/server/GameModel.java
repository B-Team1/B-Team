package ch.fhnw.itprojekt.bteam.server;

public class GameModel {
	private int numPlayer;
	private int winFamePoints;
	private boolean famePointsWin;
	
	
	public GameModel(int numPlayer, int winFamePoints, boolean famePointsWin) {
		super();
		this.numPlayer = numPlayer;
		this.winFamePoints = winFamePoints;
		this.famePointsWin = famePointsWin;
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

	
	
}
