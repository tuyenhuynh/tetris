/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Timer;
import tetris.model.event.FieldBottomListener;
import tetris.model.shape.Figure;
import tetris.model.shape.Shape;
import tetris.navigation.Direction;

/**
 * class GameField, which contains field's bottom and active figure
 */
public class GameField {
    
    /**
     * Width of game field
     */
    private int width; 
    
    /**
     * Height of game field
     */
    private int height; 
    
    /**
     * Timer to control active figure
     */
    private Timer timer; 
    
    /**
     * Additional timer to control figure after it's landing to bottom
     */
    private Timer timer1; 
    
    /**
     * Field's bottom
     */
    private FieldBottom fieldBottom; 
    
    /**
     * Active figure
     */
    private Figure figure;
    
    /**
     * Delay
     */
    private static final int DELAY = 500; 
    /**
     * lock delay - time to wait for player's action after figure's landing to bottom.\n
     * if player has no action after that amount of time, figure will be fixed to field's bottom
     */
    private static final int LOCK_DELAY = 400;
    
    /**
     * Construct GamField, given dimension
     * @param dimension dimension of game field
     */
    public GameField(Dimension dimension) {
        this.width = dimension.width; 
        this.height = dimension.height;
        fieldBottom = new FieldBottom(width, height);
        fieldBottom.setFullRowProcessor(new NaiveFullRowProcessor());
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
    
    /**
     * Set dimension for game field
     * @param dimension dimension
     */
    public void updateDimension(Dimension dimension){
        this.width = dimension.width; 
        this.height = dimension.height;
        fieldBottom.setMaxHeight(height);
        fieldBottom.setMaxWidth(width);
    }
    
    /**
     * Clear bottom
     */
    public void clearBottom () {
        fieldBottom.clear();
    }
    
    /**
     * Deactivate figure
     */
    public void deactivateFigure() {
        timer.stop();
    }
    
    /**
     * activate figure
     */
    public void activateFigure() {
        timer.start();
    }
    
    /**
     * Set active figure
     * @param figure Active figure
     */
    public void setActiveFigure(Figure figure) {
        this.figure = figure;
    }
    
    /**
     * Set listener
     * @param fieldBottomListener listener
     */
    public void addFieldBottomListener(FieldBottomListener fieldBottomListener ) {
        fieldBottom.addFieldBottomListener(fieldBottomListener); 
    }
    /**
     * Get field's bottom
     * @return Field's bottom
     */
    public List<Shape> getFieldBottom() {
        return fieldBottom.getShapes();
    }
    /**
     * Get field's width
     * @return field's width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get field's height
     * @return field's height
     */
    public int getHeight() {
        return height;
    }
    
}
