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
 */
public abstract class Shape {
    
    private int width; 
    private int height; 
    
    private Color color; 
    
    Point position; 
    
    int[][] cells;
    
    public void setColor(Color color) {
        this.color = color; 
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public boolean isCollideWith (Shape other) {
        
        List <Point> myCells = findNotEmptyCells();
        List <Point> otherCells = other.findNotEmptyCells();
        for(Point cell : myCells) {
            if(otherCells.contains(cell)) {
                return true;
            }
        }
        return false;
    }
    
    //return global coordinates of not empty cells of figure
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
