/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import tetris.model.event.FieldBottomListener;
import tetris.model.shape.Figure;
import tetris.navigation.Direction;

/**
 *
 * @author tuyenhm
 * class GameField
 */
public class GameField {
    
    //width of game field
    private int width; 
    
    //height of game field
    private int height; 
    
    //timer to control active figure
    private Timer timer; 
    
    //additional timer to control figure after it's landing to bottom
    private Timer timer1; 
    
    //field's bottom
    private FieldBottom fieldBottom; 
    
    //active figure
    private Figure figure;
    
    //delay
    private static final int DELAY = 500; 
    //lock delay - time to wait for player's action after figure's landing to bottom.
    //if player has no action after that amount of time, figure will be fixed to field's bottom
    private static final int LOCK_DELAY = 700;
    
    public GameField(int width, int height) {
        this.width = width; 
        this.height = height;
        fieldBottom = new FieldBottom(width, height);
        timer = new Timer(DELAY, null);
        
        timer1 = new Timer(LOCK_DELAY, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!figure.move(Direction.DOWN)){
                    fieldBottom.addFigure(figure);
                    timer1.stop();
                }
            }
        });
        
        //timer to move figure down after DELAY time
        timer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean isMoved = figure.move(Direction.DOWN);
                if(!isMoved) {
                    timer1.start();
                }
            }
        });
    }
    
    //clear bottom
    public void clearBottom () {
        fieldBottom.clear();
    }
    
    //deactivate figure
    public void deactivateFigure() {
        timer.stop();
    }
    
    //activate figure
    public void activateFigure() {
        timer.start();
    }
    
    //set active figure
    public void setActiveFigure(Figure figure) {
        this.figure = figure;
    }
    
    //set listener
    public void addFieldBottomListener(FieldBottomListener fieldBottomListener ) {
        fieldBottom.addFieldBottomListener(fieldBottomListener); 
    }
    //get field's bottom
    public FieldBottom getFieldBottom() {
        return this.fieldBottom;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
}
