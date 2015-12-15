package ch.fhnw.itprojekt.bteam.appClasses;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import ch.fhnw.itprojekt.bteam.template.Properties;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;

public class Card {

	int cost, effect;
	Image cardImage;
	
	/**
	 * Enum definiert die einzelnen Typen der Auswirkungen von der Karte
	 * @author Marco
	 */
	public enum CardType {attack, heal, honor}
	CardType action;
	
	/**
	 * Leerer Konstruktor um ihn dann zu füllen
	 * @author Marco
	 */
	public Card() {
	}
	
	/**
	 * Konstruktor um ein Objekt Karte zu erstellen mit den Eigenschaften
	 * @author Marco
	 */
	public Card(CardType action, int cost, Image cardImage, int effect){
		this.cost = cost;
		this.cardImage = cardImage;
		this.action = action;
		this.effect = effect;
	}
	
	/**
	 * Methode um eine neue Karte zu ziehen
	 * @author Marco
	 */
	public Card pullCard() {
		ResourceBundle bundle = ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale());
		if (GameModel.cardList.size() < 2) {
			Card newCard = evaluateCard();
			return newCard;
		} else {
			JOptionPane.showMessageDialog(null, FXCollections.observableArrayList(bundle.getString("card.twocards")));
			return null;
		}
	}
	
	/**
	 * Methode wählt zufällig aus, welche Karte gezogen wird und gibt ein Objekt zurück
	 * @author Marco
	 */
	public Card evaluateCard() {
		double newcard = (Math.random() * 5);
		if (newcard < 1) {
			int cost = 3;
			int effect = 2;
			Image image = new Image(getClass().getResourceAsStream("../images/Card_Heilung.jpg"));
			action = CardType.heal;
			Card card = new Card(action, cost, image, effect);
			GameModel.cardList.add(card);
			return card;
		} else {
			if ((newcard >= 1) && (newcard < 2)){
				int cost = 3;
				int effect = 2;
				Image image = new Image(getClass().getResourceAsStream("../images/Card_Feuerstrahl.jpg"));
				action = CardType.attack;
				Card card = new Card(action, cost, image, effect);
				GameModel.cardList.add(card);
				return card;
		} else {
			if ((newcard >= 2) && (newcard <3.5)) {
				int cost = 3;
				int effect = 1;
				Image image = new Image(getClass().getResourceAsStream("../images/Card_Eckkneipe.jpg"));
				action = CardType.honor;
				Card card = new Card(action, cost, image, effect);
				GameModel.cardList.add(card);
				return card;
		} else {
			if ((newcard >= 3.5) && (newcard <4.5)){
				int cost = 3;
				int effect = 2;
				Image image = new Image(getClass().getResourceAsStream("../images/Card_Hochbahn.jpg"));
				action = CardType.honor;
				Card card = new Card(action, cost, image, effect);
				GameModel.cardList.add(card);
				return card;
		} else {
			int cost = 3;
			int effect = 3;
			Image image = new Image(getClass().getResourceAsStream("../images/Card_Wohnblock.jpg"));
			action = CardType.honor;
			Card card = new Card(action, cost, image, effect);
			GameModel.cardList.add(card);
			return card;
		}
		}
		}
		}
	}
	
	public int getCost() {
		return cost;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getEffect() {
		return effect;
	}

	public void setEffect(int effect) {
		this.effect = effect;
	}

	public Image getCardImage() {
		return cardImage;
	}

	public void setCardImage(Image cardImage) {
		this.cardImage = cardImage;
	}

	public CardType getAction() {
		return action;
	}

	public void setAction(CardType action) {
		this.action = action;
	}
}
