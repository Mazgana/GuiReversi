package reversi;

import java.util.ArrayList;
import java.util.List;

public class Board {

	static final int DEFAULT_WIDTH = 8;
	static final int DEFAULT_LEGTH = 8;
	
	private int width;
	private int length;
	private Cell[][] CellArr;
	private List<Cell> optionalMoves;
	
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
	
	//copy constructor
	public Board(Board b) {
		this(b.getWidth(), b.getLength());
		this.CellArr = b.getCellArr();
	}
	
	public Cell getSpecificCell(int row, int col) {
		return this.CellArr[row][col];
	}
	
	public void initialize() {
		int i, j;
		int x = this.width/2;
		int y = this.length/2;
				
		//initializing all clean cells
		for (i = 0; i <= this.length ; i++) {
			for (j = 0; j <= this.width; j++) {
				CellArr[i][j] = new Cell(Status.EMPTY, i, j);
			}
		}
		
		//putting first chips in middle of board
		Cell whiteCell = new Cell(Status.WHITE, x, y);
		CellArr[x][y] = whiteCell;
		Cell whiteCell2 = new Cell(Status.WHITE, x + 1, y + 1);
		CellArr[x + 1][y + 1] = whiteCell2;
		Cell blackCell = new Cell(Status.BLACK, x, y + 1);
		CellArr[x][y + 1] = blackCell;
		Cell blackCell2 = new Cell(Status.BLACK, x + 1, y);
		CellArr[x + 1][y] = blackCell2;
	}

	public Status reveal(int row, int col) {
		return this.CellArr[row][col].getStatus();
	}
	
	public void putChip(Status chip, int x, int y) {
		//making move of putting chip and calling to flipping chips accordingly.
	  CellArr[x][y].setStatus(chip);
		flipChips(chip, CellArr[x][y]);
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
	                        CellArr[x][y].flip();
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
	
	public List<Cell> getOptions(Status player) {
		List<Cell> options = new ArrayList<Cell>();

		// loop over board finding valid cells.
	    for(int i = 1; i <= length; i++) {
	        for (int j = 1; j <= width; j++) {
	            if (CellArr[i][j].getStatus() == Status.EMPTY) {
	                if (doOneWay(player, i, -1, j, 0, false) != 0) {
	                	if (!isCellInOptionArray(CellArr[i][j]))
	                    optionalMoves.add(CellArr[i][j]);
	                }// check North
	                if (doOneWay(player, i, 1, j, 0, false) != 0) {
	                	if (!isCellInOptionArray(CellArr[i][j]))
	                		optionalMoves.add(CellArr[i][j]);
	                }// check South
	                if (doOneWay(player, i, 0, j,-1, false) != 0) {
	                	if (!isCellInOptionArray(CellArr[i][j]))
	                		optionalMoves.add(CellArr[i][j]);
	                }// check West
	                if (doOneWay(player, i, 0, j, 1, false) != 0) {
	                	if (!isCellInOptionArray(CellArr[i][j]))
	                		optionalMoves.add(CellArr[i][j]);
	                }// check East
	                if (doOneWay(player, i, -1, j,-1, false) != 0) {
	                	if (!isCellInOptionArray(CellArr[i][j]))
	                		optionalMoves.add(CellArr[i][j]);
	                }// check NE
	                if (doOneWay(player, i, 1, j, 1, false) != 0) {
	                	if (!isCellInOptionArray(CellArr[i][j]))
	                		optionalMoves.add(CellArr[i][j]);
	                }// check SE
	                if (doOneWay(player, i, 1, j,-1, false) != 0) {
	                	if (!isCellInOptionArray(CellArr[i][j]))
	                		optionalMoves.add(CellArr[i][j]);
	                }// check SW
	                if (doOneWay(player, i, -1, j, 1, false) != 0) {
	                	if (!isCellInOptionArray(CellArr[i][j]))
	                		optionalMoves.add(CellArr[i][j]);
	                }// check NE
	            }
	        }
	    }
	    options = optionalMoves;
	    return options;
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
			optionalMoves.clear();
	}
	
	
	public boolean isBoardFull() {
		int i, j;
		for (i = 1; i <= length; i++) {//checking in all cells contain chips.
			for (j = 1; j <= width; j++) {
				if (CellArr[i][j].getStatus() == Status.EMPTY) {
					return false;
				}
			}
		}

		return true;
	}
	
	public Status getWinner() {
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
	    if (xCount > oCount) {
	        return  Status.BLACK;
	    } else if (xCount < oCount) {
	        return  Status.WHITE;
	    } else {
	        return Status.EMPTY;
	    	}
	}
	
	public void printBoard() {
		int i, j, k;
		for (i = 0; i <= this.length; i++) {//printing board in given format
			for (j = 0; j <= this.width; j++) {
				if ((i == 0) && (j == 0)){
					System.out.print("  |");
				} else if ((i == 0) && (j != 0)) {
					System.out.print(" " + j + " |");
				} else if ((j == 0) && (i != 0)) {
					System.out.print(i + " |");
				} else {
					System.out.print(" " + this.getSpecificCell(i, j).getStatus().getValue() + " |");
				}
			}

			System.out.println("");;
			for (k = 0; k <= (this.width); k++) {
				System.out.print("----");
			}
			System.out.println("");;
		}
	}
}