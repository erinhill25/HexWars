import java.io.IOException;

/**
 * @author Spencer Davis
 * This is the interface for BoardFactory classes, which are used to create a Board,
 * and potentially resize the graphics or save the current Board state to a file.
 */
public interface BoardFactory {
	/**
	 * Builds a Board object and returns it.
	 * @param units a UnitHandler to place Units
	 * @param size a specifier for graphical size of the Board
	 * @param filePath a path to a file specifying the Board layout
	 * @return the Board constructed
	 * @throws IOException if filePath is invalid, or the file scanner runs into some other sort of error
	 */
    public Board constructBoard(UnitHandler units, int size, String filePath) throws IOException;
    /**
     * Resizes the graphics of the Board (optional operation).
     * @param theBoard the Board to resize
     * @param size the new size specifier
     * @return the resized Board
     */
    public Board reSize(Board theBoard, int size);
    /**
     * Saves the state of the Board to a file (optional operation).
     * @param theBoard the Board to be saved
     * @param filePath the path and file name you want to save it as
     */
    public void saveGame(Board theBoard, String filePath);
}