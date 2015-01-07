/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golfx;

/**
 *
 * @author Kimmo
 */
public class Land {
    
    private Thingy[][] mapOfThingies;
    private int rows;
    private int columns;
    
    public Land() {
        rows = 20;
        columns = 20;
        mapOfThingies = new Thingy[rows][columns];
    }
    
    public Land(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        mapOfThingies = new Thingy[rows][columns];
    }
    
    public int getWidth() {
        return columns;
    }
    
    public int getHeight() {
        return rows;
    }
    
    /**
     * Adds a thingy to a map, if there is empty place for it.
     * 
     * @param row
     * @param col
     * @param thingy
     * @return boolean true if thingy was added, false if not
     */
    public boolean addThing(int row, int col, Thingy thingy) {
        if (mapOfThingies[row][col] == null) {
            mapOfThingies[row][col] = thingy;
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Gets the thingy in given place.
     * 
     * @param row
     * @param col
     * @return null if the place is empty or Thingy is there is one.
     */
    public Thingy getThingy(int row, int col) {
        
        return mapOfThingies[row][col];
    }
    
    /**
     * Checks if there is a thingy in given direction of give palce.
     * 0-N, 1-NE, 2-E, 3-SE, 4-S, 5-SW, 6-W, 7-NW
     * 
     * @param row
     * @param col
     * @param dir
     * @return 
     */
    public boolean isThingyInDirection(int row, int col, int dir) {
        
        int[] newRnC = getCoordsOfDirection(row,col,dir);
        
        return (mapOfThingies[newRnC[0]][newRnC[1]] == null)?(false):(true);
    }
    
    public Thingy getThingyFromDirection(int row, int col, int dir) {
            
        int[] newRnC = getCoordsOfDirection(row,col,dir);
        
        return mapOfThingies[newRnC[0]][newRnC[1]];
    }
    
    private int[] getCoordsOfDirection(int row, int col, int dir) {
        
        int[] result = new int[2];
        
        switch(dir) {
            case 0:
                row -= 1;
                break;
            case 1:
                row -= 1;
                col += 1;
                break;
            case 2:
                col += 1;
                break;
            case 3:
                row += 1;
                col += 1;
                break;
            case 4:
                row += 1;
                break;
            case 5:
                row += 1;
                col -= 1;
                break;
            case 6:
                col -= 1;
                break;
            case 7:
                row -= 1;
                col -= 1;
                break;
        }
        
        // Remember table indexes from 0 to n, not from 1
        if (row < 0) row = (rows - 1);
        if (row >= rows) row = 0;
        if (col < 0) col = (columns - 1);
        if (col >= columns) col = 0;
        
        result[0] = row;
        result[1] = col;
        
        return result;
    }
    
    public int liveOnes() {
        int lives = 0;
        for (int r=0; r<rows; r++) {
            for (int c=0; c<columns; c++) {
                if (mapOfThingies[r][c].isAlive()) {
                    lives++;
                }
            }
        }
        return lives;
    }
    
    public void printMap(String name) {
        if (name.isEmpty()) {
            System.out.println("=====================");
        } else {
            System.out.println("=== "+name+" ===");
        }
        for (int r=0; r<rows; r++) {
            for (int c=0; c<columns; c++) {
                System.out.print(mapOfThingies[r][c].printChar());
            }
            System.out.println();
        }
        System.out.println("=====================");
    }
}
