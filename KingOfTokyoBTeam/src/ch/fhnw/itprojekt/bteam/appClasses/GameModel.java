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
	
	Player playerMe = new Player(lifePoints, energyPoints, honorPoints, inTokyo = true);
	Player playerTwo = new Player(lifePoints, energyPoints, honorPoints, inTokyo);
	Player playerThree = new Player(lifePoints, energyPoints, honorPoints, inTokyo);
	Player playerFour = new Player(lifePoints, energyPoints, honorPoints, inTokyo);
	
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
	 * Methode �ffnet das Fenster CreateGame und l�dt die Einstellungen
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
		int afterAttackPointsPlayer2 = playerTwo.getFutureLifePoints() + effect;
		playerTwo.setFutureLifePoints(afterAttackPointsPlayer2);
		if (playerThree!=null){
		int afterAttackPointsPlayer3 = playerThree.getFutureLifePoints() + effect;
		playerThree.setFutureLifePoints(afterAttackPointsPlayer3);
		}
		if (playerFour!=null){
		int afterAttackPointsPlayer4 = playerFour.getFutureLifePoints() + effect;
		playerFour.setFutureLifePoints(afterAttackPointsPlayer4);
		}
		payCard();
	}
	
	/**
	 * Methode berechnet die Auswirkungen bei einem Angriff mit einer Karte auf Tokyo
	 * @author Marco
	 */
	public void attackTokyo(Player player, int effect) {
		if (playerTwo.inTokyo) {
			int afterAttackPointsPlayer2 = playerTwo.getFutureLifePoints() + effect;
			playerTwo.setFutureLifePoints(afterAttackPointsPlayer2);
		}
		if ((playerThree!=null) && (playerThree.inTokyo)) {
			int afterAttackPointsPlayer3 = playerThree.getFutureLifePoints() + effect;
			playerThree.setFutureLifePoints(afterAttackPointsPlayer3);
		}
		if ((playerFour!=null) && (playerFour.inTokyo)) {
			int afterAttackPointsPlayer4 = playerFour.getFutureLifePoints() + effect;
			playerFour.setFutureLifePoints(afterAttackPointsPlayer4);
		}
		payCard();
	}
	
	public void payCard() {
		int afterAttackPointsPlayerMe = playerMe.getFutureEnergyPoints() + cardCost;
		playerMe.setFutureEnergyPoints(afterAttackPointsPlayerMe);
	}

	/**
	 * Ruft die Klasse Dice auf, und gibt eine Arraylist mit 6 W�rfel Objekten zur�ck. Variable count verhindert das mehr als drei Mal gew�rfelt wird
	 * @author Luzian
	 * @return
	 */
	public ArrayList getDiceResult(){
		ArrayList<Dice> diceResult= new ArrayList<Dice>();
		if (count <=2){
			for(int i = 0; i <= 6; i++){	
				Dice dice = new Dice();
				diceResult.add(dice.roll());	
			}
			count++;
			return diceResult;
		}else{
			diceResult = null;
			return diceResult;
		}
	}
	
	/**
	 * L�scht das bestehende Spiel vom Server
	 * @author Tobias
	 */
	public void deleteGame(){
		connectionModel.deleteGame(gameId);
	}

}
