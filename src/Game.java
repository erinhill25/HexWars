import java.io.IOException;
import java.util.*;


public class Game extends Observable implements Runnable {
	
	protected long lastTime;
	protected Unit activeUnit;
	
	protected int currentPlayer = 1, winner=-1;
	
	protected Random rand = new Random();
	
	protected Board board; 
	protected BoardFactory boardFactory;
	protected UnitHandler unitHandler = new UnitHandler();
	
	String boardCSV = "resources/DefaultBoard.csv";
	
	boolean hasMadeMove = false, gameActive = false;

	public Game() {

		boardFactory = new CsvBoardFactory();
		
		try {
			board = boardFactory.constructBoard(unitHandler, 25, boardCSV);
		} catch (IOException e) {
			System.out.println("Loading of board resource failed");
		}
		
	}
	
	public UnitHandler getUnitHandler() {
		return unitHandler;
	}
	
	public int getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void startGame() {
		
		
		gameActive = true;
		
		changePlayer(0);
		
		if (!GameLogger.isOpen()) {
		    GameLogger.open();
		}
		GameLogger.logStartGame();
	}
	
	/*
	 * The unit being controlled by the active player
	 */
	public void setActiveUnit(Unit unit) {
		
		this.activeUnit = unit;
		
		board.selectTile(unit.getTile());
		
		GameLogger.logActiveUnit(unit);
		
	}
	
	
	/*
	 * Flip the current player, reset move counts for all of their units, and notify
	 */
	public void changePlayer(int newPlayer) {
		
		//Reset move counts and clear history
		
		ArrayList<Unit> playerUnits = unitHandler.getPlayerUnits(currentPlayer);
		for(Unit i : playerUnits) {
			
			i.setMovesRemaining(i.getPossibleMoves());
			i.clearHistory();
			
		}
		
		currentPlayer = newPlayer; 
			
		ArrayList<Unit> otherPlayerUnits = unitHandler.getPlayerUnits(currentPlayer);
		for(Unit i : otherPlayerUnits) {
			
			i.setMovesRemaining(i.getPossibleMoves());
			i.clearHistory();
			
		}
		
		this.notifyObservers(new ObservableArgs(GameConstants.CURRENT_PLAYER_OA, currentPlayer));
		this.notifyObservers(new ObservableArgs(GameConstants.END_TURN_ACTIVE_OA, false));
		
		hasMadeMove = false;
		
		GameLogger.logPlayerTurn(currentPlayer);
		
	}
	
	public void endTurn() {
		
		board.clearHighlights();
		changePlayer(currentPlayer ^ 1);
		
	}
	
	/*
	 * Calls board's update, which calls tile's updates, which calls unit's update and so on
	 */
	 public void update() {
		 
		if(board != null) { 
			board.update(); 
		}
		this.setChanged();
		this.notifyObservers(new ObservableArgs(GameConstants.UPDATE_OA, true));
		 
	 }
	 
	 /*
	  * Returns the board we are using
	  */
	 public Board getBoard() {
		 return board;
	 }
	 
	 /*
	  * Restart the game from a clean state
	  * Boardfactory will need to reconstruct the units as they may have been deleted in the last round
	  * 
	  */
	 public void resetGame() {
		 
		winner = -1;
		gameActive = true;
		activeUnit = null;
		
		unitHandler.destroyAllUnits();
		
		try {
			board = boardFactory.constructBoard(unitHandler, 25, boardCSV);
		} catch (IOException e) {
			System.out.println("Loading of board resource failed");
			return;
		}
		this.notifyObservers(new ObservableArgs(GameConstants.GAME_RESET_OA, true)); 
		changePlayer(0);
		
		
		GameLogger.logGameReset();
	 }
	 
	 public void unsetActiveUnit() {
		 activeUnit = null;
		 board.clearHighlights();
		 this.notifyObservers(new ObservableArgs(GameConstants.MOVES_REMAINING_OA, -1));
	 }
	 
	 /*
	  * If possible, the active unit will move to the specified tile
	  * This may engage a battle between two units. The mover has the advantage in that battle
	  * If all of the units of the loser have been wiped out, the game is over
	  * After one move, a player has the ability to end their turn
	  */
	 
	 public void attemptTileMove(Tile tile) {
		 

		 if(!gameActive || tile == null) return;
		 
		 
		 Unit otherUnit = tile.getUnit();
		 
		 /*
		  * If the tile clicked has a unit, its not the current unit, and its the current players unit, set it active
		  */
		 if(otherUnit != null && otherUnit != activeUnit && otherUnit.getPlayer() == currentPlayer) {
			 
			 activeUnit = tile.getUnit();
			 
			 if(activeUnit != null) {
				 this.notifyObservers(new ObservableArgs(GameConstants.MOVES_REMAINING_OA, activeUnit.getMovesRemaining()));
			 }
			 board.selectTile(tile);
			 
			 return;
			 
		 } 
		
		 /* No active unit, then nothing to do */
		 if(activeUnit == null) return;
		 
		 /*
		  * Active unit will unset in the following conditions:
		  * - The tile's unit is the active unit (active unit toggle)
		  * - The tile's unit is not the player's unit
		  * - The unit is out of moves
		  * - The tile is not accessible
		  */
		 if(otherUnit == activeUnit || activeUnit.getPlayer() != currentPlayer || activeUnit.getMovesRemaining()==0 || tile.getHighlight() != TileStatus.REACHABLE) {
		     
		     GameLogger.logMoveUnitFailed(activeUnit);
	
			 unsetActiveUnit();
			 return;
		 }
		 GameLogger.logMoveUnit(activeUnit);
		
		 /* Unit will move if tile is unoccupied or the tile contains an enemy unit */
		 if(otherUnit == null || otherUnit.getPlayer() != currentPlayer) 
		 {
			
			 activeUnit.setDestination(tile);
			
			 
			 activeUnit.setMovesRemaining(activeUnit.getMovesRemaining()-1);
			 this.notifyObservers(new ObservableArgs(GameConstants.MOVES_REMAINING_OA, activeUnit.getMovesRemaining()));
			 
			 /* Battle engaged if tile occupied by enemy.
			  * Only after winning should the tile be updated with the active unit */
			 if(otherUnit != null) 
			 {
					  
				 Unit winner = handleBattle(activeUnit, otherUnit);
				 if(winner == activeUnit) {
					 tile.setUnit(activeUnit);
				 }
				  
			 }
			 else 
			 {
				 tile.setUnit(activeUnit);				 
			 }
			 
			 board.selectTile(tile);
			 
			 /* Notify that a move has been made, and the player can end their turn */
			 if(!hasMadeMove) {
				 hasMadeMove = true;
				 this.notifyObservers(new ObservableArgs(GameConstants.END_TURN_ACTIVE_OA, true));
			 }
			
			 
			 
			 /* All of the player's units moves are 0 */
			 if(!hasMovesRemaining(currentPlayer)) {
				 
				 this.endTurn();
				 
			 }
			 
		 } 
		 
	 }
	 
	 /*
	  * Given a player, determine if any of their units have moves remaining
	  */
	 public boolean hasMovesRemaining(int player) {
	
		 
		 ArrayList<Unit> playerUnits = unitHandler.getPlayerUnits(player);
		 for(Unit i : playerUnits) {
			 
			 if(i.getMovesRemaining() != 0) {
				 return true;
			 }
			 
		 }
		 
		 return false;
		 
	 }
	 
	 
	 public Unit handleBattle(Unit activeUnit, Unit otherUnit) {
		 
		  Unit loser = null;
		  Unit winner = this.engageBattle(activeUnit, otherUnit);
		 
		  if(winner == activeUnit) 
		  {
			  activeUnit.clearHistory();
			  loser = otherUnit;
		
		   	  unitHandler.removeUnit(otherUnit);

		  } 
		  else 
		  {  
			  loser = activeUnit;
			  unitHandler.removeUnit(activeUnit); 
			  unsetActiveUnit();

		  }
		  
		  this.notifyObservers(new ObservableArgs(GameConstants.BATTLE_WINNER_OA, winner.getPlayer()));
		  
		  if(loser instanceof King) {
			  
			  this.setWinner(winner.getPlayer());
			  
		  }
		  
		  //Determine if the player who won the battle won the game
		  this.determineWinner(winner.getPlayer());

		  return winner;
	 }
	 
	 /*
	  * Unit 1 is the invader, Unit 2 was invaded
	  * Give the invader (unit 1),an advantage in the battle, 90/100 chance of winning vs 1/100
	  */
	 public Unit engageBattle(Unit unit1, Unit unit2) {
		 
		 int  random = rand.nextInt(100);
		 
		 if(random == 5) {	 
			 return unit2;
		 } else {
			 
			 return unit1;
		 }
		 
	 }
	 
	 
	 public boolean determineWinner(int winner) {
		 
		 int loser = winner ^ 1;
		 ArrayList<Unit> playerUnits = unitHandler.getPlayerUnits(loser);
		  
		  /* Big Winner time! */
		  if(playerUnits.size() == 0) {
			  
			 setWinner(winner);
			 return true;
			  
		  }
		  
		  return false;
		 
	 }
	 
	 public void setWinner(int winner) {
		 
		  this.gameActive = false;
		  this.winner = winner;
		  this.notifyObservers(new ObservableArgs(GameConstants.GAME_WINNER_OA, winner));
		  board.clearHighlights();
		  
		  GameLogger.logEndGame(winner);
		  
	 }
	 
	 public void notifyObservers(Object message) {
		 
		 this.setChanged();
		 super.notifyObservers(message);
		 
	 }
	 

	 
	 
	 public void run() {
		 
		 try {
			 update();
		 }
		 catch (Exception e) {
			 e.printStackTrace();
			 throw new RuntimeException(e);
		 }
		 
	 }
	 
	 
	 
	 public void logExit() {
	     GameLogger.close();
	 }
	 
	
}
