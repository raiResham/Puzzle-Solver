
public class Piece{
	int x;
	int y;
	char position;
	
	
	public Piece(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Piece(int x, int y, char position) {
		this.x = x;
		this.y = y;
		this.position = position;
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+") - "+position;
	}
	
	// \u000d
}
