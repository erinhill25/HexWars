import java.awt.*;


public class Unit extends Entity {

	int owner;
	int movesRemaining, possibleMoves;
	int destinationX = -1, destinationY = -1;
	
	public Unit(int owner, Tile tile) {
		
		x = tile.getX();
		y = tile.getY();
		
		if(owner == 1 || owner == 0) 
			this.owner = owner;
		else
			System.out.println("Owner out of bounds, player number either 0 or 1.");
		
		movesRemaining = possibleMoves;
	}
	
	void render(Graphics2D g) {
	
		g.setPaint(new GradientPaint(0, 0, new Color(6, 28, 100), 20, 20,
	       new Color(5, 7, 100, 27), true));
		g.fillOval(x-25, y-25, 50, 50);
	}

	void update() {
		
		if(destinationX == x) 
			destinationX = -1;
		if(destinationY == y)
			destinationY = -1;
		
		if(destinationX != -1)
			x=(destinationX < x) ? x-- : x++;
		
		if(destinationY != -1)
			y=(destinationY < y) ? y-- : y++;
		
	}

	public void setDestination(Tile tile) {
		destinationX = tile.getX();
		destinationY = tile.getY();
	}

	public int getPlayer() {return owner;}
	
	public void setMovesRemaining(int movesRemaining) {this.movesRemaining = movesRemaining;}
	
	public int getMovesRemaining() {return movesRemaining;}
	
	public int getPossibleMoves() {return possibleMoves;}
	
}