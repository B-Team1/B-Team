package ch.fhnw.itprojekt.bteam.appClasses;

import java.util.Locale;
import java.util.ResourceBundle;

import ch.fhnw.itprojekt.bteam.template.Properties;
import ch.fhnw.itprojekt.bteam.template.ServiceLocator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuModel extends Application {
	ServiceLocator serviceLocator;
	ConnectionModel connectionModel;
	private String currentNickname;
	private static MenuModel singleton;
	
	public MenuModel(String currentNickname) {
		this.currentNickname = currentNickname;
		serviceLocator = ServiceLocator.getServiceLocator();        
        serviceLocator.getLogger().info("MenuModel initialized");
        connectionModel = ConnectionModel.getInstance();
	}

	public MenuModel() {
        serviceLocator = ServiceLocator.getServiceLocator();        
        serviceLocator.getLogger().info("MenuModel initialized");
        connectionModel = ConnectionModel.getInstance();
    }
	
	
	public static MenuModel getInstance() {
	     if(singleton == null) {	        
	         singleton = new MenuModel();
	      }	     
	      return singleton;
	}	


	/**
	 * Methode öffnet das Fenster GameOverview und lädt die Einstellungen
	 * @author Marco
	 */
	public void start(Stage gameOverviewStage) {
		try {
    		Properties.getProperties().setLocale(new Locale(ServiceLocator.getServiceLocator().getLanguage()));
            BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("../fxmls/gameOverview.fxml"),
            		ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale()));
    	
            Scene scene = new Scene(root);
            // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            gameOverviewStage.setScene(scene);
            gameOverviewStage.setTitle("King of Tokyo");
            gameOverviewStage.setResizable(false);
            gameOverviewStage.show();
            serviceLocator.getLogger().info("GameOverview geöffnet");
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	
	/**
	 * Methode öffnet das Fenster NewGame und lädt die Einstellungen
	 * @author Marco
	 */
	public void startNewGame(Stage newGameStage) {
		try {
    		Properties.getProperties().setLocale(new Locale(ServiceLocator.getServiceLocator().getLanguage()));
            BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("../fxmls/newGame.fxml"),
            		ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale()));
    	
            Scene scene = new Scene(root);
            // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            newGameStage.setScene(scene);
            newGameStage.setTitle("King of Tokyo");
            newGameStage.setResizable(false);
            newGameStage.show();
            serviceLocator.getLogger().info("NewGame geöffnet");
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	
	/**
	 * Methode öffnet das Fenster CreateGame und lädt die Einstellungen
	 * @author Marco
	 */
	public void startCreateGame(Stage createGameStage) {
		try {
    		Properties.getProperties().setLocale(new Locale(ServiceLocator.getServiceLocator().getLanguage()));
            BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("../fxmls/createGame.fxml"),
            		ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale()));
    	
            Scene scene = new Scene(root);
            // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            createGameStage.setScene(scene);
            createGameStage.setTitle("King of Tokyo");
            createGameStage.setResizable(false);
            createGameStage.show();
            serviceLocator.getLogger().info("CreateGame geöffnet");
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
	}
		
	/**
	 * Ruft im ConnectionModel die Statistik anhand des Nicknames ab. Nickname ist im Model gespeichert.
	 * @author Tobias
	 * @return
	 */
	public Stats getStats(){
		return connectionModel.getStat(currentNickname);
	}
	
	public String getNickname(){
		return currentNickname;
	}

	public void setNickname(String nickname) {
		this.currentNickname = nickname;
	}
	
	public boolean openNewGame(String numPlayer, boolean famePointsWin, String winFamePoins){
		 return connectionModel.sendNewGame(convertPlayerChoiceBox(numPlayer), famePointsWin, convertFamePointsChoiceBox(winFamePoins));
	}
	
	private int convertPlayerChoiceBox(String numPlayer){
		return Integer.parseInt(numPlayer.split(" ")[0]);		
	}
	
	private int convertFamePointsChoiceBox(String winFamePoins){
		return Integer.parseInt(winFamePoins.split(" ")[0]);		
	}
	

}
