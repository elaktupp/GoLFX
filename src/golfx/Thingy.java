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
public class Thingy {
    
    private enum Phases {
        EMPTY,
        DEAD,
        ALIVE,
        DYING,
        REBORN
    }

    private Phases currentPhase;
    private Phases nextPhase;
    private int row;
    private int col;
    
    public Thingy(int row, int col) {
        this.row = row;
        this.col = col;
        this.currentPhase = Phases.DEAD;
        this.nextPhase = Phases.EMPTY;
    }

    public boolean isUnoccupied() {
        return (currentPhase == Phases.EMPTY)?(true):(false);
    }
    
    public boolean isAlive() {
        return (currentPhase == Phases.ALIVE)?(true):(false);
    }

    public boolean isDead() {
        return (currentPhase == Phases.DEAD)?(true):(false);
    }
    
    public void setAlive() {
        currentPhase = Phases.ALIVE;
    }
    
    public void toNextPhase() {
        
//        System.out.print("Thingy ["+row+","+col+"] "+
//                "toNextPhase: "+currentPhase+" is "+nextPhase);
        
        switch(nextPhase) {
            case DYING:
                currentPhase = Phases.DEAD;
                break;
            case REBORN:
                currentPhase = Phases.ALIVE;
                break;
            default:
                // No change
        }
        
//        System.out.println(" and becomes "+currentPhase);
        
        nextPhase = Phases.EMPTY;
    }
    
    /*
     * Any live cell with fewer than two live neighbours dies, as if caused by under-population.
     * Any live cell with two or three live neighbours lives on to the next generation.
     * Any live cell with more than three live neighbours dies, as if by overcrowding.
     * Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
     */
    public void liveLife(Land map) {
        
        Thingy thingy;
        int alive = 0;
        
        // Count living neighbours
        for (int dir = 0; dir < 8; dir++) {
            thingy = map.getThingyFromDirection(row, col, dir);
            
//            System.out.println("Around "+row+","+col+" on "+dir+" the neighbour is "+thingy.print());
            
            if (thingy.isAlive()) {
                alive++;
            }
        }
        
        // Am I alive?
        if (this.isAlive()) {
            // Any live cell with fewer than two live neighbours dies,
            // as if caused by under-population.
            // Any live cell with more than three live neighbours dies,
            // as if by overcrowding.
            if (alive < 2) {
                nextPhase = Phases.DYING;
//                System.out.println("Thingy at "+row+","+col+" DYING ALONE "+alive);
            } else if (alive > 3) {
                nextPhase = Phases.DYING;
//                System.out.println("Thingy at "+row+","+col+" DYING IN CROWD "+alive);
            }
            // Any live cell with two or three live neighbours lives on
            // to the next generation.

        } else {
            // Any dead cell with exactly three live neighbours
            // becomes a live cell, as if by reproduction.
            if (alive == 3) {
                nextPhase = Phases.REBORN;
//                System.out.println("Thingy at "+row+","+col+" is REBORN "+alive);
            }
        }
    }
    
    public String print() {
        String result = "";
        result += ("Thingy at "+row+","+col+" is "+currentPhase.toString());
        return result;
    }
    
    // For command prompt
    public char printChar() {
        switch(currentPhase) {
            case EMPTY:
                return '.';
            case DEAD:
                return '+';
            case ALIVE:
                return 'O';
            default:
                return '?';
        }
    }
}
