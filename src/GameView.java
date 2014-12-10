import java.util.*;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Timer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.awt.font.TextLayout;

public class GameView extends JFrame implements Observer {
	
	public static final int WIDTH = 900;
	public static final int HEIGHT = 700;
	
	private static final long serialVersionUID = 1L;
	
	private Timer timer = new Timer();

	GameController gameController;
	
	JButton endTurnButton = new JButton("End Turn");
	JPanel gamePanel = new GamePanel();
	
	JMenuItem newGame, viewLog, exit;
	JTextArea notesArea = new JTextArea(5, 10);
	JScrollPane notes = new JScrollPane(notesArea);
	
	GeneralSprite general = new GeneralSprite(18, 35, 80, 80);
	FrobKnobSprite frobKnob = new FrobKnobSprite(WIDTH-140, 35, 80, 80);
	
	private int currentPlayer = 1, movesRemaining = -1, winner = -1;
	
	ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public GameView() {
		
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		try{
            Image icon = ImageIO.read(new File("./resources/icon.png"));
            this.setIconImage(icon);
        }catch(IOException e){
            e.printStackTrace();
        }
		
		this.setTitle("HexWars");
		    
		entities.add(general);
		entities.add(frobKnob);
		
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
	
	public void removeEntities() {
		entities.clear(); 
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
		
		if(argument.getName() == GameConstants.CURRENT_PLAYER_OA) {
			
			currentPlayer = (int) argument.getValue() + 1;
			movesRemaining=-1;
			notesArea.append("\nPlayer " + currentPlayer + "'s turn" );
			
		}
		
		else if(argument.getName() == GameConstants.END_TURN_ACTIVE_OA) {
			
			Boolean activate = (boolean) argument.getValue();
			
			endTurnButton.setEnabled(activate); 

			
		}
		
		else if(argument.getName() == GameConstants.MOVES_REMAINING_OA) {
			
		
			movesRemaining = (int) argument.getValue(); 
	
		}
		
		else if(argument.getName() == GameConstants.BATTLE_WINNER_OA) {
			
			int winner = ((int) argument.getValue() + 1);
			notesArea.append("\nPlayer " + winner + " won the battle" );
			
			if(winner == 1) {
				general.setAnimation("win");
				frobKnob.setAnimation("lose");
			}
			else {
				
				frobKnob.setAnimation("win");
				general.setAnimation("lose");
				
			}
			timer.schedule(new TimerTask() {
		        public void run() {
		            resetSprites(); 
		        }
		    }, 3000);
		}
		
		
		else if(argument.getName() == GameConstants.GAME_WINNER_OA) {
			
			//add Winner text
			
			winner = ((int) argument.getValue() + 1);
			endTurnButton.setEnabled(false);
			notesArea.append("\nPlayer " + winner + " wins the game!" );
		}
		
		else if(argument.getName() == GameConstants.GAME_RESET_OA) {
			
			//Remove winner text
			endTurnButton.setEnabled(false);
			winner = -1;
			notesArea.append("\nNew game started");
		}
		

		gamePanel.repaint(); 
		
	}
	
	public void resetSprites() {
		
		general.setAnimation("default");
		frobKnob.setAnimation("default");
	}
	
	public void render(Graphics2D g) {
		
		for(Entity e : entities) {
			
			e.render(g);
			
		}
	    g.setColor(new Color(0,0,0));
	    
	    
	    if(movesRemaining != -1 && winner == -1) {
	    	
	    	g.setFont(new Font("Tahoma", Font.PLAIN, 30));
	    	g.drawString(""+ movesRemaining, 15, gamePanel.getHeight()-30);
	    	
	    	g.setFont(new Font("Tahoma", Font.PLAIN, 16));
	    	g.drawString("Moves Remaining", 40, gamePanel.getHeight()-30);
	    }
	    
	    g.setFont(new Font("Tahoma", Font.PLAIN, 16));
	    
	    g.drawString("Player " + currentPlayer + "'s turn", gamePanel.getWidth()-125, gamePanel.getHeight()-30);
	    
	    g.setFont(new Font("Tahoma", Font.PLAIN, 24));
	    g.setColor(new Color(255,30,2));
	    
	    g.drawString("Player 1", 15, 30);
	    
	    g.setColor(new Color(123,21,174));

	    g.drawString("Player 2", gamePanel.getWidth()-125, 30);
	    
	
	    
	    if(winner != -1) {
	    	
	    	
	    	g.setColor(new Color(0, 0, 0, 150 ));
			g.fillRect(0,gamePanel.getHeight()/2 - 65,WIDTH, 150);
	 	
	    	Font font = new Font("Tahoma", Font.PLAIN, 55);
	    	g.setFont(font);
	    	
	    	String winnerText = "Player " + winner + " wins!";
	    	
	    	TextLayout textLayout = new TextLayout(winnerText, font, g.getFontRenderContext());
	    	
	    	int x = gamePanel.getWidth()/2-188;
	    	int y = gamePanel.getHeight()/2 + 22;
	    	
	    	g.setPaint(Color.BLACK);
	    	textLayout.draw(g, x - 2, y + 1);

	    	g.setPaint(Color.WHITE);
	    	textLayout.draw(g, x, y);
	    	
	    	
	    }
	
	  
	}
	
	public void repaint() {
		
		gamePanel.repaint();
		
	}
	

	
	
	private class GamePanel extends JPanel {

		private static final long serialVersionUID = 1L;
		
		public GamePanel(){ 
			setDoubleBuffered(true);
		}
		
		public void paintComponent(Graphics g1) {
			super.paintComponent(g1);
			Graphics2D g = (Graphics2D) g1;
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			
			render(g);
			    	
	        g.dispose();
        }	
		
    }
}
