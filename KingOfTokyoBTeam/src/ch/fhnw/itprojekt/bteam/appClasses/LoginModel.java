package ch.fhnw.itprojekt.bteam.appClasses;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
    private static LoginModel singleton;
    ForgetPasswordController forgetpasswordcontroller;
    
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
    
    public static LoginModel getInstance() {
	     if(singleton == null) {	        
	         singleton = new LoginModel();
	      }	     
	      return singleton;
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
    	connectionModel.sendLogin(user);
    	return true;    	
    }
    
    /**
     * Sendet die Benutzereingaben von Passwort vergessen ans Connection Model
     * @author Luzian
     */
    public void sendUserDataForPassword(User user){
    	if(user.getNickname().equals("") | user.getnName().equals("") | user.getvName().equals("")){
    		forgetpasswordcontroller = ForgetPasswordController.getInstance();
    		forgetpasswordcontroller.missingUserDataForPassword();
    	}else{
    		connectionModel.getSecurityQuestion(user);
    	}
    }
    
    /**
     * sendet den User ans ConnectionModel
     * @author Luzian
     * @param user
     * @return
     */
    public void getSecurityAnswer(User user){
    	connectionModel.getSecurityAnswer(user);
    }
    
    /**
     * sendet den User ans ConnectionModel
     * @author Luzian
     * @param user
     * @return
     */
    public void getPassword(User user){
    	connectionModel.getPassword(user);
    	
    }
   
    /**
     * Verbindung zum Server wird für das ganze Programm aufgebaut
     * @author Tobias
     */
    public void connectToServer(){
    	try {
			connectionModel.connect(getIPConfig(), getPortConfig());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    }
    
    /** 
     * liest die angegebene IP aus der Textdatei aus
     * @author Luzian
     */
    public String getIPConfig() throws IOException{
    	FileReader fr = new FileReader("IPConfig.txt");
    	BufferedReader br = new BufferedReader(fr);
    	String ip = br.readLine();
    	String[] splitIP = ip.split("=",2);
    	br.close();
    	return splitIP[1];
    }
    
    /**
     * liest den angegebenen Port aus der Textdatei aus
     * @author Luzian
     */
    public int getPortConfig() throws IOException{
    	FileReader fr = new FileReader("IPConfig.txt");
    	BufferedReader br = new BufferedReader(fr);
    	String ip = br.readLine();
    	String port = br.readLine();
    	String[] splitPort = port.split("=",2);
    	br.close();
    	int portwert = Integer.parseInt(splitPort[1]);
    	return portwert;
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
    
    public void addNewUser(User user){
    	connectionModel.sendRegistration(user);
    }
    
    /**
     * Prüft ob die Felder leer sind
     * @author Tobias
     * @param user
     * @param passwordRepeat
     * @return
     */
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
    
    /**
     * Überprüft ob die Passwörter identisch sinds
     * @author Tobias
     * @param user
     * @param passwordRepeat
     * @return
     */
    public boolean checkRegistrationPassword(User user, String passwordRepeat){
    	if(user.getPassword().equals(passwordRepeat)){
    		return true;
    	}
    	return false;
    }
    
    

}
