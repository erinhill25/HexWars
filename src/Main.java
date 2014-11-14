import javax.swing.JOptionPane;

import java.util.concurrent.*;
import java.awt.*;
import java.awt.event.*;


public class Main {
    // Constants
    public static final int ABORT_GAME = -999;
    public static final int HUMAN_PLAYER = 0;
    public static final int AI_PLAYER = 1;

	public static void main(String[] args) {
		/*
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
        /**/
		
		new Main(); 
	}
	
	public Main() {
	    new MenuController(this);
	}
	
	public void abortGame(String errorMessage){
	    // Goodbyes?
	    /*JOptionPane.showMessageDialog(null, "Game Aborted!\n"
	            + "Message:\n"
	            + errorMessage, "Aw Nuts!", JOptionPane.ERROR_MESSAGE);*/
	    System.exit(0);
	}
	
	public void startGame(int playerOneType, int playerTwoType) {
       /*JOptionPane.showMessageDialog(null, "Game Started!\n"
               + "Values set to:\n"
               + "Player 1: " + playerOneType + "\n"
               + "Player 2: " + playerTwoType + "\n",
               "Welcome", JOptionPane.INFORMATION_MESSAGE);*/
	    
	    Game game = new Game();
	    GameView gameView = new GameView();
	    GameController gameController = new GameController(game, gameView);
	    
	    
	    ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(3);
	    
	    executor.scheduleAtFixedRate(game, 0L, 20L, TimeUnit.MILLISECONDS);
	    
	   
	}
	
	
}
