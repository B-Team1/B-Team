package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.text.html.HTMLDocument.Iterator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
	
	GameModel gameModel = new GameModel();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	/**
	 * Methode w�rfelt die W�rfel
	 * @author Marco / Luzian
	 */
	@FXML
	public void handleRollDice(ActionEvent event) {
	ArrayList<Dice> diceResult= new ArrayList<Dice>();
	diceResult = gameModel.getDiceResult();
	int i = 0 ;
	for(i = 0; i<=5; i++){
		Dice dice = new Dice();
		dice = diceResult.get(i);
		switch (i) {
			case 0:  i = 0;
				btnDice1.setGraphic(new ImageView(dice.image));
				break;
			case 1:  i = 1;
    		 	btnDice2.setGraphic(new ImageView(dice.image));
    		 	break;
			case 2:  i = 2;
    		 	btnDice3.setGraphic(new ImageView(dice.image));
    		 	break;
			case 3:  i = 3;
    		 	btnDice4.setGraphic(new ImageView(dice.image));
    		 	break;
			case 4:  i = 4;
    		 	btnDice5.setGraphic(new ImageView(dice.image));
    		 	break;
			case 5:  i = 5;
    		 	btnDice6.setGraphic(new ImageView(dice.image));
    		 	break;
    	
	 } 		
		
		
	}
		
	btnDice1.setText(diceResult.get(0).toString());
	}
	/**
	 * Methode um den Spielzug zu beenden und die Ergebnisse ausf�hren
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
	
	
	
}
