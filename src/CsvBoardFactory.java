import java.io.*;
import java.util.*;
import java.awt.*;

/**
 * @author Spencer Davis
 * This class's purpose is to create a Board object with hexagonal tiles, from a csv file, ignoring whitespace.
 * Example:
 *      V0V,A1P,A2V
 *      A2P,A0K,N1V
 * Each item between commas represents a single Tile.
 * Each Tile specifier is made up of a:
 *      activity specifier:
 *          'V' means the Tile doesn't exist: Void
 *          'A' means it's a normal Tile: Active
 *          'N' means the Tile is untraversable: Not Active
 *      base color:
 *          a ColorScheme index
 *      unit specifier
 *          'V' means there's no Unit on the Tile
 *          'P' means there's a basic Soldier of Player 0 on the Tile
 *          'S' means there's a basic Soldier of Player 1 on the Tile
 *          'K' means there's a King of Player 0 on the Tile
 *          'Q' means there's a King of Player 1 on the Tile
 */
public class CsvBoardFactory implements BoardFactory{
    int    inRad;    //hexagon inscribed radius
    double halfSide; //half of hexagon side length
    double exRad;    //hexagon excribed radius
    double centerX, centerY; //hexagon center
    
    /**
     * Builds the Board object and returns it.
     * @param units a UnitHandler to place Units
     * @param size the inscribed radius of a Tile
     * @param filePath see class description
     * @return the Board constructed
     * @throws IOException if filePath is invalid, or the file scanner runs into some other sort of error
     */
    public Board constructBoard(UnitHandler units, int size, String filePath) throws IOException{
        ColorScheme colors = createColorScheme();
        inRad    = size;
        halfSide = inRad*1.15470; // inRad*tan(30)
        exRad    = inRad*0.57735; // radius = inRad/cos(30)
       
        String[][] initMatrix = readFile(filePath);
        Tile[][] tileMatrix = new Tile[initMatrix.length][initMatrix[0].length];
        String tileInit;
        Unit unit;
        for(int i = 0; i < initMatrix.length; i++){
            for(int j = 0; j < initMatrix[i].length; j++){
                tileInit = initMatrix[i][j];
                if(tileInit.charAt(0) != 'V'){
                	
                	
                	int colorIdx = Integer.parseInt(tileInit.substring(1, 2)); 
                	System.out.println();
                    tileMatrix[i][j] = 
                            new Tile(createHex(j,i),(int)centerX,(int)centerY,6,colors,colorIdx);
                    if(tileInit.charAt(0) == 'A'){
                        switch(tileInit.charAt(2)){
                            case 'V':
                                unit = null;
                                break;
                            case 'P':
                                unit = new Soldier(0,tileMatrix[i][j]);
                                break;
                            case 'K':
                                unit = new King(0,tileMatrix[i][j]);
                                break;
                            case 'S':
                                unit = new Soldier(1,tileMatrix[i][j]);
                                break;
                            case 'Q':
                                unit = new King(1,tileMatrix[i][j]);
                                break;
                            default:
                                unit = null;
                                break;
                        }
                        if(unit != null){
                            units.addUnit(unit);
                            tileMatrix[i][j].setUnit(unit);
                        }//end if unit != null
                    }else{
                        tileMatrix[i][j].toggleActive();
                    }
                }//end if status != 'V'
            }//end for j
        }//end for i
        
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        Tile temp;
        for(int i = 0; i < tileMatrix.length; i++){
            for(int j = 0; j < tileMatrix[i].length; j++){
                temp = tileMatrix[i][j];
                if(temp != null){
                    tiles.add(temp);
                    temp.setAdj(getWithBounds(tileMatrix,i,j+1),0);
                    temp.setAdj(getWithBounds(tileMatrix,i,j-1),3);
                    if(i%2 == 0){
                        temp.setAdj(getWithBounds(tileMatrix,i+1,j+1),1);
                        temp.setAdj(getWithBounds(tileMatrix,i+1,j),2);
                        temp.setAdj(getWithBounds(tileMatrix,i-1,j),4);
                        temp.setAdj(getWithBounds(tileMatrix,i-1,j+1),5);
                    }else{
                        temp.setAdj(getWithBounds(tileMatrix,i+1,j),1);
                        temp.setAdj(getWithBounds(tileMatrix,i+1,j-1),2);
                        temp.setAdj(getWithBounds(tileMatrix,i-1,j-1),4);
                        temp.setAdj(getWithBounds(tileMatrix,i-1,j),5);
                    }
                }
            }
        }
        
        return (new Board(tiles.toArray(new Tile[tiles.size()]), units));
    }
    
    /**
     * Helper function for constructBoard. Simply checks whether the x/y indexes entered are inside
     * the bounds of the matrix entered, and returns the Tile at that location if they are.
     * @param tileMatrix
     * @param index1
     * @param index2
     * @return the Tile at the indexes specified, or null if they are out of bounds.
     */
    private Tile getWithBounds(Tile[][] tileMatrix, int index1, int index2){
        if(index1 > tileMatrix.length-1 || index1 < 0 || 
                index2 < 0 || index2 > tileMatrix[0].length-1){
            return null;
        }
        return tileMatrix[index1][index2];
    }
    
    /**
     * Reads the text(csv) file and separates the Tile specifiers into their appropriate matrix locations
     * @param filePath the path for the File
     * @return a matrix of Strings, one cell for each Tile location
     * @throws FileNotFoundException if the file given is invalid
     */
    private String[][] readFile(String filePath) throws FileNotFoundException{
        Scanner scan = new Scanner(new BufferedReader(
                new FileReader(filePath)));
        ArrayList<String[]> rows = new ArrayList<String[]>();
        String[] temp;
        while (scan.hasNext()) {
            temp = scan.next().split(",");
            rows.add(temp);
        }
        scan.close();
        return rows.toArray(new String[rows.size()][]);
    }
    
    /**
     * Creates a Polygon for a Tile
     * @param xIndex
     * @param yIndex
     * @return the Polygon.
     */
    private Polygon createHex(int xIndex, int yIndex){
    	
        //Vertical Position
        centerY = yIndex*(exRad + halfSide) + inRad + exRad;
    	
        //Horizontal Position
        if(yIndex%2 == 1) {
        	centerX = xIndex*2*inRad + 3*inRad;
        	centerX += inRad;
        } 
        else { 
        	
        	centerX = xIndex*2*inRad + 3*inRad; //index * inscribed radius + more offset
        
        }


        
        int[] xPoints = new int[6];
        int[] yPoints = new int[6];
        
        xPoints[0] = (int)(centerX + inRad);
        xPoints[1] = (int)(centerX + inRad);
        xPoints[2] = (int)(centerX);
        xPoints[3] = (int)(centerX - inRad);
        xPoints[4] = (int)(centerX - inRad);
        xPoints[5] = (int)(centerX);
        
        yPoints[0] = (int)(centerY - exRad);
        yPoints[1] = (int)(centerY + exRad);
        yPoints[2] = (int)(centerY + halfSide);
        yPoints[3] = (int)(centerY + exRad);
        yPoints[4] = (int)(centerY - exRad);
        yPoints[5] = (int)(centerY - halfSide);
        
        return new Polygon(xPoints, yPoints, 6);
    }
    
    /**
     * Creates the default green ColorScheme
     * @return the ColorScheme
     */
    private ColorScheme createColorScheme(){
        Color[] bases          = {new Color(153, 240, 66), new Color(102, 189, 15), new Color(77, 142, 11)};
        Color translucentBlack = new Color(0,0,0);
        Color translucentCyan  = new Color(0x00FFFF40, true);
        Color translucentBlue  = new Color(0x0000FF40, true);
        return new ColorScheme(bases, translucentBlack, translucentBlue, 
                Color.RED, translucentCyan, Color.YELLOW);
    }
    
    /**
     * Operation not yet supported. Merely returns the Board specified.
     */
    public Board reSize(Board theBoard, int size){ return theBoard; }
    
    /**
     * Operation not yet supported. Does nothing.
     */
    public void saveGame(Board theBoard, String filePath){}
}