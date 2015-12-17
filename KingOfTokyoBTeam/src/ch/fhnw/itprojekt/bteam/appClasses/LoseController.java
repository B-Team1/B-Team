package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

public class LoseController implements Initializable {

	MenuModel menuModel = new MenuModel();
	
	/**
	 * Methode initialisiert die Komponenten
	 * @author Marco
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	/**
	 * Methode schliesst die Fenster und kehrt zur Spiel Übersicht zurück
	 * @author Marco
	 */
	public void handleQuitGame(ActionEvent event) {
		menuModel.start(GameOverviewController.stage);
		GameController.gameStage.close();
		Node node= (Node)event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
		
		}
	
	
}
