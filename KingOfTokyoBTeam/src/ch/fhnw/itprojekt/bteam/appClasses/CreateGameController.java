package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CreateGameController implements Initializable {

	MenuModel menuModel;
	GameModel gameModel;
	
	@FXML Label lbPlayer1;
	@FXML Label lbPlayer2;
	@FXML Label lbPlayer3;
	@FXML Label lbPlayer4;
	@FXML Button btnToGame;
	@FXML Button btnAbortGame;
	
	
	private ArrayList<Label> labelList = new ArrayList<Label>();
	private static CreateGameController singleton;
	
	public static Stage stage = new Stage();
	
	/**
	 * Methode initialisiert die Komponenten
	 * @author Marco
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gameModel = GameModel.getInstance();
		menuModel = MenuModel.getInstance();
		CreateGameController.singleton = this;
		labelList.add(lbPlayer1);
		labelList.add(lbPlayer2);
		labelList.add(lbPlayer3);
		labelList.add(lbPlayer4);
		for (Label label : labelList) {
			label.setText(null);			
		}
		btnToGame.setDisable(true);
	}
	
	public static CreateGameController getInstance() {
	     if(singleton == null) {	        
	         singleton = new CreateGameController();
	      }	     
	      return singleton;
	}
	
	/**
	 * Fügt einen Spieler der Anzeigeliste hinzu
	 * @author Tobias
	 */
	 public void addPlayers(String nickname){
		for (Label label : labelList) {
			if(label.getText() == null){
				label.setText(nickname);
				break;
			}
		}
	}
	
	/**
	 * Methode leitet die beigetretenen Spieler zum Spielbrett weiter
	 * @author Marco
	 */
	public void handleToGame(ActionEvent event) {
		gameModel.startGame();
	}
	
	/**
	 * Methode leitet alle beigetretenen Spieler zurück zum Fenster GameOverview
	 * @author Marco
	 */
//	public void handleAbortGame(ActionEvent event) {
//		gameModel.deleteGame();
//		menuModel.start(GameOverviewController.stage);
//		Node node= (Node)event.getSource();
//		Stage stage = (Stage) node.getScene().getWindow();
//		stage.close();
//	}
	
	public void disableBtns(){
		btnAbortGame.setDisable(true);
		btnToGame.setDisable(true);
	}
	
	public void enableBtns(){
		btnToGame.setDisable(false);
	}
}
