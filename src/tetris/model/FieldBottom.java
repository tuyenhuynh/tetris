/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model;

import java.util.ArrayList;
import java.util.List;
import tetris.model.figure.Shape;

/**
 *
 * @author tuyenhm
 */
public class FieldBottom {
    
    private List<Shape> shapes = new ArrayList<>();
    
    private List<Integer> widths = new ArrayList<>(); 
    
    private List<Integer> heights = new ArrayList<>(); 
    
    public void addShape(Shape shape) {
        shapes.add(shape);
    }
    
    public List<Shape> getShapes () {
        return shapes; 
    }
    
    public List<Shape> removeFullRows() {
        return new ArrayList<>();
    }
    
}
