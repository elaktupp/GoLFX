/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golfx;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Kimmo
 */
public class MapView extends AnchorPane {
    
    private final int SPOTSIZE = 10;
    private Land land;
    
    public MapView() {
        this.setMinSize(200, 200);
        this.setBackground(new Background(new BackgroundFill(Color.BLUE,
                CornerRadii.EMPTY, Insets.EMPTY)));
        this.setCenterShape(true);
        this.setStyle("-fx-margins: 10px");
    }
    
    public void setLand(Land land) {
        this.land = land;
        this.setWidth(SPOTSIZE * land.getWidth());
        this.setHeight(SPOTSIZE * land.getHeight());
        
        updateMap();
    }
    
    public final void updateMap() {
        
        int width = land.getWidth();
        int height = land.getHeight();
        ObservableList<Node> childList = this.getChildren();
        
        childList.clear();

        for (int y = 0; y < height; y++) {
            
            for (int x = 0; x < width; x++) {
                
                Rectangle r = new Rectangle(SPOTSIZE * x, SPOTSIZE * y, SPOTSIZE, SPOTSIZE);
                r.setFill(Color.GREEN);
                childList.add(r);
                
                Thingy thingy = land.getThingy(y, x);
                
                if (!thingy.isUnoccupied()) {
                   
                    Circle c = new Circle(((SPOTSIZE * x) + (SPOTSIZE / 2)),
                                          ((SPOTSIZE * y) + (SPOTSIZE / 2)),
                                          (SPOTSIZE / 2));
                    
                    if (thingy.isAlive()) {
                        c.setFill(Color.CORAL);
                    } else {
                        c.setFill(Color.GRAY);
                    }
                    
                    childList.add(c);
                }
            }
        }
    }
}
