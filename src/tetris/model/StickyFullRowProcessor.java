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
import tetris.model.shape.Shape;

/**
 *
 * @author tuyenhuynh
 */
public class StickyFullRowProcessor extends FullRowProcessor {

    @Override
    public List<Shape> removeFullRows() {
        List<Integer> fullRows = fieldBottom.findFullRows(); 
        List<Shape> removedShapes = new ArrayList<>(); 
        while(!fullRows.isEmpty()) {
            List<Shape> result = fieldBottom.removeShapesFromRows(fullRows);
            removedShapes.addAll(result);
            shiftShapesDown(fullRows, fieldBottom.getOriginalShapes());
            fullRows = fieldBottom.findFullRows(); 
        }
        return removedShapes;
    }
    
    //shift shapes down
    private void shiftShapesDown(List<Integer> fullRows, List<Shape> shapes) {
        Collections.sort(fullRows);
        int startRow = fullRows.get(0);
        for(int i  = startRow ; i < fieldBottom.getMaxHeight() ;  ++ i) {
            for(Shape currentShape : shapes) {
                Point position = currentShape.getPosition();
                if(position.y >0) {
                    currentShape.setPosition(new Point(position.x, position.y -1 ));
                    boolean isCollision = false;
                    for( int j  = 0 ; !isCollision && j < shapes.size() ; ++j){
                        if(shapes.get(j) != currentShape) {
                            if(currentShape.isCollideWith(shapes.get(j))){
                                isCollision = true;
                            }
                        } 
                    }
                    if(isCollision) {
                        currentShape.setPosition(position);
                    }
                }
            }
        }
        int [] widths = new int[100]; 
        for(Shape shape : fieldBottom.getShapes()) {
            List<Point> points = shape.findNotEmptyCells(); 
            for( int i= 0 ; i < points.size() ; ++i) {
                int y = points.get(i).y ; 
                System.out.println(y); 
                widths[y]++; 
            }
        }
        fieldBottom.updateWidths(widths);
    }
    
}
