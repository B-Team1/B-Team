package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import ch.fhnw.itprojekt.bteam.template.Properties;
import ch.fhnw.itprojekt.bteam.template.ServiceLocator;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class LoginController implements Initializable {
		
	LoginModel loginModel = new LoginModel();	
	
	@FXML TextField tfNickname;
	@FXML PasswordField pfPassword;
	@FXML Button btnRegistry;
	@FXML Button btnLogin;
	@FXML Label lbNickname;
	@FXML Label lbPassword;
	@FXML Hyperlink hlForgetPassword;
	@FXML Menu menuLanguage;
	@FXML Menu menuHelp;
	@FXML MenuItem miGerman;
	@FXML MenuItem miEnglish;
	@FXML MenuItem miTeam;
	
	/**
	 * Methode initialisiert die Komponenten
	 * @author Marco
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	/**
	 * Button Action für im GUI. Es werden Nickname und Passwort ausgelesen und dem Model übergeben.
	 * @author Tobias
	 */
	@FXML
	public void handleLogin(ActionEvent event) {
		doLogin(event);
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
	
	/**
	 * Methode ändert die Benutzersprache auf Deutsch
	 * @author Marco
	 */
	@FXML
	public void handleGerman(ActionEvent event) {
		Properties.getProperties().setLocale(new Locale("de"));
		ServiceLocator.getServiceLocator().setLanguage("de");
		updateTexts();
	}
	
	/**
	 * Methode ändert die Benutzersprache auf Englisch
	 * @author Marco
	 */
	@FXML
	public void handleEnglish(ActionEvent event) {
		Properties.getProperties().setLocale(new Locale("en"));
		ServiceLocator.getServiceLocator().setLanguage("en");
		updateTexts();
	}
	
	/**
	 * Dieses Event wird aufgerufen, wenn im Login Fenster die Taste Enter gedrückt wird
	 * @author Tobias
	 */
	@FXML
	public void handleEnter(KeyEvent event){
	    if (event.getCode() == KeyCode.ENTER) {
	    	doLogin(event);
	    }
	}
	
	/**
	 * Übergibt die Eingaben zur Kontrolle dem Model. Sind die Eingaben richtig wird das Menü aufgerufen
	 * und das Loginfenster geschlossen. Sind die Eingaben falsch
	 * @param Hier kann das Event übergeben werden, welcher schon beim Eventhandler der Parameter ist
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
	
	/**
	 * Aktualisierung der Texte bei Veränderung der Sprache
	 * @author Marco
	 */
	private void updateTexts() {
		ResourceBundle bundle = ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale());
        
		/**
		 * Aktualisierung der Befehle
		 * @author Marco
		 */
		menuLanguage.setText(bundle.getString("login.menu.language"));
		menuHelp.setText(bundle.getString("login.menu.help"));
		miGerman.setText(bundle.getString("login.menuitem.german"));
		miEnglish.setText(bundle.getString("login.menuitem.english"));
		miTeam.setText(bundle.getString("login.menuitem.info"));
		btnRegistry.setText(bundle.getString("login.btn.registry"));
		btnLogin.setText(bundle.getString("login.btn.login"));
		lbNickname.setText(bundle.getString("login.nickname"));
		lbPassword.setText(bundle.getString("login.password"));
		hlForgetPassword.setText(bundle.getString("login.link.forget"));
	    }
	
}
