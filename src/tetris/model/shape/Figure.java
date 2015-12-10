/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model.figure;

import java.awt.Point;
import tetris.model.GameField;
import tetris.model.event.FigureActionListener;
import tetris.navigation.Direction;

/**
 *
 * @author tuyenhm
 */
public abstract class Figure extends Shape {
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
        
        System.out.println(oldPos.y); 
        
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
        listener.figureMoved();
        return true; 
    }
    
    public void setGameField(GameField gameField) {
        this.gameField = gameField;
    }
    
    boolean isInGameField() {
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
    
    void validateRotation() {
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
        if(isRotated) {
            listener.figureRotated();
        } 
    }
    
}
