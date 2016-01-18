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
import org.apache.log4j.Logger;
import tetris.model.event.FieldBottomEvent;
import tetris.model.event.FieldBottomListener;
import tetris.model.event.RemoveShapesEvent;
import tetris.model.shape.AloneCell;
import tetris.model.shape.Figure;
import tetris.model.shape.Shape;

 /**
  * Bottom of field, which contains figures and remained part of figures
  */
public class FieldBottom {
    
    private static final Logger logger = Logger.getLogger(FieldBottom.class);
    
    /**
     * Strategy to clear full rows 
     */
    private FullRowProcessor fullRowProcessor; 
    
    /**
     * The first and last name of this student.
     */
    private int maxWidth;
    
    /**
     * Maximum height of field's bottom
     */
    private int maxHeight;
    
    /**
     * List contains figures and remained part of figures
     */
    private List<Shape> shapes = new ArrayList<>();
    
    /**
     * Width of each row in field's bottom
     */
    private int[] widths;
    
    /**
     * Listener
     */
    private FieldBottomListener listener; 
    
    /**
     * 
     * @param maxWidth Field bottom's max width
     * @param maxHeight Field bottom's max height
     */
    public FieldBottom (int maxWidth, int maxHeight) {
        this.maxHeight = maxHeight; 
        this.maxWidth = maxWidth;
        widths = new int [maxHeight]; 
    }
    
    /**
     * Set FullRowProcessor
     * @param processor Full row processor 
     */
    public void setFullRowProcessor(FullRowProcessor processor) {
        this.fullRowProcessor = processor; 
        processor.setFieldBottom(this);
    }
    
    /**
     * Add figure to field bottom
     * @param figure to add to field's bottom
     */
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
            FieldBottomEvent evt = new FieldBottomEvent(this);
            evt.setShapes(shapes);
            listener.figureStopped(evt);
            logger.info("Figured stopped");
            
            //remove full rows
            List<Shape>removedShapes = removeFullRows();
            //publish event of removing full rows
            if(removedShapes != null && !removedShapes.isEmpty()) {
                RemoveShapesEvent removeShapesEvent = new RemoveShapesEvent(this);
                removeShapesEvent.setRemovedShapes(removedShapes);
                removeShapesEvent.setRemainedShapes(shapes);
                listener.fullRowsRemoved(removeShapesEvent);
                logger.info("Full rows removed");
            }
        }
    }
    
    /**
     *  Remove shapes from rows
     * @param rows Rows, shapes or part of shapes on which should be remove
     * @return list of removed shapes
     */
    List<Shape> removeShapesFromRows(List<Integer> rows) {
        List<Shape> removedShapes = new ArrayList<>(); 
        //find boundary of sequence of full rows
        Collections.sort(rows);
        int lowY = rows.get(0);
        int highY = rows.size() + lowY-1;
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
                    //figure burnt totally
                    if(isTotallyBurnt(shape, rows)){
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
        return removedShapes; 
    }
    
    private boolean isTotallyBurnt(Shape shape, List<Integer> fullRows) {
        List<Point> points = shape.findNotEmptyCells(); 
        int count = 0;
        for(int i = 0 ; i < points.size() ; ++ i) {
            if(fullRows.contains(points.get(i).y)){
                count++; 
            }
        }
        return (count == points.size());
    }
    
    /**
     * Get list of shapes
     * @return shapes in field's bottom
     */
    public List<Shape> getShapes () {
        List<Shape> clonedShapes = new ArrayList<Shape>(); 
        for(Shape shape : shapes) {
            clonedShapes.add(shape);
        }
        return clonedShapes; 
    }
    
    /**
     * Reference to shapes in field's bottom
     * @return Shapes in fiele's bottom
     */
    public List<Shape> getOriginalShapes() {
        return shapes; 
    }
    
    /**
     * Remove full rows after adding figure to field's bottom
     * @return list of removed shapes
     */
    private List<Shape> removeFullRows() {
        return fullRowProcessor.removeFullRows();
    }
    
    /**
     * Find full row base of width of each rows
     * @return list of full rows
     */
    public List<Integer> findFullRows() {
        List<Integer> fullRows = new ArrayList<>();
        for(int i = 0; i < widths.length ; ++i) {
            if(widths[i] == maxWidth){
                fullRows.add(i);
            }
        }
        return fullRows;
    }
    
    /**
     * Add listener
     * @param listener to be added
     */
    public void addFieldBottomListener(FieldBottomListener listener) {
        this.listener = listener;
    }
     /**
     * Get maxWidth
     * @return maximum width of field's bottom
     */
    public int getMaxWidth() {
        return this.maxWidth; 
    }
    
    /**
     * Set max width
     * @param maxWidth maximum widths of field's bottom
     */
    public void setMaxWidth(int maxWidth){
        this.maxWidth = maxWidth;
    }
    
    /**
     * Set maxHeight
     * @param maxHeight maximum height of field's bottom
     */
    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        widths = new int[maxHeight];
    }
    
    /**
     * Get max width of fields's bottom
     * @return max height
     */
    public int getMaxHeight() {
        return this.maxHeight; 
    }
    
    /**
     * Get widths
     * @return widths
     */
    public int[] getWidths(){
        return this.widths; 
    }
    
    /**
     * Update widths
     * @param widths new values of width 
     */
    void updateWidths(int[] widths) {
        this.widths = widths; 
    }
    
    /**
     * Clear field's bottom
     */
     
    public void clear() {
        shapes.clear();
        for( int i = 0 ; i < maxHeight ; ++i ) {
            widths[i] = 0 ; 
        }
    }
    
}
