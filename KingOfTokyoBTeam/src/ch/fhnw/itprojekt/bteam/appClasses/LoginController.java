package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import ch.fhnw.itprojekt.bteam.abstractClasses.Controller;
import ch.fhnw.itprojekt.bteam.template.ServiceLocator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class LoginController implements Initializable {
		
	LoginModel loginModel = new LoginModel();
	
	@FXML
	TextField tfNickname;
	
	@FXML
	PasswordField pfPassword;
	
	@FXML
	Button btnRegistry;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	/**
	 * Button Action für im GUI. Es werden Nickname und Passwort ausgelesen und dem Model übergeben.
	 * @author Tobias
	 */
	@FXML
	public void handleLogin(ActionEvent event) {
		if(!loginModel.sendLogin(new User(tfNickname.getText(), pfPassword.getText()))){
			JOptionPane.showMessageDialog(null, "Nickname oder Passwort sind falsch!", "Falsches Login", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	/**
	 * Diese Methode öffnet das Registrations Fenster beim Klick auf den "Registrieren" Button
	 * und schliesst das Login Fenster
	 * @author Marco
	 * @param event
	 */
	@FXML
	public void handleRegistry(ActionEvent event) {      
		loginModel.startRegistry(new Stage());
		Node node= (Node)event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}
	
	/**
	 * Diese Methode öffnet das Passwort vergessen Fenster beim Klick auf den "Passwort vergessen" Link
	 * und schliesst das Login Fenster
	 * @author Marco
	 * @param event
	 */
	@FXML
	public void handleForgetPassword(ActionEvent event) {
		loginModel.startForgetPassword(new Stage());
		Node node = (Node)event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}		
}
