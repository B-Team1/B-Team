package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import ch.fhnw.itprojekt.bteam.template.Properties;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class NewGameController implements Initializable {

	MenuModel model;
	
	@FXML
	ChoiceBox<String> cbNumPlayers;
	
	@FXML
	ChoiceBox<String> cbWinFamePoints;
	
	@FXML
	CheckBox chbWinFamePoints;
	
	public static Stage newGameStage = new Stage();
	
	

	/**
	 * Methode initialisiert die Komponenten
	 * @author Marco
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ResourceBundle bundle = ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale());
		cbNumPlayers.setItems(FXCollections.observableArrayList((bundle.getString("newgame.2players")),
				(bundle.getString("newgame.3players")), (bundle.getString("newgame.4players"))));
		cbNumPlayers.setValue(bundle.getString("newgame.2players"));
		cbWinFamePoints.setItems(FXCollections.observableArrayList((bundle.getString("newgame.15points")),
				(bundle.getString("newgame.20points")), (bundle.getString("newgame.25points")), (bundle.getString("newgame.30points"))));
		cbWinFamePoints.setValue((bundle.getString("newgame.20points")));
		cbWinFamePoints.setDisable(true);
		this.model = MenuModel.getInstance();
	}
	
	/**
	 * Methode erstellt neues Spiel mit den gewünschten Einstellungen (Anzahl Spieler, Siegesmöglichkeit)
	 * @author Marco
	 */
	public void handleCreateGame(ActionEvent createevent) {
		String numPlayers = (String) cbNumPlayers.getSelectionModel().getSelectedItem().toString(); 
		String winFamePoins = (String) cbWinFamePoints.getSelectionModel().getSelectedItem().toString(); 
		boolean famePointsWin = chbWinFamePoints.isSelected();
		
		model.openNewGame(numPlayers, famePointsWin, winFamePoins);
	}
	
	/**
	 * Methode für das ändern der Spielregeln; dass auch durch Ruhmpunkte ein Sieg möglich ist
	 * @param event
	 */
	public void handleWinFamePoints(ActionEvent event) {
		if (chbWinFamePoints.isSelected()) {
			cbWinFamePoints.setDisable(false);
		} else {
			cbWinFamePoints.setDisable(true);
		}
	}
	
	/**
	 * Methode öffnet das Game Overview Fenster und schliesst das aktuelle Fenster
	 * @author Marco
	 */
	public void handleAbort(ActionEvent event) {
		model.start(GameOverviewController.stage);
		Node node = (Node)event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}
	
	public void closeStage() {
		newGameStage.close();
	}
	
}
