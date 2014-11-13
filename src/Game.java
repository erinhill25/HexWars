import java.io.IOException;
import java.util.*;


public class Game extends Observable {
	
	protected long lastTime;
	protected Unit activeUnit;
	
	protected int currentPlayer = 0, winner=-1;
	
	protected Random rand = new Random();
	
	protected Board board; 
	protected BoardFactory boardFactory;
	protected UnitHandler unitHandler = new UnitHandler();
	
	String boardCSV = "resources/DefaultBoard.csv";
	
	boolean hasMadeMove = false, gameActive = true;

	public Game() {

		boardFactory = new CsvBoardFactory();
		
		try {
			board = boardFactory.constructBoard(unitHandler, 40, boardCSV);
		} catch (IOException e) {
			System.out.println("Loading of board resource failed");
		}

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
	public void changePlayer() {
		
		//Reset move counts
		
		ArrayList<Unit> playerUnits = unitHandler.getPlayerUnits(currentPlayer);
		for(Unit i : playerUnits) {
			
			i.setMovesRemaining(i.getPossibleMoves());
			
		}
		
		currentPlayer = currentPlayer ^ 1; 
		
		this.setChanged();
		this.notifyObservers(new ObservableArgs("currentPlayer", currentPlayer));
		this.setChanged();
		this.notifyObservers(new ObservableArgs("endTurnActive", false));
		
		hasMadeMove = false;
		
	}
	
	/*
	 * Calls board's update, which calls tile's updates, which calls unit's update and so on
	 */
	 public void update() {
		 
		 board.update(); 
		 
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
		 
		currentPlayer = 0;
		winner = -1;
		gameActive = true;
		activeUnit = null;
		
		try {
			board = boardFactory.constructBoard(unitHandler, 40, boardCSV);
		} catch (IOException e) {
			System.out.println("Loading of board resource failed");
			return;
		}
		this.setChanged();
		this.notifyObservers(new ObservableArgs("gameReset", true)); 
	 }
	 
	 /*
	  * If possible, the active unit will move to the specified tile
	  * This may engage a battle between two units. The mover has the advantage in that battle
	  * If all of the units of the loser have been wiped out, the game is over
	  * After one move, a player has the ability to end their turn
	  */
	 
	 public void attemptTileMove(Tile tile) {
		 

		 if(!gameActive) return;
		 
		 if(activeUnit == null) {
			 
			 activeUnit = tile.getUnit();
			 return;
			 
		 } 
		 else if(tile.getUnit() == activeUnit) {
			 
			 activeUnit = null; 
			 return;
		 }
		 
		 
		 if(activeUnit.getPlayer() != currentPlayer || activeUnit.getMovesRemaining()==0 || tile.getHighlight() != TileStatus.REACHABLE) {
			 return;
		 }
		 
		 Unit otherUnit = tile.getUnit();
		 if(otherUnit.getPlayer() != currentPlayer) {
			 
			 activeUnit.setDestination(tile);
			 
			 /* Battle engaged */
			 if(otherUnit != null) {
				
				  
				  Unit winner = this.engageBattle(activeUnit, otherUnit);
				 
				  if(winner == activeUnit) {
					  
					  tile.setUnit(activeUnit);
					  
				   	  unitHandler.removeUnit(otherUnit);
					  
					  playerUnits = unitHandler.getPlayerUnits(otherUnit.getPlayer());
					
					  
				  } else {
					  
					  
					  unitHandler.removeUnit(activeUnit); 
					  playerUnits = unitHandler.getPlayerUnits(currentPlayer);
		
				  }
				  
				  this.setChanged();
				  this.notifyObservers(new ObservableArgs("battleWinner", winner.getPlayer()));
				  
				  //Determine if the player who won the battle won the game
				  if(this.determineWinner(winner.getPlayer())) {
					  return;
				  }
				  
			 } else {
				 
				 tile.setUnit(activeUnit);
				 
			 }
			 
			 activeUnit.setMovesRemaining(activeUnit.getMovesRemaining()-1);
			 this.setChanged();
			 this.notifyObservers(new ObservableArgs("movesRemaining", activeUnit.getMovesRemaining()));
			 
			 /* Notify that a move has been made, and the player can end their turn */
			 if(!hasMadeMove) {
				 hasMadeMove = true;
				 this.setChanged();
				 this.notifyObservers(new ObservableArgs("endTurnActive", true));
			 }
			
			 
			 ArrayList<Unit> playerUnits = unitHandler.getPlayerUnits(currentPlayer);
			 boolean canMove = false;
			 for(Unit i : playerUnits) {
				 
				 if(i.getMovesRemaining() != 0) {
					 canMove = true;
					 break; 
				 }
				 
			 }
			 
			 /* All of the player's units moves are 0 */
			 if(!canMove) {
				 
				 this.changePlayer();
				 
			 }
			 
		 }
		 
	 }
	 
	 
	 public boolean determineWinner(int winner) {
		 
		 ArrayList<Unit> playerUnits = unitHandler.getPlayerUnits(!winner.getPlayer());
		  
		  /* Winner time! */
		  if(playerUnits.size() == 0) {
			  
			  this.gameActive = false;
			  this.winner = winner.getPlayer();
			  this.setChanged();
			  this.notifyObservers(new ObservableArgs("gameWinner", winner.getPlayer()));
			  
			  return true;
			  
		  }
		  
		  return false;
		 
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
	 
	 /*
	  * Calls update 60 times a second
	  */
	 public void run() {
		 
		 while(true) {
			 
			 long now = System.nanoTime();
			 long diff = now - lastTime;
	
			 diff /= 1000000; //Nano to milliseconds
			 
			 if(diff > (1000 / 60)) {
				 update();
				 lastTime = now;
			 }
		 }
	 }
	
}
