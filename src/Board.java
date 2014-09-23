import java.awt.*;

import javax.swing.*;


/*
 *   _
 *  / \  |
 *  \_/  | h = 2r   
 *   s
 *   
 *  t __
 * r |/ \  
 *    \_/
 */

public class Board extends Entity {
	
	
	protected int[][] board = 
		{   {0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0},
			{0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0},
			{0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
			{0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
			{0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
			{0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
			{0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
			{0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0},
			{0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0 } };
	
	protected int sideLength = 50; 
	protected int r = (int) (0.8660254 * sideLength); //cos 30 * sidelength = r
	protected int height = r * 2; 
	protected int t = (int) (0.5 * sideLength); //sin 30 * sidelength = t
	protected int width = 2 * t + sideLength;

	
	public Polygon drawHex(int x, int y) {

		y = y * height;
		y = (x % 2 == 1) ? y+(r) : y; // odd rows offset
		x = x * (sideLength + t); 

		int[] cx,cy;
		cx = new int[] {x, x+t, x+t+sideLength, x+t+sideLength+t, x+t+sideLength, x+t};

		cy = new int[] {y+r, y, y, y+r, y+height, y+height};
		
		return new Polygon(cx,cy,6);
	}
	
	public void render(Graphics2D g) {

		
		for(int x=0;x<board.length;x++) {
			
			for(int i=0;i<board[x].length;i++) {
				
				if(board[x][i] == 1) {
					Polygon hex = drawHex(i, x);
			
					g.setColor(Color.ORANGE);
					g.fillPolygon(hex);
					g.setColor(Color.BLACK);
					g.drawPolygon(hex);
					
				}
				
			}
		}

	}

}
