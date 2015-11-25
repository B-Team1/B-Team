package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import ch.fhnw.itprojekt.bteam.abstractClasses.Controller;
import ch.fhnw.itprojekt.bteam.template.ServiceLocator;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	 * Button Action f�r im GUI. Es werden Nickname und Passwort ausgelesen und dem Model �bergeben.
	 * @author Tobias
	 */
	@FXML
	public void handleLogin(ActionEvent event) {
		doLogin(event);
	}
	
	/**
	 * Diese Methode �ffnet das Registrations Fenster beim Klick auf den "Registrieren" Button
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
	 * Diese Methode �ffnet das Passwort vergessen Fenster beim Klick auf den "Passwort vergessen" Link
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
	
	/**
	 * Dieses Event wird aufgerufen, wenn im Login Fenster die Taste Enter gedr�ckt wird
	 * @author Tobias
	 */
	@FXML
	public void handleEnter(KeyEvent event){
	    if (event.getCode() == KeyCode.ENTER) {
	    	doLogin(event);
	    }
	}
	
	/**
	 * �bergibt die Eingaben zur Kontrolle dem Model. Sind die Eingaben richtig wird das Men� aufgerufen
	 * und das Loginfenster geschlossen. Sind die Eingaben falsch
	 * @param Hier kann das Event �bergeben werden, welcher schon beim Eventhandler der Parameter ist
	 * @author Tobias
	 */
	private void doLogin(Event event){
		if(loginModel.sendLogin(new User(tfNickname.getText(), pfPassword.getText()))){
			MenuModel menuModel = new MenuModel();
			menuModel.start(new Stage());
			Node node= (Node)event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.close();
		}else{
			JOptionPane.showMessageDialog(null, "Nickname oder Passwort sind falsch!", "Falsches Login", JOptionPane.WARNING_MESSAGE);
		}
	}
}
