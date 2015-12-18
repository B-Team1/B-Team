package ch.fhnw.itprojekt.bteam.server;

import java.util.ArrayList;

public class GameModel {
	private int numPlayer;
	private int winFamePoints;
	private boolean famePointsWin;
	private String nickName;
	private int gameId = 0;
	private static int gameIdCounter = 1;
	private ArrayList<User> players = new ArrayList<User>();
	private ConnectionModel connectionModel;
	private int gameMove = 0;

	public GameModel(int numPlayer, int winFamePoints, boolean famePointsWin, User user) {
		this.numPlayer = numPlayer;
		this.winFamePoints = winFamePoints;
		this.famePointsWin = famePointsWin;
		this.nickName = user.getNickname();
		this.connectionModel = ConnectionModel.getInstance();
		gameId = GameModel.gameIdCounter;
		GameModel.gameIdCounter++;
		players.add(user);
	}
	
	/**
	 * Zählt den nächsten Spielzug hoch. Überspringt die toden Spieler
	 * @author Tobias
	 */
	public void changeGameMove(){
		gameMove++;
		if(gameMove >= players.size()){
			gameMove = 0;
		}
		while (players.get(gameMove).isDead()) {
			gameMove++;
			if(gameMove >= players.size()){
				gameMove = 0;
			}
		}
		connectionModel.changeGameMove(players, gameMove);
	}
	
	
	public int getGameMove(){
		int moveId = this.gameMove;
		return moveId;
	}
	
	public void startGame(){
		connectionModel.startGame(players);
		connectionModel.changeGameMove(players, gameMove);
	}
	
	public void sendNewPlayer(User player){
		connectionModel.sendNewPlayerInGame(players, player);
	}

	/**
	 * Fügt einen neuen Spieler dem Spiel hinzu.
	 * Gibt false zurück wenn das Spiel voll ist
	 * @param player
	 * @return
	 */
	public boolean addPlayer(User player){
		if (players.size() < numPlayer) {
			players.add(player);
			return true;
		}else{
			return false;
		}
		
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


	public ArrayList<User> getPlayers() {
		return players;
	}


	public void setPlayers(ArrayList<User> players) {
		this.players = players;
	}
	
	/**
	 * leitet Chattext und Playerliste ans ConnectionModel weiter
	 * @author Luzian
	 * @param text
	 * @param player
	 */
	public void sendChat(String text, ArrayList<User> player){
		connectionModel.sendChat(text, player);
	}
	
	public void sendGameStats(Message msg){
		connectionModel.sendGameStats(msg, players);
	}
	
	/**
	 * Gibt die Position des Spielers anhand des Nicknames zurück. Sollte der Spieler nicht gefunden
	 * werden, wird 100 zurück gegeben werden
	 * @author Tobias
	 * @param nickname
	 * @return
	 */
	public int getPlayerPosition(String nickname){
		for (int i = 0; i < players.size(); i++) {
			if(players.get(i).getNickname().equals(nickname)){
				return i;
			}
		}
		return 100;
	}
	
	/**
	 * Setzt den Spieler auf Tod. Leitet den nächsten Zug ein wenn der Tode Spieler ist
	 * @author Tobias
	 * @param nickname
	 */
	public void setDeadPlayer(String nickname){
		int playerId = getPlayerPosition(nickname);
		int moveId = gameMove;
		moveId++;
		if(moveId >= players.size()){
			moveId = 0;
		}
		while (players.get(moveId).isDead()) {
			moveId++;
			if(moveId >= players.size()){
				moveId = 0;
			}
		}
		try {
			players.get(playerId).setDead(true);
			players.get(playerId).deleteSocket();
			if(playerId == moveId){
				changeGameMove();
			}
		} catch (Exception e) {
			
		}
	}
}
