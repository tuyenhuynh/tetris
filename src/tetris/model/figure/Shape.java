/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model.figure;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author tuyenhm
 */
public abstract class Shape {
    
    private int width; 
    private int height; 
    
    Point position; 
    
    int[][] cells;
    
    public boolean isCollideWith (Shape other) {
        
        List <Point> myCells = findCells(this);
        List <Point> otherCells = findCells(other);
        for(Point cell : myCells) {
            if(otherCells.contains(cell)) {
                return true;
            }
        }
        return false;
    }
    
    private List<Point> findCells (Shape shape) {
        
        List<Point> result = new ArrayList(); 
        
        for(int i = 0 ; i < shape.getHeight(); ++i ) {
            for(int j = 0 ; j < shape.getWidth(); ++j){
                if(shape.cells[i][j] == 1) {
                    Point p = new Point(shape.position.x + j, shape.position.y - i);
                    result.add(p);
                }
            }
        }
        return result;
    }
    
    Point traslatePositionToGlobalPosition(Point point) {
        return new Point(0, 0);
    }
    
    public int getCellValue(int row, int col) {
        return cells[row][col];
    }
    
    public Point getPosition() {
        return this.position;
    }
    
    public void setPosition(Point position) {
        this.position = position;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int[][] getCells() {
        return cells;
    }

    public void setCells(int[][] cells) {
        this.cells = cells;
    }
    
}
