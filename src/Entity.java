import java.awt.Graphics2D;


public abstract class Entity {
    protected static boolean DEBUG = false;

	protected double x,y;
	
	abstract void render(Graphics2D g);
}
