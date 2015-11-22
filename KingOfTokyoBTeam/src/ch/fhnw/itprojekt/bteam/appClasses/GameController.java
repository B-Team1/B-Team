package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class GameController implements Initializable {

	@FXML
	Button btnDice1;
	@FXML
	Button btnDice2;
	@FXML
	Button btnDice3;
	@FXML
	Button btnDice4;
	@FXML
	Button btnDice5;
	@FXML
	Button btnDice6;
	@FXML
	Button btnDice7;
	@FXML
	Button btnDice8;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	/**
	 * Methode würfelt die Würfel
	 * @author Marco
	 */
	@FXML
	public void handleRollDice(ActionEvent event) {
	
	}
	
	/**
	 * Methode um den Spielzug zu beenden und die Ergebnisse ausführen
	 * @author Marco
	 */
	@FXML
	public void handleEndMove(ActionEvent event) {
		
	}
	
}
