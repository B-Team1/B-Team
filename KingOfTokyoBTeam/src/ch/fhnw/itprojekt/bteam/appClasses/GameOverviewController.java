package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.text.TabableView;

import ch.fhnw.itprojekt.bteam.template.Properties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class GameOverviewController implements Initializable {

	@FXML
	Label lbPlayGames;
	
	@FXML
	Label lblbWonGames;
	
	@FXML
	Label lbNickname;
	
	@FXML
	TabableView tvOpenGames;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	@FXML
	public void handleStartGame(ActionEvent event) {
	
	}
	
	/**
	 * Methode um ein neues Spiel zu erstellen. Öffnet neues Fenster und schliesst das Aktuelle
	 * @author Marco
	 */
	@FXML
	public void handleNewGame(ActionEvent event) {
		MenuModel model = new MenuModel();
		model.startNewGame(new Stage());
		Node node = (Node)event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}
	
	/**
	 * Methode um das Fenster und die Anwendung zu schliessen
	 * @author Marco
	 */
	@FXML
	public void handleCloseGame(ActionEvent event) {
		Node node = (Node)event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}
	
	/**
	 * Methode um die Sprache per MenuItem auf Deutsch zu ändern
	 * @author Marco
	 */
	@FXML
	public void handleGerman(ActionEvent event) {
		Properties.getProperties().setLocale(new Locale("de"));
//		updateTexts();
	}
	
	/**
	 * Methode um die Sprache per MenuItem auf Englisch zu ändern
	 * @author Marco
	 */
	@FXML
	public void handleEnglish(ActionEvent event) {
		Properties.getProperties().setLocale(new Locale("en"));
//		updateTexts();
	}
	
}
