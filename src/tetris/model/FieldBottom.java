/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model;

import com.sun.istack.internal.logging.Logger;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import tetris.model.event.FieldBottomEvent;
import tetris.model.event.FieldBottomListener;
import tetris.model.event.RemoveShapesEvent;
import tetris.model.shape.AloneCell;
import tetris.model.shape.Figure;
import tetris.model.shape.Shape;

/**
 *
 * @author tuyenhm
 */
public class FieldBottom {
    
    private static final Logger logger = Logger.getLogger(FieldBottom.class);
    
    private int maxWidth;
    
    private int maxHeight;
    
    private List<Shape> shapes = new ArrayList<>();
    
    private int[] widths;
    
    private FieldBottomListener listener; 
    
    public FieldBottom (int maxWidth, int maxHeight) {
        this.maxHeight = maxHeight; 
        this.maxWidth = maxWidth;
        widths = new int [maxHeight]; 
    }
    
    public void addFigure(Figure figure) {
        if(figure.getPosition().y > maxHeight -1){
            FieldBottomEvent evt = new FieldBottomEvent(this);
            evt.setShapes(shapes);
            listener.bottomOverload(evt);
            logger.info("Bottom overload");
        }else {
            shapes.add(figure);
            List<Point> points = figure.findNotEmptyCells();
            for(Point point :  points ) {
                int y = point.y; 
                widths[y] ++; 
            }

            List<Shape>removedShapes = removeFullRows();
            FieldBottomEvent evt = new FieldBottomEvent(this);
            evt.setShapes(shapes);
            listener.figureStopped(evt);
            logger.info("Figured stopped");
            

            if(!removedShapes.isEmpty()) {
                RemoveShapesEvent removeShapesEvent = new RemoveShapesEvent(this);
                removeShapesEvent.setRemovedShapes(removedShapes);
                listener.fullRowsRemoved(removeShapesEvent);
                logger.info("Full rows removed");
            }
        }
    }
    
    public List<Shape> getShapes () {
        return shapes; 
    }
    
    public List<Shape> removeFullRows() {
        
        List<Shape> removedShapes = new ArrayList<>();
        
        List<Integer> fullRows = findFullRows();
        
        if(fullRows.size() > 0) {
            int lowY = fullRows.get(0);
            int highY = fullRows.size() + lowY-1;
        
            for(int i = 0 ; i < shapes.size() ;  ++i) {
                Shape shape = shapes.get(i);
                
                //alone cell
                if(shape instanceof AloneCell){
                    AloneCell cell=  (AloneCell)shape;
                    int y = cell.getPosition().y;
                    //alone cell burnt
                    if(y >= lowY && y <= highY ) {
                        shapes.remove(i);
                        i--;
                        removedShapes.add(shape);
                        
                    }
                }else if(shape instanceof Figure) {
                    //figure or  part of figure burnt
                    if( !(shape.getPosition().y < lowY || shape.getPosition().y + 1 - shape.getHeight() > highY)) {
                        //figure burnt
                        if(shape.getPosition().y <= highY && shape.getPosition().y+ 1 - shape.getHeight() >= lowY){
                            shapes.remove(i);
                            i--;
                            removedShapes.add(shape);
                        } else {
                            //check if part of figure burnt
                            List<Point>  points = shape.findNotEmptyCells();
                            List<Point> burntCells = new ArrayList<>(); 
                            List<Point> remainCells = new ArrayList<>(); 

                            for(Point cell : points ) {
                                if(cell.y >=lowY &&  cell.y <=highY ) {
                                    burntCells.add(cell);
                                }else {
                                    remainCells.add(cell);
                                }
                            }

                            //part of figure burnt
                            if(burntCells.size() > 0) {
                                shapes.remove(i);
                                i--;
                                for(Point point : burntCells) {
                                    AloneCell burntCell = new AloneCell(point);
                                    burntCell.setColor(shape.getColor());
                                    removedShapes.add(burntCell);
                                }
                                for(Point point : remainCells) {
                                    AloneCell remainCell  = new AloneCell(point);
                                    remainCell.setColor(shape.getColor());
                                    shapes.add(remainCell);
                                }
                            }
                        }
                    }
                }
            }
            
            //ship shapes above highY down
            int fullRowCount = fullRows.size(); 
            for(Shape shape : shapes ) {
                int  y = shape.getPosition().y; 
                if( y> highY) {
                    int newX = shape.getPosition().x;
                    int newY = y - fullRowCount;
                    shape.setPosition(new Point(newX, newY));
                }
            }
            
            //update widths; 
            for( int i = lowY; i < maxHeight - fullRowCount; ++i) {
                widths[i] = widths[i+fullRowCount];
            }
            for(int i = maxHeight - fullRowCount ;  i < maxHeight; ++i ) {
                widths[i] = 0 ; 
            }
        }
        fullRows.clear();
        return  removedShapes;
    }
    
    private List<Integer> findFullRows() {
        List<Integer> fullRows = new ArrayList<>();
        for(int i = 0; i < widths.length ; ++i) {
            if(widths[i] == maxWidth){
                fullRows.add(i);
            }
        }
        return fullRows;
    }
    
    public void addFieldBottomListener(FieldBottomListener listener) {
        this.listener = listener;
    }
    
    public void setMaxWidth(int maxWidth){
        this.maxWidth = maxWidth;
    }
    
    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }
    
    public void clear() {
        shapes.clear();
        for( int i = 0 ; i < maxHeight ; ++i ) {
            widths[i] = 0 ; 
        }
    }
    
}
