import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;


public class Sprite extends Entity {

	protected int height, width;
	protected BufferedImage img = null;
	
	public Sprite(int x, int y, int width, int height, String resource) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		try {
			img = ImageIO.read(new File(resource));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void updateImg(String resource) {
		
		try {
			img = ImageIO.read(new File(resource));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void render(Graphics2D g) {
		
		 g.drawImage(img, (int) x, (int) y, (int) x+width, (int) y+height,
	    	       0, 0, img.getWidth(),img.getHeight(), null);
		
	}
	
}
