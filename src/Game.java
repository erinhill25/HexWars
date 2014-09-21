import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Game extends JPanel {
	static final int WIDTH = 800, HEIGHT = 375;
	
	private static final long serialVersionUID = 1L;
	
	protected View view;
	protected Player player;
	protected long lastTime;

	public Game() {
		view = new View();
		player= new Player();
		Board board = new Board();
		view.add(player);
		view.add(board);
		Controller control = new Controller();
		control.setPlayer(player);
		this.addMouseListener(control);
	}
	
	
	 public void paint(Graphics g1) {
		 
		 	super.paint(g1);
		    Graphics2D g = (Graphics2D) g1;
		    
		    g.setPaint(new GradientPaint(0, 0, new Color(150, 0, 0), WIDTH, HEIGHT,
		        new Color(200, 200, 255)));
		  //  g.fillRect(250, 0, WIDTH, HEIGHT);
		    view.update(g);
		    
	 }
	 
	 public void run() {
		 
		 while(true) {
			 
			 long now = System.nanoTime();
			 long diff = now - lastTime;
	
			 diff /= 1000000; //Nano to milliseconds
			 
			 if(diff > (1000 / 60)) {
				 repaint();
				 lastTime = now;
			 }
		 }
	 }
	
}
