package reversi;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

public class FXMLController implements Initializable{

	@FXML
	private GridPane root;
	
	@FXML
	private GameFlow game;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.game = new GameFlow();
		this.game.setPrefWidth(500);
		this.game.setPrefHeight(500);
		root.getChildren().add(this.game);
		
//		this.game.draw();
		
		try {
			this.game.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}