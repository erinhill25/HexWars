import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;


public class Knight extends Unit{
    static int[] BLUE = {96,255};
    static int[] SECONDARY = {64,192};
    static int[] TAINT = {32,128};
	
	public Knight(int owner, Tile tile, double radius) {
		super(owner, tile, radius);
		
		possibleMoves = 2;
	}
	
	
	//Use tile adj to find tiles adjacent to adjacent tiles
	public Tile[] getPossibleMoveLocations() {
		
		Tile[] knightTiles = new Tile[6];
		
		/*
		 * Movable tiles will be at most 6, moving only 2 spaces at a time.
		 * 
		 */
		
		for(int i = 0; i < this.tile.getAdjs().length; i++) {
			
			if(this.tile.getAdj(i) != null && this.tile.getAdj(i).getAdj(i) != null) {
				//Adjacent tile's adjacent tile at same index is appropriate tile
				knightTiles[i] = this.tile.getAdj(i).getAdj(i);
			}
			
		}
		return knightTiles;
	}

	
	void render(Graphics2D g) {
	    if(owner==0){
            g.setPaint(new GradientPaint((float)(x-rad/4), (float)y, new Color(TAINT[0],SECONDARY[0],BLUE[0]), 
                    (float)(x+rad/4), (float)y, new Color(TAINT[1],SECONDARY[1],BLUE[1])));
        }else{
            g.setPaint(new GradientPaint((float)(x-rad/4), (float)y, new Color(SECONDARY[0],TAINT[0],BLUE[0]), 
                    (float)(x+rad/4), (float)y, new Color(SECONDARY[1],TAINT[1],BLUE[1])));
        }
        Rectangle2D.Double rectangle = new Rectangle2D.Double(x-rad/4, y, rad/2, rad*10/15);
        Ellipse2D.Double bottomEllipse = new Ellipse2D.Double(x-rad/4, y+rad*8/15, rad/2, rad*4/15);
        g.fill(rectangle);
        g.fill(bottomEllipse);
        if(owner==0){
            g.setPaint(new GradientPaint((float)(x-rad), (float)y, new Color(TAINT[0],SECONDARY[0],BLUE[0]), 
                    (float)(x+rad), (float)y, new Color(TAINT[1],SECONDARY[1],BLUE[1])));
        }else{
            g.setPaint(new GradientPaint((float)(x-rad), (float)y, new Color(SECONDARY[0],TAINT[0],BLUE[0]), 
                    (float)(x+rad), (float)y, new Color(SECONDARY[1],TAINT[1],BLUE[1])));
        }
        Ellipse2D.Double topEllipse = new Ellipse2D.Double(x-rad, y-rad*.8, rad*2, rad*16/15);
        Arc2D.Double arc = new Arc2D.Double(x-rad, y-rad*16/15, rad*2, rad*1.6, 0, 180, Arc2D.PIE);
        g.fill(topEllipse);
        g.fill(arc);
    }
}
