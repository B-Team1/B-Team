package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import ch.fhnw.itprojekt.bteam.template.Properties;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
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
	@FXML Button btnEndMove;
	@FXML Button btnNext;
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
	@FXML ImageView ivMonster1;
	@FXML ImageView ivMonster2;
	@FXML ImageView ivMonster3;
	@FXML ImageView ivMonster4;
	@FXML HBox hbTokyoMe;
	@FXML VBox vbPlayer1;
	@FXML VBox vbPlayer2;
	@FXML VBox vbPlayer3;
	@FXML VBox vbPlayer4;
	
	ArrayList<ToggleButton> buttonList = new ArrayList<ToggleButton>();
	ArrayList<Label> lbLifePoints = new ArrayList<Label>();
	ArrayList<Label> lbEnergyPoints = new ArrayList<Label>();
	ArrayList<Label> lbHonorPoints = new ArrayList<Label>();
	ArrayList<Label> lbLifePointsChanges = new ArrayList<Label>();
	ArrayList<Label> lbEnergyPointsChanges = new ArrayList<Label>();
	ArrayList<Label> lbHonorPointsChanges = new ArrayList<Label>();
	ArrayList<Label> lbNickname = new ArrayList<Label>();
	ArrayList<VBox> vbPlayers = new ArrayList<VBox>();
	ArrayList<ImageView> monsters = new ArrayList<ImageView>();
	GameModel gameModel;
	ResourceBundle bundle = ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale());

	public static Stage gameStage = new Stage();
	private static GameController singleton;

	/**
	 * Initialisiert die Komponenten und füllt die Labels und ArrayLists und zeigt die Anzahl
	 * Mitspier an
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		singleton = this;
		gameModel = GameModel.getInstance();
		gameModel.askPlayer();
		taChat.setEditable(false);
		buttonList.add(btnDice1);
		buttonList.add(btnDice2);
		buttonList.add(btnDice3);
		buttonList.add(btnDice4);
		buttonList.add(btnDice5);
		buttonList.add(btnDice6);
		lbLifePoints.add(lbLifePointsPlayer1);
		lbLifePoints.add(lbLifePointsPlayer2);
		lbLifePoints.add(lbLifePointsPlayer3);
		lbLifePoints.add(lbLifePointsPlayer4);
		lbEnergyPoints.add(lbEnergyPointsPlayer1);
		lbEnergyPoints.add(lbEnergyPointsPlayer2);
		lbEnergyPoints.add(lbEnergyPointsPlayer3);
		lbEnergyPoints.add(lbEnergyPointsPlayer4);
		lbHonorPoints.add(lbFamePointsPlayer1);
		lbHonorPoints.add(lbFamePointsPlayer2);
		lbHonorPoints.add(lbFamePointsPlayer3);
		lbHonorPoints.add(lbFamePointsPlayer4);
		lbLifePointsChanges.add(lbLifePointsChangePlayer1);
		lbLifePointsChanges.add(lbLifePointsChangePlayer2);
		lbLifePointsChanges.add(lbLifePointsChangePlayer3);
		lbLifePointsChanges.add(lbLifePointsChangePlayer4);
		lbEnergyPointsChanges.add(lbEnergyPointsChangePlayer1);
		lbEnergyPointsChanges.add(lbEnergyPointsChangePlayer2);
		lbEnergyPointsChanges.add(lbEnergyPointsChangePlayer3);
		lbEnergyPointsChanges.add(lbEnergyPointsChangePlayer4);
		lbHonorPointsChanges.add(lbFamePointsChangePlayer1);
		lbHonorPointsChanges.add(lbFamePointsChangePlayer2);
		lbHonorPointsChanges.add(lbFamePointsChangePlayer3);
		lbHonorPointsChanges.add(lbFamePointsChangePlayer4);
		lbNickname.add(lbNicknamePlayer1);
		lbNickname.add(lbNicknamePlayer2);
		lbNickname.add(lbNicknamePlayer3);
		lbNickname.add(lbNicknamePlayer4);
		monsters.add(ivMonster1);
		monsters.add(ivMonster2);
		monsters.add(ivMonster3);
		monsters.add(ivMonster4);
		vbPlayers.add(vbPlayer1);
		vbPlayers.add(vbPlayer2);
		vbPlayers.add(vbPlayer3);
		vbPlayers.add(vbPlayer4);

		if (gameModel.players.size() < 3) {
			vbPlayer3.setVisible(false);
		}
		if (gameModel.players.size() < 4) {
			vbPlayer4.setVisible(false);
		}
		for (int i = 0; i < gameModel.players.size(); i++) {
			for (int j = 0; j < lbLifePoints.size(); j++) {
				lbLifePoints.get(j).setText("" + gameModel.players.get(i).getLifePoints());
				lbEnergyPoints.get(j).setText("" + gameModel.players.get(i).getEnergyPoints());
				lbHonorPoints.get(j).setText("" + gameModel.players.get(i).getHonorPoints());
			}
		}
		for (int i = 0; i < gameModel.players.size(); i++) {
			lbNickname.get(i).setText("" + gameModel.players.get(i).getNickName());
		}
		
		disableGameBtns();
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
		}
	}
	gameModel.setDicePreview();
	gameModel.setPreview();
	for (int i = 0; i < gameModel.players.size(); i++) {
		lbEnergyPointsChanges.get(i).setText("" + gameModel.players.get(i).getFutureEnergyPoints());
		lbHonorPointsChanges.get(i).setText("" + gameModel.players.get(i).getFutureHonorPoints());
	}
	for (int i = 0; i < gameModel.players.size(); i++) {
		lbLifePointsChanges.get(i).setText("" + gameModel.players.get(i).getFutureLifePoints());
	}
	}else{
		JOptionPane.showMessageDialog(null, FXCollections.observableArrayList(bundle.getString("dice.threetimes")));
	}

	}
	/**
	 * Methode um den Spielzug zu beenden und die Ergebnisse ausführen
	 * @author Marco
	 */
	@FXML
	public void handleEndMove(ActionEvent event) {
		if (gameModel.count > 0) {
			btnCardDeck.setDisable(true);
			btnEndMove.setDisable(true);
			btnRollDice.setDisable(true);
			btnNext.setDisable(false);
			resetButton();
			resetCards();
			gameModel.endMove();
			gameModel.sendGameStats();
		} else {
			JOptionPane.showMessageDialog(null, FXCollections.observableArrayList(bundle.getString("dice.one")));
		}
	}
	
	/**
	 * Setzt alle Button Werte zurück und zeigt kein Bild mehr an
	 * @author Marco
	 */
	private void resetButton() {
		ArrayList<Dice> diceResult = gameModel.getDiceResult();
		for(int i = 0; i <= 5; i++){
			if (diceResult != null){
				Dice dice = diceResult.get(i);
				switch (i) {
						case 0:
								btnDice1.setGraphic(new ImageView());
								btnDice1.setSelected(false);
							break;
						case 1:
								btnDice2.setGraphic(new ImageView());
								btnDice2.setSelected(false);
							break;
						case 2:
								btnDice3.setGraphic(new ImageView());
								btnDice3.setSelected(false);
							break;
						case 3:
								btnDice4.setGraphic(new ImageView());
								btnDice4.setSelected(false);
							break;
						case 4:
								btnDice5.setGraphic(new ImageView());
								btnDice5.setSelected(false);
							break;
						case 5:
								btnDice6.setGraphic(new ImageView());
								btnDice6.setSelected(false);
							break;
					} 
			}
		}
	}
	
	/**
	 * Setzt die Karten zurück, und zeigt kein Bild mehr an
	 * @author Marco
	 */
	private void resetCards() {
		GameModel.cardList.clear();
		ivCard1.setImage(null);
		ivCard2.setImage(null);
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
			if (GameModel.cardList.size() == 0) {
				if (gameModel.players.get(gameModel.myPosition).getEnergyPoints() >= GameModel.cardCost) {
					newCard = newCard.pullCard();
					ivCard1.setImage(newCard.getCardImage());
					cardAction(newCard);
				} else {
					JOptionPane.showMessageDialog(null, FXCollections.observableArrayList(bundle.getString("card.lessEnergy")));
				}
			} else {
				if (gameModel.players.get(gameModel.myPosition).getEnergyPoints() >= (GameModel.cardCost * 2)) {
				newCard = newCard.pullCard();
				ivCard2.setImage(newCard.getCardImage());
				cardAction(newCard);
				} else {
					JOptionPane.showMessageDialog(null, FXCollections.observableArrayList(bundle.getString("card.lessEnergy")));
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
				gameModel.cardAttack(newCard.getEffect());
				for (int i = 0; i < gameModel.players.size(); i++) {
					if (i != gameModel.myPosition) {
						lbLifePointsChanges.get(i).setText("" + gameModel.players.get(i).getFutureLifePoints());
					}
				}
				lbEnergyPointsChanges.get(gameModel.myPosition).setText("" + gameModel.players.get(gameModel.myPosition).getFutureEnergyPoints());
				break;
			case heal:
				gameModel.cardHeal(newCard.getEffect());
				for (int i = 0; i < gameModel.players.size(); i++) {
					lbLifePointsChanges.get(i).setText("" + gameModel.players.get(i).getFutureLifePoints());
					lbEnergyPointsChanges.get(i).setText("" + gameModel.players.get(i).getFutureEnergyPoints());
				}
				break;
			case honor:
				gameModel.cardHonor(newCard.getEffect());
				for (int i = 0; i < gameModel.players.size(); i++) {
					lbHonorPointsChanges.get(i).setText("" + gameModel.players.get(i).getFutureHonorPoints());
					lbEnergyPointsChanges.get(i).setText("" + gameModel.players.get(i).getFutureEnergyPoints());
				}
				break;
		}
	}

	public static GameController getInstance() {
//	     if(singleton == null) {	        
//	         singleton = new GameController();
//	      }	     
	      return singleton;
	}
	
	/**
	 * gibt den Text der Textarea zurück
	 * @author Luzian
	 * @return
	 */
	public String getText(){
		String text = taChat.getText();
		return text;
	}
	
/**
 * setzt den Texts der Textarea
 * @author Luzian
 * @param text
 */
	public void setText(String text){
		taChat.appendText( System.lineSeparator() + text);
	}
	
	public void setTokyoImage(Player player) {
		
	}

	/**
	 * Aktualisiert die Labels nach den aktuellen Punkten
	 * @author Marco
	 */
	public void updateLabels() {
		for (int i = 0; i < gameModel.players.size(); i++) {
			lbLifePoints.get(i).setText("" + gameModel.players.get(i).getLifePoints());
			lbLifePointsChanges.get(i).setText("");
			lbEnergyPoints.get(i).setText("" + gameModel.players.get(i).getEnergyPoints());
			lbEnergyPointsChanges.get(i).setText("");
			lbHonorPoints.get(i).setText("" + gameModel.players.get(i).getHonorPoints());
			lbHonorPointsChanges.get(i).setText("");
			if (gameModel.players.get(i).getLifePoints() <= 0) {
				vbPlayers.get(i).setVisible(false);
			}
		}
	}
	
	private void checkSelectedButton() {
		for (int i = 0; i < 6; i++) {
			gameModel.setDiceSelected(i, buttonList.get(i).isSelected());
		}
	}
	
	public void disableGameBtns(){
		btnCardDeck.setDisable(true);
		btnEndMove.setDisable(true);
		btnRollDice.setDisable(true);
		btnNext.setDisable(true);
	}
	
	
	public void enableGameBtns(){
		btnCardDeck.setDisable(false);
		btnEndMove.setDisable(false);
		btnRollDice.setDisable(false);
		btnNext.setDisable(true);
	}
	
	public void disableNextBtn(){
		btnNext.setDisable(true);
	}
	
	public void enableNextBtn(){
		btnNext.setDisable(false);
	}
	
	
	@FXML 
	public void handleNext(ActionEvent event) {
		gameModel.sendGameMove();
	}
	
}
