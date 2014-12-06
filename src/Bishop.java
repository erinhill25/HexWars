import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;


public class Bishop extends Unit{
    static int[] BLUE = {96,255};
    static int[] SECONDARY = {64,192};
    static int[] TAINT = {32,128};

	Tile[] bishopTiles = new Tile[6];
	
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
			
			/*
			 * Adjacent tile's adjacent tile at same index is appropriate tile to return as reachable
			 * 
			 */
			
			if(this.tile.getAdj(i) != null && this.tile.getAdj(i).getAdj(j) != null) {
				bishopTiles[i] = this.tile.getAdj(i).getAdj(j);
			}
			
		}
		
		return bishopTiles;
	}
	
	void render(Graphics2D g) {
	    if(owner==0){
            g.setPaint(new GradientPaint((float)(x-rad), (float)y, new Color(BLUE[0],TAINT[0]-32,TAINT[0]-32), 
                    (float)(x+rad), (float)y, new Color(BLUE[1],TAINT[1]-32,TAINT[1]-32)));
        }else{
            g.setPaint(new GradientPaint((float)(x-rad), (float)y, new Color(SECONDARY[0],TAINT[0],BLUE[0]), 
                    (float)(x+rad), (float)y, new Color(SECONDARY[1],TAINT[1],BLUE[1])));
        }
        Ellipse2D.Double ellipse = new Ellipse2D.Double(x-rad, y, rad*2, rad*16/15);
        Rectangle2D.Double rectangle = new Rectangle2D.Double(x-rad*2, y-rad*49/15, rad*4, rad*4);
        Arc2D.Double arc = new Arc2D.Double(rectangle, 240, 60, Arc2D.PIE);
        g.fill(arc);
        g.fill(ellipse);
        if(DEBUG){
            super.render(g);
        }
    }

}
