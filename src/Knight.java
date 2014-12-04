
public class Knight extends Unit{

	public Knight(int owner, Tile tile, double radius) {
		super(owner, tile, radius);
		
		possibleMoves = 2;
	}
	
	
	//Use tile adj to find tiles adjacent to adjacent tiles
	public Tile[] getPossibleMoveLocations() {
		
		/*
		 * Movable tiles will be at most 6, moving only 2 spaces at a time.
		 * 
		 */
		Tile[] knightTiles = new Tile[6];
		
		for(int i = 0; i < this.tile.getAdjs().length; i++) {
			
			//Adjacent tile's adjacent tile at same index is appropriate tile
			knightTiles[i] = this.tile.getAdj(i).getAdj(i);
			
		}
		return knightTiles;
	}

}
