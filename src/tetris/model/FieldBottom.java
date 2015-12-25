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

//Bottom of field, which contains figures and remained part of figures
public class FieldBottom {
    
    private static final Logger logger = Logger.getLogger(FieldBottom.class);
    
    //maximum width of field's bottom
    private int maxWidth;
    
    //maximum height of field's bottom
    private int maxHeight;
    
    //list contains figures and remained part of figures
    private List<Shape> shapes = new ArrayList<>();
    
    //width of each row in field's bottom
    private int[] widths;
    
    //listener
    private FieldBottomListener listener; 
    
    public FieldBottom (int maxWidth, int maxHeight) {
        this.maxHeight = maxHeight; 
        this.maxWidth = maxWidth;
        widths = new int [maxHeight]; 
    }
    
    //add figure to field bottom
    public void addFigure(Figure figure) {
        //check for game over
        if(figure.getPosition().y > maxHeight -1){
            FieldBottomEvent evt = new FieldBottomEvent(this);
            evt.setShapes(shapes);
            listener.bottomOverload(evt);
            logger.info("Bottom overload");
        }else {
            //add figure to field's bottom
            shapes.add(figure);
            List<Point> points = figure.findNotEmptyCells();
            for(Point point :  points ) {
                int y = point.y; 
                //updates width of rows
                widths[y] ++; 
            }
            
            //remove full rows
            List<Shape>removedShapes = removeFullRows();
            FieldBottomEvent evt = new FieldBottomEvent(this);
            evt.setShapes(shapes);
            listener.figureStopped(evt);
            logger.info("Figured stopped");
            
            //emit event of removing full rows
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
    
    //remove full rows after adding figure to field's bottom
    public List<Shape> removeFullRows() {
        
        List<Shape> removedShapes = new ArrayList<>();
        
        //find full rows
        List<Integer> fullRows = findFullRows();
        
        if(fullRows.size() > 0) {
            //find boundary of sequence of full rows
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
                            //remove figure
                            shapes.remove(i);
                            i--;
                            removedShapes.add(shape);
                        } else {
                            //check if part of figure burnt
                            List<Point>  points = shape.findNotEmptyCells();
                            List<Point> burntCells = new ArrayList<>(); 
                            List<Point> remainedCells = new ArrayList<>(); 
                                
                            for(Point cell : points ) {
                                if(cell.y >=lowY &&  cell.y <=highY ) {
                                    burntCells.add(cell);
                                }else {
                                    remainedCells.add(cell);
                                }
                            }

                            //part of figure burnt
                            if(burntCells.size() > 0) {
                                // break figures into cells and remove burnt cell
                                shapes.remove(i);
                                i--;
                                for(Point point : burntCells) {
                                    AloneCell burntCell = new AloneCell(point);
                                    burntCell.setColor(shape.getColor());
                                    removedShapes.add(burntCell);
                                }
                                //transform remained cells into alone cells and add them to field's bottom
                                for(Point point : remainedCells) {
                                    AloneCell remainCell  = new AloneCell(point);
                                    remainCell.setColor(shape.getColor());
                                    shapes.add(remainCell);
                                }
                            }
                        }
                    }
                }
            }
            
            //shift shapes above highY down
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
    
    //find full row base of width of each rows
    private List<Integer> findFullRows() {
        List<Integer> fullRows = new ArrayList<>();
        for(int i = 0; i < widths.length ; ++i) {
            if(widths[i] == maxWidth){
                fullRows.add(i);
            }
        }
        return fullRows;
    }
    
    
    //set listener
    public void addFieldBottomListener(FieldBottomListener listener) {
        this.listener = listener;
    }
    
    public void setMaxWidth(int maxWidth){
        this.maxWidth = maxWidth;
    }
    
    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }
    
    //clear field's bottom
    public void clear() {
        shapes.clear();
        for( int i = 0 ; i < maxHeight ; ++i ) {
            widths[i] = 0 ; 
        }
    }
    
}
