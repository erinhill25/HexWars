import java.io.*;
import java.util.Date;

import javax.swing.JOptionPane;

/**
 * FileWriter
 * 
 * Methods:
 *  open
 *  isOpen
 *  close
 *  logStartGame
 *  logEndGame
 *  logPlayerTurn
 *  logMoveUnit
 *  logMoveUnitFailed
 *  logActiveUnit
 *  logGameReset
 *  
 *  
 * @author dlipke
 *
 */
public class GameLogger {
    private static final String FILE_NAME = "Hex_Wars_gamenumber_";
    
    private static PrintWriter fileWriter;
    private static int gameNumber = 0;
    
    public static void open() {
        ++gameNumber;
        
        try
        {
            boolean append = true;
            File saveFile = new File("logs/" + FILE_NAME + gameNumber + ".txt");
            if (!saveFile.isFile())
            {
                append = false;
            }
            fileWriter = new PrintWriter(new BufferedWriter(new FileWriter(saveFile, append)));
        }
        catch (Exception excep)
        {
            JOptionPane.showMessageDialog(null, "Something went wrong and the file was not saved."
                    + "\nPlease check the console for more details.", "Error", JOptionPane.WARNING_MESSAGE);
            System.out.println(excep.getMessage());
            excep.printStackTrace();
        }
        
        Date date = new Date();
        fileWriter.append("Game " + gameNumber + ": " + date.toString() + "\n\n");
    }
    
    public static boolean isOpen() {
        return fileWriter != null;
    }
    
    public static void close() {
        if (fileWriter != null)
        {
            fileWriter.append("\nFile Closed\n\n\n\n");
            fileWriter.close();
            
            fileWriter = null;
        }
    }
    
    public static void logStartGame() {
        if (fileWriter == null) { // If the fileWriter hasn't been opened, then do nothing
            return;
        }
        
        fileWriter.append("Game " + gameNumber + " Started!\n\n");
        ++gameNumber;
    }
    public static void logEndGame(int playerWinner) {
        if (fileWriter == null) { // If the fileWriter hasn't been opened, then do nothing
            return;
        }
        
        fileWriter.append("\nPlayer " + (playerWinner + 1) + " has won!\n"
                + "Game Ended!");
    }
    public static void logPlayerTurn(int player) {
        if (fileWriter == null) { // If the fileWriter hasn't been opened, then do nothing
            return;
        }
        
        fileWriter.append("Player " + (player + 1) + "'s turn.\n");
    }
    public static void logMoveUnit(Unit unitMoved) {
        if (fileWriter == null) { // If the fileWriter hasn't been opened, then do nothing
            return;
        }
        
        fileWriter.append("\tUnit Moved: " + unitMoved.getClass() + "\n");
    }
    public static void logMoveUnitFailed(Unit unitMoved) {
        if (fileWriter == null) { // If the fileWriter hasn't been opened, then do nothing
            return;
        }
        
        fileWriter.append("\tMoving of Unit: " + unitMoved.getClass() + " Failed\n");
    }
    public static void logActiveUnit(Unit unitActive) {
        if (fileWriter == null) { // If the fileWriter hasn't been opened, then do nothing
            return;
        }
        
        fileWriter.append("\tUnit Selected: " + unitActive.getClass() + "\n");
    }
    public static void logGameReset() {
        if (fileWriter == null) { // If the fileWriter hasn't been opened, then do nothing
            return;
        }
        
        fileWriter.append("Game Reset!\n");
    }
}
