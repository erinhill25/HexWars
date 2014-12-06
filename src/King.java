import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.*;


public class King extends Unit {
    static int[] BLUE = {96,255,148,204};
    static int[] SECONDARY = {64,192,102,153};
    static int[] TAINT = {32,128,56,102};
	
	public King(int owner, Tile tile, double radius) {
		super(owner, tile, radius);
		possibleMoves = 3;
	}

	void render(Graphics2D g) {
        if(owner==0){
            g.setColor(Color.RED);
        }else{
            g.setColor(new Color(0x8000FF));
        }
        Ellipse2D.Double ellipse = new Ellipse2D.Double(x-rad, y-rad, rad*2, rad*2);
        g.setStroke(new BasicStroke(5));
        g.draw(ellipse);
        Point2D center = new Point2D.Double(x, y);
        float[] dist = {0.0f, 1.0f};
        Arc2D.Double arc;
        Rectangle2D.Double rectangle;
        if(owner==0){
            Color[] colors = {new Color(BLUE[2],TAINT[2]-32,TAINT[2]-32),
                    new Color(BLUE[3],TAINT[3]-32,TAINT[3]-32)};
            RadialGradientPaint p =
                new RadialGradientPaint(center, (float)(rad/2), dist, colors);
            g.setPaint(p);
        }else{
            Color[] colors = {new Color(SECONDARY[2],TAINT[2],BLUE[2]),
                    new Color(SECONDARY[3],TAINT[3],BLUE[3])};
            RadialGradientPaint p =
                new RadialGradientPaint(center, (float)(rad/2), dist, colors);
            g.setPaint(p);
        }
        rectangle = new Rectangle2D.Double(x-rad/2, y-rad/2, rad, rad);
        arc = new Arc2D.Double(rectangle, 75, 120, Arc2D.CHORD);
        g.fill(arc);
        arc = new Arc2D.Double(rectangle, 195, 120, Arc2D.CHORD);
        g.fill(arc);
        arc = new Arc2D.Double(rectangle, 315, 120, Arc2D.CHORD);
        g.fill(arc);
        if(owner==0){
            Color[] colors = {new Color(BLUE[0],TAINT[0]-32,TAINT[0]-32),
                    new Color(BLUE[1],TAINT[1]-32,TAINT[1]-32)};
            RadialGradientPaint p =
                new RadialGradientPaint(center, (float)rad, dist, colors);
            g.setPaint(p);
        }else{
            Color[] colors = {new Color(SECONDARY[0],TAINT[0],BLUE[0]),
                    new Color(SECONDARY[1],TAINT[1],BLUE[1])};
            RadialGradientPaint p =
                new RadialGradientPaint(center, (float)rad, dist, colors);
            g.setPaint(p);
        }
        rectangle = new Rectangle2D.Double(x-rad, y-rad, rad*2, rad*2);
        arc = new Arc2D.Double(rectangle, 0, 30, Arc2D.PIE);
        g.fill(arc);
        arc = new Arc2D.Double(rectangle, 120, 30, Arc2D.PIE);
        g.fill(arc);
        arc = new Arc2D.Double(rectangle, 240, 30, Arc2D.PIE);
        g.fill(arc);
        if(owner==0){
            Color[] colors = {new Color(BLUE[1],TAINT[1]-32,TAINT[1]-32),
                    new Color(BLUE[0],TAINT[0]-32,TAINT[0]-32)};
            RadialGradientPaint p =
                new RadialGradientPaint(center, (float)(rad*2/15), dist, colors);
            g.setPaint(p);
        }else{
            Color[] colors = {new Color(SECONDARY[1],TAINT[1],BLUE[1]),
                    new Color(SECONDARY[0],TAINT[0],BLUE[0])};
            RadialGradientPaint p =
                new RadialGradientPaint(center, (float)(rad*2/15), dist, colors);
            g.setPaint(p);
        }
        ellipse = new Ellipse2D.Double(x-rad*2/15, y-rad*2/15, rad*4/15, rad*4/15);
        g.fill(ellipse);
        if(DEBUG){
            super.render(g);
        }
    }
	
}
