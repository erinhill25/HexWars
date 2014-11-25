
public class GeneralSprite extends AnimatedSprite {
	
	public GeneralSprite(int x, int y, int height, int width) {
		
		super(x,y,height,width,"resources/Player/generalSpriteSheet80.png");
		
	}
	
	public void defineAnimations() {
		
		
		animations.put("default", new Animation(false, false) {{
			addFrame(new SpriteFrame(0,0,80,80));
		}});
		
		animations.put("lose",  new Animation(false, false) {{
			 addFrame(new SpriteFrame(80,0,80,80));
			 addFrame(new SpriteFrame(160,0,80,80));
			 addFrame(new SpriteFrame(240,0,80,80));
		}});
		
		animations.put("win",   new Animation(true, true) {{
			int[] postLoop = { 1, 2 }; //He chuckles
			setPostLoopSequence(postLoop);
		    addFrame(new SpriteFrame(320,0,80,80));
		    addFrame(new SpriteFrame(400,0,80,80));
		    addFrame(new SpriteFrame(480,0,80,80));
		}});
		
	}

}
