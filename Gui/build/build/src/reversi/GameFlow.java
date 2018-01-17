package reversi;

import java.io.IOException;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameFlow extends GridPane {
	
	private Player blackPlayer;
	private Player whitePlayer;
	private Player current;
	
	private Board board;
	
	private double x;
	private double y;
	
	private Cell chosen = null;
	
	private Text scores;
	
	public GameFlow(int size, String firstColor, String secondColor) {
		this.board = new Board(size, size);
		this.blackPlayer = new Player(Status.BLACK, firstColor);
		this.whitePlayer = new Player(Status.WHITE, secondColor);
		
		this.current = this.blackPlayer;
		
		this.scores = new Text("");
	}

	//checking the point is valid and if so - choose this cell
	public int checkPointAndPlay(Player p, Board board) throws IOException {
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

	//runs basic game loop.
	public void run(String firstColor, String secondColor) throws IOException {
		
		//initializing board and starting.
		  this.board.initialize(firstColor, secondColor, this);
		  showScores();
		  
		  this.board.updateOptionalMovesList(this.current.getChip());
		  
		  //waiting for player's choice on screen
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
			played = checkPointAndPlay(this.current, this.board);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//the game is over
		if (this.board.isBoardFull()) {
				endGame();
				return;
		}
		if (played != 2) {
			this.board.updateOptionalMovesList(this.current.getOppositeType());
			if (this.board.getOptions().size() == 0) { //the next player has no moves
				this.board.updateOptionalMovesList(this.current.getChip()); //both players have moves - the game is over
				if (this.board.getOptions().size() == 0)
					endGame();
			} else {
				if (this.current.getChip() == Status.BLACK) { //changing current player to the opposite
					this.current = this.whitePlayer;
				}
				else {
					this.current = this.blackPlayer;
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
				String win = "";
				if (winner == Status.BLACK)
					win = this.blackPlayer.getColor();
				else
					win = this.whitePlayer.getColor();
				alert.setContentText("Player " + win + " wins!");
		}
		
		alert.showAndWait();
	}
	
	//showing the current player and both player's scores on screen
	public void showScores() {
		this.getChildren().remove(this.scores);
		this.scores.setFont(Font.font ("Purisa", 16));
		this.add(this.scores, this.board.getWidth() + 1, 1, this.board.getWidth() + 1, 5);
		this.scores.setText(" currentent player: " + this.current.getColor() + "\n " + this.blackPlayer.getColor() + " player score: " 
						+ this.board.getBlackScore() + "\n " + this.whitePlayer.getColor() + " player score: " + this.board.getWhiteScore());
	}
}