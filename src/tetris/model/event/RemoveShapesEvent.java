/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model.event;

import java.util.EventObject;
import java.util.List;
import tetris.model.shape.Shape;

/**
 *
 * @author tuyenhm
 */
public class RemoveShapesEvent extends EventObject{
    
    private List<Shape> removedShapes; 
    
    public RemoveShapesEvent(Object source) {
        super(source);
    }
    
    public List<Shape> getRemovedShape() {
        return this.removedShapes;
    }
    
    public void setRemovedShapes(List<Shape> removedShapes) {
        this.removedShapes = removedShapes; 
    }
}
