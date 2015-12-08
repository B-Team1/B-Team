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
	
	@FXML
	TextField tfFirstName;
	
	@FXML
	TextField tfNickname;
	
	@FXML
	TextField tfSecureQuestion;
	
	@FXML
	TextField tfAnswer;
	
	@FXML
	PasswordField pfPassword;
	
	@FXML
	PasswordField pfRePassword;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	/**
	 * Methode um einen neuen User hinzuzufügen beim Klick auf den "Registrieren" Button
	 * @author Tobias
	 */
	@FXML
	public void handleRegistration(ActionEvent event) {
		ResourceBundle bundle = ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale());
		User user = new User(tfNickname.getText(),
							tfFirstName.getText(),
							tfName.getText(),
							pfPassword.getText(),
							tfAnswer.getText(),
							tfSecureQuestion.getText());
							
		if(model.isEmptyRegistration(user, pfRePassword.getText())){
			JOptionPane.showMessageDialog(null, FXCollections.observableArrayList(bundle.getString("registry.checkInput")), "Registration", JOptionPane.WARNING_MESSAGE);			
		}else{
			if(!model.checkRegistrationPassword(user, pfRePassword.getText())){
				JOptionPane.showMessageDialog(null, FXCollections.observableArrayList(bundle.getString("registry.checkPassword")), "Registration", JOptionPane.WARNING_MESSAGE);
			}else{
				model.addNewUser(user);
			}
		}		
	}
	
	/**
	 * Methode öffnet das Login Fenster und schliesst das aktuelle Fenster
	 * @author Marco
	 */
	@FXML
	public void handleToLogin(ActionEvent event) {
		model.start(new Stage());
		Node node= (Node)event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}
}
