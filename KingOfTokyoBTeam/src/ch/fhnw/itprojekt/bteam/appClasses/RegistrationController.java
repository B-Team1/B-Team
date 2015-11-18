package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class RegistrationController implements Initializable {
	LoginModel model = new LoginModel();

	@FXML
	TextField tfName;
	TextField tfFirstName;
	TextField tfNickname;
	TextField tfSecureQuestion;
	TextField tfAnswer;
	PasswordField pfPassword;
	PasswordField pfRePassword;
	Hyperlink hlToLogin;
	Button btnRegistration;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	@FXML
	public void handleRegistration(ActionEvent event) {
		
	}
	
	@FXML
	public void handleToLogin(ActionEvent event) {
			model.start(new Stage());
			 Node node= (Node)event.getSource();
			 Stage stage = (Stage) node.getScene().getWindow();
			 stage.close();
	}
}
