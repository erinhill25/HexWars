
import java.awt.*;
import java.awt.event.*;

public class Controller implements MouseListener {

	Player player;
	Board board;
	
	public void setPlayer(Player player, Board board) {
		this.player = player;
		this.board = board;
	}

	
	 public void mouseClicked(MouseEvent e) {
		 	System.out.println("click");
	      	player.setX(e.getX());
	      	player.setY(e.getY());
	      	
	      	board.showTileClicked(e.getX(),e.getY()); 
	    }
	 
	 public void mousePressed(MouseEvent e) {
		 //	System.out.println("click");
	      //	player.setX(e.getX());
	    }

	    public void mouseReleased(MouseEvent e) {
	       //saySomething("Mouse released; # of clicks: "
	                   // + e.getClickCount(), e);
	    }

	    public void mouseEntered(MouseEvent e) {
	       //saySomething("Mouse entered", e);
	    }

	    public void mouseExited(MouseEvent e) {
	      // saySomething("Mouse exited", e);
	    }

	
}
