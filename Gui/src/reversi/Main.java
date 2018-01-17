package reversi;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;

public class Main extends Application {
	private Button btnGame, btnSettings, btnMenu;
    private Label lblMenu;
    private VBox pane;
    private Scene sceneMenu, scene;
    private Stage theStage;
    
	@Override
	public void start(Stage primaryStage) {
		theStage = primaryStage;
		
        //make things to put on pane
        btnGame = new Button("play game");
        btnSettings = new Button("settings");
        btnMenu = new Button("quit");
        btnGame.setOnAction(e-> ButtonClicked(e));
        btnSettings.setOnAction(e-> ButtonClicked(e));
        btnMenu.setOnAction(e-> ButtonClicked(e));
        lblMenu = new Label("Menu");
        pane = new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.setStyle("-fx-background-color: darkblue;-fx-padding: 10px;");

        //add everything to pane
        pane.getChildren().addAll(lblMenu, btnGame, btnSettings);
        lblMenu.setMinSize(600.0, 200.0);
        lblMenu.setAlignment(Pos.CENTER);
        btnGame.setMinSize(100, 50);
        sceneMenu = new Scene(pane, 600, 400);
        primaryStage.setTitle("Reversi");
		primaryStage.setScene(sceneMenu);
		primaryStage.show();
	}
	
	public void ButtonClicked(ActionEvent e) {
		try {
	        if (e.getSource()==btnGame) {
				Pane root = (Pane)FXMLLoader.load(getClass().getResource("FXML.fxml"));
				root.getChildren().add(btnMenu);
				btnMenu.relocate(400, 460);
				scene = new Scene(root,600,500);
				btnMenu.relocate(scene.getWidth() - 50, scene.getHeight() - 30);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	            theStage.setScene(scene);
	        }
	        else if (e.getSource()==btnSettings) {
				GridPane rootSetting = (GridPane)FXMLLoader.load(getClass().getResource("SettingsFXML.fxml"));
		       	Scene sceneSetting = new Scene(rootSetting, 400, 300);
		       	Stage stage = new Stage();
		       	stage.setTitle("Reversi Settings");
		       	stage.setScene(sceneSetting);
		       	stage.show();
	        }
	        if (e.getSource()==btnMenu) {
	        	theStage.setScene(sceneMenu);
	    		theStage.show();
	        }
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    }
	 
	public static void main(String[] args) {
		launch(args);
	}
}