package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.text.html.HTMLDocument.Iterator;

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

	@FXML Button btnRollDice;
	@FXML ToggleButton btnDice1;
	@FXML ToggleButton btnDice2;
	@FXML ToggleButton btnDice3;
	@FXML ToggleButton btnDice4;
	@FXML ToggleButton btnDice5;
	@FXML ToggleButton btnDice6;
	@FXML ToggleButton btnDice7;
	@FXML ToggleButton btnDice8;
	@FXML Button btnCardDeck;
	@FXML Label lbLifePointsPlayer1;
	@FXML Label lbLifePointsPlayer2;
	@FXML Label lbLifePointsPlayer3;
	@FXML Label lbLifePointsPlayer4;
	@FXML Label lbFamePointsPlayer1;
	@FXML Label lbFamePointsPlayer2;
	@FXML Label lbFamePointsPlayer3;
	@FXML Label lbFamePointsPlayer4;
	@FXML Label lbEnergyPointsPlayer1;
	@FXML Label lbEnergyPointsPlayer2;
	@FXML Label lbEnergyPointsPlayer3;
	@FXML Label lbEnergyPointsPlayer4;
	@FXML Label lbLifePointsChangePlayer1;
	@FXML Label lbLifePointsChangePlayer2;
	@FXML Label lbLifePointsChangePlayer3;
	@FXML Label lbLifePointsChangePlayer4;
	@FXML Label lbFamePointsChangePlayer1;
	@FXML Label lbFamePointsChangePlayer2;
	@FXML Label lbFamePointsChangePlayer3;
	@FXML Label lbFamePointsChangePlayer4;
	@FXML Label lbEnergyPointsChangePlayer1;
	@FXML Label lbEnergyPointsChangePlayer2;
	@FXML Label lbEnergyPointsChangePlayer3;
	@FXML Label lbEnergyPointsChangePlayer4;
	@FXML Label lbNicknamePlayer1;
	@FXML Label lbNicknamePlayer2;
	@FXML Label lbNicknamePlayer3;
	@FXML Label lbNicknamePlayer4;
	@FXML TextField tfChat;
	@FXML TextArea taChat;
	@FXML ImageView ivCardDeck;
	@FXML ImageView ivCard1;
	@FXML ImageView ivCard2;
	


	GameModel gameModel;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gameModel = GameModel.getInstance();
	}

	/**Bei Klick auf Würfeln wird gameModel aufgerufen, welche das Würfelresultat zurückgibt. Zudem wird hier das Wüfelbild geladen.
	 * Methode würfelt die Würfel
	 * @author Marco / Luzian
	 */
	@FXML
	public void handleRollDice(ActionEvent event) {
	ArrayList<Dice> diceResult= new ArrayList<Dice>();
	diceResult = gameModel.getDiceResult();
	int i = 0 ;
	for(i = 0; i<=5; i++){
		Dice dice = new Dice();
		if (diceResult != null){
			dice = diceResult.get(i);
				switch (i) {
					case 0:  i = 0;
						if (btnDice1.isSelected() != true){
							btnDice1.setGraphic(new ImageView(dice.image));
						}
						break;
					case 1:  i = 1;
						if (btnDice2.isSelected() != true){
							btnDice2.setGraphic(new ImageView(dice.image));
						}
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
		}else{
			//Message das drei mal gewürfelt wurde
		}
	}
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
	@FXML 
	public void handleCardDeck(ActionEvent event) {
	    //Card card = new Card();
		try {
		if (GameModel.cardList.isEmpty()){
			Card firstcard = new Card();
			firstcard = gameModel.pullCard();
			ivCard1.setImage(firstcard.getCardImage());
		} else {
			Card secondcard = new Card();
			secondcard = gameModel.pullCard();
			ivCard2.setImage(secondcard.getCardImage());
		}
		}catch (NullPointerException exception){
			
		}
		
	    }


}
