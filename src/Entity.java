import java.awt.Graphics2D;

public abstract class Entity {

	protected int x,y;
	
	abstract void render(Graphics2D g);
	
	abstract void update();
}
