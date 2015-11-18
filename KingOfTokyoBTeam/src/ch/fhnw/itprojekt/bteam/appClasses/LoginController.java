package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

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
		
	@FXML
	TextField tfNickname;
	PasswordField pfPassword;
	Button btnRegistry;
	
	public LoginController() {
		//model.connectToServer(); //Verbindung zum Server wird Hergestellt
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	
	@FXML
	public void handleLogin(ActionEvent event) {
		LoginModel model = new LoginModel();
		model.sendLogin(new User(tfNickname.getText(), pfPassword.getText()));
	}

	@FXML
	public void handleRegistry(ActionEvent event) {      
			LoginModel model = new LoginModel();
			model.startRegistry(new Stage());
			Node node= (Node)event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.close();
	}
	
	/**
	 * Diese Methode zeigt dem Benutzer an, dass er etwas falsch eingegeben hat.
	 * @author Tobias
	 */
	public void wrongLogin(){
		
	}
	
}