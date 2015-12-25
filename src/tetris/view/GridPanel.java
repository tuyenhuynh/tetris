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
    
    private final int columnCount; 
    private final int rowCount; 
    
    private static final int CELL_SIZE =  30;
    private Figure activeFigure = null; 
    private List<Shape> shapes = new ArrayList<>(); 
    
    public GridPanel (int columnCount, int rowCount) {
        this.columnCount = columnCount; 
        this.rowCount = rowCount; 
        Dimension size = new Dimension(columnCount*CELL_SIZE, rowCount*CELL_SIZE);
        setMaximumSize(size);
        setMinimumSize(size);
        setPreferredSize(size);
        
    }
    
    public void updateGridStatus(GameFieldEvent event) {
        activeFigure = event.getActiveFigure(); 
        shapes = event.getShapes();
        repaint();
    }
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor((new Color(3, 5, 12)));
        for(int i = 1 ; i < columnCount; ++i ){
            g.drawLine(i*CELL_SIZE, 0, i*CELL_SIZE, rowCount*CELL_SIZE);
        }
        for(int i = 1 ; i < rowCount ; ++i) {
            g.drawLine(0, i*CELL_SIZE, columnCount*CELL_SIZE, i*CELL_SIZE);
        }
        //paint border 
        g.setColor(new Color(1, 168, 213));
        g.drawLine(0, 0, 300, 0);
        g.drawLine(0, 0, 0, 600);
        g.drawLine(299, 0, 299, 600);
        g.drawLine(0, 599, 300, 599);
        Color c = new Color (47, 79, 79);
        
        setBackground(new Color(22, 31, 74));
        if(activeFigure != null ) {
            g.setColor(activeFigure.getColor());
            DrawUtility.drawShape(g, 0, 0, CELL_SIZE, rowCount, activeFigure);
        }
        
        for(Shape shape : shapes) {
            DrawUtility.drawShape(g,0, 0, CELL_SIZE, rowCount, shape);
        }
    }
    
}
