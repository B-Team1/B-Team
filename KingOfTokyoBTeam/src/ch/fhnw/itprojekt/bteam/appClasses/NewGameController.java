package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class NewGameController implements Initializable {

	@FXML
	ChoiceBox<String> cbNumPlayers;
	
	@FXML
	ChoiceBox<String> cbWinFamePoints;
	
	@FXML
	CheckBox chbWinFamePoints;
	
	/**
	 * Methode initialisiert die Komponenten
	 * @author Marco
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	cbNumPlayers.setItems(FXCollections.observableArrayList("2 Spieler", "3 Spieler", "4 Spieler"));
	cbNumPlayers.setValue("2 Spieler");
	cbWinFamePoints.setItems(FXCollections.observableArrayList("15 Punkte", "20 Punkte", "25 Punkte", "30 Punkte"));
	cbWinFamePoints.setValue("20 Punkte");
	cbWinFamePoints.setDisable(true);
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
	
	public void handleWinFamePoints(ActionEvent event) {
		if (chbWinFamePoints.isSelected()) {
			cbWinFamePoints.setDisable(false);
		} else {
			cbWinFamePoints.setDisable(true);
		}
	}
	
	

}
