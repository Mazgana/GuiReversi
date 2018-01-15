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
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
    Button btnGame, btnSettings;
    Label lblMenu;
    VBox pane;
    Scene sceneMenu, scene;
    Stage theStage;
    
	@Override
	public void start(Stage primaryStage) {
		theStage = primaryStage;
		
        //make things to put on pane
        btnGame = new Button("play game");
        btnSettings = new Button("settings");
        btnGame.setOnAction(e-> ButtonClicked(e));
        btnSettings.setOnAction(e-> ButtonClicked(e));
        lblMenu = new Label("Menu");
        pane = new VBox();
        pane.setStyle("-fx-background-color: darkgreen;-fx-padding: 10px;");

        //add everything to pane
        pane.getChildren().addAll(lblMenu, btnGame, btnSettings);
        sceneMenu = new Scene(pane, 600, 400);
        primaryStage.setTitle("Reversi");
		primaryStage.setScene(sceneMenu);
		primaryStage.show();
	}
	
	public void ButtonClicked(ActionEvent e) {
		try {
	        if (e.getSource()==btnGame) {
				HBox root = (HBox)FXMLLoader.load(getClass().getResource("FXML.fxml"));
				scene = new Scene(root,700,500);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	            theStage.setScene(scene);
	        }
	        else if (e.getSource()==btnSettings) {
				GridPane rootSetting = (GridPane)FXMLLoader.load(getClass().getResource("SettingsFXML.fxml"));
		       	Scene sceneSetting = new Scene(rootSetting, 400, 200);
		       	Stage stage = new Stage();
		       	stage.setTitle("Reversi Settings");
		       	stage.setScene(sceneSetting);
		       	stage.show();
	        }
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//	            theStage.setScene(scene2);
	    }
	 
	public static void main(String[] args) {
		launch(args);
	}
}
