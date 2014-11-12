import java.awt.*;

/**
 * @author Spencer Davis
 * A class to define a color palette for the Board.
 */
class ColorScheme {
    protected Color[] bases;
    protected Color inactive;
    protected Color selected;
    protected Color reachable;
    protected Color selectBorder;
    protected Color reachBorder;
    
    /**
     * Basic constructor. Must fill all values.
     * @param baseColors the array of base Tile colors
     * @param inactive the 'highlight' color of an inactive, untraversable Tile
     * @param selected the highlight color of a selected Tile
     * @param selectBorder the border color of a selected Tile
     * @param reachable the highlight color of a reachable Tile
     * @param reachBorder the border color of a reachable Tile
     */
    ColorScheme(Color[] baseColors, Color inactive, Color selected, Color selectBorder, Color reachable, Color reachBorder){
        bases = baseColors;
        this.inactive = inactive;
        this.selected = selected;
        this.selectBorder = selectBorder;
        this.reachable = reachable;
        this.reachBorder = reachBorder;
    }
    
    /**
     * Resets all the Colors.
     * @param baseColors the array of base Tile colors
     * @param inactive the 'highlight' color of an inactive, untraversable Tile
     * @param selected the highlight color of a selected Tile
     * @param selectBorder the border color of a selected Tile
     * @param reachable the highlight color of a reachable Tile
     * @param reachBorder the border color of a reachable Tile
     */
    void changeColors(Color[] baseColors, Color inactive, Color selected, Color selectBorder, Color reachable, Color reachBorder){
        bases = baseColors;
        this.inactive = inactive;
        this.selected = selected;
        this.selectBorder = selectBorder;
        this.reachable = reachable;
        this.reachBorder = reachBorder;
    }
}