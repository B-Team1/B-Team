package ch.fhnw.itprojekt.bteam.appClasses;

import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.stage.Stage;

import javax.swing.JOptionPane;

import ch.fhnw.itprojekt.bteam.template.Properties;

/**
 * Diese Klasse handelt den Input, der der Client vom Server erh�lt
 * @author Tobias
 *
 */
public class ServerInputHandler {
	/**
	 * In dieser Methode wird der Input vom Server verarbeitet
	 * Im Switch case wird dann je nach Messagetyp eine Aktion ausgef�hrt.
	 * @author Tobias	
	 * @param msgIn vom Server
	 */
	public void manageInput(Message msgIn){
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
					ResourceBundle bundle = ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale());
					JOptionPane.showMessageDialog(null,FXCollections.observableArrayList(bundle.getString("login.wrongInput")), "Login", JOptionPane.WARNING_MESSAGE);
				}
				break;
			case Registration:
				//Tobias
				ResourceBundle bundle = ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale());
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
