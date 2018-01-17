package reversi;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
/*
 * This application is a reversi game.
 * User can choose to set the settings (players colors and board size,
 * or he can choose to start a new reversi game.
 */
public class Main extends Application {
	private Button btnStart, btnSettings, btnMenu;
    private Label lblMenu;
    private VBox pane;
    private Scene sceneMenu, scene;
    private Stage theStage;
    
	@Override
	public void start(Stage primaryStage) {
				theStage = primaryStage;
		
        //organize start button with image
				StackPane spStart = new StackPane();
        btnStart = new Button("startGame");
        ImageView ivStart = new ImageView(new Image(getClass().getResourceAsStream("/startButton.jpg")));
        ivStart.setFitHeight(50);
        ivStart.setFitWidth(150);
        btnStart.setMinSize(100, 50);
        btnStart.setStyle("-fx-background-color: transparent;");
        btnStart.setOnAction(e-> ButtonClicked(e));
        spStart.getChildren().addAll(ivStart, btnStart);
 
       //organize settings button with image
        StackPane spSettings = new StackPane();
        btnSettings = new Button("settings");
        ImageView ivSettings = new ImageView(new Image(getClass().getResourceAsStream("/settingsButton.jpg")));
        ivSettings.setFitHeight(50);
        ivSettings.setFitWidth(250);
        btnSettings.setMinSize(100, 50);
        btnSettings.setStyle("-fx-background-color: transparent;");
        btnSettings.setOnAction(e-> ButtonClicked(e));
        spSettings.getChildren().addAll(ivSettings, btnSettings);

        //add everything to pane and organize menu
        lblMenu = new Label("");
        lblMenu.setMinSize(600.0, 300.0);
        lblMenu.setAlignment(Pos.CENTER);
        pane = new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(lblMenu, spStart, spSettings);
        
        //setting menu background
        Image im = new Image(getClass().getResource("/reversiMenu.jpg").toExternalForm());
        Background bg = new Background(new BackgroundImage(im,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                  new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), false, false, false, true)));
       
        pane.setBackground(bg);
        
        //adding button to return to menu
        btnMenu = new Button("quit");
        btnMenu.setOnAction(e-> ButtonClicked(e));
        
        //opening window and starting menu
        sceneMenu = new Scene(pane, 800, 600);
        primaryStage.setTitle("Reversi");
				primaryStage.setScene(sceneMenu);
				primaryStage.show();
	}
	
	/*
	 * handles an event of a menu related button being pressed
	 */
	private void ButtonClicked(ActionEvent e) {
		try {
	        if (e.getSource() == btnStart) {//loading starting new game
				Pane root = (Pane)FXMLLoader.load(getClass().getResource("FXML.fxml"));
				root.getChildren().add(btnMenu);
				btnMenu.relocate(400, 460);
				scene = new Scene(root,700,500);
				btnMenu.relocate(scene.getWidth() - 50, scene.getHeight() - 30);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	            theStage.setScene(scene);
	        }
	        else if (e.getSource() == btnSettings) {//opening settings window
				GridPane rootSetting = (GridPane)FXMLLoader.load(getClass().getResource("SettingsFXML.fxml"));
		       	Scene sceneSetting = new Scene(rootSetting, 400, 350);
		       	Stage stage = new Stage();
		       	stage.setTitle("Reversi Settings");
		       	stage.setScene(sceneSetting);
		       	stage.show();
	        }
	        if (e.getSource() == btnMenu) {//returning to menu when another scene quits
	        	theStage.setScene(sceneMenu);
	    		theStage.show();
	        }
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    }
	 
	public static void main(String[] args) {
		launch(args);
	}
}