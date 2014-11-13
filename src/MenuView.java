
import javax.swing.*;
import java.awt.event.*;

/*
 * Get the information regarding what type of game to start
 */
public class MenuView extends JFrame {
    // Constants
    private static final int WIDTH = 300;
    private static final int HEIGHT = 220;
    
    private static final String TITLE = "Start HexWars!";
    
    
    public MenuView(MenuController controller) {
        // Basic frame operations
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(controller);
        this.setTitle(TITLE);
        
        this.add(controller.createView());
        
        this.setVisible(true);
    }
}
