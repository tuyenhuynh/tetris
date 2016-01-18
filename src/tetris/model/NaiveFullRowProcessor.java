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
public class NaiveFullRowProcessor extends FullRowProcessor{
    
    @Override
    List<Shape> removeFullRows() {
        List<Shape> shapes = fieldBottom.getOriginalShapes();
        List<Shape> removedShapes = null;
        //find full rows
        List<Integer> fullRows = fieldBottom.findFullRows();
        if(fullRows.size() > 0) {
            removedShapes  = fieldBottom.removeShapesFromRows(fullRows); 
            shiftShapesDown(fullRows, shapes); 
        }
        return  removedShapes;
    }
    
    private void shiftShapesDown(List<Integer> fullRows, List<Shape> shapes) {
        Collections.sort(fullRows);
        int lowY = fullRows.get(0);
        int highY = fullRows.size() + lowY-1;
        //shift shapes above highY down
        int fullRowCount = fullRows.size(); 
        for(Shape shape : shapes ) {
            int  y = shape.getPosition().y; 
            if(y > lowY && y < highY) {
                int newX = shape.getPosition().x; 
                int delta = 0 ;
                for(int i = 0 ; i < fullRows.size(); ++i) {
                    if(fullRows.get(i) < y) {
                        delta++; 
                    }
                }
                int newY = y - delta; 
            }
            if( y> highY) {
                int newX = shape.getPosition().x;
                int newY = y - fullRowCount;
                shape.setPosition(new Point(newX, newY));
            }
        }
        //update widths; 
        int maxHeight = fieldBottom.getMaxHeight(); 
        int widths[] = fieldBottom.getWidths(); 

        for( int i = lowY; i < maxHeight - fullRowCount; ++i) {
            widths[i] = widths[i+fullRowCount];
        }
        for(int i = maxHeight - fullRowCount ;  i < maxHeight; ++i ) {
            widths[i] = 0 ; 
        }
    }
    
}
