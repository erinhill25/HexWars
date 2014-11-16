
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

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
        
        try{
            Image icon = ImageIO.read(new File("./resources/icon.png"));
            this.setIconImage(icon);
        }catch(IOException e){
            e.printStackTrace();
        }
        
        this.add(controller.createView());
        
        this.setVisible(true);
    }
}
