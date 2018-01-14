package reversi;

import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

enum Status {
	BLACK("X"),
	WHITE("O"), 
	EMPTY(" "),
	OPTIONAL("P");

	private String stat;
	
	Status(String s) {
		this.stat = s;
	}

	public String getValue() {
	    return this.stat;
	}
};

public class Cell {
	private Status cellStatus;
	private int row;
	private int col;
	private GridPane grid;
	private ImageView ivWhite;
	private ImageView ivBlack;
	private ImageView ivEmpty;
	private ImageView ivOptional;
	
	public Cell(GridPane gp) {
		this.cellStatus = Status.EMPTY;
		this.row = 0;
		this.col = 0;
		this.grid = gp;
	}
	
	public Cell(Status s, int r, int c, GridPane gp) {
			this.cellStatus = s;
			this.row = r;
			this.col = c;
			this.grid = gp;
			
			this.ivWhite = new ImageView(getClass().getResource("white.PNG").toExternalForm());
			this.ivBlack = new ImageView(getClass().getResource("black.PNG").toExternalForm());
			this.ivEmpty = new ImageView(getClass().getResource("empty.PNG").toExternalForm());
			this.ivOptional = new ImageView(getClass().getResource("option.PNG").toExternalForm());
	}
	
	public Cell(int r, int c, GridPane gp) {
			this.cellStatus = Status.EMPTY;
			this.row = r;
			this.col = c;
			this.grid = gp;
	}
	
	public Status getStatus() {
			return this.cellStatus;
	}
	
	public int getRow() {
			return this.row;
	}
	
	public int getCol() {
			return this.col;
	}
	
	public void setStatus(int cellHeight, int cellWidth, Status s) {
			if (s == Status.EMPTY) {
				this.grid.getChildren().remove(this.ivOptional);
				this.ivEmpty.setFitHeight(cellHeight);
				this.ivEmpty.setFitWidth(cellWidth);
				this.grid.add(this.ivEmpty, this.col, this.row);
				
			} else {
				this.grid.getChildren().remove(this.ivEmpty);
				
				if (s == Status.OPTIONAL) {
					this.ivOptional.setFitHeight(cellHeight);
					this.ivOptional.setFitWidth(cellWidth);
					this.grid.add(this.ivOptional, this.col, this.row);
				} else	if (s == Status.WHITE) {
					this.ivWhite.setFitHeight(cellHeight);
					this.ivWhite.setFitWidth(cellWidth);
					this.grid.add(this.ivWhite, this.col, this.row);
				} else if (s == Status.BLACK) {
					this.ivBlack.setFitHeight(cellHeight);
					this.ivBlack.setFitWidth(cellWidth);
					this.grid.add(this.ivBlack, this.col, this.row);
				}
			}
			
			this.cellStatus = s;
	}
	
	public void flip(int cellHeight, int cellWidth) {
		  if (this.cellStatus == Status.BLACK) {
			  this.grid.getChildren().remove(this.ivBlack);
				this.ivWhite.setFitHeight(cellHeight);
				this.ivWhite.setFitWidth(cellWidth);
			  this.grid.add(this.ivWhite, this.col, this.row);
			  
			  this.cellStatus = Status.WHITE;
		  } else if (this.cellStatus == Status.WHITE) {
			  this.grid.getChildren().remove(this.ivWhite);
				this.ivBlack.setFitHeight(cellHeight);
				this.ivBlack.setFitWidth(cellWidth);
			  this.grid.add(this.ivBlack, this.col, this.row);
			  
			  this.cellStatus = Status.BLACK;
		  }
	}
	
	public void printCell() {
		System.out.print("(" + this.row + "," + this.col + ")");
	}
}