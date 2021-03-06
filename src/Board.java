import java.awt.*;
import java.util.Iterator;

/**
 * @author Spencer Davis
 * This is the Board class.
 */
public class Board extends Entity {
    protected Tile[]      tileList;
    protected UnitHandler units;
    protected Tile        lastSelected;
    protected Tile[]      lastHighlighted;
    protected boolean 	  freeze;
    protected int xSize, ySize;
    
    /**
     * Constructor.
     * @param tiles an array of Tiles
     * @param units a UnitHandler
     */
    protected Board(Tile[] tiles, UnitHandler units, int xSize, int ySize){
        tileList     = tiles;
        this.units   = units;
        lastSelected = null;
        this.xSize = xSize;
        this.ySize = ySize;
        freeze=false;
    }
    
    public void freeze() {
    	freeze=true;
    }
    public void unfreeze() {
    	freeze=false;
    }
    
    public int getXSize() {
        return xSize;
    }
    
    public int getYSize() {
        return ySize;
    }
    
    public Tile getTileAtCoords(int x, int y) {
    	
    	 for(int i = 0; i < tileList.length; i++){
             if(tileList[i].contains(x,y)){
            	 
            	 return tileList[i];
             }
    	 }
    	 return null;
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
        clearHighlights();
        
        Tile found = getTileAtCoords(x,y);
        
        selectTile(found);
        
        return found;
    }
    
    /**
     * Clears highlights, then selects the Tile specified, in terms of highlights,
     * unless the Tile specified is inactive.
     * @param aTile the Tile specified
     * @return whether or not the highlights actually took place
     */
    public boolean selectTile(Tile aTile) {
        clearHighlights();
        lastSelected = aTile;
        lastHighlighted = new Tile[6];
        if(lastSelected != null && lastSelected.isThisThingOn()) 
        {
        	lastSelected.setHighlight(TileStatus.SELECTED);
        	if(aTile.getUnit() == null) return true;
	        Tile[] possibleMoves = aTile.getUnit().getPossibleMoveLocations();
	        
	        int j = 0;
	        for(int i=0;i<possibleMoves.length;i++) {
	        	
	        	if(possibleMoves[i] != null && possibleMoves[i].isThisThingOn()) {
	        		possibleMoves[i].setHighlight(TileStatus.REACHABLE);
	        		lastHighlighted[j++] = possibleMoves[i];
	        	}
	        	
	        }
	        return true;
        }
    
        return false;
    }
    
    /**
     * Clears the current highlights of Tiles.
     * @return a boolean that tells whether or not there were any highlights to clear.
     */
    public boolean clearHighlights(){
        if(lastSelected != null){
            lastSelected.setHighlight(TileStatus.NONE);
            for(int i = 0; i < lastHighlighted.length; i++){
                if(lastHighlighted[i] != null)
                    lastHighlighted[i].setHighlight(TileStatus.NONE);
            }
            lastSelected    = null;
            lastHighlighted = null;
            return true;
        }else return false;
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
        if(lastHighlighted != null) {
	        for(i = 0; i < lastHighlighted.length; i++){
	        	if(lastHighlighted[i]!=null) {
	        		lastHighlighted[i].render(g2D);
	        	}
	        }
        }
       if(lastSelected != null) 
    	   lastSelected.render(g2D);
    }
    
    /**
     * Calls update on all the units;
     */
    public void update(){
    	if(freeze) return; 
        Iterator<Unit> unitIterator;
        Unit theUnit;
        for(int i = 0; i < 2; i++){
            unitIterator = units.getPlayerUnits(i).iterator();
            do{
                theUnit = unitIterator.next();
                if(theUnit != null) {
                	theUnit.update();
                }
            }while(unitIterator.hasNext());
        }
    }
}