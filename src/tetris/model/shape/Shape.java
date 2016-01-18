/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.model.shape;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tuyenhm
 * Abstract class Shape
 */
public abstract class Shape {
   
    private Color color; 
    /**
     * Shape's position on game board
     */
    Point position; 
    /**
     * Cells of shapes
     */
    int[][] cells;
    
    /**
     * Set color
     * @param color shape's color 
     */
    public void setColor(Color color) {
        this.color = color; 
    }
    
    /**
     * Get color
     * @return shape's color
     */
    public Color getColor() {
        return this.color;
    }
    
    /**
     * check for collision with other shape
     * @param other shape to check collision with 
     * @return true if collision occurred, otherwise return false 
     */
    public boolean isCollideWith (Shape other) {
        //get list of cells of current shape
        List <Point> myCells = findNotEmptyCells();
        //get list of cells of other shape
        List <Point> otherCells = other.findNotEmptyCells();
        //find interchange of shapes
        for(Point cell : myCells) {
            if(otherCells.contains(cell)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get none empty cells of figures
     * @return list of none empty cells
     */
    public List<Point> findNotEmptyCells () {
        
        List<Point> result = new ArrayList(); 
        
        for(int i = 0 ; i < getHeight(); ++i ) {
            for(int j = 0 ; j < getWidth(); ++j){
                if(cells[i][j] == 1) {
                    Point p = new Point(position.x + j, position.y - i);
                    result.add(p);
                }
            }
        }
        return result;
    }
    
    /**
     * Get cell base on it's position
     * @param row coordinate by y
     * @param col coordinate by x
     * @return value of given cell
     */
    public int getCellValue(int row, int col) {
        return cells[row][col];
    }
    
    /**
     * Get shape's position
     * @return shape's position
     */
    public Point getPosition() {
        return this.position;
    }
    /**
     * Set shape's position
     * @param position shape's position
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    public abstract int getWidth();
    
    public abstract int getHeight(); 
    
    /**
     * Get cells of shape
     * @return property cells
     */
    int[][] getCells() {
        return cells;
    }
}
