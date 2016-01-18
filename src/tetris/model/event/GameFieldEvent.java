/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model.event;

import java.util.EventObject;
import java.util.List;
import tetris.model.shape.Figure;
import tetris.model.shape.Shape;

/**
 *
 * @author tuyenhm
 */
public class GameFieldEvent extends EventObject{
    
    private List<Shape> shapes; 
    
    private Figure activeFigure;
    
    
    public GameFieldEvent(Object source) {
        super(source);
        this.shapes = shapes; 
        this.activeFigure = activeFigure; 
    }
    
    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes; 
    }
    
    public void setActiveFigure(Figure activeFigure) {
        this.activeFigure = activeFigure; 
    }
    
    public List<Shape> getShapes() {
        return this.shapes;
    }
    
    public Figure getActiveFigure() {
        return this.activeFigure;
    }
    
}
