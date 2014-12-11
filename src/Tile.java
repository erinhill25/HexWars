import java.awt.*;
import java.util.*;

/**
 * @author Spencer Davis
 * A Tile class as the base entity of a Board.
 */
public class Tile extends Entity {
    protected static int  idCounter = 0;
    protected final  int  id; //useful for debugging
    protected final  int  xIndex, yIndex;
    protected Polygon     polyGon;
    protected ColorScheme colors;
    protected int         colorIdx;
    protected Unit        tacUnit;
    protected boolean     isActive;
    protected TileStatus  highLight;
    protected Tile[]      adjTiles;
    
    /**
     * Constructor for the Tile.
     * @param shape the Polygon shape of the tile
     * @param centerX The center of the Polygon, x component
     * @param centerY The center of the Polygon, y component
     * @param numSides the number of sides the Polygon has
     * @param colors the ColorScheme to specify color
     * @param baseColorIndex the index into the ColorScheme's base colors
     */
    protected Tile(Polygon shape, int centerX, int centerY, int numSides,
            ColorScheme colors, int baseColorIndex, int xIndex, int yIndex){
        polyGon     = shape;
        adjTiles    = new Tile[numSides];
        colorIdx    = baseColorIndex;
        this.colors = colors;
        isActive    = true;
        x = centerX;
        y = centerY;
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        highLight   = TileStatus.NONE;
        id          = idCounter;
        idCounter++;
    }
    
    /**
     * Finds out whether a point exists within this Tile.
     * @param x the point's x coordinate
     * @param y the point's y coordinate
     * @return the boolean result.
     */
    public boolean contains(int x, int y){
        return polyGon.contains(x,y);
    }
    
    /**
     * Toggles the active status of this Tile. Turns it inactive if it was active, or active if it was inactive.
     * @return the new active status of this Tile.
     */
    public boolean toggleActive(){
        isActive = !isActive;
        return isActive;
    }
    
    /**
     * Tells whether this Tile is active or inactive.
     * @return the boolean value of this Tile's active status.
     */
    public boolean isThisThingOn(){
        return isActive;
    }
    
    /**
     * Sets the highlight status of this Tile.
     * @param highlight a TileStatus
     */
    public void setHighlight(TileStatus highlight){
        highLight = highlight;
    }
    
    /**
     * Gets the highlight status of this Tile.
     * @return a TileStatus.
     */
    public TileStatus getHighlight(){
        return highLight;
    }
    
    /**
     * Puts a Unit on this Tile.
     * @param newUnit the Unit to put on this Tile
     */
    public void setUnit(Unit newUnit){
        tacUnit = newUnit;
    }
    
    /**
     * Gets the Unit on this Tile, if there is one.
     * @return the Unit on this Tile, or null if there is no Unit
     */
    public Unit getUnit(){
        return tacUnit;
    }
    
    /**
     * If this Tile has a Unit on it, this method removes the Unit.
     */
    public void removeUnit(){
        tacUnit = null;
    }
    
    /**
     * Sets another Tile adjacent to this one at the side specified.
     * This is not a symmetric operation.
     * @param adjTile the Tile to be set adjacent
     * @param sideIndex the side at which the specified Tile will be set adjacent
     */
    public void setAdj(Tile adjTile, int sideIndex){
        adjTiles[sideIndex] = adjTile;
    }
    
    /**
     * Returns the Tile adjacent to this one at the side specified.
     * 0 is the right side and successive indexes go up clockwise.
     * @param sideIndex the index of the specified side
     * @return the Tile at the specified side.
     */
    public Tile getAdj(int sideIndex){
        return adjTiles[sideIndex];
    }
    
    /**
     * Gives the array of tiles adjacent to this one.
     * @return the array of Tiles adjacent to this one.
     */
    public Tile[] getAdjs(){
        return adjTiles;
    }
    
    /**
     * Gets the side index where an adjacent Tile resides.
     * @param adjacent a Tile that should be adjacent.
     * @return the Index at which the Tile is adjacent. -1 if it's not adjacent.
     */
    public int getSideIndex(Tile adjacent){
        for(int i = 0; i < adjTiles.length; i++){
            if(adjTiles[i] == adjacent) return i;
        }
        return -1;
    }
    
    /**
     * Currently does nothing because Tile isn't animated.
     */
    public void update(){}
    
    /**
     * Returns the x location of the Tile's Polygon's center.
     * @return the x location of the Tile's Polygon's center.
     */
    public double getX(){
        return x;
    }
    
    /**
     * Returns the y location of the Tile's Polygon's center.
     * @return the y location of the Tile's Polygon's center.
     */
    public double getY(){
        return y;
    }
    
    /**
     * Returns the x index of the Tile within the Board.
     * @return the x index of the Tile within the Board.
     */
    public int getXIndex(){
        return xIndex;
    }
    
    /**
     * Returns the y index of the Tile within the Board.
     * @return the y index of the Tile within the Board.
     */
    public int getYIndex(){
        return yIndex;
    }
    
    /**
     * Renders the Tile and any Unit on it.
     */
    public void render(Graphics2D g2D){
        g2D.setColor(colors.bases[colorIdx]);
        g2D.fill(polyGon);
        if(isActive){
            switch(highLight){
                case SELECTED:
                    g2D.setColor(colors.selected);
                    g2D.fill(polyGon);
                    g2D.setColor(colors.selectBorder);
                    g2D.setStroke(new BasicStroke(5));
                    g2D.draw(polyGon);
                    break;
                case REACHABLE:
                    g2D.setColor(colors.reachable);
                    g2D.fill(polyGon);
                    g2D.setColor(colors.reachBorder);
                    g2D.setStroke(new BasicStroke(3));
                    g2D.draw(polyGon);
                    break;
                default:
                    g2D.setColor(Color.BLACK);
                    g2D.setStroke(new BasicStroke(2));
                    g2D.draw(polyGon);
                    break;
            }
            if(tacUnit != null){
                tacUnit.render(g2D);
            } 
        }else{
            g2D.setColor(colors.inactive);
            g2D.fill(polyGon);
        }
        if(showIDs) g2D.drawString((id+""), (float)x, (float)y);
    }
}
