package ch.fhnw.itprojekt.bteam.appClasses;

import java.util.ArrayList;
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
	private ArrayList<GameModel> gameModelList = new ArrayList<GameModel>();
	
	public MenuModel(String currentNickname) {
		this.currentNickname = currentNickname;
		serviceLocator = ServiceLocator.getServiceLocator();        
        serviceLocator.getLogger().info("MenuModel initialized");
        connectionModel = ConnectionModel.getInstance();
        this.openGameRequest();
	}

	public MenuModel() {
        serviceLocator = ServiceLocator.getServiceLocator();        
        serviceLocator.getLogger().info("MenuModel initialized");
        connectionModel = ConnectionModel.getInstance();
        this.openGameRequest();
    }
	
	/**
	 * Diese Methode prüft, ob bereits eine Instanz besteht und gibt dann eine zurück.
	 * @return MenuModel
	 * @author Tobias
	 */
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
	 * Ruft im ConnectionModel die Statistik anhand des Nicknames ab. Nickname ist im Model gespeichert.
	 * @author Tobias
	 * @return
	 */
	public void getStats(){
		connectionModel.getStat(currentNickname);
	}
	
	public String getNickname(){
		return currentNickname;
	}

	public void setNickname(String nickname) {
		this.currentNickname = nickname;
	}
	
	/**
	 * Gibt die Daten für ein neues Spiel dem Connection Model. Dieses gibt eine gameId grösser 0
	 * zurück wenn das Game erfolgreich angelegt wurde. Nun wird ein neues Game auf dem Client mit diesr
	 * gameId erzeugt.
	 * @author Tobias
	 * @param numPlayer
	 * @param famePointsWin
	 * @param winFamePoins
	 */
	public void openNewGame(String numPlayer, boolean famePointsWin, String winFamePoins){
		connectionModel.sendNewGame(convertPlayerChoiceBox(numPlayer), famePointsWin, convertFamePointsChoiceBox(winFamePoins), this.currentNickname);		
	}
	
	/**
	 * Wandelt den Wert der Choicebox in einen int um
	 * @author Tobias
	 * @param numPlayer
	 * @return
	 */
	private int convertPlayerChoiceBox(String numPlayer){
		return Integer.parseInt(numPlayer.split(" ")[0]);		
	}
	
	
	/**
	 * Wandelt den Wert der Choicebox in einen int um
	 * @author Tobias
	 * @param winFamePoins
	 * @return
	 */
	private int convertFamePointsChoiceBox(String winFamePoins){
		return Integer.parseInt(winFamePoins.split(" ")[0]);		
	}
	
	/**
	 * Anfrage für alle offenen Spiele
	 * @author Tobias
	 */
	public void openGameRequest(){
		connectionModel.sendOpenGameRequest();
	}

	public ArrayList<GameModel> getGameModelList() {
		return gameModelList;
	}

	public void setGameModelList(ArrayList<GameModel> gameModelList) {
		this.gameModelList = gameModelList;
	}
	
	public void startSelectedGame(GameModel game){
		connectionModel.sendAccession(game.getGameId(), currentNickname);
		game.startCreateGame(new Stage());
	}
	

}
