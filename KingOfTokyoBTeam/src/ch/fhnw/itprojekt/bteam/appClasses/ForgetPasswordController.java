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
	
	@FXML
	TextField tfName;
	@FXML
	TextField tfFirstName;
	@FXML
	TextField tfNickname;
	@FXML
	TextField tfAnswer;
	@FXML
	Label lbSecureQuestion;
	@FXML
	Label lbYourPassword;
	@FXML
	Button btnEnter;
	
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
		returnvalue = model.sendUserDataForPassword(getUser());
		if(returnvalue != null){
			if(returnvalue.equals("Nicht alle Felder ausgefüllt")){
				missingUserDataForPassword();
			}else{
				setlbSecureQuestion(returnvalue);
			}
		}else{
			JOptionPane.showMessageDialog(null, "Ihre Benutzerdaten sind nicht korrekt");
		}
		
	}
	
	/**
	 * Diese Methode zeigt dem Benutzer an, dass er nicht alles Benutzerdaten eingegeben hat.
	 * @author Luzian
	 */
	public void missingUserDataForPassword(){
		JOptionPane.showMessageDialog(null, "Bitte füllen Sie alle Felder korrekt aus");
	}
	
	/**
	 * Schreibt die entsprechende SecurityQuestion ins Label 
	 * @author Luzian
	 * @param securityQuestion
	 */
	public void setlbSecureQuestion(String securityQuestion){
		lbSecureQuestion.setText(securityQuestion);
	}
	
	/**
	 * Schreibt das entsprechende Passwort in das PasswordLabel
	 * @author Luzian
	 * @param securityQuestion
	 */
	public void setlbYourPassword(String passwort){
		lbYourPassword.setText(passwort);
	}
	
	/**
	 * Holt Security Answer von der DB und vergleicht diese mit der Benutzerantwort.
	 * @author Luzian
	 * @param event
	 */
	@FXML
	public void handleConfirm(ActionEvent event) {
		LoginModel model = new LoginModel();
		String returnvalue = model.getSecurityAnswer(getUser());
		String useranswer = tfAnswer.getText();
		if (returnvalue != null){
			if (useranswer.equals(returnvalue)){
				// Antwort stimmt
				String password = model.getPassword(getUser());
					if (password != null){
						setlbYourPassword(password);
					}else{
						JOptionPane.showMessageDialog(null, "Es wurde kein Passwort zu Ihrem Benutzer gefunden");
					}		
			}else{
				JOptionPane.showMessageDialog(null, "Die Antwort ist fehlerhaft");
			}	
		}else{
			JOptionPane.showMessageDialog(null, "Es wurde keine Antwort zu Ihrem Benutzer gefunden");
		}
		
		
	}
	
	public User getUser(){
		User user = new User(tfNickname.getText(), tfName.getText(), tfFirstName.getText());
		return user;
	}

}
