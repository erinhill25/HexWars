import java.util.*;
import java.awt.Graphics2D;
import java.awt.Graphics;
import javax.swing.JPanel;


public class View {
	
	protected List<Entity> entities = new ArrayList<Entity>();
	
	public View() {
		
		
		GamePanel game = new GamePanel(); 
		
	}
	
	public void update(Graphics2D g) {
		
		for(Entity entity : entities) {
			
			entity.render(g);
			
		}
		
		
	}
	
	public void add(Entity entity) {
		entities.add(entity);
	}
	
	
	private class GamePanel extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		public void paint(Graphics g1) {
			
			super.paint(g1);
			Graphics2D g = (Graphics2D) g1;
			update(g); 
			g.dispose();
			
		}
		
	}

}
