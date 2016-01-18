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
public class FieldBottomEvent extends EventObject{
    
    private List<Shape> shapes; 
    
    public FieldBottomEvent(Object source) {
        super(source);
    }
    
    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes; 
    }
    
    public List<Shape> getShapes() {
        return this.shapes; 
    }
    
}
