package reversi;
/*
 * Player class represent a reversi game player. It holds it's chips and it's own color.
 */
public class Player {

	private Status type;
	private String color;
	
	//constructor
	public Player(Status s, String c) {
		this.type = s;
		this.color = c;
	}
	
	//returning players chip
	public Status getChip(){
	    return this.type;
	}

	//returning players color
	public String getColor() {
		return this.color;
	}
	
	//returning opponents chip according to own chip
	public Status getOppositeType() {
		if (this.type == Status.BLACK) {
			return Status.WHITE;
		} else {
			return Status.BLACK;
		}
	}
}