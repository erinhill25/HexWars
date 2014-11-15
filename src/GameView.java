import java.util.*;
import java.awt.*;

import javax.swing.*;

import java.awt.font.TextLayout;

public class GameView extends JFrame implements Observer {
	
	public static final int WIDTH = 900;
	public static final int HEIGHT = 700;
	
	private static final long serialVersionUID = 1L;

	GameController gameController;
	
	JLabel currentPlayerLabel = new JLabel("Player 1's Turn"); 
	JButton endTurnButton = new JButton("End Turn"); 
	JPanel gamePanel = new GamePanel();
	
	JMenuItem newGame, viewLog, exit;
	JTextArea notesArea = new JTextArea(5, 10);
	JScrollPane notes = new JScrollPane(notesArea);
	
	public int movesRemaining = -1, winner = -1;
	
	ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public GameView() {
		
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		 this.setTitle("HexWars");
		
	    JMenuBar bar = new JMenuBar();
	    JMenu menu = new JMenu("File");
	    menu.setMnemonic('f');
	    bar.add(menu);
	    
	    newGame = menu.add(new JMenuItem("New Game", 'n'));
	    
	    viewLog = menu.add(new JMenuItem("View Log", 'v'));
	   
	    exit = menu.add(new JMenuItem("Exit", 'e'));

	    
	    setJMenuBar(bar);
		
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
		
		gamePanel.setPreferredSize(new Dimension(WIDTH, 650));
		gamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(gamePanel);
		
		
		endTurnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(endTurnButton);
	
		this.add(Box.createRigidArea(new Dimension(WIDTH,15)));
		
		
		notesArea.setEditable(false);
		
		notes.setPreferredSize(new Dimension(WIDTH, 75));
		this.add(notes);
	  
		this.setVisible(true);
	    pack();
		
	    setSize(WIDTH,HEIGHT);
	}
	
	public void setGameController(GameController gameController) {
		this.gameController = gameController;

		gamePanel.addMouseListener(gameController);
		
		newGame.setActionCommand("resetGame");
		newGame.addActionListener(gameController);
		viewLog.addActionListener(gameController);
		exit.setActionCommand("exit");
	    exit.addActionListener(gameController);
	    
	    endTurnButton.setActionCommand("endTurn");
	    endTurnButton.addActionListener(gameController);
	}
	
	public void addEntity(Entity e) {
		
		entities.add(e);
	}
	
	public void removeEntity(Entity e) {
		
		entities.remove(e);

	}
	
	public void setMovesRemaining(int movesRemaining) {
		this.movesRemaining = movesRemaining;
	}

	public void update(Observable observed, Object arg) 
	{
		ObservableArgs argument = (ObservableArgs) arg;
		if(argument.getName() == "currentPlayer") {
			
			//Update UI label
			int newPlayer = ((int) argument.getValue() + 1);
			currentPlayerLabel.setText("Player " + newPlayer + "'s turn"); 
			notesArea.append("\nPlayer " + newPlayer + "'s turn" );
		}
		
		else if(argument.getName() == "endTurnActive") {
			
			Boolean activate = (boolean) argument.getValue();
			
			endTurnButton.setEnabled(activate); 

			
		}
		
		else if(argument.getName() == "movesRemaining") {
			
		
			movesRemaining = (int) argument.getValue(); 
	
		}
		
		else if(argument.getName() == "battleWinner") {
			
			int winner = ((int) argument.getValue() + 1);
			notesArea.append("\nPlayer " + winner + " won the battle" );
			
		}
		
		
		else if(argument.getName() == "gameWinner") {
			
			//add Winner text
			
			winner = ((int) argument.getValue() + 1);
			endTurnButton.setEnabled(false);
			notesArea.append("\nPlayer " + winner + " wins the game!" );
		}
		
		else if(argument.getName() == "gameReset") {
			
			//Remove winner text
			endTurnButton.setEnabled(false);
			winner = -1;
			notesArea.append("\nNew game started");
		}
		

		gamePanel.repaint(); 
		
	}
	
	public void render(Graphics2D g) {
		
		for(Entity e : entities) {
			
			e.render(g);
			
		}
	    g.setColor(new Color(0,0,0));
	    
	    
	    if(movesRemaining != -1 && winner == -1) {
	    	g.setFont(new Font("Tahoma", Font.PLAIN, 16));
	    	g.drawString("Moves Remaining: " + movesRemaining, 10, gamePanel.getHeight()-30);
	    }
	    
	    if(winner != -1) {
	    	
	    	Font font = new Font("Tahoma", Font.PLAIN, 51);
	    	g.setFont(font);
	    	
	    	String winnerText = "Player " + winner + " wins!";
	    	
	    	TextLayout textLayout = new TextLayout(winnerText, font, g.getFontRenderContext());
	    	
	    	int x = gamePanel.getWidth()/2-180;
	    	int y = gamePanel.getHeight()/2;
	    	
	    	 g.setPaint(new Color(255, 255, 255));
	    	 textLayout.draw(g, x - 2, y + 1);

	    	 g.setPaint(Color.BLACK);
	    	 textLayout.draw(g, x, y);
	    	
	    	
	    }
	}
	
	public void repaint() {
		
		super.repaint();
		gamePanel.repaint();
		
	}
	

	
	
	private class GamePanel extends JPanel {

		private static final long serialVersionUID = 1L;
	
		
		public GamePanel(){ 
			
	
		}
		
	

		public void paint(Graphics g1) {
			 
			 	super.paint(g1);
			    Graphics2D g = (Graphics2D) g1;
			   
			    render(g);
			    	
			    g.dispose();
		 }	
		
		
	}
	

}
