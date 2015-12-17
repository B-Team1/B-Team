package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;


import ch.fhnw.itprojekt.bteam.template.Properties;
import javafx.collections.FXCollections;
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
	private static ForgetPasswordController singleton;
	LoginModel loginModel;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loginModel = LoginModel.getInstance();
		singleton = this;
	}
	
	public static ForgetPasswordController getInstance() {
	     if(singleton == null) {	        
	         singleton = new ForgetPasswordController();
	      }	     
	      return singleton;
	}
	
	/**
	 * Methode um per Hyperlink zum Login-Fenster zu gelangen
	 * @author Marco
	 * @param event
	 */
	@FXML
	public void handleToLogin(ActionEvent event) {
		loginModel.start(new Stage());
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
		loginModel.sendUserDataForPassword(getUser());	
	}
	
	/**
	 * Diese Methode zeigt dem Benutzer an, dass er nicht alles Benutzerdaten eingegeben hat.
	 * @author Luzian
	 */
	public void missingUserDataForPassword(){
		ResourceBundle bundle = ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale());
		JOptionPane.showMessageDialog(null, FXCollections.observableArrayList(bundle.getString("forget.wrongUserData")));
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
		loginModel.getSecurityAnswer(getUser());
		
	}
	
	public User getUser(){
		User user = new User(tfNickname.getText(), tfName.getText(), tfFirstName.getText());
		return user;
	}

}
