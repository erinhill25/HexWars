import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.*;


public class Soldier extends Unit {
    static int[] BLUE = {96,255,148,204};
    static int[] SECONDARY = {64,192,102,153};
    static int[] TAINT = {32,128,56,102};

	public Soldier(int owner, Tile tile, double radius) {
		super(owner, tile, radius);
		possibleMoves = 3;
	}
	
	void render(Graphics2D g) {
	    Rectangle2D.Double rectangle = new Rectangle2D.Double(x-rad, y-rad*4/15, rad*2, rad*8/15);
        Ellipse2D.Double bottomEllipse = new Ellipse2D.Double(x-rad, y-rad*4/15, rad*2, rad*16/15);
	    if(owner==0){
	        g.setPaint(new GradientPaint((float)(x-rad), (float)y, new Color(BLUE[0],TAINT[0]-32,TAINT[0]-32), 
	                (float)(x+rad), (float)y, new Color(BLUE[1],TAINT[1]-32,TAINT[1]-32)));
	    }else{
	        g.setPaint(new GradientPaint((float)(x-rad), (float)y, new Color(SECONDARY[0],TAINT[0],BLUE[0]), 
                    (float)(x+rad), (float)y, new Color(SECONDARY[1],TAINT[1],BLUE[1])));
	    }
	    g.fill(rectangle);
	    g.fill(bottomEllipse);
        Ellipse2D.Double topEllipse = new Ellipse2D.Double(x-rad, y-rad*.8, rad*2, rad*16/15);
        if(owner==0){
            g.setPaint(new GradientPaint((float)(x-rad), (float)y, new Color(BLUE[2],TAINT[2]-32,TAINT[2]-32), 
                    (float)(x+rad), (float)y, new Color(BLUE[3],TAINT[3]-32,TAINT[3]-32)));
        }else{
            g.setPaint(new GradientPaint((float)(x-rad), (float)y, new Color(SECONDARY[2],TAINT[2],BLUE[2]), 
                    (float)(x+rad), (float)y, new Color(SECONDARY[3],TAINT[3],BLUE[3])));
        }
        g.fill(topEllipse);
        if(DEBUG){
            super.render(g);
        }
    }

}
