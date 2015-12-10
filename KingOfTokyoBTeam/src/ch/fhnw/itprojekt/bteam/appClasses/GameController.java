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
	
	ArrayList<ToggleButton> buttonList = new ArrayList<ToggleButton>();
	private int count = 0;
	GameModel gameModel;
	private static GameController singleton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		singleton = this;
		gameModel = GameModel.getInstance();
		gameModel.getPlayers();
		taChat.setEditable(false);
		buttonList.add(btnDice1);
		buttonList.add(btnDice2);
		buttonList.add(btnDice3);
		buttonList.add(btnDice4);
		buttonList.add(btnDice5);
		buttonList.add(btnDice6);

	}

	/**Bei Klick auf Würfeln wird gameModel aufgerufen, welche das Würfelresultat zurückgibt. Zudem wird hier das Wüfelbild geladen.
	 * Methode würfelt die Würfel
	 * @author Marco / Luzian
	 */
	@FXML
	public void handleRollDice(ActionEvent event) {
		ArrayList<Dice> diceResult= new ArrayList<Dice>();
		diceResult = gameModel.getDiceResult();
		for(int i = 0; i<=5; i++){
			Dice dice = new Dice();
				if (diceResult != null){
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
				}else{
			//Message das drei mal gewürfelt wurde
				}
		}
	gameModel.setDicePreview();
	gameModel.setPreview();
	lbLifePointsChangePlayer1.setText("" + gameModel.playerMe.getFutureLifePoints());
	lbEnergyPointsChangePlayer1.setText("" + gameModel.playerMe.getFutureEnergyPoints());
	lbFamePointsChangePlayer1.setText("" + gameModel.playerMe.getFutureHonorPoints());
	lbLifePointsChangePlayer2.setText("" + gameModel.playerTwo.getFutureLifePoints());
	if (gameModel.playerThree!=null){
		lbLifePointsChangePlayer3.setText("" + gameModel.playerThree.getFutureLifePoints());
	}
	if (gameModel.playerFour!=null){
		lbLifePointsChangePlayer4.setText("" + gameModel.playerFour.getFutureLifePoints());
	
	}else{
		
	}

	count++;

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
		tfChat.setText("");


	}
	
	/**
	 * Methode zieht eine neue zufällige Karte
	 * @author Marco
	 */
	@FXML 
	public void handleCardDeck(ActionEvent event) {
		try {
			Card newCard = new Card();
			gameModel.playerMe.setEnergyPoints(10);
		if (GameModel.cardList.size() == 0 && (gameModel.playerMe.getEnergyPoints() >= 3)){
			newCard = newCard.pullCard();
			ivCard1.setImage(newCard.getCardImage());
		} else {
			if (gameModel.playerMe.getEnergyPoints() >= 3) {
			newCard = newCard.pullCard();
			ivCard2.setImage(newCard.getCardImage());
			}
		}
			
			
			/**
			 * Die einzelnen Aktionen je nach Aktions-Typ der Karte
			 * @author Marco
			 */
			switch (newCard.getAction()){
			case attack:
				if (gameModel.playerMe.inTokyo){
					gameModel.attackFromTokyo(newCard.getEffect());
					lbLifePointsChangePlayer2.setText("" + gameModel.playerTwo.getFutureLifePoints());
					if (gameModel.playerThree!=null) {
					lbLifePointsChangePlayer3.setText("" + gameModel.playerThree.getFutureLifePoints());
					}
					if (gameModel.playerFour!=null) {
					lbLifePointsChangePlayer4.setText("" + gameModel.playerFour.getFutureLifePoints());
					}
					lbEnergyPointsChangePlayer1.setText("" + gameModel.playerMe.getFutureEnergyPoints());
				} else {
					gameModel.attackTokyo(gameModel.playerTwo, newCard.getEffect());
					if (gameModel.playerTwo.getFutureLifePoints() != 0){
						lbLifePointsChangePlayer2.setText("" + gameModel.playerTwo.getFutureLifePoints());
					}
					if ((gameModel.playerThree.getFutureLifePoints() != 0) && (gameModel.playerThree!=null)) {
						lbLifePointsChangePlayer3.setText("" + gameModel.playerThree.getFutureLifePoints());
					}
					if ((gameModel.playerFour.getFutureLifePoints() != 0) && (gameModel.playerFour!=null)) {
						lbLifePointsChangePlayer4.setText("" + gameModel.playerFour.getFutureLifePoints());
					}
					lbEnergyPointsChangePlayer1.setText("" + gameModel.playerMe.getFutureEnergyPoints());
				}
				break;
			case heal:
				gameModel.cardHeal(newCard.getEffect());
				gameModel.playerMe.setFutureLifePoints(gameModel.playerMe.getActualCardLifePoints() + 
						gameModel.playerMe.getActualDiceLifePoints());
				gameModel.playerMe.setFutureEnergyPoints(gameModel.playerMe.getActualCardEnergyPoints() + 
						gameModel.playerMe.getActualDiceEnergyPoints());
				lbLifePointsChangePlayer1.setText("" + gameModel.playerMe.getFutureLifePoints());
				lbEnergyPointsChangePlayer1.setText("" + gameModel.playerMe.getFutureEnergyPoints());
				break;
			case honor:
				gameModel.cardHonor(newCard.getEffect());
				gameModel.playerMe.setFutureHonorPoints(gameModel.playerMe.getActualCardHonorPoints() + 
						gameModel.playerMe.getActualDiceHonorPoints());
				gameModel.playerMe.setFutureEnergyPoints(gameModel.playerMe.getActualCardEnergyPoints() + 
						gameModel.playerMe.getActualDiceEnergyPoints());
				lbFamePointsChangePlayer1.setText("" + gameModel.playerMe.getFutureHonorPoints());
				lbEnergyPointsChangePlayer1.setText("" + gameModel.playerMe.getFutureEnergyPoints());
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
	
	/**
	 * @author Luzian
	 * @return
	 */
	public String getText(){
		String text = taChat.getText();
		return text;
	}
	
/**
 * @author Luzian
 * @param text
 */
	public void setText(String text){
		String chatverlauf = taChat.getText();
		taChat.setText(chatverlauf +System.lineSeparator() + text);
	}

	public static void getTest() {
		
	}
	
	private void checkSelectedButton() {
		for (int i = 0; i < 6; i++) {
			if (buttonList.get(i).isSelected()) {
				gameModel.setDiceSelected(i);
			}
		}

	}

}
