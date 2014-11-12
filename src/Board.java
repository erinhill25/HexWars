import java.awt.*;

/**
 * @author Spencer Davis
 * This is the Board class. This class is bored. It also hunts boar. With a drill. Which bores. Or something. Maybe not.
 */
public class Board extends Entity {
	protected Tile[] tileList;
	protected UnitHandler units;
    protected Tile lastSelected;
    protected Tile[] lastHighlighted;
	
    /**
     * Constructor.
     * @param tiles an array of Tiles
     * @param units a UnitHandler
     */
	protected Board(Tile[] tiles, UnitHandler units){
        tileList = tiles;
        this.units = units;
        lastSelected = null;
    }
    
	/**
	 * Clears current highlights, then finds a Tile, if there is one, at the graphical location specified.
	 * If there is no active Tile at the location specified, this method returns null. Otherwise, it highlights
	 * the Tile and its neighbors accordingly and returns the Tile at the location specified.
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return the Tile at the coordinates specified.
	 */
    public Tile selectTile(int x, int y){
        int i;
        if(lastSelected != null){
            lastSelected.setHighlight(TileStatus.NONE);
            for(i = 0; i < lastHighlighted.length; i++){
                if(lastHighlighted[i] != null)
                    lastHighlighted[i].setHighlight(TileStatus.NONE);
            }
        }
        for(i = 0; i < tileList.length; i++){
            if(tileList[i].contains(x,y)){
                lastSelected = tileList[i];
                if(lastSelected.isThisThingOn()){
                    lastSelected.setHighlight(TileStatus.SELECTED);
                    lastHighlighted = lastSelected.getAdjs();
                    for(i = 0; i < lastHighlighted.length; i++){
                        if((lastHighlighted[i] != null) && (lastHighlighted[i].isThisThingOn()))
                            lastHighlighted[i].setHighlight(TileStatus.REACHABLE);
                    }
                    return lastSelected;
                }else{
                    lastSelected = null;
                    lastHighlighted = null;
                    return null;
                }
            }
        }
        lastSelected = null;
        lastHighlighted = null;
        return null;
    }
    
    /**
     * Sets the UnitHandler attached to this Board.
     * @param units the UnitHandler
     */
    public void setUnits(UnitHandler units){
        this.units = units;
    }
    
    /**
     * Gets the UnitHandler attached to this Board.
     * @return the UnitHandler.
     */
    public UnitHandler getUnits(){
        return units;
    }
    
    /**
     * Calls the render method of all Tiles making up this Board, ending with the highlighted tiles so the
     * highlight borders aren't covered up by other tiles.
     */
    public void render(Graphics2D g2D){
        int i;
        for(i = 0; i < tileList.length; i++){
            tileList[i].render(g2D);
        }
        for(i = 0; i < lastHighlighted.length; i++){
            lastHighlighted[i].render(g2D);
        }
        lastSelected.render(g2D);
    }
    
    /**
     * Does nothing since the Board is not animated.
     */
    public void update(){}
}