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
import javafx.stage.WindowEvent;

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
		gameModel.players.add(gameModel.playerMe);
		gameModel.players.add(gameModel.playerTwo);
//		if (gameModel.playerList.size() > 2) {
		gameModel.players.add(gameModel.playerThree);
//		}
//		if (gameModel.playerList.size() > 3) {
			gameModel.players.add(gameModel.playerFour);
//		}

		if (gameModel.players.size() < 3) {
			vbPlayer3.setVisible(false);
		}
		if (gameModel.players.size() < 4) {
			vbPlayer4.setVisible(false);
		}
		lbLifePointsPlayer1.setText("" + gameModel.players.get(0).getLifePoints());
		lbFamePointsPlayer1.setText("" + gameModel.players.get(0).getHonorPoints());
		lbEnergyPointsPlayer1.setText("" + gameModel.players.get(0).getEnergyPoints());
		lbLifePointsPlayer2.setText("" + gameModel.players.get(1).getLifePoints());
		lbFamePointsPlayer2.setText("" + gameModel.players.get(1).getHonorPoints());
		lbEnergyPointsPlayer2.setText("" + gameModel.players.get(1).getEnergyPoints());
		if (gameModel.players.size() > 2) {
			lbLifePointsPlayer3.setText("" + gameModel.players.get(2).getLifePoints());
			lbFamePointsPlayer3.setText("" + gameModel.players.get(2).getHonorPoints());
			lbEnergyPointsPlayer3.setText("" + gameModel.players.get(2).getEnergyPoints());
		}
		if (gameModel.players.size() > 3) {
			lbLifePointsPlayer4.setText("" + gameModel.players.get(3).getLifePoints());
			lbFamePointsPlayer4.setText("" + gameModel.players.get(3).getHonorPoints());
			lbEnergyPointsPlayer4.setText("" + gameModel.players.get(3).getEnergyPoints());
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
	lbLifePointsChangePlayer1.setText("" + gameModel.players.get(0).getFutureLifePoints());
	lbEnergyPointsChangePlayer1.setText("" + gameModel.players.get(0).getFutureEnergyPoints());
	lbFamePointsChangePlayer1.setText("" + gameModel.players.get(0).getFutureHonorPoints());
	lbLifePointsChangePlayer2.setText("" + gameModel.players.get(1).getFutureLifePoints());
	if (gameModel.players.size() > 2){
		lbLifePointsChangePlayer3.setText("" + gameModel.players.get(2).getFutureLifePoints());
	}
	if (gameModel.players.size() > 3){
		lbLifePointsChangePlayer4.setText("" + gameModel.players.get(3).getFutureLifePoints());
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
		//gameModel.sendGameStats();
//		lbLifePointsPlayer1.setText("" + gameModel.players.get(0).getLifePoints());
//		lbLifePointsChangePlayer1.setText("");
//		lbFamePointsPlayer1.setText("" + gameModel.players.get(0).getHonorPoints());
//		lbFamePointsChangePlayer1.setText("");
//		lbEnergyPointsPlayer1.setText("" + gameModel.players.get(0).getEnergyPoints());
//		lbEnergyPointsChangePlayer1.setText("");
//		lbLifePointsPlayer2.setText("" + gameModel.players.get(1).getLifePoints());
//		lbLifePointsChangePlayer2.setText("");
//		if (gameModel.players.size() > 2) {
//		lbLifePointsPlayer3.setText("" + gameModel.players.get(2).getLifePoints());
//		lbLifePointsChangePlayer3.setText("");
//		}
//		if (gameModel.players.size() > 3) {
//		lbLifePointsPlayer4.setText("" + gameModel.players.get(3).getLifePoints());
//		lbLifePointsChangePlayer4.setText("");
//		}
		if (gameModel.isGoToTokyo()) {
			ivMonsterInTokyoPlayerMe.setVisible(true);
		}
		if (gameModel.checkWinner()) {
			Node node= (Node)event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.close();
		}
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
			gameModel.players.get(0).setEnergyPoints(10);
		if (GameModel.cardList.size() == 0 && (gameModel.players.get(0).getEnergyPoints() >= GameModel.cardCost)){
			newCard = newCard.pullCard();
			ivCard1.setImage(newCard.getCardImage());
			cardAction(newCard);
		} else {
			if (gameModel.players.get(0).getEnergyPoints() >= GameModel.cardCost) {
			newCard = newCard.pullCard();
			ivCard2.setImage(newCard.getCardImage());
			cardAction(newCard);
			}
		}
	} catch (NullPointerException exception){
	}
	}
			
			/**
			 * Die einzelnen Aktionen je nach Aktions-Typ der Karte
			 * @author Marco
			 */
	private void cardAction(Card newCard) {
		switch (newCard.getAction()){
			case attack:
				if (gameModel.players.get(0).inTokyo){
					gameModel.attackFromTokyo(newCard.getEffect());
					lbLifePointsChangePlayer2.setText("" + gameModel.players.get(1).getFutureLifePoints());
					if (gameModel.players.size() > 2) {
						lbLifePointsChangePlayer3.setText("" + gameModel.players.get(2).getFutureLifePoints());
					}
					if (gameModel.players.size() > 3) {
						lbLifePointsChangePlayer4.setText("" + gameModel.players.get(3).getFutureLifePoints());
					}
					lbEnergyPointsChangePlayer1.setText("" + gameModel.players.get(0).getFutureEnergyPoints());
				} else {
					gameModel.attackTokyo(gameModel.players.get(1), newCard.getEffect());
					if (gameModel.players.get(1).getFutureLifePoints() != 0){
						lbLifePointsChangePlayer2.setText("" + gameModel.players.get(1).getFutureLifePoints());
					}
					if ((gameModel.players.get(2).getFutureLifePoints() != 0) && (gameModel.players.size() > 2)) {
						lbLifePointsChangePlayer3.setText("" + gameModel.players.get(2).getFutureLifePoints());
					}
					if ((gameModel.players.get(3).getFutureLifePoints() != 0) && (gameModel.players.size() > 3)) {
						lbLifePointsChangePlayer4.setText("" + gameModel.players.get(3).getFutureLifePoints());
					}
					lbEnergyPointsChangePlayer1.setText("" + gameModel.players.get(0).getFutureEnergyPoints());
				}
				break;
			case heal:
				gameModel.cardHeal(newCard.getEffect());
				gameModel.players.get(0).setFutureLifePoints(gameModel.players.get(0).getActualCardLifePoints() + 
						gameModel.players.get(0).getActualDiceLifePoints());
				gameModel.players.get(0).setFutureEnergyPoints(gameModel.players.get(0).getActualCardEnergyPoints() + 
						gameModel.players.get(0).getActualDiceEnergyPoints());
				lbLifePointsChangePlayer1.setText("" + gameModel.players.get(0).getFutureLifePoints());
				lbEnergyPointsChangePlayer1.setText("" + gameModel.players.get(0).getFutureEnergyPoints());
				break;
			case honor:
				gameModel.cardHonor(newCard.getEffect());
				gameModel.players.get(0).setFutureHonorPoints(gameModel.players.get(0).getActualCardHonorPoints() + 
						gameModel.players.get(0).getActualDiceHonorPoints());
				gameModel.players.get(0).setFutureEnergyPoints(gameModel.players.get(0).getActualCardEnergyPoints() + 
						gameModel.players.get(0).getActualDiceEnergyPoints());
				lbFamePointsChangePlayer1.setText("" + gameModel.players.get(0).getFutureHonorPoints());
				lbEnergyPointsChangePlayer1.setText("" + gameModel.players.get(0).getFutureEnergyPoints());
				break;
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
	
	public void setTokyoImage(Player player) {
		
	}

	public void updateLabels() {
		lbLifePointsPlayer1.setText("" + gameModel.players.get(0).getLifePoints());
		lbLifePointsChangePlayer1.setText("");
		lbFamePointsPlayer1.setText("" + gameModel.players.get(0).getHonorPoints());
		lbFamePointsChangePlayer1.setText("");
		lbEnergyPointsPlayer1.setText("" + gameModel.players.get(0).getEnergyPoints());
		lbEnergyPointsChangePlayer1.setText("");
		lbLifePointsPlayer2.setText("" + gameModel.players.get(1).getLifePoints());
		lbLifePointsChangePlayer2.setText("");
		lbFamePointsPlayer2.setText("" + gameModel.players.get(1).getHonorPoints());
		lbFamePointsChangePlayer2.setText("");
		lbEnergyPointsPlayer2.setText("" + gameModel.players.get(1).getEnergyPoints());
		lbEnergyPointsChangePlayer2.setText("");
		if (gameModel.players.size() > 2) {
		lbLifePointsPlayer3.setText("" + gameModel.players.get(2).getLifePoints());
		lbLifePointsChangePlayer3.setText("");
		lbFamePointsPlayer3.setText("" + gameModel.players.get(2).getHonorPoints());
		lbFamePointsChangePlayer3.setText("");
		lbEnergyPointsPlayer3.setText("" + gameModel.players.get(2).getEnergyPoints());
		lbEnergyPointsChangePlayer3.setText("");
		}
		if (gameModel.players.size() > 3) {
		lbLifePointsPlayer4.setText("" + gameModel.players.get(3).getLifePoints());
		lbLifePointsChangePlayer4.setText("");
		lbFamePointsPlayer4.setText("" + gameModel.players.get(3).getHonorPoints());
		lbFamePointsChangePlayer4.setText("");
		lbEnergyPointsPlayer4.setText("" + gameModel.players.get(3).getEnergyPoints());
		lbEnergyPointsChangePlayer4.setText("");
		}
	}
	
	private void checkSelectedButton() {
		for (int i = 0; i < 6; i++) {
			gameModel.setDiceSelected(i, buttonList.get(i).isSelected());
		}

	}
	
}
