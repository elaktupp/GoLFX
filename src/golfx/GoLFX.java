/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.Timer;

/**
 *
 * @author Kimmo
 */
public class GoLFX extends Application {
    
    Timer t = null;
    
    @Override
    public void start(Stage primaryStage) {
       
        // Create UI for Life
        BorderPane root = new BorderPane();
        MapView map = new MapView();
        Life life = new Life(map);
        InputFields input = new InputFields(life);
        // everything goes into root
        root.setCenter(map);
        root.setLeft(input);
        // and root goes to scene
        Scene scene = new Scene(root);
        primaryStage.setTitle("Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                life.doStop();
                Platform.exit();
            }
        });
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
