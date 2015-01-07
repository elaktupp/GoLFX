package golfx;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kimmo
 */
public class InputFields extends VBox {
    
    private final TextField seedField = new TextField();
    private final TextField populationField = new TextField();
    private final TextField landWidthField = new TextField();
    private final TextField landHeightField = new TextField();
    private Button startButton = new Button("RUN");
    private Life listener;
    
    public InputFields(Life ctrl) {
        
        listener = ctrl;
        
        seedField.setPromptText("Random seed");
        populationField.setPromptText("Population");
        landWidthField.setPromptText("Land width");
        landHeightField.setPromptText("Land height");
        
        startButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (((Button)event.getSource()).getText().equals("RUN")) {
                    listener.doStart(getLandWidth(),getLandHeight(),
                            getPopulation(),getSeed());
                    startButton.setText("STOP");
                } else {
                    listener.doStop();
                    startButton.setText("RUN");
                }
            }
            
        });
        
        this.getChildren().add(landWidthField);
        this.getChildren().add(landHeightField);
        this.getChildren().add(populationField);
        this.getChildren().add(seedField);
        this.getChildren().add(startButton);
        
        
        
    }
    
    public void startButtonDisabled(boolean disabled) {
        startButton.setDisable(disabled);
    }
    
    public int getSeed() {
        return Integer.parseInt(seedField.getText());
    }
    
    public int getLandWidth() {
        return Integer.parseInt(landWidthField.getText());
    }
    
    public int getLandHeight() {
        return Integer.parseInt(landHeightField.getText());
    }
    
    public int getPopulation() {
        return Integer.parseInt(populationField.getText());
    }
}
