/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import tetris.model.shape.AloneCell;
import tetris.model.shape.Figure;
import tetris.model.shape.Shape;

/**
 *
 * @author tuyenhuynh
 */
public abstract class FullRowProcessor {
    protected FieldBottom fieldBottom; 
    /**
     * Set field's bottom
     * @param fieldBottom FieldBottom to process 
     */
    public void setFieldBottom(FieldBottom fieldBottom ) {
        this.fieldBottom = fieldBottom; 
    }
    
    /**
     * Remove full rows from field bottom
     * @return removed shapes 
     */
    abstract List<Shape> removeFullRows(); 
}
