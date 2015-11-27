package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ForgetPasswordController implements Initializable {
	
	@FXML TextField tfName;
	@FXML TextField tfFirstName;
	@FXML TextField tfNickname;
	@FXML TextField tfAnswer;
	@FXML Label lbSecureQuestion;
	@FXML Label lbYourPassword;
	@FXML Button btnEnter;
	
	//LoginModel loginModel;
	
	//public ForgetPasswordController(LoginModel loginModel){
	//	this.loginModel = loginModel;
	//}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	/**
	 * Methode um per Hyperlink zum Login-Fenster zu gelangen
	 * @author Marco
	 * @param event
	 */
	@FXML
	public void handleToLogin(ActionEvent event) {
		LoginModel model = new LoginModel();
		model.start(new Stage());
		Node node= (Node)event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}
	
	/**
	 * Erstellt ein LoginModel und liest die Benutzereingaben aus und gibt den Wert der SecurityQuestion zurück
	 * @author Luzian
	 * @param event
	 */
	@FXML
	public void handleEnter(ActionEvent event) {
		LoginModel model = new LoginModel();
		String returnvalue;
		returnvalue = model.sendUserDataForPassword(new User(tfNickname.getText(), tfName.getText(), tfFirstName.getText()));
		if(returnvalue.equals("Nicht alle Felder ausgefüllt")){
			wrongUserDataForPassword();
		}else{
			setlbSecureQuestion(returnvalue);
			}		
	}
	
	/**
	 * Diese Methode zeigt dem Benutzer an, dass er etwas falsch eingegeben hat.
	 * @author Luzian
	 */
	public void wrongUserDataForPassword(){
		JOptionPane.showMessageDialog(null, "Bitte füllen Sie alle Felder korrekt aus");
	}
	
	public void setlbSecureQuestion(String securityQuestion){
		lbSecureQuestion.setText(securityQuestion);
	}
	/**
	 * 
	 * @param event
	 */
	@FXML
	public void handleConfirm(ActionEvent event) {
		//LoginModel model = new LoginModel();
		//String returnvalue;
		//returnvalue = model.sendUserDataForPassword(new User(tfNickname.getText(), tfName.getText(), tfFirstName.getText()));
		
	}

}
