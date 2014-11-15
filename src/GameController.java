import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class GameController extends MouseAdapter implements ActionListener {

	protected Game game;
	protected GameView gameView; 
	
	
	public GameController(Game game, GameView gameView) {
		
		this.game = game;
		this.gameView = gameView;
		gameView.setGameController(this);
		
		gameView.addEntity(game.getBoard());
		
		game.addObserver(gameView);
		
		
	}
	
	
	public void mouseClicked(MouseEvent e) {


      	Tile clickedTile = game.getBoard().getTileAtCoords(e.getX(), e.getY());
		System.out.println(clickedTile);
		if(clickedTile != null) {
			
			game.attemptTileMove(clickedTile);
			
		}
		
    }
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand() == "endTurn") {
			
			game.endTurn();
			
		}
		else if(e.getActionCommand() == "resetGame") {
			
			gameView.removeEntity(game.getBoard());
			
			game.resetGame();
			
			gameView.addEntity(game.getBoard());
		}
		
		else if(e.getActionCommand() == "exit") {
			
			System.exit(0);
		}
		
	}

	
	
}
