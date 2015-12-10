package ch.fhnw.itprojekt.bteam.appClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.stage.Stage;

import javax.swing.JOptionPane;




import ch.fhnw.itprojekt.bteam.template.Properties;

/**
 * Diese Klasse handelt den Input, der der Client vom Server erhält
 * @author Tobias
 *
 */
public class ServerInputHandler {
	/**
	 * In dieser Methode wird der Input vom Server verarbeitet
	 * Im Switch case wird dann je nach Messagetyp eine Aktion ausgeführt.
	 * @author Tobias	
	 * @param msgIn vom Server
	 */
	public void manageInput(Message msgIn){
		ResourceBundle bundle = ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale());
		switch (msgIn.getType()) {
			case Login:
				//Tobias
				if(msgIn.getCheckLogin()){
					Platform.runLater(new Runnable() {
		                @Override
		                public void run() {
		                    // entsprechende UI Komponente updaten
		                	MenuModel menuModel = MenuModel.getInstance();
		    				menuModel.setNickname(msgIn.getNickname());
		    				menuModel.start(new Stage());
		    				LoginController.closeStage();
		                }
		            });			
				}else{					
					JOptionPane.showMessageDialog(null,FXCollections.observableArrayList(bundle.getString("login.wrongInput")), "Login", JOptionPane.WARNING_MESSAGE);
				}
				break;
			case OpenGameRequest:
				//Tobias
				int[] freePlaces = msgIn.getfreePlaces();
				String[][] nicknames = new String[][]{};
				int[] gameIdList = new int[]{};
				ArrayList<GameModel> gameModelList = new ArrayList<GameModel>();
				for(int i = 0; i < gameIdList.length; i ++){
					for(int c = 0; i < nicknames.length; c ++){
						if(nicknames[c][0].equals(i+"") ){
							ArrayList<String> userList = new ArrayList<String>(Arrays.asList(nicknames[c]));
							userList.remove(0);
							
							
						}
					}
				}
				break;
			case openNewGame:
				//Tobias
				int gameId = msgIn.getGameId();
				
				//Gibt eine Fehlermeldung, wenn das Spiel nicht richtig erstellt wurde
				if(gameId > 0){
					new GameModel(gameId);
					GameModel gameModel = GameModel.getInstance();
					Platform.runLater(new Runnable() {
		                @Override
		                public void run() {
		                	gameModel.startCreateGame(new Stage());
		                }
		            });
				}else{					
					JOptionPane.showMessageDialog(null, FXCollections.observableArrayList(bundle.getString("newGame.error"), JOptionPane.WARNING_MESSAGE));
				}
				break;
			case Registration:
				//Tobias
				if(msgIn.getWriteCheck()){
					JOptionPane.showMessageDialog(null, FXCollections.observableArrayList(bundle.getString("registry.success")), "Registration", JOptionPane.PLAIN_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, FXCollections.observableArrayList(bundle.getString("registry.userError")), "Registration", JOptionPane.WARNING_MESSAGE);
				}
				break;
		default:
		
		}
	}
}
