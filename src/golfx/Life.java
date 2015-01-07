/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golfx;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javafx.application.Platform;
import javax.swing.Timer;

/**
 *
 * Any live cell with fewer than two live neighbours dies, as if caused by under-population.
 * Any live cell with two or three live neighbours lives on to the next generation.
 * Any live cell with more than three live neighbours dies, as if by overcrowding.
 * Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
 *
 * @author Kimmo
 */
public class Life {
    
    private int count;

    private Timer lifeTimer;
    
    private Random rand = new Random();
    private Land land;
    private MapView mapView;
    
    private int rows;
    private int columns;
    
    private int test = 0;
    
    public Life(MapView map) {
        this.mapView = map; // handle to UI view
    }
    
    public void doStart(int width, int height, int population, long seed) {
        
        count = population;
        
//        testMap(test++); // crashes at the end
        
        rand.setSeed(seed);
        land = new Land(height,width);
        rows = height;
        columns = width;
        int r,c;
        
        // Create a dead land
        for (r=0; r<height; r++) {
            for (c=0; c<width; c++) {
                land.addThing(r,c,new Thingy(r,c));
            }
        }
        
        // Populate the land
        do {
            Thingy thingy;
            r = rand.nextInt(height);
            c = rand.nextInt(width);
            thingy = land.getThingy(r, c);
            if (thingy.isDead()) {
                thingy.setAlive();
                count--; // one down, number of count to go
            }
            // else { Place was already occupied, lets try again }
        } while(count > 0);
        
        
        mapView.setLand(land);
        mapView.updateMap();
        
        launchTimer();
    }
    
    public void doStop() {
        if (lifeTimer != null) {
            lifeTimer.stop();
        }
        
        r = 0;
    }
    
    public Land getLand() {
        return land;
    }
    
    private int r = 0;
    
    public void liveLife() {
        
        r++;
        System.out.println("Round: "+r);

        // Determine next phase for each Thingy accoring to its neighbours
        
        for (int r=0; r<rows; r++) {
            for (int c=0; c<columns; c++) {
                Thingy thingy = land.getThingy(r,c);
                if (!thingy.isUnoccupied()) {
                    thingy.liveLife(land);
                }
                // No point checking neighbours of unoccupied piece of land
            }
        }

        // Move Thingies to next phase
        
        for (int r=0; r<rows; r++) {
            for (int c=0; c<columns; c++) {
                land.getThingy(r,c).toNextPhase();
            }
        }

    }
    
    public void launchTimer() {
        
        // Start to live life until everything is dead
        lifeTimer = new Timer(100, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(new Runnable(){
                    @Override
                    public void run(){
                        liveLife();
                        mapView.updateMap();
                    }
                });
                
                if(land.liveOnes() < 1) {
                    if (lifeTimer != null) {
                        lifeTimer.stop();
                    }
                }
            }
        });
        
        Platform.runLater(new Runnable(){
            @Override
            public void run(){
                lifeTimer.start();
            }
        });
        
    }

    // Known patterns for testing
    
    private String[] testMap0 = {
        ".........................",
        ".........................",
        ".OOO.............OOO.....",
        "..OOO...........OOO......",
        ".........................",
        "........................."
    };
    private String[] testMap1 = {
        ".........................",
        "...............O.........",
        "...OOO.........O.........",
        "...............O.........",
        "........................."
    };
    private String[] testMap2 = {
        ".........................",
        ".........................",
        "..O......................",
        "O.O......................",
        ".OO......................",
        ".........................",
        "........................."
    };
    private String[] testMap3 = {
        ".........................",
        "......OO.................",
        "....OO.OO................",
        "....OOOO.................",
        ".....OO..................",
        ".........................",
        "........................."
    };
    private String[][] testMaps = {
        testMap0,testMap1,testMap2,testMap3
    };
    
    private void testMap(int nbr) {
        String[] testThisMap = testMaps[nbr];
        rows = testThisMap.length;
        columns = testThisMap[0].length();
        int r,c;

        land = new Land(rows,columns);
        // Create an empty land
        for (r=0; r<rows; r++) {
            for (c=0; c<columns; c++) {
                land.addThing(r,c,new Thingy(r,c));
            }
        }
        // Populate land according to testMap
        for (r = 0; r < rows; r++) {
            for (c = 0; c < columns; c++) {
                if (testThisMap[r].charAt(c) == 'O') {
                    land.getThingy(r,c).setAlive();
                }
            }
        }
        
    }
    
}
