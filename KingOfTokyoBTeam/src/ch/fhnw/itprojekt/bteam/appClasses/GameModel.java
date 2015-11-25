package ch.fhnw.itprojekt.bteam.appClasses;

import java.util.Locale;
import java.util.ResourceBundle;

import ch.fhnw.itprojekt.bteam.template.Properties;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameModel extends Application {

	/**
	 * Methode öffnet das Spielfeld und lädt die Einstellungen
	 * @author Marco
	 */
	@Override
	public void start(Stage gameStage){
		try {
    		Properties.getProperties().setLocale(new Locale("de"));
            BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("../fxmls/gameBoard.fxml"),
            		ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale()));
    	
            Scene scene = new Scene(root);
            // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            gameStage.setScene(scene);
            gameStage.setTitle("King of Tokyo");
            gameStage.setResizable(false);
            gameStage.show();
            
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
	}

}
