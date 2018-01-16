package reversi;

public class Player {

	private Status type;
	private String color;
	
	public Player() {
		this.type = Status.BLACK;
		this.color = "black";
	}
	
	public Player(Status s, String c) {
		this.type = s;
		this.color = c;
	}
	
	public Status getChip() {
	    return this.type;
	}

	public String getColor() {
		return this.color;
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