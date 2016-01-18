/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model.shape;

import java.awt.Point;
import org.apache.log4j.Logger;
import tetris.model.GameField;
import tetris.model.event.FigureActionListener;
import tetris.model.event.GameFieldEvent;
import tetris.navigation.Direction;

/**
 * Abstract class of figures
 */
public abstract class Figure extends Shape {
    
    private static final Logger logger = Logger.getLogger(Figure.class);
    
    /**
     * Current state of figures
     */
    protected int state;
    
    /**
     * Game field
     */
    GameField gameField; 
    
    /**
     * FigureActionListener
     */
    FigureActionListener listener; 
    
    /**
     * move with given direction 
     * @param direction direction to move
     * @return result of operation "move"
     */
    public boolean move(Direction direction) {
        
        //current position of figure
        Point oldPos = this.position; 
        
        //new position
        Point newPosition = null; 
        //define new position
        switch (direction){
            case DOWN:
                newPosition = new Point(position.x, position.y - 1);
                break;
            case LEFT:
                newPosition = new Point(position.x - 1 , position.y );
                break;
            case RIGHT:
                newPosition = new Point(position.x + 1, position.y);
                break;
            default:
                newPosition = new Point(position.x , position.y +1);
        }
        //figure with new position
        setPosition(newPosition);
        
        //check collision with fields' border
        if(!isInGameField()) {
            setPosition(oldPos);
            return false; 
        }
        //check collision with other figures in fields' bottom
        for(Shape shape : gameField.getFieldBottom()) {
            if(isCollideWith(shape)) {
                setPosition(oldPos);
                return false;
            }
        }
        //publish signal after moving
        GameFieldEvent event = new GameFieldEvent(this);
        event.setShapes(gameField.getFieldBottom());
        event.setActiveFigure(this);
        listener.figureMoved(event);
        logger.info("Figure moved");
        return true; 
    }
    
    /**
     * Set game field for figure
     * @param gameField game field, on which figure's moving 
     */
    public void setGameField(GameField gameField) {
        this.gameField = gameField;
    }
    
    /**
     * Check whether figure is in game field
     * @return true if figure is on game field, otherwise return false
     */
    protected boolean isInGameField() {
        //rotate right after appearing of new figure
        int x = this.position.x; 
        int y = this.position.y; 
        boolean result = x >=0; 
        result&= x+ this.getWidth() <= gameField.getWidth();
        result&= (y- this.getHeight()) >=-1;
        
        return result; 
    }
    
    /**
     * Add FigureActionListener
     * @param listener FigureActionListener
     */
    public void addActionListener(FigureActionListener listener) {
        this.listener = listener; 
    }
    
    /**
     * Rotate by clockwise
     */
    public abstract void rotateByClockWise() ;
    
    /**
     * Rotate anti clockwise
     */
    public abstract void rotateAntiClockWise() ;
    
    /**
     * rotate with wall kick
     * @return result of operation "rotate"
     */
    public boolean rotate() {
        boolean isRotated = rotateRightAndCheckCollision(); 
        if(!isRotated && move(Direction.RIGHT)) {
            isRotated = rotateRightAndCheckCollision();
            if(!isRotated) {
                //kick left wall
                if(move(Direction.RIGHT)){
                    //kick wall second time if failed at the first time 
                    isRotated = rotateRightAndCheckCollision(); 
                    if(!isRotated) {
                        //return to previous position
                        move(Direction.LEFT);
                    }
                }
                //return to previous position
                if(!isRotated) {
                    move(Direction.LEFT);
                }
            }
        }
        //kick the right wall 
        if(!isRotated && move(Direction.LEFT)) {
            rotateByClockWise(); 
            isRotated = validateRotation(); 
            if(!isRotated){
                move(Direction.RIGHT);
            }
        }
        //publish signal after rotating
        if(isRotated){
            GameFieldEvent event = new GameFieldEvent(this);
            event.setShapes(gameField.getFieldBottom());
            event.setActiveFigure(this);
            listener.figureRotated(event);
            logger.info("Figure rotated");
        }
        return isRotated;
    }
    
    private boolean rotateRightAndCheckCollision(){
        rotateByClockWise(); 
        boolean isRotated = validateRotation(); 
        return isRotated; 
    }
    
    /**
     * check for collision with field's borders and figures in game field
     * @return false if collision occurred, otherwise return true
     */
    private boolean validateRotation() {
        
        boolean isRotated = true;
        if(!isInGameField()){
            rotateAntiClockWise();
            isRotated = false;
        }
        
        for(Shape shape: gameField.getFieldBottom()) {
            if(this.isCollideWith(shape)) {
                rotateAntiClockWise();
                isRotated =  false; 
                break;
            }
        }
        return isRotated;
    }
}
