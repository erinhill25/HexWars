import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class GameController extends MouseAdapter implements ActionListener {

	protected Game game;
	protected GameView gameView; 
	protected AIController aI1, aI2;
	
	public GameController(Game game, GameView gameView, int playerOneType, int playerTwoType) {
		
		this.game = game;
		this.gameView = gameView;
		gameView.setGameController(this);
		
		gameView.addEntity(game.getBoard());
		
	
		
		if(playerOneType == GameConstants.AI_PLAYER_MIA) {
			aI1 = new AIController(this.game, game.getUnitHandler().getPlayerUnits(0), 0);	
			
			game.addObserver(aI1);
		}
		
		if(playerTwoType == GameConstants.AI_PLAYER_MIA) {
			aI2 = new AIController(this.game, game.getUnitHandler().getPlayerUnits(1), 1);	
			
			game.addObserver(aI2);
		}
		
		game.addObserver(gameView);
		
	}
	
	 public void mouseDragged(MouseEvent e) {
		this.mouseClicked(e);
	}
	
	public void mouseClicked(MouseEvent e) {

		
		if(playerIsAI(game.getCurrentPlayer())) {
			
			return; 
			
		}
		
		
      	Tile clickedTile = game.getBoard().getTileAtCoords(e.getX(), e.getY());
		if(clickedTile != null) {
			
			game.attemptTileMove(clickedTile);
			
		}
		
    }
	
	public boolean playerIsAI(int player) {
		
		return (player == 0 && aI1 != null) || (player == 1 && aI2 != null);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand() == "endTurn" && !playerIsAI(game.getCurrentPlayer())) {
			
			gameView.setMovesRemaining(-1);
			game.endTurn();
			
		}
		else if(e.getActionCommand() == "resetGame") {
			
			game.getBoard().freeze();
			
			gameView.removeEntity(game.getBoard());
			
			game.resetGame();
			
			gameView.addEntity(game.getBoard());
			
			
			if(aI1 != null){
			    aI1.setUnits(game.getUnitHandler().getPlayerUnits(0));
			}
			if(aI2 != null){
			    aI2.setUnits(game.getUnitHandler().getPlayerUnits(1));
			}
		}
		
		else if(e.getActionCommand() == "exit") {
			
		    game.logExit();
			System.exit(0);
		}
		
	}

	
	
}
