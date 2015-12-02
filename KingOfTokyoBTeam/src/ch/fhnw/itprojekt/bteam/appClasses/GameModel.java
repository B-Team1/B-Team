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

	static public ArrayList<Card> cardList = new ArrayList<Card>();
	
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
	 * Methode zieht eine Karte in der Klasse Card
	 * @author Marco
	 */
	public Card pullCard(){
		Card newcard = new Card();
		newcard = newcard.pullCard();
		
		return newcard;
	}

	/**
	 * @author Luzian
	 * @return
	 */
	public ArrayList getDiceResult(){
		ArrayList<Dice> diceResult= new ArrayList<Dice>();
	for(int i = 0; i <= 6; i++){	
		Dice dice = new Dice();
		diceResult.add(dice.roll());	
	}
	return diceResult;
	}

}
