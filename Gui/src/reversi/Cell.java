package reversi;

enum Status {
	BLACK("X"),
	WHITE("O"), 
	EMPTY(" ");

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
	
	public Cell() {
		cellStatus = Status.EMPTY;
		row = 0;
		col = 0;
	}
	
	public Cell(Status s, int r, int c) {
		cellStatus = s;
		row = r;
		col = c;
	}
	
	public Cell(int r, int c) {
		cellStatus = Status.EMPTY;
		row = r;
		col = c;
	}
	
	public Status getStatus() {
		return this.cellStatus;
	}
	
	public void setNewStatus(Status newStat) {
		this.cellStatus = newStat;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public void setStatus(Status newStatus) {
		this.cellStatus = newStatus;
	}
	
	public void flip() {
		  if (this.cellStatus == Status.BLACK) {
			  this.cellStatus = Status.WHITE;
		  } else if (this.cellStatus == Status.WHITE) {
			  this.cellStatus = Status.BLACK;
		  }
	}
	
	public void printCell() {
		System.out.print("(" + this.row + "," + this.col + ")");
	}
}