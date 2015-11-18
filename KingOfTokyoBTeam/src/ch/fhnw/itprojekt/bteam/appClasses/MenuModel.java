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

	public void start(Stage gameOverviewStage) {
		try {
    		
    		Properties.getProperties().setLocale(new Locale("de"));
            BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("../fxmls/login.fxml"),
            		ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale()));
    	
            Scene scene = new Scene(root);
            // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            gameOverviewStage.setScene(scene);
            gameOverviewStage.setTitle("King of Tokyo");
            gameOverviewStage.show();
            
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
	}

}
