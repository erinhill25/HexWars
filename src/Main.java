import javax.swing.JOptionPane;

import java.util.concurrent.*;
import java.awt.*;
import java.awt.event.*;


public class Main {

	public static void main(String[] args) {
		
		new Main(); 
	}
	
	public Main() {
	    new MenuController(this);
	}
	
	public void abortGame(String errorMessage){
	    // Goodbyes?
	    System.exit(0);
	}
	
	public void startGame(int playerOneType, int playerTwoType) {
	    
	    Game game = new Game();
	    GameView gameView = new GameView();
	    GameController gameController = new GameController(game, gameView, playerOneType, playerTwoType);
	    
	    game.startGame();
	    
	    ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(3);
	    
	    executor.scheduleAtFixedRate(game, 0L, (long) 16.6, TimeUnit.MILLISECONDS);
	    
	   
	}
	
	
}
