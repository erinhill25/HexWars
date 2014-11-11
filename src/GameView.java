import java.util.*;
import java.awt.*;

import javax.swing.*;

public class GameView extends JFrame implements Observer {
	
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 900;
	
	private static final long serialVersionUID = 1L;

	GameController gameController;
	
	JLabel currentPlayerLabel = new JLabel("Player 1's Turn"); 
	JLabel movesRemainingLabel = new JLabel("0 Moves Remaining"); 
	JButton endTurnButton = new JButton("End Turn"); 
	JPanel gamePanel = new GamePanel();
	
	JMenuItem newGame, viewLog, exit;
	JTextArea notes = new JTextArea(5, 10);
	
	ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public GameView(GameController gameController) {
		
		this.gameController = gameController;
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		currentPlayerLabel.setPreferredSize(new Dimension(300, 100));
		this.add(currentPlayerLabel, BorderLayout.CENTER);
		this.add(movesRemainingLabel, BorderLayout.CENTER);
		this.add(gamePanel, BorderLayout.NORTH);
		
		this.setVisible(true);
		
		gamePanel.addMouseListener(gameController);
		endTurnButton.addActionListener(gameController);
		
	    JMenuBar bar = new JMenuBar();
	    JMenu menu = new JMenu("File");
	    menu.setMnemonic('f');
	    bar.add(menu);
	    
	    newGame = menu.add(new JMenuItem("New Game", 'n'));
	    newGame.addActionListener(gameController);
	    viewLog = menu.add(new JMenuItem("View Log", 'v'));
	    viewLog.addActionListener(gameController);
	    exit = menu.add(new JMenuItem("Exit", 'e'));
	    exit.addActionListener(gameController);
	    
	    setJMenuBar(bar);
	    
	    this.add(notes, BorderLayout.SOUTH);
		
	    pack();
	    setSize(WIDTH,HEIGHT);
	}
	
	public void addEntity(Entity e) {
		
		entities.add(e);
	}
	

	public void update(Observable observed, Object arg) 
	{
		ObservableArgs argument = (ObservableArgs) arg;
		if(argument.getName() == "currentPlayer") {
			
			//Update UI label
			int newPlayer = ((int) argument.getValue() + 1);
			currentPlayerLabel.setText("Player " + newPlayer + "'s turn"); 
			notes.append("\nPlayer " + newPlayer + "'s turn" );
		}
		
		else if(argument.getName() == "endTurnActive") {
			
			Boolean activate = (boolean) argument.getValue();
			
			endTurnButton.setEnabled(activate); 

			
		}
		
		else if(argument.getName() == "movesRemaining") {
			
			int movesRemaining = (int) argument.getValue(); 
			
			movesRemainingLabel.setText(movesRemaining + " Moves Remaining");
			
		}
		
		else if(argument.getName() == "battleWinner") {
			
			int winner = ((int) argument.getValue() + 1);
			notes.append("\nPlayer " + winner + " won the battle" );
			
		}
		
		
		else if(argument.getName() == "gameWinner") {
			
			//add Winner text
			
			int winner = ((int) argument.getValue() + 1);
			notes.append("\nPlayer " + winner + " wins!" );
		}
		
		else if(argument.getName() == "gameReset") {
			
			//Remove winner text
			
			
			notes.append("\nNew game started");
		}
		

		gamePanel.repaint(); 
		
	}
	
	public void render(Graphics2D g) {
		
		for(Entity e : entities) {
			
			e.render(g);
			
		}
		
	}
	

	
	
	private class GamePanel extends JPanel {

		private static final long serialVersionUID = 1L;

		public void paint(Graphics g1) {
			 
			 	super.paint(g1);
			    Graphics2D g = (Graphics2D) g1;
			   
			    render(g);
			    
			    g.dispose();
		 }	
		
		
	}
	

}
