package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.crypto.spec.IvParameterSpec;
import javax.swing.text.html.HTMLDocument.Iterator;


//import ch.fhnw.itprojekt.bteam.server.ConnectionModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
	@FXML ImageView ivMonsterInTokyoPlayerMe;
	@FXML ImageView ivMonsterInTokyoPlayerTwo;
	@FXML ImageView ivMonsterInTokyoPlayerThree;
	@FXML ImageView ivMonsterInTokyoPlayerFour;
	@FXML HBox hbTokyoMe;
	@FXML VBox vbPlayer3;
	@FXML VBox vbPlayer4;
	
	ArrayList<ToggleButton> buttonList = new ArrayList<ToggleButton>();
	GameModel gameModel;

	private static GameController singleton;

	/**
	 * Initialisiert die Komponenten und füllt die Labels
	 */
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

		if (gameModel.playerThree == null) {
			vbPlayer3.setVisible(false);
		}
		if (gameModel.playerFour == null) {
			vbPlayer4.setVisible(false);
		}
		lbLifePointsPlayer1.setText("" + gameModel.playerMe.getLifePoints());
		lbFamePointsPlayer1.setText("" + gameModel.playerMe.getHonorPoints());
		lbEnergyPointsPlayer1.setText("" + gameModel.playerMe.getEnergyPoints());
		lbLifePointsPlayer2.setText("" + gameModel.playerTwo.getLifePoints());
		lbFamePointsPlayer2.setText("" + gameModel.playerTwo.getHonorPoints());
		lbEnergyPointsPlayer2.setText("" + gameModel.playerTwo.getEnergyPoints());
		if (gameModel.playerThree!=null) {
			lbLifePointsPlayer3.setText("" + gameModel.playerThree.getLifePoints());
			lbFamePointsPlayer3.setText("" + gameModel.playerThree.getHonorPoints());
			lbEnergyPointsPlayer3.setText("" + gameModel.playerThree.getEnergyPoints());
		}
		if (gameModel.playerFour!=null) {
			lbLifePointsPlayer4.setText("" + gameModel.playerFour.getLifePoints());
			lbFamePointsPlayer4.setText("" + gameModel.playerFour.getHonorPoints());
			lbEnergyPointsPlayer4.setText("" + gameModel.playerFour.getEnergyPoints());
		}
	}

	/**Bei Klick auf Würfeln wird gameModel aufgerufen, welche das Würfelresultat zurückgibt. Zudem wird hier das Wüfelbild geladen.
	 * Methode würfelt die Würfel
	 * @author Marco / Luzian
	 */
	@FXML

	public void handleRollDice(ActionEvent event) {
	if (gameModel.count <= 2){
	checkSelectedButton();	
	ArrayList<Dice> diceResult = gameModel.getDiceResult();
	for(int i = 0; i <= 5; i++){
		if (diceResult != null){
			Dice dice = diceResult.get(i);
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
	}
	}else{
		
	}

	}
	/**
	 * Methode um den Spielzug zu beenden und die Ergebnisse ausführen
	 * @author Marco
	 */
	@FXML
	public void handleEndMove(ActionEvent event) {
		gameModel.endMove();
		lbLifePointsPlayer1.setText("" + gameModel.playerMe.getLifePoints());
		lbLifePointsChangePlayer1.setText("");
		lbFamePointsPlayer1.setText("" + gameModel.playerMe.getHonorPoints());
		lbFamePointsChangePlayer1.setText("");
		lbEnergyPointsPlayer1.setText("" + gameModel.playerMe.getEnergyPoints());
		lbEnergyPointsChangePlayer1.setText("");
		lbLifePointsPlayer2.setText("" + gameModel.playerTwo.getLifePoints());
		lbLifePointsChangePlayer2.setText("");
		if (gameModel.playerThree!=null) {
		lbLifePointsPlayer3.setText("" + gameModel.playerThree.getLifePoints());
		lbLifePointsChangePlayer3.setText("");
		}
		if (gameModel.playerFour!=null) {
		lbLifePointsPlayer4.setText("" + gameModel.playerFour.getLifePoints());
		lbLifePointsChangePlayer4.setText("");
		}
		if (gameModel.isGoToTokyo()) {
			ivMonsterInTokyoPlayerMe.setVisible(true);
		}
		// Resultate Server senden und nächster Spieler
		/*if (gameModel.win == true) {
			Node node= (Node)event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.close();
			gameModel.startWinner(new Stage());
		}*/
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

//	public static void handleCloseRequest(ActionEvent event) {
//		Node node= (Node)event.getSource();
//		Stage stage = (Stage) node.getScene().getWindow();
//		stage.close();
//	}
	
	
	private void checkSelectedButton() {
		for (int i = 0; i < 6; i++) {
			gameModel.setDiceSelected(i, buttonList.get(i).isSelected());
		}

	}
	
}
