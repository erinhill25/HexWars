import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameView extends JFrame implements Observer {
	
	
	GameController gameController;
	
	JLabel currentPlayerLabel = new JLabel("Player 1's Turn"); 
	JLabel movesRemainingLabel = new JLabel("0 Moves Remaining"); 
	JButton endTurnButton = new JButton("End Turn"); 
	JPanel gamePanel = new GamePanel();
	
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
	}
	
	public void addEntity(Entity e) {
		
		entities.add(e);
	}
	

	public void update(Observable observed, Object arg) 
	{
		ObservableArgs argument = (ObservableArgs) arg;
		if(argument.getName() == "currentPlayer") {
			
			//Update UI label
			currentPlayerLabel.setText("Player " + ((int) argument.getValue() + 1) + "'s turn"); 
			
		}
		
		if(argument.getName() == "endTurnActive") {
			
			Boolean activate = (boolean) argument.getValue();
		
			endTurnButton.setEnabled(activate); 

			
		}
		
		if(argument.getName() == "movesRemaining") {
			
			int movesRemaining = (int) argument.getValue(); 
			
			movesRemainingLabel.setText(movesRemaining + " Moves Remaining");
			
		}
		
		
		if(argument.getName() == "gameWinner") {
			
			//add Winner text
			
		}
		
		if(argument.getName() == "gameReset") {
			
			//Remove winner text
			
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
