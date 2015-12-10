package ch.fhnw.itprojekt.bteam.appClasses;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import ch.fhnw.itprojekt.bteam.appClasses.Card.CardType;
import ch.fhnw.itprojekt.bteam.template.Properties;
import ch.fhnw.itprojekt.bteam.template.ServiceLocator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameModel extends Application {
	private int count, lifePoints, energyPoints, honorPoints;
	static int cardCost = 3;
	private boolean inTokyo = false;
	static public ArrayList<Card> cardList = new ArrayList<Card>();
	static public boolean tokyoTaken = false;
	private static GameModel singleton;
	private int gameId;
	private ConnectionModel connectionModel;
	private String nickname;
	private int freePlayers;
	private ArrayList<String> playerList = new ArrayList<String>();
	private ArrayList<Dice> diceResult= new ArrayList<Dice>();

	
	Player playerMe = new Player(lifePoints, energyPoints, honorPoints, inTokyo = true);
	Player playerTwo = new Player(lifePoints, energyPoints, honorPoints, inTokyo);
	Player playerThree = new Player(lifePoints, energyPoints, honorPoints, inTokyo);
	Player playerFour = new Player(lifePoints, energyPoints, honorPoints, inTokyo);
	
	

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
	public void start(Stage gameStage){
		try {
    		Properties.getProperties().setLocale(new Locale(ServiceLocator.getServiceLocator().getLanguage()));
            BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("../fxmls/gameBoard.fxml"),
            		ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale()));
    	
            Scene scene = new Scene(root);
            // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
            // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            createGameStage.setScene(scene);
            createGameStage.setTitle("King of Tokyo");
            createGameStage.setResizable(false);
            createGameStage.show();            
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	
	/**
	 * Methode berechnet die Auswirkungen bei einem Angriff mit einer Karte von Tokyo
	 * @author Marco
	 */
	public void attackFromTokyo(int effect) {
		playerTwo.setActualCardLifePoints(playerTwo.getActualCardLifePoints() - effect);
		if (playerThree!=null){
		playerThree.setActualCardLifePoints(playerThree.getActualCardLifePoints() - effect);
		}
		if (playerFour!=null){
		playerFour.setActualCardLifePoints(playerFour.getActualCardLifePoints() - effect);
		}
		payCard();
		setPreview();
	}
	
	/**
	 * Methode berechnet die Auswirkungen bei einem Angriff mit einer Karte auf Tokyo
	 * @author Marco
	 */
	public void attackTokyo(Player player, int effect) {
		if (playerTwo.inTokyo) {
			playerTwo.setActualCardLifePoints(playerTwo.getActualCardLifePoints() - effect);
		}
		if ((playerThree!=null) && (playerThree.inTokyo)) {
			playerThree.setActualCardLifePoints(playerThree.getActualCardLifePoints() - effect);
		}
		if ((playerFour!=null) && (playerFour.inTokyo)) {
			playerFour.setActualCardLifePoints(playerFour.getActualCardLifePoints() - effect);
		}
		payCard();
		setPreview();
	}
	
	public void cardHeal(int effect) {
		playerMe.setActualCardLifePoints(playerMe.getActualCardLifePoints() + effect);
		payCard();
	}
	
	public void cardHonor(int effect) {
		playerMe.setActualCardHonorPoints(playerMe.getActualCardHonorPoints() + effect);
		payCard();
	}
	
	public void payCard() {
		playerMe.setActualCardEnergyPoints(playerMe.getActualCardEnergyPoints() - cardCost);
	}

	/**
	 * Ruft die Klasse Dice auf, und gibt eine Arraylist mit 6 Würfel Objekten zurück. Variable count verhindert das mehr als drei Mal gewürfelt wird
	 * @author Luzian
	 * @return
	 */
	public ArrayList<Dice> getDiceResult() {
		if (count <=2 ) {
			for(int i = 0; i < 6; i++){
				
				diceResult.add(new Dice());
				if(!diceResult.get(i).isSelected) {
					diceResult.get(i).roll();
				}				
			}
			count++;
			return diceResult;
		}else{
			diceResult = null;
			return diceResult;
		}
	}
	
	public int countDice(int value) {
		return 5;
	}
	
	public void setDicePreview() {
		/**
		 * Setzt die Änderungen der Würfelwerte auf 0
		 */
		playerMe.setActualDiceEnergyPoints(0);
		playerMe.setActualDiceLifePoints(0);
		playerMe.setActualDiceHonorPoints(0);
		playerTwo.setActualDiceLifePoints(0);
		if (playerThree!=null) {
			playerThree.setActualDiceLifePoints(0);
		}
		if (playerFour!=null) {
			playerFour.setActualDiceLifePoints(0);
		}
		
		/**
		 * Zählt in der Würfelliste die Anzahl der verschiedenen Würfel
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
		if (heart >= 1 && !playerMe.inTokyo) {
			playerMe.setActualDiceLifePoints(playerMe.getActualDiceLifePoints() + heart);
		}
		/**
		 * Berechnet die Auswirkungen bei einem Angriffs-Würfel
		 * @author Marco
		 */
		if (attack >= 1) {
			if (playerMe.inTokyo) {
				playerTwo.setActualDiceLifePoints(playerTwo.getActualDiceLifePoints() - attack);
				if (playerThree!=null) {
					playerThree.setActualDiceLifePoints(playerThree.getActualDiceLifePoints() - attack);
				}
				if (playerFour!=null) {
					playerFour.setActualDiceLifePoints(playerFour.getActualDiceLifePoints() - attack);
				}
			} else {
				if (playerTwo.inTokyo) {
					playerTwo.setActualDiceLifePoints(playerTwo.getActualDiceLifePoints() - attack);
				} else {
					if (playerThree.inTokyo && playerThree!=null) {
						playerThree.setActualDiceLifePoints(playerThree.getActualDiceLifePoints() - attack);
					} else {
						if (playerFour.inTokyo && playerFour!=null) {
							playerFour.setActualDiceLifePoints(playerFour.getActualDiceLifePoints() - attack);
						} else {
							playerMe.setInTokyo(true);
						}
					}
				}
			}
		}
		if (flash >= 1) {
			playerMe.setActualDiceEnergyPoints(playerMe.getActualDiceEnergyPoints() + flash);
		}
		if (num1 >= 3) {
			playerMe.setActualDiceHonorPoints(playerMe.getActualDiceHonorPoints() + (num1 - 2));
		}
		if (num3 >= 3) {
			playerMe.setActualDiceHonorPoints(playerMe.getActualDiceHonorPoints() + num3);
					}
		if (num2 >= 3) {
			playerMe.setActualDiceHonorPoints(playerMe.getActualDiceHonorPoints() + (num2 - 1));
		}
	}
	
	/**
	 * Setzt die Änderungen von den Karten und den Würfeln
	 * @author Marco
	 */
	public void setPreview() {
		playerMe.setFutureLifePoints(playerMe.getActualDiceLifePoints() + playerMe.getActualCardLifePoints());
		playerMe.setFutureHonorPoints(playerMe.getActualDiceHonorPoints() + playerMe.getActualCardHonorPoints());
		playerMe.setFutureEnergyPoints(playerMe.getActualDiceEnergyPoints() + playerMe.getActualCardEnergyPoints());
		playerTwo.setFutureLifePoints(playerTwo.getActualDiceLifePoints() + playerTwo.getActualCardLifePoints());
		if (playerThree!=null) {
			playerThree.setFutureLifePoints(playerThree.getActualDiceLifePoints() + playerThree.getActualCardLifePoints());
		}
		if (playerFour!=null) {
			playerFour.setFutureLifePoints(playerFour.getActualDiceLifePoints() + playerFour.getActualCardLifePoints());
		}
	}
	
	public void setDiceSelected(int i) {
		diceResult.get(i).setSelected(true);
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
		connectionModel.sendChat(text);	
	}

	public void getPlayers(){
		connectionModel.getPlayers(gameId);
	}
	
	public void setPlayers(ArrayList<String> players){
		for (int i = 0; i< players.size(); i++){
			playerList.add(players.get(i));
		}
	}



	public ArrayList<String> getPlayerList() {
		return playerList;
	}



	public void setPlayerList(ArrayList<String> playerList) {
		this.playerList = playerList;
	}
	
	
}
