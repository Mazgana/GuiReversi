package reversi;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameFlow extends GridPane {
	
	private Player blackPlayer;
	private Player whitePlayer;
	private Board board;
	
	public GameFlow() {
		this.blackPlayer = new Player(Status.BLACK);
		this.whitePlayer = new Player(Status.WHITE);
		this.board = new Board();
		
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
		int cellHeight = height / this.board.getLength();
		int cellWidth = width / this.board.getCellArr()[0].length;
		for (int i = 0; i < this.board.getLength(); i++) {
			for (int j = 0; j < this.board.getCellArr()[i].length; j++) {
				if (this.board.getCellArr()[i][j].getStatus() == Status.BLACK)
					this.add(new Rectangle(cellWidth, cellHeight, Color.BLACK), j, i);
				else if (this.board.getCellArr()[i][j].getStatus() == Status.WHITE)
					this.add(new Rectangle(cellWidth, cellHeight, Color.WHITE), j, i);
				else
					this.add(new Rectangle(cellWidth, cellHeight, Color.ORANGE), j, i);
			}
		}
	}

	public int playTurn(Player p, Board board) throws IOException {
	    List<Cell> options = board.getOptions(p.getChip());
	    if (options.isEmpty()) {
		    	System.out.println(p.getChip().toString() + ": you have got no moves.");
		    	System.out.println("press enter to continue..");
		    	System.in.read();

	        return 0;
	    }//no moves can be done, turn passes to other player
	    
	    Cell chosen;
	    chosen = p.doTurn(options, board.getWidth(), board.getLength());//getting cell to play

	    board.putChip(p.getChip(), chosen.getRow(), chosen.getCol());// putting chip on board and flipping chips accordingly
	    board.cleanOptionalMovesList();

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
		System.out.println("Welcome to Reversi!");
		
		//initializing board and starting.
	    int oPlayed = 1, xPlayed;
	    boolean closed = false;
	    this.board.initialize();
	    this.board.printBoard();

	    //playing game, 1 round per player.
	    while (!board.isBoardFull()) {
	        xPlayed = playTurn(this.getBlackPlayer(), this.board);
	        if (xPlayed == 0 && oPlayed == 0) {
	            //when no more moves can be done.
	            break;
	        } else if (xPlayed == 2) {
	        	//X closed the game
	        	closed = true;
	        	break;
	        		}

	        this.board.printBoard();

	        oPlayed = playTurn(this.getWhitePlayer(), this.board);
	        if (xPlayed == 0 && oPlayed == 0) {
	        	//when no more moves can be done.
	        	break;
	        } else if (oPlayed == 2) {
	        	//O closed the game
	        	closed = true;
	        	break;
	        		}

	        this.board.printBoard();
	    	}

			if (closed) {
				endGame(2); // One of the players closed the game
			} else {
				endGame(1); // The game ended
			}
	}

	//ending game and declaring winner.
	public void endGame(int cause) throws IOException {
		    //ending game and announcing winner
				if (cause == 1) {
					 System.out.println("GAME ENDED!");
				}

		    Status winner = board.getWinner();
		    if(winner == Status.EMPTY) {
		    	System.out.println("It's a tie!");
		    	}
		    else {
		    	System.out.println("Player " + winner.toString() + " wins!");
		    	}

			run();
	}
}