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
    
    //shape's width
    private int width; 
    //shape's height
    private int height; 
    //shape's color
    private Color color; 
    //shape's position on game board
    Point position; 
    //cells of shapes
    int[][] cells;
    
    public void setColor(Color color) {
        this.color = color; 
    }
    
    public Color getColor() {
        return this.color;
    }
    
    //check for collision with other shape
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
    
    //get none empty cells of figures
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
    
    //get one cell base on it's coordinates
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
