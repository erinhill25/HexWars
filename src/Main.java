import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;


public class Main {

	public static void main(String[] args) {
		
		Game game = new Game();
		 JFrame f = new JFrame();
		    f.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		        System.exit(0);
		      }
		    });
		    f.setContentPane(game);
		    f.setSize(1024,800);
		    f.setVisible(true);
		    
		    game.run();

	}

}
