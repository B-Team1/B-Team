package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ForgetPasswordController implements Initializable {
	
	@FXML
	TextField tfName;
	TextField tfFirstName;
	TextField tfNickname;
	TextField tfAnswer;
	Label lbSecureQuestion;
	Label lbYourPassword;
	
	
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
	 * 
	 * @param event
	 */
	@FXML
	public void handleEnter(ActionEvent event) {
		
	}
	
	/**
	 * 
	 * @param event
	 */
	@FXML
	public void handleConfirm(ActionEvent event) {
		
	}

}
