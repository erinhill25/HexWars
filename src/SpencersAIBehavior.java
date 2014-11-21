
public class SpencersAIBehavior extends AIBehavior {
    
    private AIController controller;
    
    public SpencersAIBehavior(AIController controller) {
        this.controller = controller;
    }

    @Override
    public void makeMove(Unit unit){
        // Decide what tile to move to
        Tile exampleTile = unit.getTile().getAdj(3);
        controller.makeMove(unit, exampleTile);
    }
}
