package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.text.html.HTMLDocument.Iterator;

//import ch.fhnw.itprojekt.bteam.server.ConnectionModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
	@FXML ImageView ivMonsterInTokyo;
	


	GameModel gameModel;
	private static GameController singleton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnDice1.setVisible(false);
		btnDice2.setVisible(false);
		btnDice3.setVisible(false);
		btnDice4.setVisible(false);
		btnDice5.setVisible(false);
		btnDice6.setVisible(false);
		
		gameModel = GameModel.getInstance();

	}

	/**Bei Klick auf Würfeln wird gameModel aufgerufen, welche das Würfelresultat zurückgibt. Zudem wird hier das Wüfelbild geladen.
	 * Methode würfelt die Würfel
	 * @author Marco / Luzian
	 */
	@FXML
	public void handleRollDice(ActionEvent event) {
		if (!btnDice1.isVisible()&& !btnDice2.isVisible()&& !btnDice3.isVisible()&& !btnDice4.isVisible()
														&& !btnDice5.isVisible()&& !btnDice6.isVisible()){
			btnDice1.setVisible(true);
			btnDice2.setVisible(true);
			btnDice3.setVisible(true);
			btnDice4.setVisible(true);
			btnDice5.setVisible(true);
			btnDice6.setVisible(true);
		}
		ArrayList<Dice> diceResult= new ArrayList<Dice>();
		diceResult = gameModel.getDiceResult();
		for(int i = 0; i<=5; i++){
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
							if (btnDice3.isSelected() != true){
								btnDice3.setGraphic(new ImageView(dice.image));
							}
							break;
						case 3:  i = 3;
							if (btnDice4.isSelected() != true){
								btnDice4.setGraphic(new ImageView(dice.image));
							}
							break;
						case 4:  i = 4;
							if (btnDice5.isSelected() != true){
								btnDice5.setGraphic(new ImageView(dice.image));
							}
							break;
						case 5:  i = 5;
							if (btnDice6.isSelected() != true){
								btnDice6.setGraphic(new ImageView(dice.image));
							}
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
	 * @author Luzian
	 */
	@FXML
	public void handleSend(ActionEvent event) {
		gameModel.sendChat(tfChat.getText());
	}
	
	/**
	 * Methode zieht eine neue zufällige Karte
	 * @author Marco
	 */
	@FXML 
	public void handleCardDeck(ActionEvent event) {
		try {
			Card newCard = new Card();
			GameModel.getInstance().playerMe.setEnergyPoints(10);
		if (GameModel.cardList.size() == 0 && (GameModel.getInstance().playerMe.getEnergyPoints() >= 3)){
			newCard = newCard.pullCard();
			ivCard1.setImage(newCard.getCardImage());
		} else {
			if (GameModel.getInstance().playerMe.getEnergyPoints() >= 3) {
			newCard = newCard.pullCard();
			ivCard2.setImage(newCard.getCardImage());
			}
		}
			
			
			/**
			 * Die einzelnen Aktionen je nach Aktions-Typ der Karte
			 */
			switch (newCard.getAction()){
			case attack:
				if (GameModel.getInstance().playerMe.inTokyo){
					gameModel.attackFromTokyo(newCard.getEffect());
					lbLifePointsChangePlayer2.setText("-" + GameModel.getInstance().playerTwo.getFutureLifePoints());
					if (GameModel.getInstance().playerThree!=null) {
					lbLifePointsChangePlayer3.setText("-" + GameModel.getInstance().playerThree.getFutureLifePoints());
					}
					if (GameModel.getInstance().playerFour!=null) {
					lbLifePointsChangePlayer4.setText("-" + GameModel.getInstance().playerFour.getFutureLifePoints());
					}
					lbEnergyPointsChangePlayer1.setText("-" + GameModel.getInstance().playerMe.getFutureEnergyPoints());
				} else {
					gameModel.attackTokyo(GameModel.getInstance().playerTwo, newCard.getEffect());
					if (GameModel.getInstance().playerTwo.getFutureLifePoints() != 0){
						lbLifePointsChangePlayer2.setText("-" + GameModel.getInstance().playerTwo.getFutureLifePoints());
					}
					if ((GameModel.getInstance().playerThree.getFutureLifePoints() != 0) && (GameModel.getInstance().playerThree!=null)) {
						lbLifePointsChangePlayer3.setText("-" + GameModel.getInstance().playerThree.getFutureLifePoints());
					}
					if ((GameModel.getInstance().playerFour.getFutureLifePoints() != 0) && (GameModel.getInstance().playerFour!=null)) {
						lbLifePointsChangePlayer4.setText("-" + GameModel.getInstance().playerFour.getFutureLifePoints());
					}
					lbEnergyPointsChangePlayer1.setText("-" + GameModel.getInstance().playerMe.getFutureEnergyPoints());
				}
				break;
			case heal:
				int afterHealPoints = GameModel.getInstance().playerMe.getFutureLifePoints() + newCard.getEffect();
				GameModel.getInstance().playerMe.setFutureLifePoints(afterHealPoints);
				lbLifePointsChangePlayer1.setText("+" + GameModel.getInstance().playerMe.getFutureLifePoints());
				GameModel.getInstance().payCard();
				lbEnergyPointsChangePlayer1.setText("-" + GameModel.getInstance().playerMe.getFutureEnergyPoints());
				break;
			case honor:
				int futureHonorPoints = GameModel.getInstance().playerMe.getFutureHonorPoints() + newCard.getEffect();
				GameModel.getInstance().playerMe.setFutureHonorPoints(futureHonorPoints);
				lbFamePointsChangePlayer1.setText("+" + GameModel.getInstance().playerMe.getFutureHonorPoints());
				GameModel.getInstance().payCard();
				lbEnergyPointsChangePlayer1.setText("-" + GameModel.getInstance().playerMe.getFutureEnergyPoints());
				break;
		}
				
		}catch (NullPointerException exception){
			
		}
		
	    }
	
	
	public static GameController getInstance() {
	     if(singleton == null) {	        
	         singleton = new GameController();
	      }	     
	      return singleton;
	}

}
