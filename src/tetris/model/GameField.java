/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import tetris.model.event.GameFieldListener;
import tetris.model.figure.Figure;
import tetris.model.figure.Shape;
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
    
    private GameFieldListener listener;
    
    public GameField(int width, int height) {
        this.width = width; 
        this.height = height;
        fieldBottom = new FieldBottom();
        timer = new Timer(500, null);
    }
    
    public void disableFigure() {
        timer.stop();
    }
    
    public void activateFigure() {
        timer.start();
        timer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean isMoved = figure.move(Direction.DOWN);
                
                
                
                if(!isMoved) {
                    fieldBottom.addShape(figure);
                    removeFullRows();
                    //change widths
                    
                    //change heights
                    listener.figureStopped();
                }
            }
        });
        
    }
    
    private void removeFullRows() {
        
    }
    
    public void setActiveFigure(Figure figure) {
        this.figure = figure;
    }
    
    public void addGameFieldListener(GameFieldListener listener ) {
        this.listener = listener; 
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
