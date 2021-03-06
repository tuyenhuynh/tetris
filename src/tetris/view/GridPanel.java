/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import tetris.model.event.GameFieldEvent;
import tetris.model.shape.Figure;
import tetris.model.shape.Shape;

/**
 *
 * @author tuyenhm
 */
public class GridPanel  extends JPanel{
    
    private int columnCount; 
    private int rowCount; 
    
    private static final int CELL_SIZE =  30;
    private Figure activeFigure = null; 
    private List<Shape> shapes = new ArrayList<>(); 
    
    public GridPanel (Dimension gridSize) {
        this.columnCount = gridSize.width; 
        this.rowCount = gridSize.height; 
        Dimension size = new Dimension(columnCount*CELL_SIZE, rowCount*CELL_SIZE);
        setMaximumSize(size);
        setMinimumSize(size);
        setPreferredSize(size);
    }
    
    //repaint game board
    public void updateGridStatus(GameFieldEvent event) {
        activeFigure = event.getActiveFigure(); 
        shapes = event.getShapes();
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //draw grid 
        g.setColor((new Color(3, 5, 12)));
        for(int i = 1 ; i < columnCount; ++i ){
            g.drawLine(i*CELL_SIZE, 0, i*CELL_SIZE, rowCount*CELL_SIZE);
        }
        for(int i = 1 ; i < rowCount ; ++i) {
            g.drawLine(0, i*CELL_SIZE, columnCount*CELL_SIZE, i*CELL_SIZE);
        }
        //draw border 
        g.setColor(new Color(1, 168, 213));
        int realWidth= CELL_SIZE* columnCount;
        int realHeight = CELL_SIZE* rowCount;
        g.drawLine(0, 0, realWidth, 0);
        g.drawLine(0, 0, 0, realHeight);
        g.drawLine(realWidth -1 , 0, realWidth - 1, realHeight);
        g.drawLine(0, realHeight -1 , realWidth,realHeight - 1);
        Color c = new Color (47, 79, 79);
        //set background
        setBackground(new Color(22, 31, 74));
                
        //draw figure 
        if(activeFigure != null ) {
            g.setColor(activeFigure.getColor());
            ShapeVisual.drawShape(g, 0, 0, CELL_SIZE, rowCount, activeFigure);
        }
        //draw bottom 
        for(Shape shape : shapes) {
            ShapeVisual.drawShape(g,0, 0, CELL_SIZE, rowCount, shape);
        }
    }
    
    @Override
    public int getWidth(){
        return columnCount*CELL_SIZE;
    }
    
    @Override 
    public int getHeight() {
        return rowCount*CELL_SIZE; 
    }
    
}
