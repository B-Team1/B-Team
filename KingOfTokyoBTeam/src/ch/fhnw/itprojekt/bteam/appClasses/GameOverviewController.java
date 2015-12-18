package ch.fhnw.itprojekt.bteam.appClasses;

import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import ch.fhnw.itprojekt.bteam.template.Properties;
import ch.fhnw.itprojekt.bteam.template.ServiceLocator;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class GameOverviewController implements Initializable {

	MenuModel model;
	private static GameOverviewController singleton;
	
	@FXML Button btnStartGame;
	@FXML Button btnNewGame;
	@FXML Button btnCloseGame;
	@FXML Label lbPlayGames;
	@FXML Label lbWonGames;
	@FXML Label lbNickname;
	@FXML Label lbPlayed;
	@FXML Label lbWon;
	@FXML Label lbHello;
	@FXML Label lbOpenGames;
	@FXML Label lbStats;
	@FXML TableColumn tcOpenGames;
	@FXML TableView<GameModel> tvOpenGames;
	@FXML Menu menuLanguage;
	@FXML Menu menuHelp;
	@FXML MenuItem miGerman;
	@FXML MenuItem miEnglish;
	@FXML MenuItem miInfo;
	
	public static Stage stage = new Stage();
	
	/**
	 * Holt die Instanz des Menumodels, da der Nickname dort abgelegt ist
	 * Holt vom Server die Statistikwerte anhand des Nicknames des Spielers
	 * @author Tobias
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		model = MenuModel.getInstance();
		lbNickname.setText(model.getNickname());
		GameOverviewController.singleton = this;
		model.getStats();
		btnStartGame.setDisable(true);
	}
	
	public static GameOverviewController getInstance() {
	     if(singleton == null) {	        
	         singleton = new GameOverviewController();
	      }	     
	      return singleton;
	}
	
	public void setStats(Stats stats){
		lbPlayGames.setText(stats.getSumGames() + "");
		lbWonGames.setText(stats.getWonGames() + "");
	}
	
	public void fillTable(ArrayList<GameModel> openGameList){
		ObservableList<GameModel> data = FXCollections.observableArrayList();
		
		for(GameModel ogl : openGameList){
			data.add(ogl);
		}		
		
		tvOpenGames.setEditable(true); 
		tcOpenGames.setCellValueFactory(new PropertyValueFactory<GameModel, String>("nickname"));
		tvOpenGames.setItems(data);
	            
    	if(openGameList.size() == 0){
			btnStartGame.setDisable(true);
		}else{
			tvOpenGames.getSelectionModel().selectFirst();
			btnStartGame.setDisable(false);
		}
	}
	
	
	
	@FXML
	public void handleStartGame(ActionEvent event) {
		model.startSelectedGame(tvOpenGames.getSelectionModel().getSelectedItem());
	}
	
	/**
	 * Methode um ein neues Spiel zu erstellen. Öffnet neues Fenster und schliesst das Aktuelle
	 * @author Marco
	 */
	@FXML
	public void handleNewGame(ActionEvent event) {
		model.startNewGame(NewGameController.newGameStage);
		Node node = (Node)event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}
	
	/**
	 * Methode um das Fenster und die Anwendung zu schliessen
	 * @author Marco
	 */
	@FXML
	public void handleCloseGame(ActionEvent event) {
		Node node = (Node)event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}
	
	/**
	 * Methode ändert die Benutzersprache auf Deutsch
	 * @author Marco
	 */
	@FXML
	public void handleGerman(ActionEvent event) {
		Properties.getProperties().setLocale(new Locale("de"));
		ServiceLocator.getServiceLocator().setLanguage("de");
		updateTexts();
	}
	
	/**
	 * Methode ändert die Benutzersprache auf Englisch
	 * @author Marco
	 */
	@FXML
	public void handleEnglish(ActionEvent event) {
		Properties.getProperties().setLocale(new Locale("en"));
		ServiceLocator.getServiceLocator().setLanguage("en");
		updateTexts();
	}
	
	/**
	 * Aktualisierung der Texte bei Veränderung der Sprache
	 * @author Marco
	 */
	public void updateTexts() {
		ResourceBundle bundle = ResourceBundle.getBundle("ch.fhnw.itprojekt.bteam.bundles.JavaFXAppTemplate", Properties.getProperties().getLocale());
        
		/**
		 * Aktualisierung der Befehle
		 * @author Marco
		 */
		btnStartGame.setText(bundle.getString("overview.btn.start"));
		btnNewGame.setText(bundle.getString("overview.btn.new"));
		btnCloseGame.setText(bundle.getString("overview.btn.close"));
		menuLanguage.setText(bundle.getString("overview.menu.language"));
		menuHelp.setText(bundle.getString("overview.menu.help"));
		miGerman.setText(bundle.getString("overview.menuitem.german"));
		miEnglish.setText(bundle.getString("overview.menuitem.english"));
		miInfo.setText(bundle.getString("overview.menuitem.info"));
		lbPlayed.setText(bundle.getString("overview.lb.games"));
		lbWon.setText(bundle.getString("overview.lb.won"));
		lbHello.setText(bundle.getString("overview.lb.hello"));
		lbOpenGames.setText(bundle.getString("overview.lb.opengames"));
		lbStats.setText(bundle.getString("overview.lb.stats"));
		tcOpenGames.setText(bundle.getString("overview.tc.opengames"));
	}
}
