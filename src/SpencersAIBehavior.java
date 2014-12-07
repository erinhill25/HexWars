import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;


public class SpencersAIBehavior extends AIBehavior {
    int player;
    int[][][] weights;
    int[][][] paths;
    String fileName;
    
    private AIController controller;
    
    public SpencersAIBehavior(AIController controller, int player, Board board, String fileName) {
        this.controller = controller;
        this.fileName = fileName;
        weights = new int[board.getXSize()][board.getYSize()][6];
        paths = new int[board.getXSize()][board.getYSize()][6];
        for(int i = 0; i < weights.length; i++){
            for(int j = 0; j < weights[i].length; j++){
                for(int k = 0; k < weights[i][j].length; k++){
                    weights[i][j][k] = 0;
                    paths[i][j][k] = 0;
                }
            }
        }
        try{
            readFile(fileName);
        }catch(FileNotFoundException e){
            
        }
        this.player = player;
    }
    
    public void commit(int win){
        for(int i = 0; i < weights.length; i++){
            for(int j = 0; j < weights[i].length; j++){
                for(int k = 0; k < weights[i][j].length; k++){
                    weights[i][j][k] += paths[i][j][k]*win;
                    paths[i][j][k] = 0;
                }
            }
        }
        saveFile(fileName);
    }
    
    private void readFile(String fileName) throws FileNotFoundException{
        File file = new File("ais/" + fileName + ".csv");
        if(!file.isFile()){
            return;
        }
        Scanner scan = new Scanner(new BufferedReader(
                new FileReader("ais/" + fileName + ".csv")));
        ArrayList<String[]> rows = new ArrayList<String[]>();
        String[] temp;
        boolean locNotWeight = true;
        int tempX = 0;
        int tempY = 0;
        while (scan.hasNext()) {
            temp = scan.next().split(",");
            if(locNotWeight){
                tempX = Integer.parseInt(temp[0]);
                tempY = Integer.parseInt(temp[1]);
            }else{
                for(int i = 0; i < weights[tempX][tempY].length; i++){
                    weights[tempX][tempY][i] = Integer.parseInt(temp[i]);
                    //format: 
                    //locX,locY
                    //weight0,weight1,weight2,weight3,weight4,weight5
                    //locX,locY
                    //...
                }
            }
        }
        scan.close();
    }
    
    private void saveFile(String fileName) {
        PrintWriter fileWriter;
        try
        {
            File saveFile = new File("ais/" + fileName + ".csv");
            fileWriter = new PrintWriter(new BufferedWriter(new FileWriter(saveFile, false)));
        }
        catch (Exception excep)
        {
            JOptionPane.showMessageDialog(null, "Something went wrong and the AI file was not saved."
                    + "\nPlease check the console for more details.", "Error", JOptionPane.WARNING_MESSAGE);
            System.out.println(excep.getMessage());
            excep.printStackTrace();
            return;
        }
        for(int xLoc = 0; xLoc < weights.length; xLoc++){
            for(int yLoc = 0; yLoc < weights[xLoc].length; yLoc++){
                fileWriter.println(xLoc + "," + yLoc);
                int temp = weights[xLoc][yLoc].length;
                for(int wIndex = 0; wIndex < temp; wIndex++){
                    fileWriter.print(weights[xLoc][yLoc][wIndex]);
                    if(wIndex < temp - 1){
                        fileWriter.print(",");
                    }else{
                        fileWriter.println();
                    }
                }
            }
        }
        fileWriter.close();
    }

    @Override
    public void makeMove(Unit unit){
        // Decide what tile to move to
        Tile[] future = unit.getPossibleMoveLocations();
        Tile current = unit.getTile();
        int[] curWeights = weights[current.getXIndex()][current.getYIndex()];
        int max = 0;
        int maxIndex = 0;
        int temp = 0;
        int tempPlayer;
        for(int i = 0; i < curWeights.length; i++){
            tempPlayer = -1;
            temp = curWeights[i];
            if(future[i] != null && future[i].isThisThingOn()){
                if(future[i].getUnit() != null){
                    tempPlayer = future[i].getUnit().getPlayer();
                    if(tempPlayer != player){
                        if(temp >= 0) temp += temp*2 + 1;
                        else temp += -(temp*2) + 1;
                    }
                }
                if(temp >= max && tempPlayer != player){
                    max = temp;
                    maxIndex = i;
                }
            }
        }
        controller.makeMove(unit, future[maxIndex]);
        paths[current.getXIndex()][current.getYIndex()][maxIndex]++;
    }
}
