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
				synchronized (msgIn) {
					//Tobias
					ArrayList<String> nicknames = new ArrayList<String>(Arrays.asList(msgIn.getUserList()));
					int[] gameIdList = msgIn.getGameIdList();
					ArrayList<GameModel> gameModelList = new ArrayList<GameModel>();
					for(int i = 0; i < gameIdList.length; i ++){
						ArrayList<String> userList = new ArrayList<String>();
						for(int c = 0; c < nicknames.size(); c ++){
							
							if(nicknames.get(c).equals(gameIdList[i]+"") ){
								userList = new ArrayList<String>();
							}
							if(nicknames.get(c).equals("<EndArray>")){
								break;
							}
							
							userList.add(nicknames.get(c));
							
							
						}
						try{
							nicknames.removeAll(userList);
							nicknames.remove(0);
							userList.remove(0);
							gameModelList.add(new GameModel(gameIdList[i], userList));
						}catch(Exception e){}
					}
					MenuModel.getInstance().setGameModelList(gameModelList);
					
					Platform.runLater(new Runnable() {
		                @Override
		                public void run() {
		                	GameOverviewController.getInstance().fillTable(gameModelList);
		                }
		            });
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
			case Chat:
				Platform.runLater(new Runnable() {
	                @Override
	                public void run() {
	                	// entsprechende UI Komponente updaten
	                	GameController gamecontroller = GameController.getInstance();
	                	//String text = msgIn.getNickname() + " :"+ msgIn.getChat();
	                	gamecontroller.setText(msgIn.getChat());
	                	
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
			case Players:
				GameModel gameModel = GameModel.getInstance();
				ArrayList<String> playerList = new ArrayList<String>(Arrays.asList(msgIn.getPlayers()));
				gameModel.setPlayers(playerList);
				
		default:
		
		}
	}
}
