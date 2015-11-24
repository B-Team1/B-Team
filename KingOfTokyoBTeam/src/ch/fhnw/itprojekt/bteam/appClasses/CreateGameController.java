package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class CreateGameController implements Initializable {

	@FXML
	Label lbPlayer1;
	
	@FXML
	Label lbPlayer2;
	
	@FXML
	Label lbPlayer3;
	
	@FXML
	Label lbPlayer4;
	
	/**
	 * Methode initialisiert die Komponenten
	 * @author Marco
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	/**
	 * Methode leitet die beigetretenen Spieler zum Spielbrett weiter
	 * @author Marco
	 */
	public void handleToGame(ActionEvent event) {
		
	}
	
	/**
	 * Methode leitet alle beigetretenen Spieler zurück zum Fenster GameOverview
	 * @author Marco
	 */
	public void handleAbortGame(ActionEvent event) {
		
	}
	
}
