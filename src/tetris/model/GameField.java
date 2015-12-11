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
 */
public class GameField {
    
    private int width; 
    
    private int height; 
    
    private Timer timer; 
    
    private FieldBottom fieldBottom; 
    
    private Figure figure;
    
    public GameField(int width, int height) {
        this.width = width; 
        this.height = height;
        fieldBottom = new FieldBottom(width, height);
        timer = new Timer(500, null);
        
        timer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                boolean isMoved = figure.move(Direction.DOWN);
                
                if(!isMoved) {
                    fieldBottom.addFigure(figure);
                }
            }
        });
    }
    
    public void clearBottom () {
        fieldBottom.clear();
    }
    
    public void deactivateFigure() {
        timer.stop();
    }
    
    public void activateFigure() {
        timer.start();
    }
    
    public void setActiveFigure(Figure figure) {
        this.figure = figure;
    }
    
    public void addFieldBottomListener(FieldBottomListener fieldBottomListener ) {
        fieldBottom.addFieldBottomListener(fieldBottomListener); 
    }
    
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
