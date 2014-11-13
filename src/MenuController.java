import java.awt.event.*;
import java.awt.GridLayout;

import javax.swing.*;


public class MenuController extends WindowAdapter
implements ActionListener {
    // Constants
    private static final String MAIN_LABEL = "Choose your type of game:";
    private static final String[] GAME_TYPES = {"Human Player", "AI Player"};
    private static final int GRID_ROWS = 1;
    private static final int GRID_COLS = 2;
    
    // Components
    private MenuView view;
    private Main main;
    
    private JButton startB, exitB;
    private JSpinner playerOneSpin, playerTwoSpin;
    
    public MenuController(Main main) {
        view = new MenuView(this);
        this.main = main;
    }
    
    public JComponent createView() {
     // Content operations
        Box mainBox = Box.createVerticalBox();
            // Main Label
            JPanel labelWrap = new JPanel();
            labelWrap.add(new JLabel(MAIN_LABEL));
        mainBox.add(labelWrap);
            // Player 1
            JPanel p1wrap = new JPanel();
                Box p1Box = Box.createHorizontalBox();
                p1Box.add(new JLabel("Player 1: "));
                    playerOneSpin = new JSpinner(new SpinnerListModel(GAME_TYPES));
                p1Box.add(playerOneSpin);
            p1wrap.add(p1Box);
        mainBox.add(p1wrap);
            // Player 2
            JPanel p2wrap = new JPanel();
                Box p2Box = Box.createHorizontalBox();
                p2Box.add(new JLabel("Player 2: "));
                    playerTwoSpin = new JSpinner(new SpinnerListModel(GAME_TYPES));
                p2Box.add(playerTwoSpin);
            p2wrap.add(p2Box);
        mainBox.add(p2wrap);
            JPanel buttonGrid = new JPanel();
            buttonGrid.setLayout(new GridLayout(GRID_ROWS, GRID_COLS));
                // Start
                startB = new JButton("Start Game!");
                startB.addActionListener(this);
            buttonGrid.add(startB);
                // Exit
                exitB = new JButton("Exit");
                exitB.addActionListener(this);
            buttonGrid.add(exitB);
        mainBox.add(buttonGrid);
        
        return mainBox;
    }
    
    private void startGamePressed() {
        int playerOneValue = 0;
        int playerTwoValue = 0;
        
        if(playerOneSpin.getValue().toString() == GAME_TYPES[0]) {
            playerOneValue = main.HUMAN_PLAYER;
        } else if (playerOneSpin.getValue().toString() == GAME_TYPES[1]) {
            playerOneValue = main.AI_PLAYER;
        } else { main.abortGame("Unknown Player type!");}
        
        if(playerTwoSpin.getValue().toString() == GAME_TYPES[0]) {
            playerTwoValue = main.HUMAN_PLAYER;
        } else if (playerTwoSpin.getValue().toString() == GAME_TYPES[1]) {
            playerTwoValue = main.AI_PLAYER;
        } else { main.abortGame("Unknown Player type!");}
        
        main.startGame(playerOneValue, playerTwoValue);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == startB) {
            System.out.println("Start Button Pressed.\n"
                    + "Spinner Values: " + playerOneSpin.getValue() + " and " + playerTwoSpin.getValue() + ".");
            
            startGamePressed();
            
            view.dispose();
            
        } else if (e.getSource() == exitB) {
            System.out.println("Exit Button Pressed.");
            main.abortGame("User cancelled!");
        }
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("Some Window Closed");
        main.abortGame("User cancelled!");
    }

}
