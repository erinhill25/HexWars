import java.util.*;

public class Animation {

	protected boolean loop, hasPostLoopSequence, hasLooped;
	protected ArrayList<SpriteFrame> frames = new ArrayList<SpriteFrame>();
	protected int[] postLoopSequence;
	protected int currentFrame = 0, frameDelay = 12, frameCounter = 0, postLoopIndex = 0;
	
	public Animation(boolean loop, boolean hasPostLoopSequence) {

		this.loop = loop;
		this.hasPostLoopSequence = hasPostLoopSequence;
		
	}
	
	public void reset() {
		
		this.hasLooped = false;
		currentFrame = 0;
		frameCounter = 0;
		postLoopIndex = 0;
		
	}
	
	public void setFrameDelay(int frameDelay) {
		this.frameDelay = frameDelay;
	}
	
	public void addFrame(int x, int y, int height, int width) {
		
		SpriteFrame newFrame = new SpriteFrame(x,y,height,width);
		frames.add(newFrame);
		
	}
	
	public void addFrame(SpriteFrame newFrame) {
		frames.add(newFrame);
	}
	
	public void setPostLoopSequence(int[] frames) {
		
		this.postLoopSequence = frames;
		
	}
	
	public SpriteFrame getCurrentFrame() {
		
		return frames.get(currentFrame);
		
	}
	
	public void advanceFrame() {
		
		if(hasLooped && !loop) {
			return;
		}
		
		frameCounter++;
		if(frameCounter >= frameDelay) {
			
			frameCounter = 0;
			
			//If animation has a sequence that should play after a full loop, advance current frame along this array
			if(hasPostLoopSequence && hasLooped) {
				currentFrame = postLoopSequence[postLoopIndex++];
				
				postLoopIndex = postLoopIndex % postLoopSequence.length;
				
			} else {
				//Advance frame along default sequence
				currentFrame++;
				if(currentFrame == frames.size()-1) {
					hasLooped = true;
				}
				
				currentFrame = currentFrame % frames.size(); 
			
			}
			
		}
		
		
		
	}
	
	
}
