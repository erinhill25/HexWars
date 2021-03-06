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
		int k; // To keep track of i-1
		
		//nullify old movable tiles
		for(int i = 0; i < 6; i++) {
			bishopTiles[i] = null;
		}
		
		Tile[] adjs = tile.getAdjs();
		for(int i = 0; i < adjs.length; i++) {
			
			/*
			 * If we are pulling adjacent tiles from adjacent tiles, and
			 * we want the second set of tiles to have the opposite
			 */
			
			if(i == 5)
				j = 0;
			else
				j = i+1;
			
			if(i == 0)
			    k = 5;
			else
			    k = i-1;
			
			/*
			 * Adjacent tile's adjacent tile at same index is appropriate tile to return as reachable
			 * 
			 */
			if(adjs[i] != null){
			    bishopTiles[i] = adjs[i].getAdj(j);
			    if(bishopTiles[k] == null){
			        bishopTiles[k] = adjs[i].getAdj(k);
			    }
			}
		}
		
		return bishopTiles;
	}
	
	void render(Graphics2D g) {
		
		int alpha = (movesRemaining > 0) ? 255 : 128;
		
	    if(owner==0){
            g.setPaint(new GradientPaint((float)(x-rad), (float)y, new Color(BLUE[0],TAINT[0]-32,TAINT[0]-32, alpha), 
                    (float)(x+rad), (float)y, new Color(BLUE[1],TAINT[1]-32,TAINT[1]-32, alpha)));
        }else{
            g.setPaint(new GradientPaint((float)(x-rad), (float)y, new Color(SECONDARY[0],TAINT[0],BLUE[0], alpha), 
                    (float)(x+rad), (float)y, new Color(SECONDARY[1],TAINT[1],BLUE[1], alpha)));
        }
        Ellipse2D.Double ellipse = new Ellipse2D.Double(x-rad, y, rad*2, rad*16/15);
        Rectangle2D.Double rectangle = new Rectangle2D.Double(x-rad*2, y-rad*49/15, rad*4, rad*4);
        Arc2D.Double arc = new Arc2D.Double(rectangle, 240, 60, Arc2D.PIE);
        g.fill(arc);
        g.fill(ellipse);
        if(showIDs){
            super.render(g);
        }
    }

}
