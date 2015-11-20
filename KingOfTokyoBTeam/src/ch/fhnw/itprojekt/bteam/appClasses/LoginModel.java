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
    ConnectionModel connectionModel = new ConnectionModel();
    LoginController loginController = new LoginController();
    
    public LoginModel() {
        
        serviceLocator = ServiceLocator.getServiceLocator();        
        serviceLocator.getLogger().info("Application model initialized");
    }
    
    /**
     * Methode startet das Login Fenster mit den eingestellten Einstellungen
     * @author Marco
     */
    public void start(Stage loginStage) {
    	
    	try {
    		
    		Properties.getProperties().setLocale(new Locale("de"));
            BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("../fxmls/login.fxml"),
            		ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale()));
    	
            Scene scene = new Scene(root);
            // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            loginStage.setScene(scene);
            loginStage.setTitle("King of Tokyo");
            loginStage.show();
            
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Sendet die Logininformationen ans Connection Model
     * @author Tobias
     */
    public void sendLogin(User user){
    	if(user.getNickname() == "" | user.getPassword() == ""){
    		loginController.wrongLogin();
    	}
    	
    	if(connectionModel.sendLogin(user)){
    		serviceLocator.getLogger().info("Passwort ok!");
    	}
    }
    
    /**
     * Verbindung zum Server wird für das ganze Programm aufgebaut
     * @author Tobias
     */
    public void connectToServer(){
    	connectionModel.connect("127.0.0.1", 8080);    	
    }
    
    public void startRegistry(Stage registryStage) {
try {
    		
    		Properties.getProperties().setLocale(new Locale("de"));
            BorderPane root1 = (BorderPane) FXMLLoader.load(getClass().getResource("../fxmls/registration.fxml"),
            		ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale()));
    	
            Scene scene = new Scene(root1);
            // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            registryStage.setScene(scene);
            registryStage.setTitle("King of Tokyo");
            registryStage.show();
            
    	} catch(Exception e) {
    		e.printStackTrace();
    	}

    }
    
    public void startForgetPassword(Stage forgetPasswordStage) {
    	try {
    		Properties.getProperties().setLocale(new Locale("de"));
            BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("../fxmls/forgetPassword.fxml"),
            		ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale()));
    	
            Scene scene = new Scene(root);
            // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            forgetPasswordStage.setScene(scene);
            forgetPasswordStage.setTitle("King of Tokyo");
            forgetPasswordStage.show();
            
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    
}
