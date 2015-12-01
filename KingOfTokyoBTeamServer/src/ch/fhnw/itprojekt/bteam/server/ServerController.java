package ch.fhnw.itprojekt.bteam.server;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class ServerController {
    
    final private ConnectionModel model;
    final private ServerView view;
    
    protected ServerController(ConnectionModel model, ServerView view) {
        this.model = model;
        this.view = view;
        
        // register ourselves to listen for button clicks
        view.btnGo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Integer port = new Integer(view.txtPort.getText());
                model.startServerSocket(port);
            }
        });
        
        view.btna.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                model.sendBroadcast();
            }
        });

        // register ourselves to handle window-closing event
        view.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                view.stop();     // closes the GUI
                Platform.exit(); // ends any JavaFX activities
                System.exit(0);  // end all activities (our server task) - not good code
            }
        });
    }
}
