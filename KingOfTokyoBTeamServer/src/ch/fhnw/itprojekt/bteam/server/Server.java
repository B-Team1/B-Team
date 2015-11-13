package ch.fhnw.itprojekt.bteam.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;

public class Server extends Application {
	private ServerView view;
	private ServerController controller;
	private ConnectionModel connectionModel;

	public static void main(String[] args) {
        launch(args);
    }
 
	@Override
    public void start(Stage primaryStage) throws Exception {
        // Part of the GUI will contain log output from our own handler
        TextAreaHandler textAreaHandler = new TextAreaHandler();
        textAreaHandler.setLevel(Level.INFO);
        Logger defaultLogger = Logger.getLogger("");
        defaultLogger.addHandler(textAreaHandler);
        
        // Initialize the GUI
        connectionModel = new ConnectionModel();
        view = new ServerView(primaryStage, connectionModel, textAreaHandler.getTextArea());
        controller = new ServerController(connectionModel, view);

        // Display the GUI after all initialization is complete
        view.start();
    }

    /**
     * The stop method is the opposite of the start method. It provides an
     * opportunity to close down the program gracefully, when the program has
     * been closed.
     */
    @Override
    public void stop() {
        if (view != null)
            view.stop();
    }
}
