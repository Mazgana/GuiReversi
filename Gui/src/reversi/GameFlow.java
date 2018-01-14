package reversi;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class GameFlow extends GridPane {
	
	@FXML
	Label scores;

	private Player blackPlayer;
	private Player whitePlayer;
	private Player curr;
	
	private Board board;
	
	private double x;
	private double y;
	
	private Cell chosen = null;
	
	private Text current;
	
	public GameFlow() {
		this.board = new Board();
		this.blackPlayer = new Player(Status.BLACK);
		this.whitePlayer = new Player(Status.WHITE);
		
		this.curr = this.blackPlayer;
		this.current = new Text("Current playe: " + this.curr);
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameFlow.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		
		try {
				fxmlLoader.load();
		} catch (IOException exception) {
				throw new RuntimeException(exception);
		}
	}
	
	public void draw() {
		
	}

	public int playTurn(Player p, Board board) throws IOException {
	    int[] coordinates = board.locationOfPoint(this.x, this.y);
	    this.chosen = new Cell(coordinates[0], coordinates[1], this);
				    
	    //validate that coordinate is an option
	    if (!board.isCellInOptionArray(this.chosen)) {
		    	return 2;
	    } else  {
			    board.putChip(p.getChip(), chosen.getRow(), chosen.getCol());// putting chip on board and flipping chips accordingly
	    	}
				
	    return 1;
	}

	public Player getBlackPlayer() {
		return this.blackPlayer;
	}

	public Player getWhitePlayer() {
		return this.whitePlayer;
	}

	//runs basic game loop.
	public void run() throws IOException {
		
		//initializing board and starting.
		  this.board.initialize(this);
		  
		  this.board.updateOptionalMovesList(this.curr.getChip());
		  
			this.setOnMouseClicked(event -> {
				this.x = event.getX();
				this.y = event.getY();
				event.consume();
				try {
					playOneTurn();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
//				this.getChildren().remove(current);
//				this.add(current, this.board.getWidth() + 1, 1);
//				this.board.draw();
//				
//				this.current.setText("Current player: " + this.curr.getChip());
			});
	}

	public void playOneTurn() throws IOException {
		int played = 0;
		
		try {
			played = playTurn(this.curr, this.board);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (this.board.isBoardFull())
				endGame();
		
		if (played != 2) {
			this.board.updateOptionalMovesList(this.curr.getOppositeType());
			if (this.board.getOptions().size() == 0) { //the next player has no moves
				this.board.updateOptionalMovesList(this.curr.getChip()); //both players have moves
				if (this.board.getOptions().size() == 0)
					endGame();
			} else {
				if (this.curr.getChip() == Status.BLACK) { //changing current player
					this.curr = this.whitePlayer;
				}
				else {
					this.curr = this.blackPlayer;
				}
			}
		}
	}
	
	//ending game and declaring winner.
	public void endGame() throws IOException {
		//ending game and announcing winner

		Alert alert = new Alert(AlertType.NONE);
		alert.setHeaderText("GAME ENDED!");
		
		Status winner = board.getWinner();
		if(winner == Status.EMPTY) {
				alert.setContentText("It's a tie!");
		} else {
				alert.setContentText("Player " + winner.toString() + " wins!");
		}
		
		if(winner == Status.EMPTY) {
			System.out.println("It's a tie!");
		} else {
			System.out.println("Player " + winner.toString() + " wins!");
		}
		
//		alert.showAndWait();
//		alert.close();
	}
}