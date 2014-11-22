/*
 * The controller class for the AI Behavior
 */
import java.util.*;

public class AIController implements Observer {
    
    private Game game;
    private AIBehavior aI;
    private ArrayList<Unit> units;
    
    private int playerNumber;
    
    public AIController(Game game, ArrayList<Unit> units, int playerNumber) {
        this.game = game;
        this.units = units;
        this.playerNumber = playerNumber;
        
        aI = new SpencersAIBehavior(this);
    }
    
    public void setUnits(ArrayList<Unit> units){
        this.units = units;
    }
    
    /*
     * Detect when it is this AI Player's turn
     */
    public void update(Observable observed, Object arg) {
        
        ObservableArgs argument = (ObservableArgs) arg;
        if(argument.getName() == GameConstants.CURRENT_PLAYER_OA) {
            if(playerNumber == (int) argument.getValue()) {
                // If it is this AI Player's turn, then execute it's moves
                executeMoves();
            }
        }
    }
    
    
    /* 
     * Traverse all the units owned by this AI Player
     * Then end the turn
     */
    private void executeMoves() {
        for(int i = 0; i < units.size(); ++i) {
            aI.makeMove(units.get(i));
        }
        game.endTurn();
    }
    
    /*
     * Add a layer of abstraction to the AIBehavior
     */
    public void makeMove(Unit activeUnit, Tile moveTo) {
        game.setActiveUnit(activeUnit);
        game.attemptTileMove(moveTo);
    }

}
