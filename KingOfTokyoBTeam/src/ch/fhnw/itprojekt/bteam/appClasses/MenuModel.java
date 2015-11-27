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
	
	public MenuModel() {
        serviceLocator = ServiceLocator.getServiceLocator();        
        serviceLocator.getLogger().info("MenuModel initialized");
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

}
