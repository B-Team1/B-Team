package ch.fhnw.itprojekt.bteam.appClasses;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;



public class Dice {
	private int value;
	public Image image;
	

	/**
	 * Setzt eine Radom Zahl als Würfelwert und setzt zu dem das passende Bild des Würfels und gibt ein Würfel Objekt zurück
	 * @author Luzian
	 * @return
	 */
public Dice roll(){
	int randomZahl = 0;
	Random rand = new Random();
	randomZahl = rand.nextInt(6)+1;
	this.value = randomZahl;
	switch (value) {
    	case 1:  value = 1;
    		 this.image = new Image(getClass().getResourceAsStream("../images/Würfel1.jpg"));
    		 
             break;
    	case 2:  value = 2;
    		 this.image = (new Image(getClass().getResourceAsStream("../images/Würfel2.jpg")));
    		 break;
    	case 3:  value = 3;
             this.image = (new Image(getClass().getResourceAsStream("../images/Würfel3.jpg")));
    		 break;
    	case 4:  value = 4;
    		 this.image = (new Image(getClass().getResourceAsStream("../images/Würfel4.jpg")));
	         break;
    	case 5:  value = 5;
    		 this.image = (new Image(getClass().getResourceAsStream("../images/Würfel5.jpg")));
    		 break;
    	case 6:  value = 6;
    		 this.image = (new Image(getClass().getResourceAsStream("../images/Würfel6.jpg")));
    		 break;
	 } 		
	return this;
}
	
}
