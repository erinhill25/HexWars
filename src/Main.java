import java.awt.event.WindowAdapter;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import java.awt.event.WindowEvent;
import javax.swing.JMenuItem;

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
		    f.setVisible(true);
		    
		    
		    JMenuBar bar = new JMenuBar();
		    JMenu menu = new JMenu("File");
		    menu.setMnemonic('f');
		    bar.add(menu);
		    
		    JMenuItem newgame = menu.add(new JMenuItem("New Game", 'n'));
		    JMenuItem viewlog = menu.add(new JMenuItem("View Log", 'v'));
		    JMenuItem exit = menu.add(new JMenuItem("Exit", 'e'));
		    
		    
		    
		    f.setJMenuBar(bar);
		    
		    f.pack();
		    f.setSize(1500,1080);
		    
		    
		    
		    game.run();

	}
	
	
	
	


}
