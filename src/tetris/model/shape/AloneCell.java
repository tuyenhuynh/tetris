/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model.shape;

import java.awt.Point;

/**
 *
 * @author tuyenhm
 * Class AloneCell used to demonstrate remained cell of figure after removing it's burnt cells
 */
public class AloneCell extends Shape {
    
    /**
     * Default constructor
     * @param position position of cell
     */
    public AloneCell(Point position) {
        this.position =  position; 
        this.cells = new int[][] {{1}}; 
    }
    
    @Override
    public int getWidth() {
        return 1; 
    }
    
    @Override
    public int getHeight() {
        return 1; 
    }
}
