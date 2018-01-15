package reversi;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.GridPane;

public class Board {

	static final int DEFAULT_WIDTH = 8;
	static final int DEFAULT_LEGTH = 8;
	
	private int width;
	private int length;
	private Cell[][] CellArr;
	private List<Cell> optionalMoves;
	private GridPane grid; 
	
	private int blackScore = 2;
	private int whiteScore = 2;
	
	private int cellHeight;
	private int cellWidth;
	
	public Board(int wid, int len) {
		this.width = wid;
		this.length = len;
		
		this.CellArr = new Cell[this.width + 1][this.length + 1];
		this.optionalMoves = new ArrayList<Cell>();
 	}
	
	public Board() {
		this.width = DEFAULT_WIDTH;
		this.length = DEFAULT_LEGTH;
		
		this.CellArr = new Cell[this.width + 1][this.length + 1];
		this.optionalMoves = new ArrayList<Cell>();
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getLength() {
		return this.length;
	}
	
	public Cell[][] getCellArr() {
		return this.CellArr;
	}
	
	public Cell getSpecificCell(int row, int col) {
		return this.CellArr[row][col];
	}
	
	public int getBlackScore() {
		return this.blackScore;
	}
	
	public int getWhiteScore() {
		return this.whiteScore;
	}
	
	public void initialize(GridPane gp) {
		this.grid = gp;
		
		int i, j;
		int x = this.width/2;
		int y = this.length/2;
		
		int height = (int) this.grid.getPrefHeight();
		int width = (int) this.grid.getPrefWidth();
		
		this.cellHeight = height / this.length;
		this.cellWidth = width / this.CellArr[0].length;
		
		for (i = 0; i <= this.length; i++) {
			CellArr[i][0] = new Cell(Status.EMPTY, i, 0, this.grid);
		}
		
		for (i = 0; i <= this.width; i++) {
			CellArr[0][i] = new Cell(Status.EMPTY, 0, i, this.grid);
		}
		
		//initializing all clean cells
		for (i = 1; i <= this.length ; i++) {
			for (j = 1; j <= this.width; j++) {
				CellArr[i][j] = new Cell(Status.EMPTY, i, j, this.grid);
				CellArr[i][j].setStatus(this.cellHeight, this.cellWidth, Status.EMPTY);
			}
		}
		
		//putting first chips in middle of board
		CellArr[x][y].setStatus(this.cellHeight, this.cellWidth, Status.WHITE);
		CellArr[x + 1][y + 1].setStatus(this.cellHeight, this.cellWidth, Status.WHITE);
		CellArr[x][y + 1].setStatus(this.cellHeight, this.cellWidth, Status.BLACK);
		CellArr[x + 1][y].setStatus(this.cellHeight, this.cellWidth, Status.BLACK);
	}

	public Status reveal(int row, int col) {
		return this.CellArr[row][col].getStatus();
	}
	
	public void putChip(Status chip, int x, int y) {
		//making move of putting chip and calling to flipping chips accordingly.
	  CellArr[x][y].setStatus(this.cellHeight, this.cellWidth, chip);
		flipChips(chip, CellArr[x][y]);
		this.optionalMoves.remove(CellArr[x][y]);
		calculateCurrentScore();
	}
	
	public int doOneWay(Status player, int x, int dx, int y, int dy, boolean flip) {
		int chipCounter = 0;
		x = x + dx;
		y = y + dy;
		if (x < 0 || x > length || y < 0 || y > width) {
	        return 0;
	    }//out of bounds cell
	    if (reveal(x,y) == Status.EMPTY || reveal(x,y) == player) {
	        return 0;
	    }// no continuation in direction
	    else {
	        while (reveal(x,y) != player && reveal(x,y) != Status.EMPTY) {
	            x = x + dx;
	            y = y + dy;
	            chipCounter++;
	            if(x < 0 || x > length || y < 0 || y > width) {
	                return 0;
	            }// out of bounds
	            if(reveal(x,y) == player) {
	                if (flip) {
	                    x = x - dx;
	                    y = y - dy;
	                    while (reveal(x, y) != player) {
	                        CellArr[x][y].flip(this.cellHeight, this.cellWidth);
	                        x = x - dx;
	                        y = y - dy;
	                        if(x < 0 || x > length || y < 0 || y > width) return 0;
	                    }
	        }//flipping if cell was chosen
	        return chipCounter;
	            }
	        }
	    }
	    return 0;
	}
	
	public void flipChips(Status playerColr, Cell chosen) {
    doOneWay(playerColr, chosen.getRow(), -1, chosen.getCol(), 0, true);
    doOneWay(playerColr, chosen.getRow(), 1, chosen.getCol(), 0, true);
    doOneWay(playerColr, chosen.getRow(), -1, chosen.getCol(), 1, true);
    doOneWay(playerColr, chosen.getRow(), 1, chosen.getCol(), 1, true);
    doOneWay(playerColr, chosen.getRow(), 0, chosen.getCol(), 1, true);
    doOneWay(playerColr, chosen.getRow(), -1, chosen.getCol(), -1, true);
    doOneWay(playerColr, chosen.getRow(), 1, chosen.getCol(), -1, true);
    doOneWay(playerColr, chosen.getRow(), 0, chosen.getCol(), -1, true);
	}
	
	public List<Cell> getOptions() {
		return this.optionalMoves;
	}
	
	public void updateOptionalMovesList(Status s) {
		cleanOptionalMovesList();
		calculateOptions(s);
		turnOptional();
	}
	
	public void turnOptional() {
		for (int i = 0; i < this.optionalMoves.size(); i++)
			this.optionalMoves.get(i).setStatus(this.cellHeight, this.cellWidth, Status.OPTIONAL);
	}
	
	public void calculateOptions(Status player) {
		// loop over board finding valid cells.
	    for(int i = 1; i <= length; i++) {
	        for (int j = 1; j <= width; j++) {
	            if (CellArr[i][j].getStatus() == Status.EMPTY) {
	                if (doOneWay(player, i, -1, j, 0, false) != 0) {
	                	if (!isCellInOptionArray(CellArr[i][j])) {
	                    optionalMoves.add(CellArr[i][j]);
	                					}
	                }// check North
	                if (doOneWay(player, i, 1, j, 0, false) != 0) {
	                	if (!isCellInOptionArray(CellArr[i][j])) {
	                		optionalMoves.add(CellArr[i][j]);
	                					}
	                }// check South
	                if (doOneWay(player, i, 0, j,-1, false) != 0) {
	                	if (!isCellInOptionArray(CellArr[i][j])) {
	                		optionalMoves.add(CellArr[i][j]);
	                					}
	                }// check West
	                if (doOneWay(player, i, 0, j, 1, false) != 0) {
	                	if (!isCellInOptionArray(CellArr[i][j])) {
	                		optionalMoves.add(CellArr[i][j]);
	                					}
	                }// check East
	                if (doOneWay(player, i, -1, j,-1, false) != 0) {
	                	if (!isCellInOptionArray(CellArr[i][j])) {
	                		optionalMoves.add(CellArr[i][j]);
	                					}
	                }// check NE
	                if (doOneWay(player, i, 1, j, 1, false) != 0) {
	                	if (!isCellInOptionArray(CellArr[i][j])) {
	                		optionalMoves.add(CellArr[i][j]);
	                					}
	                }// check SE
	                if (doOneWay(player, i, 1, j,-1, false) != 0) {
	                	if (!isCellInOptionArray(CellArr[i][j])) {
	                		optionalMoves.add(CellArr[i][j]);
	                					}
	                }// check SW
	                if (doOneWay(player, i, -1, j, 1, false) != 0) {
	                	if (!isCellInOptionArray(CellArr[i][j])) {
	                		optionalMoves.add(CellArr[i][j]);
	                					}
	                }// check NE
	            }
	        }
	    }
}
	
	public boolean isCellInOptionArray(Cell check) {
    int i;

		//checking if given cell is an empty one
    if ((int)optionalMoves.size() == 0) {
	    return false;
    	}
		for (i = 0; i < (int)optionalMoves.size(); i++) {
			if (optionalMoves.get(i).getRow() == check.getRow() && optionalMoves.get(i).getCol() == check.getCol())
				return true;
		}

		return false;
	}
	
	public void cleanOptionalMovesList(){
		for (int i = 0; i < this.optionalMoves.size(); i++) {
			this.optionalMoves.get(i).setStatus(this.cellHeight, this.cellWidth, Status.EMPTY);
		}
			this.optionalMoves.clear();
	}
	
	
	public boolean isBoardFull() {
		int i, j;
		for (i = 1; i <= length; i++) {//checking in all cells contain chips.
			for (j = 1; j <= width; j++) {
				if (CellArr[i][j].getStatus() == Status.EMPTY || CellArr[i][j].getStatus() == Status.OPTIONAL) {
					return false;
				}
			}
		}

		return true;
	}
	
	public Status getWinner() {
		calculateCurrentScore();
	    if (this.blackScore > this.whiteScore) {
	        return  Status.BLACK;
	    } else if (this.blackScore < this.whiteScore) {
	        return  Status.WHITE;
	    } else {
	        return Status.EMPTY;
	    	}
	}
	
	public void calculateCurrentScore() {
	    int xCount = 0, oCount = 0;
	    for(int i = 0; i <= length; i++) {
	        for (int j = 0; j <= width; j++) {
	        	  if (CellArr[i][j].getStatus() == Status.BLACK) {
	        		  ++xCount;
				  	  	  	}
	        	  if (CellArr[i][j].getStatus() == Status.WHITE) {
	        	      ++oCount;
				  	  	  	}
					}
	    } // going over board and counting
	    
	    this.blackScore = xCount;
	    this.whiteScore = oCount;
	}
	
	public int[] locationOfPoint(double x, double y) {
		int i, j;
		
		int increasingRow = this.cellHeight;
		int increasingCol = this.cellWidth;
		
		//finding the point's row
		for (i = 1; i <= this.length; i++) {
			if ((int) y <= (i * increasingRow))
				break;
		}
		
		//finding the point's col
		for (j = 1; j <= this.width; j++) {
			if ((int) x <= (j * increasingCol))
				break;
		}
		
		int[] location = {i, j};
		return location;
	}
}