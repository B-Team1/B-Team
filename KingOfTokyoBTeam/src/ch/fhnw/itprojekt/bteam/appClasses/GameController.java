package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameController implements Initializable {

	@FXML
	ToggleButton btnDice1;
	@FXML
	ToggleButton btnDice2;
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
	@FXML Button btnCardDeck;
	@FXML
	Label lbLifePointsPlayer1;
	@FXML
	Label lbLifePointsPlayer2;
	@FXML
	Label lbLifePointsPlayer3;
	@FXML
	Label lbLifePointsPlayer4;
	@FXML
	Label lbFamePointsPlayer1;
	@FXML
	Label lbFamePointsPlayer2;
	@FXML
	Label lbFamePointsPlayer3;
	@FXML
	Label lbFamePointsPlayer4;
	@FXML
	Label lbEnergyPointsPlayer1;
	@FXML
	Label lbEnergyPointsPlayer2;
	@FXML
	Label lbEnergyPointsPlayer3;
	@FXML
	Label lbEnergyPointsPlayer4;
	@FXML
	Label lbLifePointsChangePlayer1;
	@FXML
	Label lbLifePointsChangePlayer2;
	@FXML
	Label lbLifePointsChangePlayer3;
	@FXML
	Label lbLifePointsChangePlayer4;
	@FXML
	Label lbFamePointsChangePlayer1;
	@FXML
	Label lbFamePointsChangePlayer2;
	@FXML
	Label lbFamePointsChangePlayer3;
	@FXML
	Label lbFamePointsChangePlayer4;
	@FXML
	Label lbEnergyPointsChangePlayer1;
	@FXML
	Label lbEnergyPointsChangePlayer2;
	@FXML
	Label lbEnergyPointsChangePlayer3;
	@FXML
	Label lbEnergyPointsChangePlayer4;
	@FXML
	Label lbNicknamePlayer1;
	@FXML
	Label lbNicknamePlayer2;
	@FXML
	Label lbNicknamePlayer3;
	@FXML
	Label lbNicknamePlayer4;
	@FXML
	TextField tfChat;
	@FXML
	TextArea taChat;
	@FXML
	ImageView ivCardDeck;
	@FXML
	ImageView ivCard1;
	@FXML
	ImageView ivCard2;
	
	GameModel model = new GameModel();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		btnCardDeck.setGraphic("../images/KingOfTokyo.jpg");
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
	
	/**
	 * Methode sendet den eingegebenen Text an alle Mitspieler
	 * @author
	 */
	@FXML
	public void handleSend(ActionEvent event) {
		
	}
	
	/**
	 * Methode zieht eine neue zufällige Karte
	 * @author Marco
	 */
	    @FXML public void handle(ActionEvent event) {
	    	Card card = new Card();
			card = model.pullCard();
			ivCard1.setImage(card.getCardImage());
	    }

}
