import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.*;


public class King extends Unit {
	
	public King(int owner, Tile tile, double radius) {
		super(owner, tile, radius);
		possibleMoves = 5;
	}

	void render(Graphics2D g) {
	    
        g.setPaint(new GradientPaint(0, 0, new Color(6, 28, 100), 20, 20,
           new Color(5, 7, 100, 27), true));
        Ellipse2D.Double ellipse = new Ellipse2D.Double(x-rad, y, rad*2, rad*16/15);
        Rectangle2D.Double rectangle = new Rectangle2D.Double(x-rad*2, y-rad*49/15, rad*4, rad*4);
        Arc2D.Double arc = new Arc2D.Double(rectangle, 240, 60, Arc2D.PIE);
        //g.fill(rectangle);
        g.fill(arc);
        g.fill(ellipse);
    }

}
