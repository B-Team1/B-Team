package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

public class LeaveTokyoController implements Initializable{

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	/**
	 * Ändert den Besetzter von Tokyo
	 * @author Marco
	 */
	public void handleYes(ActionEvent event) {
		GameModel.getInstance().sendTokyoChange();
		Node node= (Node)event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}
	
	/**
	 * Der Spieler bleibt in Tokyo
	 * @author Marco
	 */
	public void handleNo(ActionEvent event) {
		Node node= (Node)event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}
}
