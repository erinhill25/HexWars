import javax.swing.*;

import java.awt.*;

public class Tile extends Entity {

	protected Polygon poly;
	protected Color color;
	
	protected Unit unit; 
	
	protected final int BORDER = 8;
	
	protected int x, y;
	
	public boolean isActive = true, isHighlighted = false, isAccessible = false;
	
	
	Tile(Polygon poly) {
		this.poly = poly;
		this.isActive = true;
		this.isHighlighted=false;
		this.isAccessible = false;
	}
	
	Tile(Polygon poly, Color color, boolean isActive, int x, int y) {
		this.poly = poly;
		this.color = color;
		this.isActive = isActive;
		this.y = y;
		this.x = x;
	}
	
	public Unit getUnit() {
		return Unit;
	}
	
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public boolean isHighlighted() {
		return isHighlighted;
	}
	
	public boolean isAccessible() {
		return isAccessible;
	}
	
	public void setAccessible(boolean accessible) { 
		this.isAccessible = accessible;
	}
	
	public void setHiglighted(boolean highlighted) {
		this.isHighlighted = highlighted;
	}
	
	public void setActive(Boolean active) {
		this.isActive = active;
	}
	
	public void render(Graphics2D g) {
		
		g.setColor(color);
		g.fillPolygon(poly);
		
		if(isHighlighted) {
			g.setColor(Color.YELLOW);
			g.setStroke(new BasicStroke(BORDER));
			g.drawPolygon(poly);
		}
		
		else if(isAccessible) {
			g.setColor(Color.RED);
			g.setStroke(new BasicStroke(BORDER));
			g.drawPolygon(poly);
		}
		
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getX() {
		return this.x;
	}
	
	
	Polygon getPoly() {
		
		return this.poly;
	}
	
}
