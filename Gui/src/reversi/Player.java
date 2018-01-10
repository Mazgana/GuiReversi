package reversi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Player {

	private Status type;
	
	public Player() {
		this.type = Status.BLACK;
	}
	
	public Player(Status s) {
		this.type = s;
	}
	
	public int[] doTurn(List<Cell> options, int maxWidth, int maxLength) throws IOException {
	    int i;
	    System.out.println(type.getValue() + ": It's your move.");
	    System.out.println("your possible moves:");

	    for (i = 0; i < (int)options.size(); i++) { //getting move from console
	    	options.get(i).printCell();
	    	System.out.print(" ");
	        }
	    System.out.println("\n");

	    //validating choice
	    int x = 0, y = 0;
	    boolean valid = false;
	    char[] integers;
	    String input;

	    while (!valid) {
						System.out.println("Please enter your move row,col: ");

						BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
						input = br.readLine();

						//extracting two integers from the player's choice
						integers = input.toCharArray();
						x = (int) integers[0] - 48;

						if (integers[1] == ',') {
							y = (int) integers[2] - 48;
						} else {
							y = (int) integers[1] - 48;
						}

//						if (0 > x || x > maxWidth || y < 0 || y > maxLength) {
//							displayer->clearBuffer();
//				      displayer->ignoreInput('\n');
//							displayer->printMessageWitheNewLine("Invalid input!");
//							displayer->clearBuffer();
//						} else {
							//validate that the player's choice is one of the given options
							for (i = 0; i < (int)options.size(); i++) {
								if (options.get(i).getRow() == x && options.get(i).getCol() == y) {
									valid = true;
									break;
								}
							}

							if (!valid) {
								System.out.println("That is not an option.");
//								displayer->getBufferContent();
							}
//						}
	    	}
	    int[] c = {x,y};//returning console choice
	    return c;
	}
	
	public Status getChip() {
	    return this.type;
	}

	public Status getOppositeType() {
		if (this.type == Status.BLACK) {
			return Status.WHITE;
		} else {
			return Status.BLACK;
		}
	}

	public boolean isComp() {
	    return false;
	}
}