package ch.fhnw.itprojekt.bteam.appClasses;

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
			case Chat:
				Platform.runLater(new Runnable() {
	                @Override
	                public void run() {
	                	// entsprechende UI Komponente updaten
	                	GameController gamecontroller = GameController.getInstance();
	                	gamecontroller.taChat.setText(msgIn.getChat());
	                	JOptionPane.showMessageDialog(null, "Gratullation!"+ msgIn.getChat() +"" , "Gratullation", JOptionPane.WARNING_MESSAGE);
	                }
				});             	
				break;
			case SecurityQuestion:
				String returnvalue = msgIn.getSecurityQuestion();
				bundle = ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale());
				ForgetPasswordController forgetpasswordcontroller = ForgetPasswordController.getInstance();
				if(returnvalue != null){
					if(returnvalue.equals("Nicht alle Felder ausgefüllt")){
						forgetpasswordcontroller.missingUserDataForPassword();
					}else{
						Platform.runLater(new Runnable(){
							@Override
							public void run(){
								forgetpasswordcontroller.setlbSecureQuestion(returnvalue);
							}
						});
					}
				}else{
						JOptionPane.showMessageDialog(null, FXCollections.observableArrayList(bundle.getString("forget.wrongUserData")));
				}
				
				break;
			case SecurityAnswer:	
				String returnvalueAnswer = msgIn.getSecurityAnswer();
				ForgetPasswordController forgetpasswordcontrollerAnswer = ForgetPasswordController.getInstance();
				LoginModel loginmodel = LoginModel.getInstance();
				String useranswer = forgetpasswordcontrollerAnswer.tfAnswer.getText();
				bundle = ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale());
				if (returnvalueAnswer != null){
					if (useranswer.equals(returnvalueAnswer)){
						// Antwort stimmt
						User user = forgetpasswordcontrollerAnswer.getUser();
						loginmodel.getPassword(user);							
					}else{
						JOptionPane.showMessageDialog(null, FXCollections.observableArrayList(bundle.getString("forget.wrongAnswer")));
					}	
				}else{
					JOptionPane.showMessageDialog(null, FXCollections.observableArrayList(bundle.getString("forget.noAnswerFound")));
				}
				break;
			case Password:
				String returnpassword = msgIn.getPassword();
				bundle = ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale());
				ForgetPasswordController forgetpasswordcontrollerpassword = ForgetPasswordController.getInstance();
				if (returnpassword != null){
					Platform.runLater(new Runnable(){
						@Override
						public void run(){
							forgetpasswordcontrollerpassword.setlbYourPassword(returnpassword);
						}
					});
				}else{
					JOptionPane.showMessageDialog(null, FXCollections.observableArrayList(bundle.getString("forget.noAnswerFound")));
				}	
				break;
		default:
		
		}
	}
}
