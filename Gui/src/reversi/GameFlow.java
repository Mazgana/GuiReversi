package reversi;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
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
	
	public GameFlow() {
		this.board = new Board();
		this.blackPlayer = new Player(Status.BLACK);
		this.whitePlayer = new Player(Status.WHITE);
		
		this.curr = this.blackPlayer;
		
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
		this.getChildren().clear();
	}

	public int playTurn(Player p, Board board) throws IOException {
	    List<Cell> options = board.getOptions(p.getChip());
	    
	    if (options.isEmpty()) {
		    	System.out.println(p.getChip().toString() + ": you have got no moves.");
		    	System.out.println("press enter to continue..");
		    	System.in.read();

	        return 0;
	    }//no moves can be done, turn passes to other player
	    
	    int[] coordinates = board.locationOfPoint(this.x, this.y);
	    this.chosen = new Cell(coordinates[0], coordinates[1], this);
				    
	    //validate that coordinate is an option
	    if (!board.isCellInOptionArray(this.chosen)) {
		    	return 2;
	    } else  {
			    board.putChip(p.getChip(), chosen.getRow(), chosen.getCol());// putting chip on board and flipping chips accordingly
			    board.cleanOptionalMovesList();
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
		this.add(new Text("Curren player: " + this.curr.getChip()), this.board.getWidth() + 1, 1);
		this.add(new Text("Black player score: " + this.board.getBlackScore()), this.board.getWidth() + 1, 2);
		this.add(new Text("White player score: " + this.board.getWhiteScore()), this.board.getWidth() + 1, 3);
		
		//initializing board and starting.
		  this.board.initialize(this);
		  
			this.setOnMouseClicked(event -> {
				this.x = event.getX();
				this.y = event.getY();
				event.consume();
				playTurn();
			});
	}

	public void playTurn() {
		int played = 0;
		int last = 1;
		
		try {
			played = playTurn(this.curr, this.board);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if ((played == 0 && last == 0) || this.board.isBoardFull())
			try {
				endGame();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		last = played;
		
		if (played == 1) {
			if (this.curr.getChip() == Status.BLACK)
				this.curr = this.whitePlayer;
			else
				this.curr = this.blackPlayer;
		}
		
		this.board.calculateCurrentScore();
		played = 0;		
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