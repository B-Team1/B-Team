package ch.fhnw.itprojekt.bteam.appClasses;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;





@Root
public class Message {
	public enum MessageType {
		Error, Login, SecurityQuestion, Registration, OpenGameRequest, SecurityAnswer, Password, Test, Broadcast,
		getStat, openNewGame, deleteGame, Chat, Players, AddPlayerToGame, AddNewPlayerToGame, GameStats, StartGame, 
		ChangeGameMove, ChangeTokyo
	};

	// Data included in a message
	@Element
	private MessageType type;
	
	@Element(required = false)
	private boolean checkLogin;
	
	@Element(required = false)
	private boolean famePointsWin;
	
	@Element(required = false)
	private int[] freePlaces;
	
	@Element(required = false)
	private int gameId;
	
	@Element(required = false)
	private int gameMove;
	
	@Element(required = false)
	private int[] gameIdList;
	
	@Element(required = false)
	private String nickname;
	
	@Element(required = false)
	private String nName;
	
	@Element(required = false)
	private int numPlayer;

	@Element(required = false)
	private String password;
	
	@Element(required = false)
	private int playedGames;
	
	@Element(required = false)
	private String securityQuestion;
	
	@Element(required = false)
	private String securityAnswer;
	
	@Element(required = false)
	private String[] userList;
	
	@Element(required = false)
	private String vName;
	
	@Element(required = false)
	private int winFamePoints;
	
	@Element(required = false)
	private int wonGames;
	
	@Element(required = false)
	private boolean writeCheck;
	
	@Element(required = false)
	private String chat;
	
	@Element(required = false)
	private String player1;
	
	@Element(required = false)
	private String player2;
	
	@Element(required = false)
	private String player3;
	
	@Element(required = false)
	private String player4;
	
	@Element(required = false)
	private String [] players;
	
	@Element(required = false)
	private String gamerName;
	
	@Element(required = false)
	private int[] myPoints;

	@Element(required = false)
	private int[] lifepoints;
	
	@Element(required = false)
	private boolean[] tokyo;

	public Message(@Element(name = "type") MessageType type) {
		this.type = type;
	}
	


	/**
	 * Receive a message: create a message object and fill it using data transmitted over the given
	 * socket.
	 * 
	 * @param socket
	 *          the socket to read from
	 * @return the new message object, or null in case of an error
	 * @throws Exception
	 */
	public static Message receive(Socket socket) throws Exception {
		Serializer netIn = new Persister();
		InputStream in = socket.getInputStream();
		Message msg = netIn.read(Message.class, in);
		return msg;
	}

	
	/**
	 * Send the current message.
	 * 
	 * @param socket
	 *          the socket to write to
	 * @throws Exception
	 */
	public void send(Socket socket) throws Exception {
		Serializer netOut = new Persister();
		OutputStream out = socket.getOutputStream();
		netOut.write(this, out);
		out.write('\n');
		out.flush();
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public String getNname() {
		return nName;
	}

	public String getPassword() {
		return password;
	}
		
	public boolean getCheckLogin() {
		return checkLogin;
	}

	public MessageType getType() {
		return type;
	}
	
	public void setType(MessageType type) {
		this.type = type;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCheckLogin(boolean checkLogin) {
		this.checkLogin = checkLogin;
	}

	public void setNname(String nName) {
		this.nName = nName;
	}

	public void setVname(String vName) {
		this.vName = vName;
	}
	
	public String getSecurityQuestion() {
		return securityQuestion;
	}
	
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	
	public String getSecurityAnswer() {
		return securityAnswer;
	}
	
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}
	
	public String getVName() {
		return vName;
	}
	
	public boolean getWriteCheck() {
		return writeCheck;
	}

	public void setWriteCheck(boolean writeCheck) {
		this.writeCheck = writeCheck;
	}

	public int getPlayedGames() {
		return playedGames;
	}

	public void setPlayedGames(int playedGames) {
		this.playedGames = playedGames;
	}

	public int getWonGames() {
		return wonGames;
	}

	public void setWonGames(int wonGames) {
		this.wonGames = wonGames;
	}

	public boolean getFamePointsWin() {
		return famePointsWin;
	}

	public void setFamePointsWin(boolean famePointsWin) {
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

	public void setWinFamePoins(int winFamePoins) {
		this.winFamePoints = winFamePoins;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public String getChat() {
		return chat;
	}

	public void setChat(String chat) {
		this.chat = chat;
	}

	public String getPlayer1() {
		return player1;
	}

	public void setPlayer1(String player1) {
		this.player1 = player1;
	}

	public String getPlayer2() {
		return player2;
	}

	public void setPlayer2(String player2) {
		this.player2 = player2;
	}

	public String getPlayer3() {
		return player3;
	}

	public void setPlayer3(String player3) {
		this.player3 = player3;
	}

	public String getPlayer4() {
		return player4;
	}

	public void setPlayer4(String player4) {
		this.player4 = player4;
	}

	public String[] getPlayers() {
		return players;
	}

	public void setPlayers(String[] players) {
		this.players = players;
	}
	
	public int[] getfreePlaces() {
		return freePlaces;
	}

	public void setfreePlaces(int[] freePlaces) {
		this.freePlaces = freePlaces;
	}

	public int[] getGameIdList() {
		return gameIdList;
	}

	public void setGameIdList(int[] gameIdList) {
		this.gameIdList = gameIdList;
	}

	public String[] getUserList() {
		return userList;
	}

	public void setUserList(String[] userList) {
		this.userList = userList;
	}
	
	public String getGamerName() {
		return gamerName;
	}
	
	public void setGamerName(String gamerName) {
		this.gamerName = gamerName;
	}
	
	public int[] getMyPoints() {
		return myPoints;
	}
	
	public void setMyPoints(int[] myPoints) {
		this.myPoints = myPoints;
	}
	
	public int[] getLifepoints() {
		return lifepoints;
	}
	
	public void setLifepoints(int[] lifepoints) {
		this.lifepoints = lifepoints;
	}
	
	public boolean[] getTokyo() {
		return tokyo;
	}
	
	public void setTokyo(boolean[] tokyo) {
		this.tokyo = tokyo;
	}

	public int getGameMove() {
		return gameMove;
	}

	public void setGameMove(int gameMove) {
		this.gameMove = gameMove;
	}
	
}
