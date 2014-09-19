import java.awt.*;


public class Player extends Entity {

	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public void render(Graphics2D g) {
		
		g.setPaint(new GradientPaint(0, 0, new Color(0, 150, 0), 20, 20,
		        new Color(0, 150, 0, 0), true));
		 g.fillRect(x, y, 200, 200);
		
	}
}
