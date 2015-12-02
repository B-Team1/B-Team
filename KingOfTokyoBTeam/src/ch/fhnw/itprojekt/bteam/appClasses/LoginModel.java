package ch.fhnw.itprojekt.bteam.appClasses;

import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ch.fhnw.itprojekt.bteam.abstractClasses.Model;
import ch.fhnw.itprojekt.bteam.template.Properties;
import ch.fhnw.itprojekt.bteam.template.ServiceLocator;

public class LoginModel extends Application {
    ServiceLocator serviceLocator;
    ConnectionModel connectionModel;
    
    public LoginModel() {
        connectionModel = ConnectionModel.getInstance();
        serviceLocator = ServiceLocator.getServiceLocator();        
        serviceLocator.getLogger().info("Application model initialized");        
    }
    
    /**
     * Methode startet das Login Fenster mit den gespeicherten Einstellungen
     * @author Marco
     */
    public void start(Stage loginStage) {
    	
    	try {
    		Properties.getProperties().setLocale(new Locale(ServiceLocator.getServiceLocator().getLanguage()));
            BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("../fxmls/login.fxml"),
            		ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale()));
    	
            Scene scene = new Scene(root);
            // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            loginStage.setScene(scene);
            loginStage.setTitle("King of Tokyo");
            loginStage.setResizable(false);
            loginStage.show();
            
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Sendet die Logininformationen ans Connection Model
     * Weiter wird überprüft, ob die Felder leer sind
     * @author Tobias
     */
    public boolean sendLogin(User user){
    	if(user.getNickname().equals("") | user.getPassword().equals("")){
    		return false;
    	}
    	
    	if(connectionModel.sendLogin(user)){
    		serviceLocator.getLogger().info("Passwort ok!");
    		return true;
    	}
    	return false;
    }
    
    /**
     * Sendet die Benutzereingaben von Passwort vergessen ans Connection Model
     * @author Luzian
     */
    public String sendUserDataForPassword(User user){
    	if(user.getNickname().equals("") | user.getnName().equals("") | user.getvName().equals("")){
    		String error = "Nicht alle Felder ausgefüllt";
    		return error; 
    		
    	}
    	String securityQuestion = connectionModel.getSecurityQuestion(user);
    	if (securityQuestion != null)
    		user.setSecurityQuestion(securityQuestion);
    	//forgetPasswordController.setlbSecureQuestion(user.getSecurityQuestion());
    	return user.getSecurityQuestion();
    }
    
    /**
     * @author Luzian
     * @param user
     * @return
     */
    public String getSecurityAnswer(User user){
    	String securityAnswer = connectionModel.getSecurityAnswer(user);
    	return securityAnswer;
    }
    
    /**
     * @author Luzian
     * @param user
     * @return
     */
    public String getPassword(User user){
    	String password = connectionModel.getPassword(user);
    	return password;
    }
   
    /**
     * Verbindung zum Server wird für das ganze Programm aufgebaut
     * @author Tobias
     */
    public void connectToServer(){
    	connectionModel.connect("127.0.0.1", 8080);    	
    }
    
    /**
     * Methode lädt das Fenster Registration und die gespeicherten Einstellungen
     * @author Marco
     */
    public void startRegistry(Stage registryStage) {
    	try {
    		
    		Properties.getProperties().setLocale(new Locale(ServiceLocator.getServiceLocator().getLanguage()));
            BorderPane root1 = (BorderPane) FXMLLoader.load(getClass().getResource("../fxmls/registration.fxml"),
            		ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale()));
    	
            Scene scene = new Scene(root1);
            // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            registryStage.setScene(scene);
            registryStage.setTitle("King of Tokyo");
            registryStage.setResizable(false);
            registryStage.show();
            
    	} catch(Exception e) {
    		e.printStackTrace();
    	}

    }
    
    /**
     * Methode lädt das Fenster ForgetPassword und die gespeicherten Einstellungen
     * @author Marco
     */
    public void startForgetPassword(Stage forgetPasswordStage) {
    	try {
    		Properties.getProperties().setLocale(new Locale(ServiceLocator.getServiceLocator().getLanguage()));
            BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("../fxmls/forgetPassword.fxml"),
            		ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale()));
    	
            Scene scene = new Scene(root);
            // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            forgetPasswordStage.setScene(scene);
            forgetPasswordStage.setTitle("King of Tokyo");
            forgetPasswordStage.setResizable(false);
            forgetPasswordStage.show();
            
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public boolean addNewUser(User user){
    	return connectionModel.sendRegistration(user);
    }
    
    public boolean isEmptyRegistration(User user, String passwordRepeat){
    	if(user.getnName().equals("") |
    			user.getvName().equals("") |
    			user.getNickname().equals("") |
    			user.getSecurityAnswer().equals("") |
    			user.getSecurityQuestion().equals("") |
    			passwordRepeat.equals("") |
    			user.getPassword().equals("")){
    		return true;
    	}
    	return false;
    }
    
    public boolean checkRegistrationPassword(User user, String passwordRepeat){
    	if(user.getPassword().equals(passwordRepeat)){
    		return true;
    	}
    	return false;
    }
    
    

}
