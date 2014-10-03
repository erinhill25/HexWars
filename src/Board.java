import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
	
	
	protected Color[] colors =  {new Color(153,240,66),new Color(102,189,15),new Color(77,142,11)};  
	
	
	protected int[][] board = 
		{   {0,0,0,0,0,1,1,1,1,1,1,0,0,0,0},
			{0,0,0,0,1,1,1,1,1,1,1,0,0,0,0},
			{0,0,0,0,1,1,2,2,2,1,1,1,0,0,0},
			{0,1,1,1,1,2,1,1,1,1,1,1,1,1,0},
			{0,1,1,1,1,2,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,2,1,1,1,1,1,1,1},
			{0,1,1,1,1,1,1,1,1,1,2,1,1,1,1},
			{0,1,1,1,1,1,1,1,1,2,1,1,1,1,0},
			{0,0,0,0,1,1,1,2,2,2,1,1,0,0,0},
			{0,0,0,0,1,1,1,1,1,1,1,0,0,0,0},
			{0,0,0,0,0,1,1,1,1,1,1,0,0,0,0 } };
	
	int[] startColors = {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 };
	
	protected int sideLength = 48; 
	protected int r = (int) (0.8660254 * sideLength); //cos 30 * sidelength = r
	protected int height = r * 2; 
	protected int t = (int) (0.5 * sideLength); //sin 30 * sidelength = t
	protected int width = 2 * t + sideLength;
	
	
	protected List<Tile> tiles = new ArrayList<Tile>();

	
	public Board() {
		
		//Create tiles with polygon and color
		for(int x=0;x<board.length;x++) {
				
				int startColor = startColors[x];
				for(int i=0;i<board[x].length;i++) {
					
					if(board[x][i] > 0) {
						Polygon hex = drawHex(i, x);
						if(board[x][i] == 2) {
							tiles.add(new Tile(hex, Color.black, Tile.States.INACTIVE));
						}
						else
						{
							tiles.add(new Tile(hex, colors[startColor], Tile.States.ACTIVE));
						}
					}
					startColor++;
					startColor = startColor % colors.length;
				}
			}
		
		
	}
	
	public Polygon drawHex(int x, int y) {

		
		//y = (x % 2 == 1) ? y+(r) : y; // odd rows offset
		//x = x * (sideLength + t); 
		
		x = x * (2*r);
		
		x = (y % 2 == 1) ? x+(r) : x;
				
		//y = y * height;

		y = y * (sideLength + t);
		
		int[] cx,cy;
		
		
		cx = new int[] {x, x+r, x+2*r, x+2*r, x+r, x};
		cy = new int[] {y+t, y, y+t, y+t+sideLength, y+t+sideLength+t, y+t+sideLength};
		
	//	cx = new int[] {x, x+t, x+t+sideLength, x+t+sideLength+t, x+t+sideLength, x+t};

	//	cy = new int[] {y+r, y, y, y+r, y+height, y+height};
		
		return new Polygon(cx,cy,6);
	}
	
	public void render(Graphics2D g) {

		for(Tile tile : tiles) {
			
			tile.render(g);
			
		}
		
	}

}
