package reversi;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameFlowController extends GridPane {
	private int[][] board;
	private static final int BLACK = 0;
	private static final int WHITE = 1;
	private static final int EMPTY = 2;
	
	public GameFlowController(int[][] board) {
		this.board = board;
		FXMLLoader fxmlLoader = new
		FXMLLoader(getClass().getResource("MazeBoard.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public void draw() {
		this.getChildren().clear();
		int height = (int)this.getPrefHeight();
		int width = (int)this.getPrefWidth();
		int cellHeight = height / board.length;
		int cellWidth = width / board[0].length;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == EMPTY)
					this.add(new Rectangle(cellWidth, cellHeight, Color.WHITE), j, i);
				else
					this.add(new Rectangle(cellWidth, cellHeight, Color.BLACK), j, i);
			}
		}
	}

}
