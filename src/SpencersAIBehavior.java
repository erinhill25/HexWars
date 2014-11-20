
public class SpencersAIBehavior extends AIBehavior {
    
    private AIController controller;
    
    public SpencersAIBehavior(AIController controller) {
        this.controller = controller;
    }

    @Override
    public void makeMove(Unit unit){
        // Decide what tile to move to
        Tile exampleTile = new Tile(null, 0, 0, 0, null, 0);
        controller.makeMove(unit, exampleTile);
    }
}
