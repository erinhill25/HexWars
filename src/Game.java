import java.io.IOException;
import java.util.*;


public class Game extends Observable implements Runnable {
	
	protected long lastTime;
	protected Unit activeUnit;
	
	protected int currentPlayer = 0, winner=-1;
	
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
	
	public void startGame() {
		gameActive = true;
		
		changePlayer(0);
	}
	
	/*
	 * The unit being controlled by the active player
	 */
	public void setActiveUnit(Unit unit) {
		
		this.activeUnit = unit;
		
	}
	
	
	/*
	 * Flip the current player, reset move counts for all of their units, and notify
	 */
	public void changePlayer(int newPlayer) {
		
		//Reset move counts
		
		ArrayList<Unit> playerUnits = unitHandler.getPlayerUnits(currentPlayer);
		for(Unit i : playerUnits) {
			
			i.setMovesRemaining(i.getPossibleMoves());
			
		}
		
		currentPlayer = newPlayer; 
		
		ArrayList<Unit> otherPlayerUnits = unitHandler.getPlayerUnits(currentPlayer);
		for(Unit i : otherPlayerUnits) {
			
			i.setMovesRemaining(i.getPossibleMoves());
			
		}
		
		this.notifyObservers(new ObservableArgs("currentPlayer", currentPlayer));
		this.notifyObservers(new ObservableArgs("endTurnActive", false));
		
		hasMadeMove = false;
		
	}
	
	public void endTurn() {
		
		board.clearHighlights();
		changePlayer(currentPlayer ^ 1);
		
	}
	
	/*
	 * Calls board's update, which calls tile's updates, which calls unit's update and so on
	 */
	 public void update() {
		 
		board.update(); 
		this.setChanged();
		this.notifyObservers(new ObservableArgs("update", true));
		 
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
		 
		board.clearHighlights();
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
		this.notifyObservers(new ObservableArgs("gameReset", true)); 
		changePlayer(0);
	 }
	 
	 public void unsetActiveUnit() {
		 activeUnit = null;
		 board.clearHighlights();
		 this.notifyObservers(new ObservableArgs("movesRemaining", -1));
	 }
	 
	 /*
	  * If possible, the active unit will move to the specified tile
	  * This may engage a battle between two units. The mover has the advantage in that battle
	  * If all of the units of the loser have been wiped out, the game is over
	  * After one move, a player has the ability to end their turn
	  */
	 
	 public void attemptTileMove(Tile tile) {
		 

		 if(!gameActive) return;
		 
		 
		 Unit otherUnit = tile.getUnit();
		 
		 /*
		  * If the tile clicked has a unit, its not the current unit, and its the current players unit, set it active
		  */
		 if(otherUnit != null && otherUnit != activeUnit && otherUnit.getPlayer() == currentPlayer) {
			 
			 activeUnit = tile.getUnit();
			 
			 if(activeUnit != null) {
				 this.notifyObservers(new ObservableArgs("movesRemaining", activeUnit.getMovesRemaining()));
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
			 unsetActiveUnit();
			 return;
		 }
		 
		
		 /* Unit will move if tile is unoccupied or the tile contains an enemy unit */
		 if(otherUnit == null || otherUnit.getPlayer() != currentPlayer) 
		 {
			
			 activeUnit.setDestination(tile);
			 board.selectTile(tile);
			 
			 activeUnit.setMovesRemaining(activeUnit.getMovesRemaining()-1);
			 this.notifyObservers(new ObservableArgs("movesRemaining", activeUnit.getMovesRemaining()));
			 
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
			 

			 
			 /* Notify that a move has been made, and the player can end their turn */
			 if(!hasMadeMove) {
				 hasMadeMove = true;
				 this.notifyObservers(new ObservableArgs("endTurnActive", true));
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
			  loser = otherUnit;
		
		   	  unitHandler.removeUnit(otherUnit);

		  } 
		  else 
		  {  
			  loser = activeUnit;
			  unitHandler.removeUnit(activeUnit); 
			  unsetActiveUnit();

		  }
		  
		  this.notifyObservers(new ObservableArgs("battleWinner", winner.getPlayer()));
		  
		  if(loser instanceof King) {
			  
			  this.setWinner(winner.getPlayer());
			  
		  }
		  
		  //Determine if the player who won the battle won the game
		  this.determineWinner(winner.getPlayer());

		  return winner;
	 }
	 
	 /*
	  * Unit 1 is the invader, Unit 2 was invaded
	  * Give the invader (unit 1),an advantage in the battle, 6/10 chance of winning vs 4/10
	  */
	 public Unit engageBattle(Unit unit1, Unit unit2) {
		 
		 int  random = rand.nextInt(10);
		 
		 if(random % 2 == 0 || random % 9 == 0) {	 
			 return unit1;
		 } else {
			 
			 return unit2;
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
		  this.notifyObservers(new ObservableArgs("gameWinner", winner));
		  board.clearHighlights();
		  
	 }
	 
	 public void notifyObservers(Object message) {
		 
		 this.setChanged();
		 super.notifyObservers(message);
		 
	 }
	 

	 
	 
	 public void run() {
		 
		 update();
		 
	 }
	 
	
}
