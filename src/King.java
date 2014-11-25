import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.*;


public class King extends Unit {
    static int[] BLUE = {96,255};
    static int[] SECONDARY = {64,192};
    static int[] TAINT = {32,128};
	
	public King(int owner, Tile tile, double radius) {
		super(owner, tile, radius);
		possibleMoves = 8;
	}

	void render(Graphics2D g) {
	    
	    if(owner==0){
            g.setPaint(new GradientPaint((float)(x-rad), (float)y, new Color(TAINT[0],SECONDARY[0],BLUE[0]), 
                    (float)(x+rad), (float)y, new Color(TAINT[1],SECONDARY[1],BLUE[1])));
        }else{
            g.setPaint(new GradientPaint((float)(x-rad), (float)y, new Color(SECONDARY[0],TAINT[0],BLUE[0]), 
                    (float)(x+rad), (float)y, new Color(SECONDARY[1],TAINT[1],BLUE[1])));
        }
        Ellipse2D.Double ellipse = new Ellipse2D.Double(x-rad, y, rad*2, rad*16/15);
        Rectangle2D.Double rectangle = new Rectangle2D.Double(x-rad*2, y-rad*49/15, rad*4, rad*4);
        Arc2D.Double arc = new Arc2D.Double(rectangle, 240, 60, Arc2D.PIE);
        g.fill(arc);
        g.fill(ellipse);
    }

}
