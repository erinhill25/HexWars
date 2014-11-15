import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.*;


public class Soldier extends Unit {

	public Soldier(int owner, Tile tile, double radius) {
		super(owner, tile, radius);
		possibleMoves = 1;
	}
	
	void render(Graphics2D g) {
	    
        g.setPaint(new GradientPaint(0, 0, new Color(6, 28, 100), 20, 20,
           new Color(5, 7, 100, 27), true));
        Ellipse2D.Double topEllipse = new Ellipse2D.Double(x-rad, y-rad*.8, rad*2, rad*16/15);
        Rectangle2D.Double rectangle = new Rectangle2D.Double(x-rad, y-rad*4/15, rad*2, rad*8/15);
        Ellipse2D.Double bottomEllipse = new Ellipse2D.Double(x-rad, y-rad*4/15, rad*2, rad*16/15);
        g.fill(bottomEllipse);
        g.fill(rectangle);
        g.fill(topEllipse);
    }

}
