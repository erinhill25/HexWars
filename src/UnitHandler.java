import java.util.ArrayList;

public class UnitHandler {
	
	static final int NUM_OF_UNITS = 11;
	
	ArrayList<Unit> player1Units = new ArrayList<Unit>();
	ArrayList<Unit> player2Units = new ArrayList<Unit>();
	
	public UnitHandler() {
	}
	
	public void addUnit(Unit unit) {
		//switch statement for adding to each player
		if(unit.getPlayer() == 0)
			player1Units.add(unit);
		else
			player2Units.add(unit);
	}
	
	public void removeUnit(Unit unit) {
		
		if(unit.getPlayer() == 0) {
			player1Units.remove(unit);
		}
		else {
			player2Units.remove(unit);
		}
		
	}
	
	public void destroyAllUnits() {
		
		player1Units = new ArrayList<Unit>();
		player2Units = new ArrayList<Unit>();
		
	}
	
	
	public ArrayList<Unit> getPlayerUnits(int player) {
		if(player == 0)
			return player1Units;
		else
			return player2Units;
		
		
	}
}
