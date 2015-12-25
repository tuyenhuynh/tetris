/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model.shape;

import java.awt.Point;
import org.apache.log4j.Logger;
import tetris.model.FieldBottom;
import tetris.model.GameField;
import tetris.model.event.FigureActionListener;
import tetris.model.event.GameFieldEvent;
import tetris.navigation.Direction;

/**
 *
 * @author tuyenhm
 */
public abstract class Figure extends Shape {
    
    private static final Logger logger = Logger.getLogger(Figure.class);
    
    protected int state;
    
    GameField gameField; 
    
    FigureActionListener listener; 
    
    public boolean move(Direction direction) {
        
        Point oldPos = this.position; 
        
        Point newPosition = null; 
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
        
        setPosition(newPosition);
        
        if(!isInGameField()) {
            setPosition(oldPos);
            return false; 
        }
        for(Shape shape : gameField.getFieldBottom().getShapes()) {
            if(isCollideWith(shape)) {
                setPosition(oldPos);
                return false;
            }
        }
        
        FieldBottom bottom = gameField.getFieldBottom();
        GameFieldEvent event = new GameFieldEvent(this);
        event.setShapes(bottom.getShapes());
        event.setActiveFigure(this);
        listener.figureMoved(event);
        logger.info("Figure moved");
        return true; 
    }
    
    public void setGameField(GameField gameField) {
        this.gameField = gameField;
    }
    
    protected boolean isInGameField() {
        //rotate right after appearing of new figure
        int x = this.position.x; 
        int y = this.position.y; 
        boolean result = x >=0; 
        result&= x+ this.getWidth() <= gameField.getWidth();
        result&= (y- this.getHeight()) >=-1;
        
        return result; 
    }
    
    public void addActionListener(FigureActionListener listener) {
        this.listener = listener; 
    }
    
    
    public abstract void rotateByClockWise() ;
    
    public abstract void rotateAntiClockWise() ;
    
    public void rotate() {
        boolean isRotated = rotateRightAndCheckCollision(); 
        if(!isRotated && move(Direction.RIGHT)) {
            isRotated = rotateRightAndCheckCollision();
            if(!isRotated) {
                if(move(Direction.RIGHT)){
                    isRotated = rotateRightAndCheckCollision(); 
                    if(!isRotated) {
                        move(Direction.LEFT);
                    }
                }
                if(!isRotated) {
                    move(Direction.LEFT);
                }
            }
        }
        if(!isRotated && move(Direction.LEFT)) {
            rotateByClockWise(); 
            isRotated = validateRotation(); 
            if(!isRotated){
                move(Direction.RIGHT);
            }
        }
        
        if(isRotated){
            FieldBottom bottom = gameField.getFieldBottom();
            GameFieldEvent event = new GameFieldEvent(this);
            event.setShapes(bottom.getShapes());
            event.setActiveFigure(this);
            listener.figureRotated(event);
            logger.info("Figure rotated");
        }
    }
    
    private boolean rotateRightAndCheckCollision(){
        rotateByClockWise(); 
        boolean isRotated = validateRotation(); 
        return isRotated; 
    }
    
    private boolean validateRotation() {
        
        boolean isRotated = true;
        if(!isInGameField()){
            rotateAntiClockWise();
            isRotated = false;
        }
        
        for(Shape shape: gameField.getFieldBottom().getShapes() ) {
            if(this.isCollideWith(shape)) {
                rotateAntiClockWise();
                isRotated =  false; 
                break;
            }
        }
        
        return isRotated;
    }
}
