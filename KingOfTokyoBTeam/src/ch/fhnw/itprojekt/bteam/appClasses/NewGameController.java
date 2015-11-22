package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class NewGameController implements Initializable {

	@FXML
	ComboBox<String> cbNumPlayers;
	
	@FXML
	ComboBox<String> cbWinFamePoints;
	
	@FXML
	CheckBox chbWinFamePoints;
	
	/**
	 * Methode initialisiert die Komponenten
	 * @author Marco
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	/**
	 * Methode erstellt neues Spiel mit den gewünschten Einstellungen (Anzahl Spieler, Siegesmöglichkeit)
	 * @author Marco
	 */
	public void handleCreateGame(ActionEvent event) {
		// Einstellungen übernehmen!
		MenuModel model = new MenuModel();
		model.startCreateGame(new Stage());
	}

}
