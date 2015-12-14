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
import javafx.scene.image.ImageView;
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
	public int count;
	
	Player playerMe = new Player(lifePoints = 10, energyPoints = 0, honorPoints = 0, inTokyo);
	Player playerTwo = new Player(lifePoints = 10, energyPoints = 0, honorPoints = 0, inTokyo);
	Player playerThree = new Player(lifePoints = 10, energyPoints = 0, honorPoints = 0, inTokyo);
	Player playerFour = new Player(lifePoints = 10, energyPoints = 0, honorPoints = 0, inTokyo);
		
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
	 * Methode berechnet die Auswirkungen bei einem Angriff mit einer Karte von Tokyo
	 * @author Marco
	 */
	public void attackFromTokyo(int effect) {
		for (int i = 1; i < players.size(); i++) {
		players.get(i).setActualCardLifePoints(players.get(i).getActualCardLifePoints() - effect);
		}
		payCard();
		setPreview();
	}
	
	/**
	 * Methode berechnet die Auswirkungen bei einem Angriff mit einer Karte auf Tokyo
	 * @author Marco
	 */
	public void attackTokyo(Player player, int effect) {
		for (int i = 1; i < players.size(); i++) {
			if (players.get(i).inTokyo) {
				players.get(i).setActualCardLifePoints(players.get(i).getActualCardLifePoints() - effect);
			}
		}
		payCard();
		setPreview();
	}
	
	public void cardHeal(int effect) {
		players.get(0).setActualCardLifePoints(players.get(0).getActualCardLifePoints() + effect);
		payCard();
	}
	
	public void cardHonor(int effect) {
		players.get(0).setActualCardHonorPoints(players.get(0).getActualCardHonorPoints() + effect);
		payCard();
	}
	
	public void payCard() {
		players.get(0).setActualCardEnergyPoints(players.get(0).getActualCardEnergyPoints() - cardCost);
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
		players.get(0).setActualDiceEnergyPoints(0);
		players.get(0).setActualDiceHonorPoints(0);
		for (int i = 0; i > players.size(); i++){
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
		if (heart >= 1 && !players.get(0).inTokyo) {
			players.get(0).setActualDiceLifePoints(players.get(0).getActualDiceLifePoints() + heart);
		}
		/**
		 * Berechnet die Auswirkungen bei einem Angriffs-Würfel
		 * @author Marco
		 */
		if (attack >= 1) {
			if (players.get(0).inTokyo) {
				for (int i = 1; i < players.size(); i++) {
					players.get(i).setActualDiceLifePoints(players.get(i).getActualDiceLifePoints() - attack);
				}
				setGoToTokyo(false);
			} else {
				for (int i = 1; i < players.size(); i++) {
				if (players.get(i).inTokyo) {
					players.get(i).setActualDiceLifePoints(players.get(i).getActualDiceLifePoints() - attack);
					setGoToTokyo(false);
				} else {
					setGoToTokyo(true);
				}
				}
			}
		}
		
		/**
		 * Berechnet die Auswirkungen durch die Flash- und Nummer-Würfel
		 */
		if (flash >= 1) {
			players.get(0).setActualDiceEnergyPoints(players.get(0).getActualDiceEnergyPoints() + flash);
		}
		if (num1 >= 3) {
			players.get(0).setActualDiceHonorPoints(players.get(0).getActualDiceHonorPoints() + (num1 - 2));
		}
		if (num3 >= 3) {
			players.get(0).setActualDiceHonorPoints(players.get(0).getActualDiceHonorPoints() + num3);
					}
		if (num2 >= 3) {
			players.get(0).setActualDiceHonorPoints(players.get(0).getActualDiceHonorPoints() + (num2 - 1));
		}
	}
	
	/**
	 * Setzt die Änderungen von den Karten und den Würfeln
	 * @author Marco
	 */
	public void setPreview() {
		players.get(0).setFutureLifePoints(players.get(0).getActualDiceLifePoints() + players.get(0).getActualCardLifePoints());
		players.get(0).setFutureHonorPoints(players.get(0).getActualDiceHonorPoints() + players.get(0).getActualCardHonorPoints());
		players.get(0).setFutureEnergyPoints(players.get(0).getActualDiceEnergyPoints() + players.get(0).getActualCardEnergyPoints());
		for (int i = 1; i < players.size(); i++) {
			players.get(i).setFutureLifePoints(players.get(i).getActualDiceLifePoints() + players.get(i).getActualCardLifePoints());
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
		players.get(0).setEnergyPoints(players.get(0).getEnergyPoints() + players.get(0).getFutureEnergyPoints());
		players.get(0).setHonorPoints(players.get(0).getHonorPoints() + players.get(0).getFutureHonorPoints());
		for (int i = 0; i < players.size(); i++) {
			players.get(i).setLifePoints(players.get(i).getLifePoints() + players.get(i).getFutureLifePoints());
		}
		if (isGoToTokyo()) {
			players.get(0).setInTokyo(true);
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
		if (players.get(0).getLifePoints() <= 0) {
			//GameController.getInstance().loser();
		}
		for (int i = 1; i < players.size(); i++) {
			if (players.get(i).getLifePoints() <= 0) {
			}
		}
	}
	
	/**
	 * Überprüft ob PlayerMe dank den Ruhmespunkten gewonnen hat
	 * @author Marco
	 */
	public boolean checkWinner() {
		if (famePointsWin = true) {
			if (players.get(0).getHonorPoints() >= honorPointsWin) {
				win = true;
				return true;
			} else {
				return false;
			} 
		}else {
			if ((players.get(1).getLifePoints() <= 0) && ((players.size() < 3) || (players.get(2).getLifePoints() <= 0)) && ((players.size() < 4) || (players.get(3).getLifePoints() <= 0))) {
					return true;
				} else {
					return false;
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
		connectionModel.sendChat(text, getNickname());	
	}

	public void getPlayers(){
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
		int[] pointsMe = new int[3];
		int[] others = new int[players.size() - 1];
		boolean[] tokyo = new boolean[players.size()];
		pointsMe[0] = players.get(0).getLifePoints();
		pointsMe[1] = players.get(0).getEnergyPoints();
		pointsMe[2] = players.get(0).getHonorPoints();
		for (int i = 1; i < players.size(); i++) {
			others[i-1] = players.get(i).getLifePoints();
		}
		for (int i = 0; i < players.size(); i++) {
			tokyo[i] = players.get(i).inTokyo;
		}
 		connectionModel.sendGameStats(pointsMe, others, tokyo);
	}
	
	/**
	 * Speichert die Änderungen der Lebenspunkte aus dem InputHandler
	 * @author Marco
	 */
	public void setLifePoints(int[] lifePoints) {
		for (int i = 0; i < lifePoints.length; i++) {
			players.get(i).setLifePoints(lifePoints[i]);
		}
	}
	
	/**
	 * Speichert die Änderungen des aktuellen Spielers aus dem InputHandler
	 * @author Marco
	 */
	public void setPlayerPoints(int[] playerPoints) {
		players.get(1).setLifePoints(playerPoints[0]);
		players.get(1).setEnergyPoints(playerPoints[1]);
		players.get(1).setHonorPoints(playerPoints[2]);
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
					GameController.getInstance().ivMonsterInTokyoPlayerMe.setImage(new Image(getClass().getResourceAsStream("../images/Monster1.png")));
					break;
				case 1:
					GameController.getInstance().ivMonsterInTokyoPlayerMe.setImage(new Image(getClass().getResourceAsStream("../images/Monster2.png")));
					break;
				case 2:
					GameController.getInstance().ivMonsterInTokyoPlayerMe.setImage(new Image(getClass().getResourceAsStream("../images/Monster3.png")));
					break;
				case 3:
					GameController.getInstance().ivMonsterInTokyoPlayerMe.setImage(new Image(getClass().getResourceAsStream("../images/Monster4.png")));
					break;
				}
			}
		}
	}
	
	public void setGoToTokyo(boolean goToTokyo) {
		this.goToTokyo = goToTokyo;
	}
}
