import javax.swing.*;

import java.awt.*;

public class Tile extends Entity {

	protected Polygon poly;
	protected Color color;
	
	public static enum States {ACTIVE, INACTIVE};
	
	protected States state;
	
	Tile(Polygon poly) {
		this.poly = poly;
		this.state = States.ACTIVE;
	}
	
	Tile(Polygon poly, Color color, States state) {
		this.poly = poly;
		this.color = color;
		this.state = state;
	}
	
	
	public void render(Graphics2D g) {
		
		g.setColor(color);
		g.fillPolygon(poly);
		g.setColor(Color.BLACK);
		//g.drawPolygon(hex);
		
	}
	
}
