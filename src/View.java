import java.util.*;
import java.awt.Graphics2D;


public class View {
	
	protected List<Entity> entities = new ArrayList<Entity>();
	
	
	public void update(Graphics2D g) {
		
		for(Entity entity : entities) {
			
			entity.render(g);
			
		}
		
		
	}
	
	public void add(Entity entity) {
		entities.add(entity);
	}

}
