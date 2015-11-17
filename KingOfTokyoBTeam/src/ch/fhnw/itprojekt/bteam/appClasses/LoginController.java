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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class LoginController implements Initializable {
	LoginModel model = new LoginModel();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	
	
	@FXML
	public void handleLogin(ActionEvent event) {
		model.sendLogin();
	}

	@FXML
	public void handleRegistry(ActionEvent event) {   
	        RegistrationModel registryModel = new RegistrationModel();
			registryModel.start(new Stage());
			model.sendLogin();
	}
	
}