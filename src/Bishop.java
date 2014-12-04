
public class Bishop extends Unit{

	public Bishop(int owner, Tile tile, double radius) {
		super(owner, tile, radius);
		
		possibleMoves = 2;
	}
	
	//Use tile adj to find tiles adjacent to adjacent tiles
	public Tile[] getPossibleMoveLocations() {
		
		/*
		 * Movable tiles will be at most 6, moving only 2 spaces at a time.
		 * 
		 */
		Tile[] bishopTiles = new Tile[6];
		
		int j; // To keep track of i+1
		
		for(int i = 0; i < this.tile.getAdjs().length; i++) {
			
			/*
			 * If we are pulling adjacent tiles from adjacent tiles, and
			 * we want the second set of tiles to have the opposite
			 */
			if(i == 5)
				j = 0;
			else
				j = i+1;
			
			//Adjacent tile's adjacent tile at same index is appropriate tile
			bishopTiles[i] = this.tile.getAdj(i).getAdj(j);
		}
		
		return bishopTiles;
	}

}
