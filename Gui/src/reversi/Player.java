package reversi;

public class Player {

	private Status type;
	
	public Player() {
		this.type = Status.BLACK;
	}
	
	public Player(Status s) {
		this.type = s;
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