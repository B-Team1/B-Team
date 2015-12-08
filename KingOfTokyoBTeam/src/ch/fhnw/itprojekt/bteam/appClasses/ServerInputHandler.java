package ch.fhnw.itprojekt.bteam.appClasses;

import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.stage.Stage;

import javax.swing.JOptionPane;

import ch.fhnw.itprojekt.bteam.template.Properties;

public class ServerInputHandler {
	public void manageInput(Message msg){
		switch (msg.getType()) {
		case Login:
			//Tobias
			if(msg.getCheckLogin()){
				MenuModel menuModel = MenuModel.getInstance();
				menuModel.setNickname(msg.getNickname());
				menuModel.start(new Stage());
				LoginController.closeStage();
			}else{
				ResourceBundle bundle = ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale());
				JOptionPane.showMessageDialog(null,FXCollections.observableArrayList(bundle.getString("card.twocards")), "Login", JOptionPane.WARNING_MESSAGE);
			}
			break;						
		default:
		
		}
	}
}
