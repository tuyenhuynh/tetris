/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import tetris.model.event.FieldBottomListener;
import tetris.model.shape.AloneCell;
import tetris.model.shape.Figure;
import tetris.model.shape.Shape;

/**
 *
 * @author tuyenhm
 */
public class FieldBottom {
    
    private static final int WIDTH = 10;
    
    private static final int ROW_COUNT = 20; 
    
    private List<Shape> shapes = new ArrayList<>();
    
    private int[] widths = new int [ROW_COUNT]; 
    
    private FieldBottomListener listener; 
    
    public void addFigure(Figure figure) {
        shapes.add(figure);
        List<Point> points = figure.findNotEmptyCells();
        for(Point point :  points ) {
            int y = point.y; 
            widths[y] ++; 
        }
        
        List<Shape>removedShapes = removeFullRows();
        
        listener.figureStopped();
        
        if(!removedShapes.isEmpty()) {
            listener.fullRowsRemoved(removedShapes);
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
                                    removedShapes.add(burntCell);
                                }
                                for(Point point : remainCells) {
                                    AloneCell remainCell  = new AloneCell(point);
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
            for( int i = lowY; i < ROW_COUNT - fullRowCount; ++i) {
                widths[i] = widths[i+fullRowCount];
            }
            for(int i = ROW_COUNT - fullRowCount ;  i < ROW_COUNT; ++i ) {
                widths[i] = 0 ; 
            }
            
            System.out.println("Widths changed"); 
            
            for(int width  : widths) {
                System.out.println(width);
            }
        }
        fullRows.clear();
        return  removedShapes;
    }
    
    private List<Integer> findFullRows() {
        List<Integer> fullRows = new ArrayList<>();
        for(int i = 0; i < widths.length ; ++i) {
            if(widths[i] == WIDTH){
                fullRows.add(i);
            }
        }
        return fullRows;
    }
    
    public void addFieldBottomListener(FieldBottomListener listener) {
        this.listener = listener;
    }
    
}
