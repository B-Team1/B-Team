package ch.fhnw.itprojekt.bteam.appClasses;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import ch.fhnw.itprojekt.bteam.appClasses.Card.CardType;
import ch.fhnw.itprojekt.bteam.template.Properties;
import ch.fhnw.itprojekt.bteam.template.ServiceLocator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameModel extends Application {
	private int lifePoints, energyPoints, honorPoints, honorPointsWin;
	static int cardCost = 3;
	private boolean inTokyo = false;
	static public ArrayList<Card> cardList = new ArrayList<Card>();
	public boolean goToTokyo, win = false;
	Stage gameStage;

	private static GameModel singleton;
	private int gameId;
	private ConnectionModel connectionModel;
	ArrayList<Dice> diceResult= new ArrayList<Dice>();
	boolean famePointsWin;
	public int count;
	
	Player playerMe = new Player(lifePoints = 0, energyPoints = 0, honorPoints = 0, inTokyo);
	Player playerTwo = new Player(lifePoints = 10, energyPoints = 0, honorPoints = 0, inTokyo);
	Player playerThree = new Player(lifePoints = 10, energyPoints = 0, honorPoints = 0, inTokyo);
	Player playerFour = new Player(lifePoints = 10, energyPoints = 0, honorPoints = 0, inTokyo);
	
	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

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
		setStage(createGameStage);
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
	
	private void setStage(Stage createGameStage) {
		gameStage = createGameStage;
	}
	
	public Stage getStage() {
		return gameStage;
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
            // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
            // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
				setGoToTokyo(false);
			} else {
				if (playerTwo.inTokyo) {
					playerTwo.setActualDiceLifePoints(playerTwo.getActualDiceLifePoints() - attack);
					setGoToTokyo(false);
				} else {
					if (playerThree.inTokyo && playerThree!=null) {
						playerThree.setActualDiceLifePoints(playerThree.getActualDiceLifePoints() - attack);
						setGoToTokyo(false);
					} else {
						if (playerFour.inTokyo && playerFour!=null) {
							playerFour.setActualDiceLifePoints(playerFour.getActualDiceLifePoints() - attack);
							setGoToTokyo(false);
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
		 * Berechnet die Auswirkungen durch die Flash- und Nummer-Würfel
		 */
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
		playerMe.setEnergyPoints(playerMe.getEnergyPoints() + playerMe.getFutureEnergyPoints());
		playerMe.setLifePoints(playerMe.getLifePoints() + playerMe.getFutureLifePoints());
		playerMe.setHonorPoints(playerMe.getHonorPoints() + playerMe.getFutureHonorPoints());
		playerTwo.setLifePoints(playerTwo.getLifePoints() + playerTwo.getFutureLifePoints());
		if (playerThree!=null) {
			playerThree.setLifePoints(playerThree.getLifePoints() + playerThree.getFutureLifePoints());
		}
		if (playerFour!=null) {
			playerFour.setLifePoints(playerFour.getLifePoints() + playerFour.getFutureLifePoints());
		}
		if (isGoToTokyo()) {
			playerMe.setInTokyo(true);
		}
		/**
		 * Überprüft ob es Verlierer oder Gewinner gibt
		 */
		checkLoser();
		checkWinner();
		setChangesZero();
	}
	
	 /**
	  * Überprüft ob ein Mitspieler keine Lebenspunkte mehr hat
	  * @author 
	  */
	private void checkLoser() {
		if (playerMe.getLifePoints() <= 0) {
			startLoser(new Stage());
		}
		if (playerTwo.getLifePoints() <= 0) {
			// Du häsch verlore
		}
		if (playerThree!=null && (playerThree.getLifePoints() <= 0)) {
			// Du häsch verlore
		}
		if (playerFour!=null && (playerFour.getLifePoints() <= 0)) {
			// Du häsch verlore
		}
	}
	
	/**
	 * Überprüft ob PlayerMe dank den Ruhmespunkten gewonnen hat
	 * @author Marco
	 */
	private void checkWinner() {
		if (famePointsWin = true) {
			if (playerMe.getHonorPoints() >= honorPointsWin) {
				win = true;
			}
		}
	}
	
	/**
	 * Setzt die gespeicherten Änderungen auf Null für den nächsten Zug
	 * @author Marco
	 */
	private void setChangesZero() {
		playerMe.setFutureEnergyPoints(0);
		playerMe.setFutureHonorPoints(0);
		playerMe.setFutureLifePoints(0);
		playerMe.setActualCardEnergyPoints(0);
		playerMe.setActualCardHonorPoints(0);
		playerMe.setActualCardLifePoints(0);
		playerTwo.setFutureLifePoints(0);
		playerTwo.setActualCardEnergyPoints(0);
		playerTwo.setActualCardHonorPoints(0);
		playerTwo.setActualCardLifePoints(0);
		if (playerThree!=null) {
		playerThree.setFutureLifePoints(0);
		playerThree.setActualCardEnergyPoints(0);
		playerThree.setActualCardHonorPoints(0);
		playerThree.setActualCardLifePoints(0);
		}
		if (playerFour != null) {
		playerFour.setFutureLifePoints(0);
		playerFour.setActualCardEnergyPoints(0);
		playerFour.setActualCardHonorPoints(0);
		playerFour.setActualCardLifePoints(0);
		}
	}
	
	/**
	 * Löscht das bestehende Spiel vom Server
	 * @author Tobias
	 */
	public void deleteGame(){
		connectionModel.deleteGame(gameId);
	}
	public boolean isGoToTokyo() {
		return goToTokyo;
	}

	public void setGoToTokyo(boolean goToTokyo) {
		this.goToTokyo = goToTokyo;
	}
	
}
