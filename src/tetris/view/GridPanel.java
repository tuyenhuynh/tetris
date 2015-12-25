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
    
    private final int width; 
    private final int height; 
    
    private static final int CELL_SIZE =  30;
    
    private Figure activeFigure = null; 
    
    private List<Shape> shapes = new ArrayList<>(); 
    
    public GridPanel (int width, int height) {
        this.width = width; 
        this.height = height; 
        Dimension size = new Dimension(width*CELL_SIZE, height*CELL_SIZE);
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
        //g.setColor(Color.BLUE);
        g.setColor((new Color(3, 5, 12)));
        for(int i = 1 ; i < width; ++i ){
            g.drawLine(i*CELL_SIZE, 0, i*CELL_SIZE, height*CELL_SIZE);
        }
        for(int i = 1 ; i < height ; ++i) {
            g.drawLine(0, i*CELL_SIZE, width*CELL_SIZE, i*CELL_SIZE);
        }
        //paint border 
        g.setColor(new Color(1, 168, 213));
        g.drawLine(0, 0, 300, 0);
        g.drawLine(0, 0, 0, 600);
        g.drawLine(299, 0, 299, 600);
        g.drawLine(0, 599, 300, 599);
        //#002039
        Color c = new Color (47, 79, 79);
        
        //setBackground(new Color(0, 28, 49));
        setBackground(new Color(22, 31, 74));
        if(activeFigure != null ) {
            g.setColor(activeFigure.getColor());
            drawShape(g, activeFigure);
        }
        
        for(Shape shape : shapes) {
            drawShape(g, shape);
        }
        
    }
    
    private void fillSquare(Graphics g, int x, int y, int size) {
        Color c = g.getColor();
        g.fillRect(x+1, y, size-1, size-1);
    }
    
    private void drawSquare(Graphics g, int x, int y, int size){
        
    }
    
    private void drawShape(Graphics g, Shape shape) {
        for(int i = 0 ; i < shape.getHeight() ; ++i) {
            for( int j = 0 ; j < shape.getWidth() ; ++j) {
                if(shape.getCellValue(i, j) == 1) {
                    g.setColor(shape.getColor());
                    int x = shape.getPosition().x + j ; 
                    int y = height - (shape.getPosition().y - i) -1; 
                    int realX = CELL_SIZE * x ; 
                    int realY = CELL_SIZE * y ;
                    fillSquare(g, realX , realY , CELL_SIZE);
                    g.setColor(new Color(234, 238, 239));
                    g.drawRect(realX+5, realY +4,  CELL_SIZE - 9, CELL_SIZE - 9);
                    Color c = shape.getColor(); 
                    for( int t = 0 ; t < 10 ; ++ t ) {
                       c.darker();
                    }
                    g.setColor(c);
                    fillSquare(g,realX+5, realY+6, CELL_SIZE - 10);
                }
            }
        }
    }
}
