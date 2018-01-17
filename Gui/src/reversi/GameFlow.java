package reversi;

import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class GameFlow extends GridPane {
	
	private Player blackPlayer;
	private Player whitePlayer;
	private Player curr;
	
	private Board board;
	
	private double x;
	private double y;
	
	private Cell chosen = null;
	
	private Text scores;
	
	public GameFlow(int size, String firstColor, String secondColor) {
		this.board = new Board(size, size);
		this.blackPlayer = new Player(Status.BLACK, firstColor);
		this.whitePlayer = new Player(Status.WHITE, secondColor);
		
		this.curr = this.blackPlayer;
		
		this.scores = new Text("");
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
	public void run(String firstColor, String secondColor) throws IOException {
		
		//initializing board and starting.
		  this.board.initialize(firstColor, secondColor, this);
		  showScores();
		  
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
				showScores();

			});
	}

	public void playOneTurn() throws IOException {
		int played = 0;
		
		try {
			played = playTurn(this.curr, this.board);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (this.board.isBoardFull()) {
				endGame();
				return;
		}
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
		alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
		
		Status winner = board.getWinner();
		if(winner == Status.EMPTY) {
				alert.setContentText("It's a tie!");
		} else {
				alert.setContentText("Player " + winner.toString() + " wins!");
		}
		
		alert.showAndWait();
	}
	
	public void showScores() {
		this.getChildren().remove(this.scores);
		this.add(this.scores, this.board.getWidth() + 1, 1,this.board.getWidth() + 2, 5);
		this.scores.setText("Current player: " + this.curr.getColor() + "\n" + this.blackPlayer.getColor() + " player score: " 
						+ this.board.getBlackScore() + "\n" + this.whitePlayer.getColor() + " player score: " + this.board.getWhiteScore());
	}
}