package ch.fhnw.itprojekt.bteam.splashScreen;

import java.net.URL;
import java.util.ResourceBundle;

import ch.fhnw.itprojekt.bteam.abstractClasses.SplashController;
import ch.fhnw.itprojekt.bteam.template.JavaFXAppTemplate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.stage.Stage;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class Splash_Controller extends SplashController<Splash_Model, Splash_View> {

    public Splash_Controller(final JavaFXAppTemplate main, Splash_Model model, Splash_View view) {
        super(model, view);
        
        // Fortschrittsanzeige
        view.progress.progressProperty().bind(model.initializer.progressProperty());
        
        // Using a lambda expression
        model.initializer.stateProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue == Worker.State.SUCCEEDED)
                    	main.startApp();
                });
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
