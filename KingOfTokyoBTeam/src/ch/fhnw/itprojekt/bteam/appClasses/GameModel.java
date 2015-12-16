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
	private int lifePoints, energyPoints, honorPoints, honorPointsWin = 25;
	static int cardCost = 3;
	private boolean inTokyo = false;
	static public ArrayList<Card> cardList = new ArrayList<Card>();
	public boolean goToTokyo, win = false;

	private static GameModel singleton;
	private int gameId;
	private ConnectionModel connectionModel;
	private String nickname;
	private int freePlayers;
	public ArrayList<Player> players = new ArrayList<Player>();
	public ArrayList<String> playerList = new ArrayList<String>();
	private ArrayList<Dice> diceResult= new ArrayList<Dice>();
	boolean famePointsWin;
	public int count, myPosition = 0;
	
	Player playerMe = new Player(nickname, lifePoints = 10, energyPoints = 0, honorPoints = 0, inTokyo);
	Player playerTwo = new Player(nickname, lifePoints = 10, energyPoints = 0, honorPoints = 0, inTokyo);
	Player playerThree = new Player(nickname, lifePoints = 10, energyPoints = 0, honorPoints = 0, inTokyo);
	Player playerFour = new Player(nickname, lifePoints = 10, energyPoints = 0, honorPoints = 0, inTokyo);
		
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

	public GameModel(int gameId, ArrayList<String> playerList) {
		this.playerList = playerList;
		this.singleton = this;
		this.gameId = gameId;
		connectionModel = ConnectionModel.getInstance();
		this.nickname = playerList.get(0);
	}
	
	/**
	 * Diese Methode prüft, ob bereits eine Instanz besteht und gibt dann eine zurück.
	 * @return 
	 * @author Tobias
	 */
	public static GameModel getInstance() {
		return singleton;
	}
	
	/**
	 * Methode öffnet das Spielfeld und lädt die Einstellungen
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
           
            
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	
	/**
	 * Methode öffnet das Fenster CreateGame und lädt die Einstellungen
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
	 * Öffnet das Fenster für den Gewinner mit der eingestellten Sprache
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
	 * Öffnet das Fenster für den Verlierer mit der eingestellten Sprache
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
	 * Öffnet das Fenster für das Wechseln aus Tokyo
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
	 * Methode berechnet die Auswirkungen bei einem Angriff mit einer Karte von Tokyo
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
	 * Methode berechnet die Auswirkungen bei einem Angriff mit einer Karte auf Tokyo
	 * @author Marco
	 */
	public void attackTokyo(int effect) {
		for (int i = 1; i < players.size(); i++) {
			if (players.get(i).inTokyo) {
				players.get(i).setActualCardLifePoints(players.get(i).getActualCardLifePoints() - effect);
			}
		}
		payCard();
		setPreview();
	}
	
	public void cardHeal(int effect) {
		players.get(myPosition).setActualCardLifePoints(players.get(myPosition).getActualCardLifePoints() + effect);
		players.get(myPosition).setFutureLifePoints(players.get(myPosition).getActualCardLifePoints() + players.get(myPosition).getActualDiceLifePoints());
		players.get(myPosition).setFutureEnergyPoints(players.get(myPosition).getActualCardEnergyPoints() + players.get(myPosition).getActualDiceEnergyPoints());
		payCard();
	}
	
	public void cardHonor(int effect) {
		players.get(myPosition).setActualCardHonorPoints(players.get(myPosition).getActualCardHonorPoints() + effect);
		players.get(myPosition).setFutureHonorPoints(players.get(myPosition).getActualCardHonorPoints() + players.get(myPosition).getActualDiceHonorPoints());
		players.get(myPosition).setFutureEnergyPoints(players.get(myPosition).getActualCardEnergyPoints() + players.get(myPosition).getActualDiceEnergyPoints());
		payCard();
	}
	
	public void payCard() {
		players.get(myPosition).setActualCardEnergyPoints(players.get(myPosition).getActualCardEnergyPoints() - cardCost);
	}

	/**
	 * Ruft die Klasse Dice auf, und gibt eine Arraylist mit 6 Würfel Objekten zurück. Variable count verhindert das mehr als drei Mal gewürfelt wird
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
	 * Zeigt an, wie sich die Punkte durch die aktuelle Sitation verändern würden
	 * @author Marco
	 */
	public void setDicePreview() {
		/**
		 * Setzt die Änderungen der Würfelwerte auf 0
		 */
		players.get(myPosition).setActualDiceEnergyPoints(0);
		players.get(myPosition).setActualDiceHonorPoints(0);
		players.get(myPosition).setActualDiceLifePoints(0);
		for (int i = 0; i > players.size(); i++) {
			players.get(i).setActualDiceLifePoints(0);
		}
		
		/**
		 * Zählt in der Würfelliste die Anzahl der verschiedenen Würfel
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
		 * Berechnet die Auswirkungen der Herz-Würfel
		 * @author Marco
		 */
		if (heart >= 1 && !players.get(myPosition).inTokyo) {
			players.get(myPosition).setActualDiceLifePoints(players.get(myPosition).getActualDiceLifePoints() + heart);
		}
		/**
		 * Berechnet die Auswirkungen bei einem Angriffs-Würfel
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
						} else {
							setGoToTokyo(true);
						}
					}
				}
			}
		}
		
		/**
		 * Berechnet die Auswirkungen durch die Flash- und Nummer-Würfel
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
	 * Setzt die Änderungen von den Karten und den Würfeln
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
	 * Setzt den Würfel als Selected wenn er angewählt ist
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
		}
		if (isGoToTokyo()) {
			players.get(myPosition).setInTokyo(true);
		}
		/**
		 * Überprüft ob es Verlierer oder Gewinner gibt
		 */
		setChangesZero();
	}
	
	 /**
	  * Überprüft ob ein Mitspieler keine Lebenspunkte mehr hat
	  * @author Marco
	  */
	public void checkLoser() {
		if (players.get(myPosition).getLifePoints() <= 0) {
			GameController.getInstance().loser();
		}
	}
	
	/**
	 * Überprüft ob PlayerMe dank den Ruhmespunkten gewonnen hat
	 * @author Marco
	 */
	public void checkWinner() {
		if (famePointsWin = true) {
			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).getHonorPoints() >= honorPointsWin) {
					if (players.get(i).equals(players.get(myPosition))) {
						GameController.getInstance().winner();
					} else {
						GameController.getInstance().loser();
					} 
				}
			}
		}else {
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
			if (win) {
				GameController.getInstance().winner();
			}
		}
	}
	
	/**
	 * Setzt die gespeicherten Änderungen auf Null für den nächsten Zug
	 * @author Marco
	 */
	private void setChangesZero() {
		count = 0;
		for (int i = 0; i < players.size(); i++) {
			players.get(i).setFutureEnergyPoints(0);
			players.get(i).setFutureHonorPoints(0);
			players.get(i).setFutureLifePoints(0);
			players.get(i).setActualCardEnergyPoints(0);
			players.get(i).setActualCardLifePoints(0);
			players.get(i).setActualCardHonorPoints(0);
		}
	}
	
	/**
	 * Löscht das bestehende Spiel vom Server
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
	 * Sendet die aktuelle Spielstände an den Server
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
	 * Speichert die Änderungen der Lebenspunkte aus dem InputHandler
	 * @author Marco
	 */
	public void setLifepoints(int[] lifePoints) {
		for (int i = 0; i < lifePoints.length; i++) {
			players.get(i).setLifePoints(lifePoints[i]);
		}
	}
	
	/**
	 * Speichert die Änderungen des aktuellen Spielers aus dem InputHandler
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
	 * Speichert die Änderung wer in Tokyo ist aus dem InputHandler
	 * @author Marco
	 */
	public void setActualTokyo(boolean[] tokyo) {
		for (int i = 0; i < tokyo.length; i++) {
			players.get(i).setInTokyo(tokyo[i]);
			if (players.get(i).inTokyo) {
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
	
	private void otherMonsters(int i) {
		for (int j = 0; j < players.size(); j++) {
			if (i != j) {
				GameController.getInstance().monsters.get(i).setVisible(false);
			}
		}
	}
	
	public void stayInTokyo(int[] lifepoints) {
		if (players.get(myPosition).inTokyo) {
			if (lifepoints[0] < players.get(myPosition).lifePoints) {
				startChangeTokyo(new Stage());
			}
		}
	}
	
	public void startGame(){
		connectionModel.startGame(this.gameId);
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
	
}
