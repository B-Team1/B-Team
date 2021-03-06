package ch.fhnw.itprojekt.bteam.appClasses;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.crypto.spec.IvParameterSpec;

import ch.fhnw.itprojekt.bteam.appClasses.Card.CardType;
import ch.fhnw.itprojekt.bteam.template.Properties;
import ch.fhnw.itprojekt.bteam.template.ServiceLocator;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GameModel extends Application {
	private int lifePoints, energyPoints, honorPoints, honorPointsWin;
	static int cardCost = 3;
	private boolean inTokyo = false;
	static public ArrayList<Card> cardList = new ArrayList<Card>();
	public boolean goToTokyo = false, win = false;

	private static GameModel singleton;
	private int gameId;
	private ConnectionModel connectionModel;
	private String nickname;
	private int freePlayers;
	public ArrayList<Player> players = new ArrayList<Player>();
	public ArrayList<String> playerList = new ArrayList<String>();
	private ArrayList<Dice> diceResult= new ArrayList<Dice>();
	boolean famePointsWin;
	public int count, myPosition = 0, moveId;
	
		
	/**
	 * Darf nur zum Erstellen eines neuen Spiels verwendet werden!!!
	 * @author Tobias
	 * @param gameId
	 */
	public GameModel(int gameId) {
		this.singleton = this;
		this.gameId = gameId;
		connectionModel = ConnectionModel.getInstance();
	}

	/**
	 * Wird beim Laden aller Games ben�tigt
	 * @param gameId
	 * @param playerList
	 */
	public GameModel(int gameId, ArrayList<String> playerList) {
		this.playerList = playerList;
		this.singleton = this;
		this.gameId = gameId;
		connectionModel = ConnectionModel.getInstance();
		this.nickname = playerList.get(0);
	}
	
	/**
	 * Diese Methode pr�ft, ob bereits eine Instanz besteht und gibt dann eine zur�ck.
	 * @return 
	 * @author Tobias
	 */
	public static GameModel getInstance() {
		return singleton;
	}
	
	/**
	 * Methode �ffnet das Spielfeld und l�dt die Einstellungen
	 * @author Marco
	 */
	@Override
	public void start (Stage gameStage){
		try {
    		Properties.getProperties().setLocale(new Locale(ServiceLocator.getServiceLocator().getLanguage()));
            BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("../fxmls/gameBoard.fxml"),
            		ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale()));
    	
            Scene scene = new Scene(root);
            gameStage.setScene(scene);
            gameStage.setTitle("King of Tokyo");
            gameStage.setResizable(false);
            gameStage.show();
            if(myPosition == 0){
            	GameController.getInstance().enableGameBtns();          
            }
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	
	/**
	 * Methode �ffnet das Fenster CreateGame und l�dt die Einstellungen
	 * @author Marco
	 */
	public void startCreateGame(Stage createGameStage) {
		try {
    		Properties.getProperties().setLocale(new Locale(ServiceLocator.getServiceLocator().getLanguage()));
            BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("../fxmls/createGame.fxml"),
            		ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale()));
    	
            Scene scene = new Scene(root);
            createGameStage.setScene(scene);
            createGameStage.setTitle("King of Tokyo");
            createGameStage.setResizable(false);
            createGameStage.show();            
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
	}

	/**
	 * �ffnet das Fenster f�r den Gewinner mit der eingestellten Sprache
	 * @author Marco
	 */
	public void startWinner(Stage winnerStage) {
		try {
    		Properties.getProperties().setLocale(new Locale(ServiceLocator.getServiceLocator().getLanguage()));
            BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("../fxmls/winner.fxml"),
            		ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale()));
    	
            Scene scene = new Scene(root);
            winnerStage.setScene(scene);
            winnerStage.setTitle("King of Tokyo");
            winnerStage.setResizable(false);
            winnerStage.show();            
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	
	/**
	 * �ffnet das Fenster f�r den Verlierer mit der eingestellten Sprache
	 * @author Marco
	 */
	public void startLoser(Stage loserStage) {
		try {
    		Properties.getProperties().setLocale(new Locale(ServiceLocator.getServiceLocator().getLanguage()));
            BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("../fxmls/loser.fxml"),
            		ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale()));
            Scene scene = new Scene(root);
            loserStage.setScene(scene);
            loserStage.setTitle("King of Tokyo");
            loserStage.setResizable(false);
            loserStage.show();            
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	
	/**
	 * �ffnet das Fenster f�r das Wechseln aus Tokyo
	 * @author Marco
	 */
	public void startChangeTokyo(Stage changeStage) {
		try {
    		Properties.getProperties().setLocale(new Locale(ServiceLocator.getServiceLocator().getLanguage()));
            BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("../fxmls/leaveTokyo.fxml"),
            		ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale()));
            Scene scene = new Scene(root);
            changeStage.setScene(scene);
            changeStage.setTitle("King of Tokyo");
            changeStage.setResizable(false);
            changeStage.show();            
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	
	/**
	 * Methode berechnet die Auswirkungen bei einem Angriff mit einer Karte
	 * @author Marco
	 */
	public void cardAttack(int effect) {
		for (int i = 0; i < players.size(); i++) {
			if (i != myPosition) {
				players.get(i).setActualCardLifePoints(players.get(i).getActualCardLifePoints() - effect);
			}
		}
		payCard();
		setPreview();
	}
	
	/**
	 * Speichert die �nderungen bei einer Heilen-Karte
	 * @author Marco
	 */
	public void cardHeal(int effect) {
		players.get(myPosition).setActualCardLifePoints(players.get(myPosition).getActualCardLifePoints() + effect);
		players.get(myPosition).setFutureLifePoints(players.get(myPosition).getActualCardLifePoints() + players.get(myPosition).getActualDiceLifePoints());
		players.get(myPosition).setFutureEnergyPoints(players.get(myPosition).getActualCardEnergyPoints() + players.get(myPosition).getActualDiceEnergyPoints());
		payCard();
	}
	
	/**
	 * Speichert die �nderungen bei einer Ruhmeskarte
	 * @author Marco
	 */
	public void cardHonor(int effect) {
		players.get(myPosition).setActualCardHonorPoints(players.get(myPosition).getActualCardHonorPoints() + effect);
		players.get(myPosition).setFutureHonorPoints(players.get(myPosition).getActualCardHonorPoints() + players.get(myPosition).getActualDiceHonorPoints());
		players.get(myPosition).setFutureEnergyPoints(players.get(myPosition).getActualCardEnergyPoints() + players.get(myPosition).getActualDiceEnergyPoints());
		payCard();
	}
	
	/**
	 * Die Kosten der gekauften Karte wird abgezogen
	 * @author Marco
	 */
	public void payCard() {
		players.get(myPosition).setActualCardEnergyPoints(players.get(myPosition).getActualCardEnergyPoints() - cardCost);
	}

	/**
	 * Ruft die Klasse Dice auf, und gibt eine Arraylist mit 6 W�rfel Objekten zur�ck. Variable count verhindert das mehr als drei Mal gew�rfelt wird
	 * @author Luzian
	 * @return
	 */
	public ArrayList<Dice> getDiceResult() {
			for(int i = 0; i < 6; i++){
				
				diceResult.add(new Dice());
				if(!diceResult.get(i).isSelected) {
					diceResult.get(i).roll();
				}				
			}
			count++;
			return diceResult;
	}
	
	public int countDice(int value) {
		return 5;
	}
	
	/**
	 * Zeigt an, wie sich die Punkte durch die aktuelle Sitation ver�ndern w�rden
	 * @author Marco
	 */
	public void setDicePreview() {
		/**
		 * Setzt die �nderungen der W�rfelwerte auf 0
		 */
		players.get(myPosition).setActualDiceEnergyPoints(0);
		players.get(myPosition).setActualDiceHonorPoints(0);
		for (int i = 0; i < players.size(); i++) {
				players.get(i).setActualDiceLifePoints(0);
		}
		
		/**
		 * Z�hlt in der W�rfelliste die Anzahl der verschiedenen W�rfel
		 * @author Marco
		 */
		int heart = 0, attack = 0, flash = 0, num1 = 0, num3 = 0, num2 = 0;
		for(int i = 0; i <= 5; i++) {
			Dice dice = new Dice();
			if (diceResult != null){
			dice = diceResult.get(i);
				switch (dice.value) {
					case 1:  
						heart++;
						break;
					case 2: 
						attack++;
						break;
					case 3: 
						flash++;
						break;
					case 4: 
						num1++;
						break;
					case 5: 
						num3++;
						break;
					case 6: 
						num2++;
						break;
				}
			}
		}
		
		/**
		 * Berechnet die Auswirkungen der Herz-W�rfel
		 * @author Marco
		 */
		if (heart >= 1 && !players.get(myPosition).inTokyo) {
			if (players.get(myPosition).getLifePoints() != 10) {
				players.get(myPosition).setActualDiceLifePoints(players.get(myPosition).getActualDiceLifePoints() + heart);
			}
		}
		/**
		 * Berechnet die Auswirkungen bei einem Angriffs-W�rfel
		 * @author Marco
		 */
		if (attack >= 1) {
			if (players.get(myPosition).inTokyo) {
				for (int i = 0; i < players.size(); i++) {
					if (i != myPosition) {
						players.get(i).setActualDiceLifePoints(players.get(i).getActualDiceLifePoints() - attack);
					}
				}
				setGoToTokyo(false);
			} else {
				for (int i = 0; i < players.size(); i++) {
					if (i != myPosition) {
						if (players.get(i).inTokyo) {
							players.get(i).setActualDiceLifePoints(players.get(i).getActualDiceLifePoints() - attack);
							setGoToTokyo(false);
							break;
						} else {
							setGoToTokyo(true);
						}
					}
				}
			}
		} else {
			setGoToTokyo(false);
		}
		
		/**
		 * Berechnet die Auswirkungen durch die Flash- und Nummer-W�rfel
		 */
		if (flash >= 1) {
			players.get(myPosition).setActualDiceEnergyPoints(players.get(myPosition).getActualDiceEnergyPoints() + flash);
		}
		if (num1 >= 3) {
			players.get(myPosition).setActualDiceHonorPoints(players.get(myPosition).getActualDiceHonorPoints() + (num1 - 2));
		}
		if (num3 >= 3) {
			players.get(myPosition).setActualDiceHonorPoints(players.get(myPosition).getActualDiceHonorPoints() + num3);
					}
		if (num2 >= 3) {
			players.get(myPosition).setActualDiceHonorPoints(players.get(myPosition).getActualDiceHonorPoints() + (num2 - 1));
		}
	}
	
	/**
	 * Setzt die �nderungen von den Karten und den W�rfeln
	 * @author Marco
	 */
	public void setPreview() {
		players.get(myPosition).setFutureLifePoints(players.get(myPosition).getActualDiceLifePoints() + players.get(myPosition).getActualCardLifePoints());
		players.get(myPosition).setFutureHonorPoints(players.get(myPosition).getActualDiceHonorPoints() + players.get(myPosition).getActualCardHonorPoints());
		players.get(myPosition).setFutureEnergyPoints(players.get(myPosition).getActualDiceEnergyPoints() + players.get(myPosition).getActualCardEnergyPoints());
		for (int i = 0; i < players.size(); i++) {
			if (i != myPosition) {
				players.get(i).setFutureLifePoints(players.get(i).getActualDiceLifePoints() + players.get(i).getActualCardLifePoints());
			}
		}
	}
	
	/**
	 * Setzt den W�rfel als Selected wenn er angew�hlt ist
	 * @author Marco
	 */
	public void setDiceSelected(int i, boolean check) {
		if (count != 0) {
			diceResult.get(i).setSelected(check);
		}
	}
	
	/**
	 * Berechnet die neuen Lebens-, Energie- und Ruhmpunkte aus den Voranzeigen
	 * @author Marco
	 */
	public void endMove() {
		players.get(myPosition).setEnergyPoints(players.get(myPosition).getEnergyPoints() + players.get(myPosition).getFutureEnergyPoints());
		players.get(myPosition).setHonorPoints(players.get(myPosition).getHonorPoints() + players.get(myPosition).getFutureHonorPoints());
		for (int i = 0; i < players.size(); i++) {
			players.get(i).setLifePoints(players.get(i).getLifePoints() + players.get(i).getFutureLifePoints());
			if (players.get(i).getLifePoints() >= 10) {
				players.get(i).setLifePoints(10);
			}
		}
		if (isGoToTokyo()) {
			players.get(myPosition).setInTokyo(true);
		}
		diceResult.removeAll(diceResult);
		setChangesZero();
	}
	
	 /**
	  * �berpr�ft ob der Spieler verloren hat, falls ja wird das Verlierer Fenster ge�ffnet
	  * @author Marco
	  */
	public void checkLoser() {
		if (players.get(myPosition).getLifePoints() <= 0) {
			if (players.get(myPosition).isInTokyo()) {
				sendTokyoChange(true);
			}
			connectionModel.setStat(players.get(myPosition).getNickName(), "lose", this.gameId);
			startLoser(new Stage());
		}
		for (int i = 0; i < players.size(); i++) {
			if ((i != myPosition) && famePointsWin && (players.get(i).getHonorPoints() >= honorPointsWin)) {
				connectionModel.setStat(players.get(myPosition).getNickName(), "lose", this.gameId);
				startLoser(new Stage());
			}
		}
	}
	
	/**
	 * �berpr�ft ob der Spieler gewonnen hat, falls ja wird das Gewinner Fenster ge�ffnet
	 * @author Marco
	 */
	public void checkWinner() {
		boolean win = true;
			for (int i = 0; (i < players.size()) && win; i++) {
				if (i != myPosition) {
					if (players.get(i).getLifePoints() <= 0) {
						win = true;
					} else {
						win = false;
					}
				}
			}
		if (famePointsWin) {
			if (players.get(myPosition).getHonorPoints() >= honorPointsWin) {
				connectionModel.setStat(players.get(myPosition).getNickName(), "won", this.gameId);
				startWinner(new Stage());
				}
		}	
		if (win) {
			connectionModel.setStat(players.get(myPosition).getNickName(), "won", this.gameId);
			startWinner(new Stage());
		}
		
	}
	
	/**
	 * Setzt die gespeicherten �nderungen auf Null f�r den n�chsten Zug
	 * @author Marco
	 */
	private void setChangesZero() {
		count = 0;
		for (int i = 0; i < players.size(); i++) {
			players.get(i).setFutureEnergyPoints(0);
			players.get(i).setFutureHonorPoints(0);
			players.get(i).setFutureLifePoints(0);
			players.get(i).setActualDiceEnergyPoints(0);
			players.get(i).setActualDiceLifePoints(0);
			players.get(i).setActualDiceHonorPoints(0);
			players.get(i).setActualCardEnergyPoints(0);
			players.get(i).setActualCardLifePoints(0);
			players.get(i).setActualCardHonorPoints(0);
		}
	}
	
	/**
	 * L�scht das bestehende Spiel vom Server
	 * @author Tobias
	 */
	public void deleteGame(){
		connectionModel.deleteGame(gameId);
	}

	public int getGameId() {
		return gameId;
	}
	public boolean isGoToTokyo() {
		return goToTokyo;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getFreePlayers() {
		return freePlayers;
	}

	public void setFreePlayers(int freePlayers) {
		this.freePlayers = freePlayers;
	}
	
	/**
	 *@author Luzian
	 */
	public void sendChat(String text){
		connectionModel.sendChat(text, players.get(myPosition).getNickName() , getGameId() );	
	}

	public void askPlayer(){
		connectionModel.getPlayers(gameId);
	}
	
	public void setPlayers(ArrayList<String> players){
		playerList.addAll(players);
	}

	public ArrayList<String> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(ArrayList<String> playerList) {
		this.playerList = playerList;
	}
	
	/**
	 * Sendet die aktuelle Spielst�nde an den Server
	 * @author Marco
	 */
	public void sendGameStats() {
		String nickName = players.get(myPosition).getNickName();
		int[] pointsMe = new int[2];
		int[] lifePoints = new int[players.size()];
		boolean[] tokyo = new boolean[players.size()];
		pointsMe[0] = players.get(myPosition).getEnergyPoints();
		pointsMe[1] = players.get(myPosition).getHonorPoints();
		for (int i = 0; i < players.size(); i++) {
			lifePoints[i] = players.get(i).getLifePoints();
		}
		for (int i = 0; i < players.size(); i++) {
			tokyo[i] = players.get(i).inTokyo;
		}
 		connectionModel.sendGameStats(nickName ,pointsMe, lifePoints, tokyo, this.gameId);
	}
	
	/**
	 * Speichert die �nderungen der Lebenspunkte aus dem InputHandler
	 * @author Marco
	 */
	public void setLifepoints(int[] lifePoints) {
		for (int i = 0; i < lifePoints.length; i++) {
			players.get(i).setLifePoints(lifePoints[i]);
		}
	}
	
	/**
	 * Speichert die �nderungen des aktuellen Spielers aus dem InputHandler
	 * @author Marco
	 */
	public void setPlayerpoints(String gamerName, int[] playerPoints) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getNickName().equals(gamerName)){
				players.get(i).setEnergyPoints(playerPoints[0]);
				players.get(i).setHonorPoints(playerPoints[1]);
			}
		}
	}
	
	/**
	 * Speichert die �nderung wer in Tokyo ist aus dem InputHandler und zeigt das Bild an
	 * @author Marco
	 */
	public void setActualTokyo(boolean[] tokyo) {
		for (int i = 0; i < tokyo.length; i++) {
			players.get(i).setInTokyo(tokyo[i]);
			if (players.get(i).isInTokyo()) {
				switch (i) {
				case 0:
					GameController.getInstance().monsters.get(i).setVisible(true);
					break;
				case 1:
					GameController.getInstance().monsters.get(i).setVisible(true);
					break;
				case 2:
					GameController.getInstance().monsters.get(i).setVisible(true);
					break;
				case 3:
					GameController.getInstance().monsters.get(i).setVisible(true);
					break;
				}
			} else {
				GameController.getInstance().monsters.get(i).setVisible(false);
			}
		}
	}
	
	
	/**
	 * Pr�ft ob ich in Tokyo bin und attackiert wurde, damit ich gefragt werden kann ob ich
	 * Tokyo verlassen m�chte
	 * @author Marco
	 */
	public boolean underAttack(int[] lifepoints) {
		if (players.get(myPosition).inTokyo && (lifepoints[myPosition] < Integer.parseInt(GameController.getInstance().
				lbLifePoints.get(myPosition).getText())) && (lifepoints[myPosition] > 0)) {
				return true;
			} else {
				return false;
		}
	}
	
	/**
	 * Pr�ft ob ein Spieler in Tokyo angegriffen wurde, damit der Next Button deaktiviert wird
	 * bis dieser Spieler weis, ob er Tokyo verlassen m�chte
	 * @author Marco
	 */
	public boolean changeNextButton(int[] lifepoints) {
		boolean change = false;
		for (int i = 0; (i < players.size()) && !change; i++) {
			if (players.get(i).inTokyo && (lifepoints[i] < Integer.parseInt(GameController.getInstance().
					lbLifePoints.get(i).getText())) && (lifepoints[i] > 0)) {
				change = true;
			}
		}
		if (change) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Leited den Btn Befehl zum Starten des Spiels weiter
	 * @author Tobias
	 */
	public void startGame(){
		connectionModel.startGame(this.gameId);
	}
	
	
	public void tokyoChange() {
		startChangeTokyo(new Stage());
	}
	
	/**
	 * Wechselt den Spieler welcher in Tokyo ist und versendet die neue Liste
	 * @author Marco
	 */
	public void sendTokyoChange(boolean answer) {
		if(answer){
			players.get(myPosition).setInTokyo(false);
			players.get(getMoveId()).setInTokyo(true);
		}
		boolean[] tokyo = new boolean[players.size()];
		for (int i = 0; i < players.size(); i++) {
			tokyo[i] = players.get(i).isInTokyo();
		}
 		connectionModel.sendTokyoChange(tokyo, this.gameId);
	}
	
	public void setGoToTokyo(boolean goToTokyo) {
		this.goToTokyo = goToTokyo;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void addPlayerToModel(String nickname){
		players.add(new Player(nickname, lifePoints = 10, energyPoints = 0, honorPoints = 0, inTokyo));
	}

	public int getMyPosition() {
		return myPosition;
	}

	public void setMyPosition(int myPosition) {
		this.myPosition = myPosition;
	}
	
	/**
	 * Schaltet die Buttons frei wenn man am Zug ist. Ist man in Tokio gibts noch Ruhmpunkte
	 * @author Tobias
	 * @param moveId
	 */
	public void setGameMove(int moveId){
		if(moveId == myPosition){
			Player me = players.get(myPosition);
			if(me.isInTokyo()){
				me.setHonorPoints(me.getHonorPoints() + 2);
			}
			GameController.getInstance().enableGameBtns();
		}else{
			GameController.getInstance().disableGameBtns();
		}
	}
	
	/**
	 * Setzt die Sichtbarkeit der Spieler welche nicht am Zug sind herunter
	 * @author Marco
	 */
	public void setPlayerVisual(int moveId) {
		for (int i = 0; i < players.size(); i++) {
			if (i == moveId) {
				GameController.getInstance().vbPlayers.get(i).setOpacity(100);
			} else {
				GameController.getInstance().vbPlayers.get(i).setOpacity(50);
			}
		}
	}

	public int getHonorPointsWin() {
		return honorPointsWin;
	}

	public void setHonorPointsWin(int honorPointsWin) {
		this.honorPointsWin = honorPointsWin;
	}

	public boolean getFamePointsWin() {
		return famePointsWin;
	}

	public void setFamePointsWin(boolean famePointsWin) {
		this.famePointsWin = famePointsWin;
	}
	
	/**
	 * disabled den Btn wenn man nicht das Spiel erstellt hat
	 * @author Tobias
	 */
	public void disableNgcBtns(){
		if(this.myPosition != 0){
			CreateGameController.getInstance().disableBtns();
		}
	}
	
	/**
	 * enabled den Btn wenn man das Spiel erstellt hat
	 */
	public void enableNgcBtn(){
		if(this.myPosition == 0){
			CreateGameController.getInstance().enableBtns();
		}
	}

	public int getMoveId() {
		return moveId;
	}

	public void setMoveId(int moveId) {
		this.moveId = moveId;
	}

	public void sendGameMove(){
		connectionModel.sendGameMove(this.gameId);
	}
	
	public void enableNextBtn(){
		if(this.myPosition == this.moveId){
			GameController.getInstance().enableNextBtn();
		}
	}
	
}
