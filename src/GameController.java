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
		
		game.addObserver(gameView);
		
		
	}
	
	
	public void mouseClicked(MouseEvent e) {

		
      	Tile clickedTile = game.getBoard().selectTile(e.getX(), e.getY());
		
		if(clickedTile != null) {
			
			game.attemptTileMove(clickedTile);
			
		}
    }
	
	public void actionPerformed(ActionEvent e) {
		
		//TODO things
		
	}

	
	
}
