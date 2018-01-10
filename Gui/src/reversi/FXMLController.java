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
	private Board board = new Board();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GameFlow game = new GameFlow(board);
		game.setPrefWidth(400);
		game.setPrefHeight(400);
		root.getChildren().add(0, game);
		game.draw();
	}
}