import java.awt.Graphics2D;
import java.util.HashMap;


public abstract class AnimatedSprite extends Sprite {
	
	protected HashMap<String, Animation> animations = new HashMap<String, Animation>();
	
	protected String currentAnimation = "default";

	
	public AnimatedSprite(int x, int y, int height, int width, String resource) {
		
		super(x,y,height,width, resource);
		
		defineAnimations();
	}
	
	public abstract void defineAnimations();
	
	
	public void setAnimation(String newAnimation) {
		
		if(!animations.containsKey(newAnimation)) {
			return;
		}
		animations.get(currentAnimation).reset();
		currentAnimation = newAnimation;
		
		
	}
	
	public void render(Graphics2D g) {
		 
		 SpriteFrame currentFrame = animations.get(currentAnimation).getCurrentFrame();
		 
		 animations.get(currentAnimation).advanceFrame();
		
		 g.drawImage(img, (int) x, (int) y, (int) x+width, (int) y+height,
	    	       currentFrame.x, currentFrame.y, currentFrame.x + currentFrame.width, currentFrame.y + currentFrame.height, null);
		
	}
	
}
